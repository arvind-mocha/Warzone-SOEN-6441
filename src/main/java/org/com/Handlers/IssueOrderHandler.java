package org.com.Handlers;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonErrorMessages;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Orders.AdvanceOrder;
import org.com.Orders.DeployOrder;
import org.com.Orders.Order;
import org.com.Utils.HelperUtil;
import org.com.Utils.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

public class IssueOrderHandler{
    /**
     * This method marks the beginning of the game where players are asked to <b>deploy all of their armies in their own countries.</b>
     * Only after a player <b>deploys all</b> of his/her armies, the next player's turn begins.
     * @param p_gamePhaseHandler Game Phase
     * @param p_commandArray User Input Command
     * @throws Exception Invalid Deploy Command
     */
    public static void processDeployArmies(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        String l_countryName = p_commandArray[1];
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        int l_numArmies = Integer.parseInt(p_commandArray[2]);
        Country l_targetCountry = HelperUtil.getCountryByCountryName(l_countryName, l_gameMap);
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());

        Order l_deployOrder = new DeployOrder(l_currentPlayer, l_targetCountry, l_numArmies);
        l_deployOrder.isValid();
        l_currentPlayer.get_orderList().add(l_deployOrder);
        issueOrder(l_currentPlayer);
    }

    /**
     * This method processes the advance command, which moves armies from one country to another.
     * The command can either be to move armies or to commit buffered commands.
     * @param p_gamePhaseHandler The current game phase handler
     * @param p_commandArray The user input command array
     * @throws Exception If the command is invalid or an error occurs during processing
     */
    public static void processAdvanceCommand(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        Country l_sourceCountryName = HelperUtil.getCountryByCountryName(p_commandArray[1], l_gameMap);
        Country l_targetCountryName =  HelperUtil.getCountryByCountryName(p_commandArray[2], l_gameMap);
        int l_numArmies = Integer.parseInt(p_commandArray[3]);
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        Order l_advanceOrder = new AdvanceOrder(l_currentPlayer, l_sourceCountryName, l_targetCountryName, l_numArmies);
        l_advanceOrder.isValid();
        l_currentPlayer.get_orderList().add(l_advanceOrder);
        System.console().println(String.format("Order to advance %d armies from %s to %s as been saved", l_numArmies, l_sourceCountryName.getName(), l_targetCountryName.getName()));
    }

    public static void processCommitCommand(GamePhaseHandler p_gamePhaseHandler) {
        advanceTurn(p_gamePhaseHandler);
    }

    /**
     * Advances the turn to the next player.
     * @param p_gamePhaseHandler The current game phase handler
     */
    public static void advanceTurn(GamePhaseHandler p_gamePhaseHandler) {
        int l_nextPlayerIndex = (p_gamePhaseHandler.getCurrentPlayer() + 1) % p_gamePhaseHandler.getPlayerList().size();
        p_gamePhaseHandler.setCurrentPlayer(l_nextPlayerIndex);
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(l_nextPlayerIndex);
        if(l_nextPlayerIndex == 0)
        {
            for (Player l_player : p_gamePhaseHandler.getPlayerList())
            {
                List<Order> l_orderList = l_player.get_orderList();
                for(Order l_order: l_orderList)
                {
                    l_order.execute();
                }
                l_player.set_orderList(new ArrayList<>());
            }
            p_gamePhaseHandler.setTurnsCompleted(p_gamePhaseHandler.getTurnsCompleted()+1);
            System.console().println(String.format("Turn %d completed. All buffered commands as been executed", p_gamePhaseHandler.getTurnsCompleted()));
            p_gamePhaseHandler.assignReinforcements();
        }
        System.console().println(String.format(CommandOutputMessages.PLAYER_TURN_INDICATOR, l_currentPlayer.get_name(), l_currentPlayer.get_armyCount()));
    }

    public static void issueOrder(Player p_player)
    {
        List<Order> l_ordersList = p_player.get_orderList();
        if (l_ordersList.isEmpty()) {
            System.console().println(String.format(CommonErrorMessages.NO_ADVANCE_COMMAND, p_player.get_name()));
            return;
        }
        for(Order l_order:l_ordersList){
            l_order.execute();
        }
        p_player.set_orderList(new ArrayList<>());
    }
}
