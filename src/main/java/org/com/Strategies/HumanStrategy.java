package org.com.Strategies;

import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Player;

public class HumanStrategy implements Strategy{
    @Override
    public String createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        return null;
    }
}
