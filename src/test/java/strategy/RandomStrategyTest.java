package strategy;

import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Handlers.GamePhaseHandler;
import org.com.Strategies.RandomStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;


/**
 * Test class for the RandomStrategy.
 *  * This class contains the setup and tests for the AggressiveStrategy used by players in the game.
 * @author Swathi Priya P
 */
public class RandomStrategyTest {

    // Declare the necessary objects to be used in the tests
    private RandomStrategy randomStrategy;
    private GamePhaseHandler gamePhaseHandler;
    private Player currentPlayer;
    private Country strongestCountry;
    private Country targetCountry;

    // Setup method that runs before each test case
    @Before
    public void setUp() {
        // Initialize the necessary objects before each test case
        gamePhaseHandler = new GamePhaseHandler();
        randomStrategy = new RandomStrategy();
        currentPlayer = new Player("Player1");

        // Set up the strongest and target countries
        strongestCountry = new Country("StrongCountry", null, 10);
        targetCountry = new Country("TargetCountry", null, 5);

        // Add both countries to the player's list of countries
        currentPlayer.addCountry(strongestCountry);
        currentPlayer.addCountry(targetCountry);

        // Set the current player's army count
        currentPlayer.set_armyCount(5);
        strongestCountry.setArmyCount(10);
        targetCountry.setArmyCount(5);

        // Make the two countries neighbors (for possible interactions)
        strongestCountry.addNeighbourCountryId(targetCountry.getId());
    }

    // Test method to check if the RandomStrategy generates a deployment order correctly
    @Test
    public void testRandomDeploymentOrder() {
        // Simulate the RandomStrategy generating a deployment order
        List<String> orders = randomStrategy.createOrder(gamePhaseHandler, currentPlayer);

        // Check if the orders list is not null
        assertNotNull("Orders should not be null", orders);

        // Check if the orders list is not empty
        assertFalse("Orders should not be empty", orders.isEmpty());

        // Check if the generated orders contain the word "deploy"
        assertTrue("There should be a deployment order", orders.get(0).contains("deploy"));
    }
}
