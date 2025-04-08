package org.com.Strategies;

import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Player;

import java.util.List;

public class HumanStrategy implements Strategy{
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        return null;
    }
}
