package org.com.Orders;

import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdvanceOrderTest {

    private Player player;
    private Player opponent;
    private Country sourceCountry;
    private Country targetCountry;
    private AdvanceOrder advanceOrder;

    @Before
    public void setUp() {
        player = new Player("Player1");
        opponent = new Player("Opponent");

        Continent continent = new Continent("Asia", 5); // Ensure Continent instance is created

        sourceCountry = new Country("Source", continent, 1);
        targetCountry = new Country("Target", continent, 2);

        sourceCountry.setOwner(player);
        targetCountry.setOwner(opponent);
    }

    @Test
    public void testValidAdvanceOrder() throws Exception {
        sourceCountry.setArmyCount(10);
        sourceCountry.setTurnArmyCount(10);
        targetCountry.setArmyCount(5);
        sourceCountry.addNeighbourCountryId(targetCountry.getId());

        advanceOrder = new AdvanceOrder(player, sourceCountry, targetCountry, 7);
        advanceOrder.isValid(); // Should not throw exception
        advanceOrder.execute();

        assertEquals(3, sourceCountry.getArmyCount());
        assertEquals(2, targetCountry.getArmyCount()); // Attack successful
        assertEquals(player.get_name(), targetCountry.getOwner().get_name()); // Ownership should transfer
    }

}
