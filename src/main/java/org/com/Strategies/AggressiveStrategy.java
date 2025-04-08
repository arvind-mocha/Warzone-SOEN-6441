package org.com.Strategies;

import org.com.Constants.Cards;
import org.com.Constants.CommonConstants;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Orders.AdvanceOrder;
import org.com.Orders.BombOrder;
import org.com.Orders.Order;
import org.com.Utils.HelperUtil;

import java.util.List;

public class AggressiveStrategy implements Strategy {
    @Override
    public String createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        int l_availableArmies = p_currentPlayer.get_armyCount();
        if (p_currentPlayer.get_countries().isEmpty()) {
            return null;
        }

        Country l_strongestCountry = HelperUtil.getPlayerHighestArmyCountry(p_currentPlayer);
        Boolean l_hasPlayerExecutedCard = p_currentPlayer.get_orderList().stream().anyMatch(order -> order instanceof BombOrder);
        Boolean l_hasPlayerExecutedAdvance = p_currentPlayer.get_orderList().stream().anyMatch(order -> order instanceof AdvanceOrder);
        if (l_availableArmies > 0) {
            return String.format(CommonConstants.DEPLOY, l_strongestCountry.getName(), l_availableArmies);
        } else if (!l_hasPlayerExecutedCard && p_currentPlayer.get_cards().containsKey(Cards.BOMB_CARD) && !p_currentPlayer.get_countries().isEmpty()) {
            Country l_countryToBomb = HelperUtil.getStrongestCountryOtherThanCurrentPlayer(p_currentPlayer, p_gamePhaseHandler.getPlayerList());
            if (l_countryToBomb != null) {
                return String.format(CommonConstants.BOMB, l_countryToBomb.getName());
            }
        } else if(!l_hasPlayerExecutedAdvance) {
            List<Integer> l_neighbours = l_strongestCountry.getNeighbourCountryIds();
            // Attacking unowned country from the strongest country
            Country l_randomNeighbour = HelperUtil.getRandomNeighbour(l_strongestCountry, p_gamePhaseHandler.getGameMap(), p_currentPlayer);
            return String.format(CommonConstants.ADVANCE, l_strongestCountry.getName(), l_randomNeighbour.getName(), l_strongestCountry.getArmyCount() - 1);
        }
        return CommonConstants.COMMIT;
    }
}
