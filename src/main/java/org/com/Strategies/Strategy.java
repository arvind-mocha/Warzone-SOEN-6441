package org.com.Strategies;

import org.com.Constants.Cards;
import org.com.Constants.CommonConstants;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

import java.util.Random;

public interface Strategy {
    String createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer);

    default public String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard) {
        Random l_random = new Random();
        if (!p_currentPlayer.get_cards().isEmpty()) {
            String l_card = p_currentPlayer.get_cards().containsKey(p_prioritizeCard) ? p_prioritizeCard : p_currentPlayer.get_cards().keySet().iterator().next();
            switch (l_card) {
                case Cards.BOMB_CARD:
                    Country l_countryToBomb = HelperUtil.getStrongestCountryOtherThanCurrentPlayer(p_currentPlayer, p_gameManager.getPlayerList());
                    return String.format(CommonConstants.BOMB, l_countryToBomb);
                case Cards.AIRLIFT_CARD:
                    if (p_currentPlayer.get_countries().size() == 1) break;
                    Country l_randCountryFrom = p_currentPlayer.get_countries().get(l_random.nextInt(p_currentPlayer.get_countries().size()));
                    Country l_randCountryTo;
                    do {
                        l_randCountryTo = p_currentPlayer.get_countries().get(
                                l_random.nextInt(p_currentPlayer.get_countries().size()));
                    } while (l_randCountryTo != l_randCountryFrom);
                    return String.format(CommonConstants.AIRLIFT, l_randCountryFrom, l_randCountryTo, l_randCountryFrom.getArmyCount());

                case Cards.DIPLOMACY_CARD:
                    Player l_oppPlayer;
                    do {
                        l_oppPlayer = p_gameManager.getPlayerList().get(l_random.nextInt(p_gameManager.getPlayerList().size()));
                    } while (l_oppPlayer != p_currentPlayer);
                    return String.format(CommonConstants.NEGOTIATE, l_oppPlayer);

                case Cards.BLOCKADE_CARD:
                    Country l_randCountry = p_currentPlayer.get_countries().get(
                            l_random.nextInt(p_currentPlayer.get_countries().size()));
                    return String.format(CommonConstants.BLOCKADE, l_randCountry);
            }
        }
        return null;
    }
}
