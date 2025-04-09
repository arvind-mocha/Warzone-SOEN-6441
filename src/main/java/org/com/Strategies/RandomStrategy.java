package org.com.Strategies;

import org.com.Constants.CommonConstants;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Orders.AdvanceOrder;
import org.com.Utils.HelperUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements Strategy{
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        int l_availableArmies = p_currentPlayer.get_armyCount();
        if (p_currentPlayer.get_countries().isEmpty()) {
            return null;
        }

        Random l_random = new Random();
        Boolean l_hasPlayerExecutedCard = HelperUtil.hasPlayerExecutedCard(p_currentPlayer);
        Boolean l_hasPlayerExecutedAdvance = p_currentPlayer.get_orderList().stream().anyMatch(order -> order instanceof AdvanceOrder);
        if (l_availableArmies > 0) {
            int l_randomCountry = l_random.nextInt(p_currentPlayer.get_countries().size());
            return Arrays.asList(String.format(CommonConstants.DEPLOY, p_currentPlayer.get_countries().get(l_randomCountry).getName(), p_currentPlayer.get_armyCount()));
        } else if (!l_hasPlayerExecutedCard && !p_currentPlayer.get_cards().isEmpty() && !p_currentPlayer.get_countries().isEmpty()) {
            return Arrays.asList(generateCardOrder(p_gamePhaseHandler, p_currentPlayer, null));
        } else if(!l_hasPlayerExecutedAdvance) {
            List<String> commands = new ArrayList<>();
            Country l_randomCountry = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
            int l_randomArmies = Math.max(1, l_random.nextInt(l_randomCountry.getArmyCount()-1));
            List<Integer> l_neighbours = l_randomCountry.getNeighbourCountryIds();
            Country l_randomNeighbour = getRandomNeighbour(l_randomCountry, p_gamePhaseHandler.getGameMap(), p_currentPlayer);
//            commands.add(String.format(CommonConstants.ADVANCE, l_strongestCountry.getName(), l_randomNeighbour.getName(), l_strongestCountry.getArmyCount() - 1));
//            for (int l_neighborCountryID : l_neighbours) {
//                Country l_neighborCountry = p_gamePhaseHandler.getGameMap().getCountryById(l_neighborCountryID);
//                if (l_neighborCountry.getArmyCount() > 1 && p_currentPlayer.equals(l_neighborCountry.getOwner())) {
//                    commands.add(String.format(CommonConstants.ADVANCE, l_neighborCountry.getName(), l_strongestCountry.getName(), l_neighborCountry.getArmyCount() - 1));
//                }
//            }
            return commands;
        }
        return Arrays.asList(CommonConstants.COMMIT);
    }

    @Override
    public String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard) {
        return "";
    }

    @Override
    public Country getNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer) {
        return null;
    }

    public static Country getRandomNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer) {
        List<Integer> l_neighbourIds = p_country.getNeighbourCountryIds();
        Country l_selectedCountry = null;

        for (int l_neighbourId : l_neighbourIds) {
            Country l_neighbour = p_gameMap.getCountryById(l_neighbourId);
            if (l_neighbour != null) {
                int l_unownedNeighboursCount = (int) l_neighbour.getNeighbourCountryIds().stream()
                        .map(p_gameMap::getCountryById)
                        .filter(l_neighbourObj -> l_neighbourObj != null && !p_currentPlayer.equals(l_neighbourObj.getOwner()))
                        .count();
            }
        }
        return l_selectedCountry;
    }
}
