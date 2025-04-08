package org.com.Strategies;

import org.com.Constants.Cards;
import org.com.Constants.CommonConstants;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Orders.AdvanceOrder;
import org.com.Orders.BombOrder;
import org.com.Utils.HelperUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AggressiveStrategy implements Strategy {
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        int l_availableArmies = p_currentPlayer.get_armyCount();
        if (p_currentPlayer.get_countries().isEmpty()) {
            return null;
        }

        Country l_strongestCountry = HelperUtil.getPlayerHighestArmyCountry(p_currentPlayer);
        Boolean l_hasPlayerExecutedCard = HelperUtil.hasPlayerExecutedCard(p_currentPlayer);
        Boolean l_hasPlayerExecutedAdvance = p_currentPlayer.get_orderList().stream().anyMatch(order -> order instanceof AdvanceOrder);
        if (l_availableArmies > 0) {
            return Arrays.asList(String.format(CommonConstants.DEPLOY, l_strongestCountry.getName(), l_availableArmies));
        } else if (!l_hasPlayerExecutedCard && !p_currentPlayer.get_cards().isEmpty() && !p_currentPlayer.get_countries().isEmpty()) {
            return Arrays.asList(generateCardOrder(p_gamePhaseHandler, p_currentPlayer, Cards.BOMB_CARD));
        } else if(!l_hasPlayerExecutedAdvance) {
            List<String> commands = new ArrayList<>();
            List<Integer> l_neighbours = l_strongestCountry.getNeighbourCountryIds();
            // Attacking unowned country from the strongest country
            Country l_randomNeighbour = HelperUtil.getRandomNeighbour(l_strongestCountry, p_gamePhaseHandler.getGameMap(), p_currentPlayer);
            commands.add(String.format(CommonConstants.ADVANCE, l_strongestCountry.getName(), l_randomNeighbour.getName(), l_strongestCountry.getArmyCount() - 1));
            for (int l_neighborCountryID : l_neighbours) {
                Country l_neighborCountry = p_gamePhaseHandler.getGameMap().getCountryById(l_neighborCountryID);
                if (l_neighborCountry.getArmyCount() > 1 && p_currentPlayer.equals(l_neighborCountry.getOwner())) {
                    commands.add(String.format(CommonConstants.ADVANCE, l_neighborCountry.getName(), l_strongestCountry.getName(), l_neighborCountry.getArmyCount() - 1));
                }
            }
            return commands;
        }
        return Arrays.asList(CommonConstants.COMMIT);
    }
}
