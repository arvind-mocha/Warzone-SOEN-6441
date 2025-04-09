package org.com.Strategies;

import org.com.Constants.Cards;
import org.com.Constants.CommonConstants;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BenevolentStrategy implements Strategy{
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        if(l_currentPlayer.get_countries().isEmpty()) return null;

        List<Country> l_countries = l_currentPlayer.get_countries();
        Country l_weakestCountry = getWeakestCountry(l_countries);
        Random l_random = new Random();

        if(l_weakestCountry == null) return null;
        int l_availableArmies = p_currentPlayer.get_armyCount();
        if(l_currentPlayer.get_armyCount() > 0) {
            return Arrays.asList(String.format(CommonConstants.DEPLOY, l_weakestCountry.getName(), l_availableArmies));
        } else if (l_currentPlayer.get_cards().containsKey(Cards.AIRLIFT_CARD)){
            Country l_countryFrom = HelperUtil.getPlayerHighestArmyCountry(p_currentPlayer);
            return Arrays.asList(String.format(CommonConstants.AIRLIFT, l_countryFrom, l_weakestCountry));
        } else if(l_currentPlayer.get_cards().containsKey(Cards.DIPLOMACY_CARD) && !l_currentPlayer.get_countries().isEmpty()){
            Player l_oppPlayer = p_gamePhaseHandler.getPlayerList().get(l_random.nextInt(p_gamePhaseHandler.getPlayerList().size()));
            return p_currentPlayer.equals(l_oppPlayer) ? null : Arrays.asList(String.format(CommonConstants.NEGOTIATE, l_oppPlayer.get_name()));
        } else if(l_currentPlayer.get_cards().containsKey(Cards.BLOCKADE_CARD)){
            return Arrays.asList(String.format(CommonConstants.BLOCKADE, l_weakestCountry.getName()));
        } else {
            List<Integer> l_neighbours = l_weakestCountry.getNeighbourCountryIds();
            for (int l_neighborCountryID: l_neighbours) {
                Country l_neighborCountry = p_gamePhaseHandler.getGameMap().getCountryById(l_neighborCountryID);
                if(l_neighborCountry.getArmyCount() > 1 && l_currentPlayer.equals(l_neighborCountry.getOwner())) {
                    return Arrays.asList(String.format(CommonConstants.ADVANCE, l_neighborCountry, l_weakestCountry, l_neighborCountry.getArmyCount() - 1));
                }
            }
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
