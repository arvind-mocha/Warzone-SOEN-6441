package org.com.Orders;

import org.com.Constants.Cards;
import org.com.Models.Country;
import org.com.Models.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class BombOrderTest {

    private Player player;
    private Player opponent;
    private Country targetCountry;
    private BombOrder bombOrder;

    @Before
    public void setUp() {
        player = new Player("Player1");
        opponent = new Player("Opponent");
        targetCountry = new Country("Target", null, 5);
        targetCountry.setOwner(opponent);

        // Assigning a bomb card to the player
        player.get_cards().put(Cards.BOMB_CARD,1);
    }

    @Test(expected = Exception.class)
    public void testValidBombOrder() throws Exception {
        bombOrder = new BombOrder(player, targetCountry);
        bombOrder.isValid(); // Should not throw an exception
        bombOrder.execute();

        assertEquals(2, targetCountry.getArmyCount()); // Army count should be halved
        assertTrue(player.get_cards().containsKey(Cards.BOMB_CARD)); // Bomb card should be removed
    }

    @Test(expected = Exception.class)
    public void testBombWithoutCard() throws Exception {
        player.get_cards().remove(Cards.BOMB_CARD); // Remove bomb card
        bombOrder = new BombOrder(player, targetCountry);
        bombOrder.isValid(); // Should throw an exception
    }

    @Test(expected = Exception.class)
    public void testBombOwnCountry() throws Exception {
        targetCountry.setOwner(player); // Player owns the target country
        bombOrder = new BombOrder(player, targetCountry);
        bombOrder.isValid(); // Should throw an exception
    }

    @Test(expected = Exception.class)
    public void testBombNeutralCountry() throws Exception {
        targetCountry.setOwner(null); // Neutral country
        bombOrder = new BombOrder(player, targetCountry);
        bombOrder.isValid(); // Should throw an exception
    }

    @Test
    public void testBombCountryWithZeroArmies() throws Exception {
        targetCountry.setArmyCount(0); // Target country has zero armies
        bombOrder = new BombOrder(player, targetCountry);
        bombOrder.execute();
        assertEquals(0, targetCountry.getArmyCount()); // Army count should remain zero
    }
}
