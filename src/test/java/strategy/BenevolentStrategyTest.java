package strategy;

import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Handlers.GamePhaseHandler;
import org.com.Strategies.BenevolentStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;


/**
 * Test class for the BenevolentStrategy.
 * This class contains the setup and tests for the BenevolentStrategy used by players in the game.
 *
 * @author Swathi Priya P
 */
public class BenevolentStrategyTest {

    private BenevolentStrategy benevolentStrategy;
    private GamePhaseHandler gamePhaseHandler;
    private Player currentPlayer;
    private Country strongestCountry;
    private Country weakestCountry;

    @Before
    public void setUp() {
        gamePhaseHandler = new GamePhaseHandler();
        benevolentStrategy = new BenevolentStrategy();
        currentPlayer = new Player("Player1");

        // Initialize the strongest and weakest countries
        strongestCountry = new Country("StrongCountry", null, 10);  // Country with 10 armies
        weakestCountry = new Country("WeakCountry", null, 3);  // Country with 3 armies

        // Add countries to the player
        currentPlayer.addCountry(strongestCountry);
        currentPlayer.addCountry(weakestCountry);

        // Set the current player to have armies available
        currentPlayer.set_armyCount(5);
        strongestCountry.setArmyCount(10);
        weakestCountry.setArmyCount(3);
    }

    @Test
    public void testDeployToWeakestCountry() {
        // Simulating the BenevolentStrategy generating a deployment order
        List<String> orders = benevolentStrategy.createOrder(gamePhaseHandler, currentPlayer);

        // Ensure that a deployment order is generated and not empty
        assertNotNull("Orders should not be null", orders);
        assertFalse("Orders should not be empty", orders.isEmpty());

        // Verify that the deployment order is targeting the weakest country
        assertTrue("Deployment order should target the weakest country", orders.get(0).contains(weakestCountry.getName()));
    }
}
