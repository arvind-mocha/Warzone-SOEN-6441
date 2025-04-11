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

public class AggressiveStrategy implements Strategy {
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        int l_availableArmies = p_currentPlayer.get_armyCount();
        if (p_currentPlayer.get_countries().isEmpty()) {
            return null;
        }

        Country l_strongestCountry = HelperUtil.getPlayerHighestArmyCountry(p_currentPlayer);
        Boolean l_hasPlayerExecutedCard = p_currentPlayer.get_cardsExecuted();
        Boolean l_hasPlayerExecutedAdvance = p_currentPlayer.get_advanceExecuted();
        if (l_availableArmies > 0) {
            p_currentPlayer.set_advanceExecuted(false);
            p_currentPlayer.set_cardsExecuted(false);
            return Arrays.asList(String.format(CommonConstants.DEPLOY, l_strongestCountry.getName(), l_availableArmies));
        } else if (!l_hasPlayerExecutedCard && !p_currentPlayer.get_cards().isEmpty() && !p_currentPlayer.get_countries().isEmpty()) {
            return Arrays.asList(generateCardOrder(p_gamePhaseHandler, p_currentPlayer, Cards.BOMB_CARD));
        } else if(!l_hasPlayerExecutedAdvance) {
            if(l_strongestCountry.getArmyCount() <= 1){
                p_currentPlayer.set_advanceExecuted(false);
                return null;
            }
            Random l_random = new Random();
            List<String> commands = new ArrayList<>();
            List<Integer> l_neighbours = l_strongestCountry.getNeighbourCountryIds();

            // Attacking unowned country from the strongest country
            Country l_neighbour = getNeighbour(l_strongestCountry, p_gamePhaseHandler.getGameMap(), p_currentPlayer);
            commands.add(String.format(CommonConstants.ADVANCE, l_strongestCountry.getName(), l_neighbour.getName(),  l_strongestCountry.getArmyCount()-1));

            // Moving armies from owned countries to strong owned country
            for (int l_neighborCountryID : l_neighbours) {
                Country l_neighborCountry = p_gamePhaseHandler.getGameMap().getCountryById(l_neighborCountryID);
                if (l_neighborCountry.getArmyCount() > 2 && p_currentPlayer.equals(l_neighborCountry.getOwner())) {
                    if(l_neighborCountry.getArmyCount() <= 1){
                        p_currentPlayer.set_advanceExecuted(false);
                        return null;
                    }
                    System.out.println(l_neighborCountry.getArmyCount()-1);
                    commands.add(String.format(CommonConstants.ADVANCE, l_neighborCountry.getName(), l_strongestCountry.getName(), l_neighborCountry.getArmyCount()-1));
                }
            }
            p_currentPlayer.set_advanceExecuted(true);
            return commands;
        }
        return Arrays.asList(CommonConstants.COMMIT);
    }

    /**
     * Finds a neighboring country of the given country that is not owned by the current player.
     *
     * @param p_country       The country whose neighbors are being checked.
     * @param p_gameMap       The game map containing all countries and their relationships.
     * @param p_currentPlayer The current player whose ownership is being considered.
     * @return A neighboring country not owned by the current player, or null if none exist.
     */
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
//    public Country getNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer) {
//        Country l_neighbor;
//        for(Country l_country: p_currentPlayer.get_countries()){
//            for (int l_neighborID: l_country.getNeighbourCountryIds()){
//                l_neighbor = p_gameMap.getCountryById(l_neighborID);
//                if(!p_currentPlayer.equals(l_neighbor.getOwner())){
//                    return l_neighbor;
//                }
//            }
//        }
//        return null;
//    }

    /**
     * Generates a card order based on the player's available cards and prioritization.
     *
     * @param p_gameManager   The game phase handler managing the game state.
     * @param p_currentPlayer The current player for whom the card order is generated.
     * @param p_prioritizeCard The card type to prioritize if available.
     * @return A formatted string representing the card order, or null if no cards are available.
     */
    public String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard) {
        Random l_random = new Random();
        if (!p_currentPlayer.get_cards().isEmpty()) {
            p_currentPlayer.set_cardsExecuted(true);
            String l_card = p_currentPlayer.get_cards().containsKey(p_prioritizeCard) ? p_prioritizeCard : p_currentPlayer.get_cards().keySet().iterator().next();
            Country l_strongestCountry = HelperUtil.getStrongestNeighbouringEnemyCountry(p_currentPlayer, p_gameManager.getGameMap());
            switch (l_card) {
                case Cards.BOMB_CARD:
                    if (l_strongestCountry == null){
                        p_currentPlayer.set_cardsExecuted(false);
                        return null;
                    }
                    return String.format(CommonConstants.BOMB, l_strongestCountry.getName());
                case Cards.AIRLIFT_CARD:
                    if (p_currentPlayer.get_countries().size() == 1) break;
                    Country l_randCountryFrom = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
                    Country l_randCountryTo = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
                    if(l_randCountryTo == l_randCountryFrom || l_randCountryFrom.getArmyCount() < 2) {
                        p_currentPlayer.set_cardsExecuted(false);
                        return null;
                    }
                    return String.format(CommonConstants.AIRLIFT, l_randCountryFrom.getName(), l_randCountryTo.getName(), l_randCountryFrom.getArmyCount() - 1);
                case Cards.DIPLOMACY_CARD:
                    Player l_oppPlayer = p_gameManager.getPlayerList().get(l_random.nextInt(p_gameManager.getPlayerList().size()));
                    if(l_oppPlayer == p_currentPlayer){
                        p_currentPlayer.set_cardsExecuted(false);
                        return null;
                    }
                    return p_currentPlayer.equals(l_oppPlayer) ? null : String.format(CommonConstants.NEGOTIATE, l_oppPlayer.get_name());
                case Cards.BLOCKADE_CARD:
                    Country l_randCountry = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
                    return String.format(CommonConstants.BLOCKADE, l_randCountry.getName());
            }
        }
        p_currentPlayer.set_cardsExecuted(false);
        return null;
    }
}
