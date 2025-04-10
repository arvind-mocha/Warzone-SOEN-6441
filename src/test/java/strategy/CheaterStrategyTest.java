package strategy;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Handlers.GamePhaseHandler;
import org.com.Strategies.CheaterStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for the CheaterStrategy.
 * This class contains the setup and tests for the CheaterStrategy used by players in the game.
 *
 * @author Swathi Priya P
 */
public class CheaterStrategyTest {

    private CheaterStrategy cheaterStrategy;
    private GamePhaseHandler gamePhaseHandler;
    private Player currentPlayer;
    private Country strongestCountry;

    @Before
    public void setUp() {
        gamePhaseHandler = new GamePhaseHandler();
        cheaterStrategy = new CheaterStrategy();
        currentPlayer = new Player("Player1");

        // Initialize the strongest country for the player
        strongestCountry = new Country("StrongCountry", null, 10);
        currentPlayer.addCountry(strongestCountry);

        // Set the current player to have no "Bomb" cards
        currentPlayer.get_cards().clear();
    }

    @Test
    public void testCheaterGenerateCardOrderWithoutHavingCard() {
        // Simulating the cheater strategy trying to generate a card order
        String cardOrder = cheaterStrategy.generateCardOrder(gamePhaseHandler, currentPlayer, "Bomb");

        // Check that the cheater strategy generates a card order
        assertNotNull("Card order should not be null", cardOrder);
        assertTrue("Card order should be a bomb", cardOrder.contains("Bomb"));
    }
}
