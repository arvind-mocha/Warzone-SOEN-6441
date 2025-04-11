package org.com.Strategies;

import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;

import java.util.List;

/**
 * The `Strategy` interface defines the contract for implementing various game strategies.
 * Classes implementing this interface must provide logic for creating orders,
 * generating card orders, and retrieving neighboring countries during gameplay.
 *
 * @author Arvind Lakshmanan
 */
public interface Strategy {
    /**
     * Creates a list of orders for the current player during the game phase.
     *
     * @param p_gamePhaseHandler The handler managing the current game phase.
     * @param p_currentPlayer The player for whom the orders are being created.
     * @return A list of orders as strings.
     */
    List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer);

    /**
     * Generates a card order for the current player based on the prioritized card type.
     *
     * @param p_gameManager The handler managing the current game phase.
     * @param p_currentPlayer The player for whom the card order is being generated.
     * @param p_prioritizeCard The type of card to prioritize when generating the order.
     * @return A string representing the generated card order.
     */
    String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard);

    /**
     * Retrieves a neighboring country of the given country for the current player.
     *
     * @param p_country The country for which a neighbor is to be found.
     * @param p_gameMap The map of the game containing all countries and their connections.
     * @param p_currentPlayer The player for whom the neighboring country is being retrieved.
     * @return A neighboring country of the given country.
     */
    Country getNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer);
}
