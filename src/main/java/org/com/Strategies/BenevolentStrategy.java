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
import java.util.List;
import java.util.Random;

public class BenevolentStrategy implements Strategy, Serializable {
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        List<String> orders = new ArrayList<>();
        int l_availableArmies = p_currentPlayer.get_armyCount();

        if (p_currentPlayer.get_countries().isEmpty()) {
            return orders;
        }

        Country l_weakestCountry = getWeakestCountry(p_currentPlayer.get_countries());

        if (l_weakestCountry == null) {
            orders.add(CommonConstants.COMMIT);
            return orders;
        }

        if (l_availableArmies > 0) {
            p_currentPlayer.set_advanceExecuted(false);  // Reset advance execution flag
            orders.add(String.format(CommonConstants.DEPLOY, l_weakestCountry.getName(), l_availableArmies));
        }
        else if (!p_currentPlayer.get_cards().isEmpty() && !p_currentPlayer.get_cardsExecuted()) {
            List<String> cardOrders = new ArrayList<>();

            if (p_currentPlayer.get_cards().containsKey(Cards.AIRLIFT_CARD)) {
                cardOrders.add(String.format(CommonConstants.AIRLIFT, l_weakestCountry.getName(), l_weakestCountry.getName(), l_weakestCountry.getArmyCount()));
            } else if (p_currentPlayer.get_cards().containsKey(Cards.DIPLOMACY_CARD) && !p_currentPlayer.get_countries().isEmpty()) {
                Player opponent = p_gamePhaseHandler.getPlayerList().get(new Random().nextInt(p_gamePhaseHandler.getPlayerList().size()));
                cardOrders.add(String.format(CommonConstants.NEGOTIATE, opponent.get_name()));
            } else if (p_currentPlayer.get_cards().containsKey(Cards.BLOCKADE_CARD)) {
                cardOrders.add(String.format(CommonConstants.BLOCKADE, l_weakestCountry.getName()));
            }

            p_currentPlayer.set_cardsExecuted(true);
            orders.addAll(cardOrders);
        } else if (!p_currentPlayer.get_advanceExecuted()) {
            List<Integer> l_neighbours = l_weakestCountry.getNeighbourCountryIds();
            for (int l_neighborCountryID : l_neighbours) {
                Country l_neighborCountry = p_gamePhaseHandler.getGameMap().getCountryById(l_neighborCountryID);
                if (l_neighborCountry != null && l_neighborCountry.getArmyCount() > 1) {
                    orders.add(String.format(CommonConstants.ADVANCE, l_weakestCountry.getName(), l_neighborCountry.getName(), l_neighborCountry.getArmyCount() - 1));
                }
            }
            p_currentPlayer.set_advanceExecuted(true);
        }

        if (orders.isEmpty()) {
            orders.add(CommonConstants.COMMIT);
        }

        return orders;
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
                    if(l_randCountryTo == l_randomCountry){
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
