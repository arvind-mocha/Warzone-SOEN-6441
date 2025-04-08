package org.com.Strategies;

import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Player;

public interface Strategy {
    String createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer);
}
