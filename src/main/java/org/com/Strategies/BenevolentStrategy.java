package org.com.Strategies;

import org.com.Constants.Cards;
import org.com.Constants.CommonConstants;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BenevolentStrategy implements Strategy, Serializable {
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        if(l_currentPlayer.get_countries().isEmpty()) {return null;}

        List<Country> l_countries = l_currentPlayer.get_countries();
        Country l_weakestCountry = getWeakestCountry(l_countries);
        Random l_random = new Random();

        if(l_weakestCountry == null) {return null;}
        int l_availableArmies = p_currentPlayer.get_armyCount();
        if(l_availableArmies > 0) {
            return Arrays.asList(String.format(CommonConstants.DEPLOY, l_weakestCountry.getName(), l_availableArmies));
        } else if (!l_currentPlayer.get_cards().isEmpty() && !l_currentPlayer.get_cardsExecuted()) {
            List<String> l_commandArray = new ArrayList<>();
            if (l_currentPlayer.get_cards().containsKey(Cards.AIRLIFT_CARD)){
                Country l_countryFrom = HelperUtil.getPlayerHighestArmyCountry(p_currentPlayer);
                l_commandArray.add(String.format(CommonConstants.AIRLIFT, l_countryFrom.getName(), l_weakestCountry.getName(), l_countryFrom.getArmyCount()-1));
            } else if(l_currentPlayer.get_cards().containsKey(Cards.DIPLOMACY_CARD) && !l_currentPlayer.get_countries().isEmpty()){
                Player l_oppPlayer = p_gamePhaseHandler.getPlayerList().get(l_random.nextInt(p_gamePhaseHandler.getPlayerList().size()));
                l_commandArray.add(p_currentPlayer.equals(l_oppPlayer) ? null : String.format(CommonConstants.NEGOTIATE, l_oppPlayer.get_name()));
            } else if(l_currentPlayer.get_cards().containsKey(Cards.BLOCKADE_CARD)){
                l_commandArray.add(String.format(CommonConstants.BLOCKADE, l_weakestCountry.getName()));
            }
            l_currentPlayer.set_cardsExecuted(true);
            return l_commandArray;
        } else if(!l_currentPlayer.get_advanceExecuted()) {
            List<Integer> l_neighbours = l_weakestCountry.getNeighbourCountryIds();
            for (int l_neighborCountryID: l_neighbours) {
                Country l_neighborCountry = p_gamePhaseHandler.getGameMap().getCountryById(l_neighborCountryID);
                if(l_neighborCountry.getArmyCount() > 1 && l_currentPlayer.equals(l_neighborCountry.getOwner())) {
                    return Arrays.asList(String.format(CommonConstants.ADVANCE, l_neighborCountry.getName(), l_weakestCountry.getName(), l_neighborCountry.getArmyCount() - 1));
                }
            }
            l_currentPlayer.set_advanceExecuted(true);
        }

        return Arrays.asList(CommonConstants.COMMIT);
    }

    @Override
    public String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard) {
        Random l_random = new Random();
        p_currentPlayer.set_cardsExecuted(true);
        if (!p_currentPlayer.get_cards().isEmpty()) {
            String l_card = p_currentPlayer.get_cards().containsKey(p_prioritizeCard) ? p_prioritizeCard : p_currentPlayer.get_cards().keySet().iterator().next();
            Country l_strongestCountry = HelperUtil.getStrongestNeighbouringEnemyCountry(p_currentPlayer, p_gameManager.getGameMap());
            switch (l_card) {
                case Cards.BOMB_CARD:
                    return String.format(CommonConstants.BOMB, l_strongestCountry.getName());
                case Cards.AIRLIFT_CARD:
                    if (p_currentPlayer.get_countries().size() == 1) break;
                    Country l_randCountryFrom = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
                    Country l_randCountryTo = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
                    return String.format(CommonConstants.AIRLIFT, l_randCountryFrom.getName(), l_randCountryTo.getName(), l_randCountryFrom.getArmyCount());
                case Cards.DIPLOMACY_CARD:
                    Player l_oppPlayer = p_gameManager.getPlayerList().get(l_random.nextInt(p_gameManager.getPlayerList().size()));
                    return p_currentPlayer.equals(l_oppPlayer) ? null : String.format(CommonConstants.NEGOTIATE, l_oppPlayer.get_name());
                case Cards.BLOCKADE_CARD:
                    Country l_randCountry = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
                    return String.format(CommonConstants.BLOCKADE, l_randCountry.getName());
            }
        }
        return null;
    }

    @Override
    public Country getNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer) {
        return null;
    }

    /**
     * Iterates through the countries and finds the country with the least deployed armies
     * @param p_countryList List of player owned countries
     * @return {@link Country} with the least deployed armies
     */
    private Country getWeakestCountry(List<Country> p_countryList) {
        Country l_weakestCountry = null;
        int l_lowestArmies = Integer.MAX_VALUE;
        for (Country l_country : p_countryList) {
            if (l_country.getArmyCount() < l_lowestArmies) {
                l_weakestCountry = l_country;
                l_lowestArmies = l_country.getArmyCount();
            }
        }
        return l_weakestCountry;
    }
}
