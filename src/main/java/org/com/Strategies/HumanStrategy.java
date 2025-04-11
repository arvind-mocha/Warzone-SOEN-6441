package org.com.Strategies;

import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;

import java.io.Serializable;
import java.util.List;

/**
 * The `HumanStrategy` class represents the behavior of a human player in the game.
 * This strategy allows the player to manually decide their actions during the game.
 * It implements the `Strategy` interface and is `Serializable` for potential persistence.
 *
 * @author Arvind Lakshmanan
 */
public class HumanStrategy implements Strategy, Serializable {

    /**
     * Creates an order for the human player during their turn.
     *
     * @param p_gamePhaseHandler The handler for the current game phase.
     * @param p_currentPlayer    The player for whom the order is being created.
     * @return A list of strings representing the order created by the player.
     */
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        return null;
    }

    /**
     * Generates a card order for the human player based on their prioritization.
     *
     * @param p_gameManager    The handler for the current game phase.
     * @param p_currentPlayer  The player for whom the card order is being generated.
     * @param p_prioritizeCard The card type that the player wants to prioritize.
     * @return A string representing the generated card order.
     */
    @Override
    public String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard) {
        return "";
    }

    /**
     * Retrieves a neighboring country of the specified country for the current player.
     *
     * @param p_country       The country for which a neighbor is to be found.
     * @param p_gameMap       The map of the game containing all countries and their connections.
     * @param p_currentPlayer The player for whom the neighbor is being retrieved.
     * @return A `Country` object representing the neighboring country, or `null` if no neighbor is found.
     */
    @Override
    public Country getNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer) {
        return null;
    }
}
