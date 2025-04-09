package org.com.Strategies;

import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;

import java.util.List;

public interface Strategy {
    List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer);

    String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard);

    Country getNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer);
}
