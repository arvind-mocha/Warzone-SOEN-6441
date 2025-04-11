package org.com.Strategies;

import org.com.Constants.Cards;
import org.com.Constants.CommonConstants;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
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
        Boolean l_hasPlayerExecutedCard = p_currentPlayer.get_cardsExecuted();
        Boolean l_hasPlayerExecutedAdvance = p_currentPlayer.get_advanceExecuted();

        Country l_randomCountry = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));

        if (l_availableArmies > 0) {
            p_currentPlayer.set_advanceExecuted(false);
            p_currentPlayer.set_cardsExecuted(false);
            return Arrays.asList(String.format(CommonConstants.DEPLOY, l_randomCountry.getName(), p_currentPlayer.get_armyCount()));
        } else if (!l_hasPlayerExecutedCard && !p_currentPlayer.get_cards().isEmpty() && !p_currentPlayer.get_countries().isEmpty()) {
            return Arrays.asList(generateCardOrder(p_gamePhaseHandler, p_currentPlayer, null));
        } else if(!l_hasPlayerExecutedAdvance) {
            if(l_randomCountry.getArmyCount() <= 1){
                return null;
            }
            List<String> commands = new ArrayList<>();
            Country l_randomNeighbour = getNeighbour(l_randomCountry, p_gamePhaseHandler.getGameMap(), p_currentPlayer);
            if(l_randomNeighbour != null) {
                int l_randomArmies = Math.max(1, l_random.nextInt(Math.max(l_randomCountry.getArmyCount(), 1)));
                commands.add(String.format(CommonConstants.ADVANCE, l_randomCountry.getName(), l_randomNeighbour.getName(), l_randomArmies));
                p_currentPlayer.set_advanceExecuted(true);
            }
            return commands;
        }
        return Arrays.asList(CommonConstants.COMMIT);
    }

    @Override
    public String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard) {
        Random l_random = new Random();
        if (!p_currentPlayer.get_cards().isEmpty()) {
            p_currentPlayer.set_cardsExecuted(true);
            String l_card = p_currentPlayer.get_cards().containsKey(p_prioritizeCard) ? p_prioritizeCard : p_currentPlayer.get_cards().keySet().iterator().next();
            Country l_randomCountry = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
            int l_randomArmies = Math.max(1, l_random.nextInt(Math.max(l_randomCountry.getArmyCount(), 1)));
            switch (l_card) {
                case Cards.BOMB_CARD:
                    Country l_randomEnemyCountry = HelperUtil.getRandomNeighbouringEnemyCountry(l_randomCountry, p_gameManager.getGameMap(), p_currentPlayer);
                    if (l_randomEnemyCountry == null){
                        p_currentPlayer.set_cardsExecuted(false);
                        return null;
                    }
                    return String.format(CommonConstants.BOMB, l_randomEnemyCountry.getName());
                case Cards.AIRLIFT_CARD:
                    if (p_currentPlayer.get_countries().size() < 2) break;
                    Country l_randCountryTo = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
                    if(l_randCountryTo == l_randomCountry || l_randomCountry.getArmyCount() < 2){
                        p_currentPlayer.set_cardsExecuted(false);
                        return null;
                    }
                    return String.format(CommonConstants.AIRLIFT, l_randomCountry.getName(), l_randCountryTo.getName(), l_randomArmies);
                case Cards.DIPLOMACY_CARD:
                    Player l_oppPlayer = p_gameManager.getPlayerList().get(l_random.nextInt(p_gameManager.getPlayerList().size()));
                    if(l_oppPlayer == p_currentPlayer){
                        p_currentPlayer.set_cardsExecuted(false);
                        return null;
                    }
                    return String.format(CommonConstants.NEGOTIATE, l_oppPlayer.get_name());
                case Cards.BLOCKADE_CARD:
                    return String.format(CommonConstants.BLOCKADE, l_randomCountry.getName());
            }
        }
        return null;
    }

    @Override
    public Country getNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer) {
        List<Country> l_neighbouringCountries = new ArrayList<>();
        for (int l_neighborID : p_country.getNeighbourCountryIds()) {
            Country l_neighbor = p_gameMap.getCountryById(l_neighborID);
//            if (l_neighbor != null && !p_currentPlayer.equals(l_neighbor.getOwner())) {
            if (l_neighbor != null){
                l_neighbouringCountries.add(l_neighbor);
            }
        }
        if (l_neighbouringCountries.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return l_neighbouringCountries.get(random.nextInt(l_neighbouringCountries.size()));
    }
}
