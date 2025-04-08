package org.com.Strategies;

import org.com.Constants.Cards;
import org.com.Constants.CommonConstants;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

import java.util.List;
import java.util.Random;

public interface Strategy {
    List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer);

    /**
     * Generates a card order based on the player's available cards and prioritization.
     *
     * @param p_gameManager   The game phase handler managing the game state.
     * @param p_currentPlayer The current player for whom the card order is generated.
     * @param p_prioritizeCard The card type to prioritize if available.
     * @return A formatted string representing the card order, or null if no cards are available.
     */
    default public String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard) {
        Random l_random = new Random();
        if (!p_currentPlayer.get_cards().isEmpty()) {
            String l_card = p_currentPlayer.get_cards().containsKey(p_prioritizeCard) ? p_prioritizeCard : p_currentPlayer.get_cards().keySet().iterator().next();
            switch (l_card) {
                case Cards.BOMB_CARD:
                    Country l_countryToBomb = HelperUtil.getStrongestCountryOtherThanCurrentPlayer(p_currentPlayer, p_gameManager.getPlayerList());
                    return String.format(CommonConstants.BOMB, l_countryToBomb.getName());
                case Cards.AIRLIFT_CARD:
                    if (p_currentPlayer.get_countries().size() == 1) break;
                    Country l_randCountryFrom = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
                    Country l_randCountryTo;
                    do {
                        l_randCountryTo = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
                    } while (l_randCountryTo != l_randCountryFrom);
                    return String.format(CommonConstants.AIRLIFT, l_randCountryFrom.getName(), l_randCountryTo.getName(), l_randCountryFrom.getArmyCount());
                case Cards.DIPLOMACY_CARD:
                    Player l_oppPlayer;
                    do {
                        l_oppPlayer = p_gameManager.getPlayerList().get(l_random.nextInt(p_gameManager.getPlayerList().size()));
                    } while (l_oppPlayer != p_currentPlayer);
                    return String.format(CommonConstants.NEGOTIATE, l_oppPlayer);
                case Cards.BLOCKADE_CARD:
                    Country l_randCountry = p_currentPlayer.get_countries().get(
                            l_random.nextInt(p_currentPlayer.get_countries().size()));
                    return String.format(CommonConstants.BLOCKADE, l_randCountry.getName());
            }
        }
        return null;
    }
}
