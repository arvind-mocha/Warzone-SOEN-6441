package strategy;

import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Handlers.GamePhaseHandler;
import org.com.Strategies.HumanStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the HumanStrategy.
 * This class contains the setup and tests for the HumanStrategy used by players in the game.
 *
 * @author Swathi Priya P
 */
public class HumanStrategyTest {

    private HumanStrategy humanStrategy;
    private GamePhaseHandler gamePhaseHandler;
    private Player currentPlayer;
    private Country country;

    @Before
    public void setUp() {
        gamePhaseHandler = new GamePhaseHandler();
        humanStrategy = new HumanStrategy();
        currentPlayer = new Player("Player1");

        // Initialize a country for the player
        country = new Country("Country1", null, 10);
        currentPlayer.addCountry(country);
    }

    @Test
    public void testCreateOrder() {
        // Simulating the scenario where a human player is expected to provide input
        List<String> orders = humanStrategy.createOrder(gamePhaseHandler, currentPlayer);

        // Check that the order is null or empty
        assertNull("Orders should be null", orders);
    }

    @Test
    public void testGenerateCardOrder() {
        // Simulating the scenario where a human player is expected to generate a card order manually
        String cardOrder = humanStrategy.generateCardOrder(gamePhaseHandler, currentPlayer, "Bomb");

        // Check that the card order is empty string as human player will decide the card order manually
        assertEquals("Card order should be an empty string", "", cardOrder);
    }

    @Test
    public void testGetNeighbourWithNoValidNeighbours() {
        // Simulating the scenario where the player has no neighboring countries
        country.setNeighbourCountryIds(new ArrayList<>());

        // Simulate the human strategy trying to get a neighbor
        Country neighbour = humanStrategy.getNeighbour(country, gamePhaseHandler.getGameMap(), currentPlayer);

        // Assert that no neighbor is found
        assertNull("There should be no valid neighbor", neighbour);
    }
}
