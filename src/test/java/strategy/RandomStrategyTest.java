package strategy;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Handlers.GamePhaseHandler;
import org.com.Strategies.RandomStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class RandomStrategyTest {

    private RandomStrategy randomStrategy;
    private GamePhaseHandler gamePhaseHandler;
    private Player currentPlayer;
    private Country strongestCountry;
    private Country targetCountry;

    @Before
    public void setUp() {
        gamePhaseHandler = new GamePhaseHandler();
        randomStrategy = new RandomStrategy();
        currentPlayer = new Player("Player1");

        // Set up the strongest and target countries
        strongestCountry = new Country("StrongCountry", null, 10);  // Country with 10 armies
        targetCountry = new Country("TargetCountry", null, 5);  // Country with 5 armies

        // Add countries to the player
        currentPlayer.addCountry(strongestCountry);
        currentPlayer.addCountry(targetCountry);

        // Set the current player to have armies
        currentPlayer.set_armyCount(5);  // Player has 5 armies available for deployment
        strongestCountry.setArmyCount(10);  // Country with 10 armies
        targetCountry.setArmyCount(5);  // Country with 5 armies

        // Make the countries neighbors
        strongestCountry.addNeighbourCountryId(targetCountry.getId());
    }

    @Test
    public void testRandomDeploymentOrder() {
        // Simulate the RandomStrategy generating a deployment order
        List<String> orders = randomStrategy.createOrder(gamePhaseHandler, currentPlayer);

        // Check that orders are generated and not null
        assertNotNull("Orders should not be null", orders);
        assertFalse("Orders should not be empty", orders.isEmpty());

        // The orders should contain a deployment order
        assertTrue("There should be a deployment order", orders.get(0).contains("deploy"));
    }
}
