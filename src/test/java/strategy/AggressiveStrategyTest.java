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

public class AggressiveStrategyTest {

    private AggressiveStrategy aggressiveStrategy;
    private GamePhaseHandler gamePhaseHandler;
    private Player currentPlayer;
    private Country strongestCountry;

    @Before
    public void setUp() {
        gamePhaseHandler = new GamePhaseHandler();
        aggressiveStrategy = new AggressiveStrategy();

        // Initialize the current player and map
        currentPlayer = new Player("Player1");

        // Initialize the strongest country and target country
        strongestCountry = new Country("StrongCountry", null, 10); // Country with 10 armies
        Country targetCountry = new Country("TargetCountry", null, 5); // Country with 5 armies

        // Add countries to the current player
        currentPlayer.addCountry(strongestCountry);
        currentPlayer.addCountry(targetCountry);

        // Add the current player to the game phase handler
        gamePhaseHandler.setPlayerList(List.of(currentPlayer));  // Ensure currentPlayer is in the player list

        // Add the countries to the game map (using MapOperationsHandler or any method you have)
        gamePhaseHandler.setGameMap(new Map());
        MapOperationsHandler.editMap(gamePhaseHandler, "europe.map");  // Assuming europe.map exists

        // Set the ownership of the countries
        strongestCountry.setOwner(currentPlayer);
        targetCountry.setOwner(currentPlayer);
        strongestCountry.addNeighbourCountryId(targetCountry.getId()); // Make them neighbors
    }

    @Test
    public void testDeployOrder() {
        currentPlayer.set_armyCount(5);  // Player has 5 armies
        strongestCountry.setArmyCount(10);  // Country has 10 armies

        // Simulating the deployment of armies
        List<String> orders = aggressiveStrategy.createOrder(gamePhaseHandler, currentPlayer);

        assertNotNull("Orders should not be null", orders);
        assertTrue("There should be a deployment order", orders.get(0).contains("deploy"));
        assertTrue("Deployment order should be for strongest country", orders.get(0).contains(strongestCountry.getName()));
    }
}
