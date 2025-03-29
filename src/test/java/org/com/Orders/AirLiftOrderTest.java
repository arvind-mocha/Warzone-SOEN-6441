package org.com.Orders;

import org.com.Constants.Cards;
import org.com.Models.Country;
import org.com.Models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AirLiftOrderTest {

    private Player player;
    private Country sourceCountry;
    private Country targetCountry;
    private AirLiftOrder airLiftOrder;

    @Before
    public void setUp() {
        player = new Player("Player1");
        sourceCountry = new Country("Source", null, 10);
        targetCountry = new Country("Target", null, 5);

        sourceCountry.setOwner(player);
        targetCountry.setOwner(player);

        player.get_cards().put(Cards.AIRLIFT_CARD, 1); // Giving player an airlift card
    }

    @Test
    public void testValidAirliftOrder() throws Exception {
        airLiftOrder = new AirLiftOrder(player, sourceCountry, targetCountry, 5);
        airLiftOrder.isValid(); // Should not throw exception
        airLiftOrder.execute();

        assertEquals(5, sourceCountry.getArmyCount());
        assertEquals(10, targetCountry.getArmyCount());
    }

    @Test
    public void testAirliftWithoutOwnership() throws Exception {
        sourceCountry.setOwner(new Player("Opponent"));
        airLiftOrder = new AirLiftOrder(player, sourceCountry, targetCountry, 5);
        airLiftOrder.isValid();
    }

    @Test
    public void testAirliftToEnemyCountry() throws Exception {
        targetCountry.setOwner(new Player("Opponent"));
        airLiftOrder = new AirLiftOrder(player, sourceCountry, targetCountry, 5);
        airLiftOrder.isValid();
    }

    @Test(expected = Exception.class)
    public void testAirliftWithInsufficientArmies() throws Exception {
        airLiftOrder = new AirLiftOrder(player, sourceCountry, targetCountry, 15);
        airLiftOrder.isValid();
    }

    @Test(expected = Exception.class)
    public void testAirliftAllArmies() throws Exception {
        airLiftOrder = new AirLiftOrder(player, sourceCountry, targetCountry, 10);
        airLiftOrder.isValid();
    }
}
