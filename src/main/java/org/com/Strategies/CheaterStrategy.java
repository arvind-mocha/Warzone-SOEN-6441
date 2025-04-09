package org.com.Strategies;

import org.com.GameLog.LogManager;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;

import java.util.List;

public class CheaterStrategy implements Strategy {
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        if (l_currentPlayer.get_countries().isEmpty()) return null;

        for (Country l_country : l_currentPlayer.get_countries()) {
            for (int l_neighbourCountryId : l_country.getNeighbourCountryIds()) {
                Country l_neighbour = p_gamePhaseHandler.getGameMap().getCountryById(l_neighbourCountryId);
                if (l_neighbour.getOwner() != l_currentPlayer)
                    l_neighbour.getOwner().get_countries().remove(l_neighbour);
                l_neighbour.setOwner(l_currentPlayer);
                LogManager.logAction("CountryID " + l_neighbour.getId() + " conquered by " + l_currentPlayer.get_name());
            }
        }
        for (Country l_country : l_currentPlayer.get_countries()) {
            for (int l_neighbourCountryId : l_country.getNeighbourCountryIds()) {
                Country l_neighbour = p_gamePhaseHandler.getGameMap().getCountryById(l_neighbourCountryId);
                if (l_neighbour.getOwner() != l_currentPlayer) {
                    l_country.setArmyCount(2 * l_country.getArmyCount());
                    break;
                }
            }
        }
        return null;
    }

    @Override
    public String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard) {
        return "";
    }

    @Override
    public Country getNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer) {
        return null;
    }
}
