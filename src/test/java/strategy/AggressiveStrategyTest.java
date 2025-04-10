package strategy;

import org.com.Handlers.MapOperationsHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Handlers.GamePhaseHandler;
import org.com.Strategies.AggressiveStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

/**
 * Test class for the AggressiveStrategy.
 * This class contains the setup and tests for the AggressiveStrategy used by players in the game.
 *
 * @author Swathi Priya P
 */
public class AggressiveStrategyTest {

    private AggressiveStrategy aggressiveStrategy;  // The strategy being tested
    private GamePhaseHandler gamePhaseHandler;  // The game phase handler to manage game state
    private Player currentPlayer;  // The current player in the game
    private Country strongestCountry;  // The strongest country owned by the current player

    /**
     * Setup method to initialize objects before each test.
     * This method prepares the necessary objects (player, countries, game phase handler) for the tests.
     */
    @Before
    public void setUp() {
        gamePhaseHandler = new GamePhaseHandler();
        aggressiveStrategy = new AggressiveStrategy();

        // Initialize the current player and map
        currentPlayer = new Player("Player1");

        // Initialize the strongest country and target country
        strongestCountry = new Country("StrongCountry", null, 10);
        Country targetCountry = new Country("TargetCountry", null, 5);

        // Add countries to the current player
        currentPlayer.addCountry(strongestCountry);
        currentPlayer.addCountry(targetCountry);

        // Add the current player to the game phase handler
        gamePhaseHandler.setPlayerList(List.of(currentPlayer));

        // Add the countries to the game map
        gamePhaseHandler.setGameMap(new Map());
        MapOperationsHandler.editMap(gamePhaseHandler, "europe.map");

        // Set the ownership of the countries
        strongestCountry.setOwner(currentPlayer);
        targetCountry.setOwner(currentPlayer);
        strongestCountry.addNeighbourCountryId(targetCountry.getId()); // Make them neighbors
    }

    /**
     * Test case to verify deployment order generation by the AggressiveStrategy.
     * This test checks if the strategy generates a deployment order when the player has armies.
     */
    @Test
    public void testDeployOrder() {
        currentPlayer.set_armyCount(5);
        strongestCountry.setArmyCount(10);

        // Simulating the deployment of armies
        List<String> orders = aggressiveStrategy.createOrder(gamePhaseHandler, currentPlayer);

        // Assertions to verify the deployment order
        assertNotNull("Orders should not be null", orders);
        assertTrue("There should be a deployment order", orders.get(0).contains("deploy"));
        assertTrue("Deployment order should be for strongest country", orders.get(0).contains(strongestCountry.getName()));
    }
}
