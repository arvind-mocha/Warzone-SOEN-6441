package org.com.Strategies;

import org.com.GameLog.LogManager;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The `CheaterStrategy` class implements the behavior of a cheater player in the game.
 * This strategy automatically conquers all neighboring enemy countries and doubles the armies
 * in its owned countries after each turn. It adheres to the `Strategy` interface and is
 * `Serializable` for potential persistence.
 *
 * @author Arvind Lakshmanan
 */
public class CheaterStrategy implements Strategy, Serializable {

    /**
     * Creates orders for the cheater strategy during the game phase.
     * This method conquers all neighboring enemy countries and doubles the armies
     * in the owned countries of the current player.
     *
     * @param p_gamePhaseHandler The game phase handler managing the game state.
     * @param p_currentPlayer    The current player executing the strategy.
     * @return A list of orders as strings, or null if no orders are created.
     */
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        if (l_currentPlayer.get_countries().isEmpty()) return null;
        boolean l_conquredEnemy = false;
        List<Country> originalCountries = new ArrayList<>(l_currentPlayer.get_countries());
        for (Country l_country : originalCountries) {
            for (int l_neighbourCountryId : l_country.getNeighbourCountryIds()) {
                Country l_neighbour = p_gamePhaseHandler.getGameMap().getCountryById(l_neighbourCountryId);
                if (l_neighbour.getOwner() != l_currentPlayer && !l_neighbour.isCountryNeutral()) {
                    l_neighbour.getOwner().get_countries().remove(l_neighbour);
                    l_neighbour.setOwner(l_currentPlayer);
                    l_currentPlayer.addCountry(l_neighbour);
                    l_conquredEnemy = true;
                    LogManager.logAction("CountryID " + l_neighbour.getId() + " conquered by " + l_currentPlayer.get_name());
                }
            }
            if (l_conquredEnemy) {
                l_country.setArmyCount(2 * l_country.getArmyCount());
                l_conquredEnemy = false;
            }
        }
        return null;
    }

    /**
     * Generates a card order for the cheater strategy.
     * This method prioritizes the specified card type and assumes the player can always play a "Bomb" card.
     * If the prioritized card is "Bomb", it returns a "Bomb" card order with the available count.
     * For unsupported card types, it returns an empty string.
     *
     * @param p_gameManager    The game phase handler managing the game state.
     * @param p_currentPlayer  The current player executing the strategy.
     * @param p_prioritizeCard The card type to prioritize.
     * @return A string representing the card order, or an empty string for unsupported card types.
     */
    @Override
    public String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard) {
        // In CheaterStrategy, we assume the player can always play a Bomb card
        // Regardless of whether the player actually has the card, we return a "Bomb" card order
        if ("Bomb".equals(p_prioritizeCard)) {
            return "Bomb " + p_currentPlayer.get_cards().getOrDefault("Bomb", 0);  // Return Bomb order with available count
        }

        // You can add similar checks for other cards (e.g., Airlift, Blockade) if needed

        return "";  // Return empty string for unsupported card types
    }

    /**
     * Retrieves a neighboring country of the given country.
     * This method is currently not implemented and returns null.
     *
     * @param p_country       The country for which a neighbor is to be retrieved.
     * @param p_gameMap       The game map containing all countries.
     * @param p_currentPlayer The current player executing the strategy.
     * @return A neighboring country, or null if not implemented.
     */
    @Override
    public Country getNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer) {
        return null;
    }
}
