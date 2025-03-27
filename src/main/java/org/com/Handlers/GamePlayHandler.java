package org.com.Handlers;

import org.com.Constants.Cards;
import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonErrorMessages;
import org.com.Models.Player;
import org.com.Orders.AdvanceOrder;
import org.com.Orders.DeployOrder;
import org.com.Orders.Order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * Gameplay related codes are present in this class
 *
 * @author Arvind Nachiappan
 */
public class GamePlayHandler {
    /**
     * Advances the turn to the next player.
     * @param p_gamePhaseHandler The current game phase handler
     */
    public static void advanceTurn(GamePhaseHandler p_gamePhaseHandler) {
        int l_nextPlayerIndex = (p_gamePhaseHandler.getCurrentPlayer() + 1) % p_gamePhaseHandler.getPlayerList().size();
        p_gamePhaseHandler.setCurrentPlayer(l_nextPlayerIndex);
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(l_nextPlayerIndex);

        if (l_nextPlayerIndex == 0) {
            for (Player l_player : p_gamePhaseHandler.getPlayerList()) {
                int l_numberOfCountriesBeforeExecution = l_player.get_countries().size();

                //
                List<Order> l_orderList = l_player.get_orderList();
                for (Order l_order : l_orderList) {
                    l_order.execute();
                }
                //

                issueCards(l_player, l_numberOfCountriesBeforeExecution);
                l_player.set_orderList(new ArrayList<>());
            }
            p_gamePhaseHandler.setTurnsCompleted(p_gamePhaseHandler.getTurnsCompleted() + 1);
            System.console().println(String.format("Turn %d completed. All buffered commands have been executed", p_gamePhaseHandler.getTurnsCompleted()));
            p_gamePhaseHandler.assignReinforcements();
        }

        System.console().println(String.format(CommandOutputMessages.PLAYER_TURN_INDICATOR, l_currentPlayer.get_name(), l_currentPlayer.get_armyCount(), l_currentPlayer.get_cards().toString()));
    }

    /**
     * Issues orders for the given player.
     * @param p_player The player for whom the orders are issued
     */
    public static void issueOrder(Player p_player)
    {
        List<Order> l_ordersList = p_player.get_orderList();
        if (l_ordersList.isEmpty()) {
            System.console().println(String.format(CommonErrorMessages.NO_ADVANCE_COMMAND, p_player.get_name()));
            return;
        }


        Order l_order = p_player.nextOrder();

        if(l_order instanceof DeployOrder){
            l_order.execute();
        }

        else {
            List<Order> l_cardPlays = new ArrayList<>();
            List<Order> l_advancePlays = new ArrayList<>();
            while (l_order != null) {
                if (l_order instanceof AdvanceOrder) {
                    l_advancePlays.add(l_order);
                } else {
                    l_cardPlays.add(l_order);
                }
                l_order = p_player.nextOrder();
            }

            l_cardPlays.forEach(Order::execute);
            l_advancePlays.forEach(Order::execute);

        }
//        l_ordersList.stream()
//                .filter(order -> !(order instanceof AdvanceOrder))
//                .forEach(Order::execute);
//
//        l_ordersList.stream()
//                .filter(order -> order instanceof AdvanceOrder)
//                .forEach(Order::execute);
//        p_player.set_negotiationPlayer(new HashSet<>());
//        p_player.set_orderList(new ArrayList<>());
    }

    /**
     * Issues cards to the player if they have conquered new countries.
     * @param p_player The player to whom the cards are issued
     * @param p_initialNumberOfCountries The number of countries the player had before executing orders
     */
    public static void issueCards(Player p_player, int p_initialNumberOfCountries)
    {
        int l_numberOfCountriesAfterExecution = p_player.get_countries().size();
        if (l_numberOfCountriesAfterExecution > p_initialNumberOfCountries) {
            String l_randomCard = Cards.getRandomCard();
            p_player.get_cards().merge(l_randomCard, 1, Integer::sum);
        }
    }
}
