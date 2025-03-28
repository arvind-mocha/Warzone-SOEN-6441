package org.com.Orders;

import org.com.Constants.Cards;
import org.com.Models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiplomacyOrderTest {

    private Player player;
    private Player targetPlayer;
    private DiplomacyOrder diplomacyOrder;

    @Before
    public void setUp() {
        player = new Player("Player1");
        targetPlayer = new Player("Player2");

        // Give the player a diplomacy card
        player.get_cards().put(Cards.DIPLOMACY_CARD,1);
    }

    @Test
    public void testValidDiplomacyOrder() throws Exception {
        diplomacyOrder = new DiplomacyOrder(player, targetPlayer);
        diplomacyOrder.isValid(); // Should not throw exception
        diplomacyOrder.execute();

        assertTrue(player.get_negotiationPlayer().contains(targetPlayer));
        assertTrue(targetPlayer.get_negotiationPlayer().contains(player));
        assertEquals((Integer)0, player.get_cards().getOrDefault(Cards.DIPLOMACY_CARD, 0));
    }

    @Test(expected = Exception.class)
    public void testDiplomacyWithoutCard() throws Exception {
        player.get_cards().clear(); // Remove all cards
        diplomacyOrder = new DiplomacyOrder(player, targetPlayer);
        diplomacyOrder.isValid();
    }

    @Test(expected = Exception.class)
    public void testDiplomacyWithNullTarget() throws Exception {
        diplomacyOrder = new DiplomacyOrder(player, null);
        diplomacyOrder.isValid();
    }

    @Test(expected = Exception.class)
    public void testMultiplePowerCardsInTurn() throws Exception {
        Order bombOrder = new BombOrder(player, targetPlayer.get_countries().getFirst());
        player.set_cardPlayedInTurn(bombOrder);
        diplomacyOrder = new DiplomacyOrder(player, targetPlayer);
        diplomacyOrder.isValid();
    }
}
