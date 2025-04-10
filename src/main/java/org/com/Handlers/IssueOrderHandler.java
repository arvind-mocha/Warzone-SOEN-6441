package org.com.Handlers;

import org.com.GameLog.LogManager;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Orders.*;
import org.com.Utils.HelperUtil;

import java.io.Serializable;

/**
 *
 * Order related functions are present in this class
 *
 * @author Arvind Nachiappan
 * @author Devasenan Murugan
 */
public class IssueOrderHandler implements Serializable {
    /**
     * This method marks the beginning of the game where players are asked to <b>deploy all of their armies in their own countries.</b>
     * Only after a player <b>deploys all</b> of his/her armies, the next player's turn begins.
     *
     * @param p_gamePhaseHandler Game Phase
     * @param p_commandArray     User Input Command
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
        l_deployOrder.execute();
        l_currentPlayer.get_orderList().remove(l_deployOrder);
    }


    /**
     * This method processes the advance command, which moves armies from one country to another.
     * The command can either be to move armies or to commit buffered commands.
     *
     * @param p_gamePhaseHandler The current game phase handler
     * @param p_commandArray     The user input command array
     * @throws Exception If the command is invalid or an error occurs during processing
     */
    public static void processAdvanceCommand(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        Country l_sourceCountry = HelperUtil.getCountryByCountryName(p_commandArray[1], l_gameMap);
        Country l_targetCountry = HelperUtil.getCountryByCountryName(p_commandArray[2], l_gameMap);
        int l_numArmies = Integer.parseInt(p_commandArray[3]);
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        Order l_advanceOrder = new AdvanceOrder(l_currentPlayer, l_sourceCountry, l_targetCountry, l_numArmies);
        l_advanceOrder.isValid();
        l_currentPlayer.get_orderList().add(l_advanceOrder);
        System.out.println(String.format("Order to advance %d armies from %s to %s has been saved", l_numArmies, l_sourceCountry.getName(), l_targetCountry.getName()));
        LogManager.logAction(String.format("Order to advance %d armies from %s to %s has been saved", l_numArmies, l_sourceCountry.getName(), l_targetCountry.getName()));
    }

    public static void processCommitCommand(GamePhaseHandler p_gamePhaseHandler) throws Exception {
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        if (l_currentPlayer.get_armyCount() > 0) {
            throw new Exception(String.format("Deploy all armies to commit your changes. Remaining deployable armies: %s", l_currentPlayer.get_armyCount()));
        }
        GamePlayHandler.advanceTurn(p_gamePhaseHandler);
    }

    /**
     * This method processes the commit command, which finalizes the player's turn.
     * It ensures that all armies are deployed before committing the changes.
     *
     * @param p_gamePhaseHandler The current game phase handler
     * @throws Exception If there are remaining deployable armies or an error occurs during processing
     */
    public static void processBlockadeCommand(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        Country l_country = HelperUtil.getCountryByCountryName(p_commandArray[1], l_gameMap);
        Player l_player = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        Order l_blockadeOrder = new BlockadeOrder(l_country, l_player);
        l_blockadeOrder.isValid();
        l_player.set_cardPlayedInTurn(l_blockadeOrder);
        l_player.get_orderList().add(l_blockadeOrder);
        System.out.println(String.format("Order to blockade country %s has been saved", l_country.getName()));
        LogManager.logAction(String.format("Order to blockade country %s has been saved", l_country.getName()));
    }

    /**
     * This method processes the negotiate command, which allows a player to negotiate peace with another player.
     *
     * @param p_gamePhaseHandler The current game phase handler
     * @param p_commandArray     The user input command array
     * @throws Exception If the command is invalid or an error occurs during processing
     */
    public static void processNegotiateCommand(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        Player l_targetPlayer = HelperUtil.getPlayerByName(p_commandArray[1], p_gamePhaseHandler.getPlayerList());
        Player l_player = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        Order l_diplomacyOrder = new DiplomacyOrder(l_player, l_targetPlayer);
        l_diplomacyOrder.isValid();
        l_player.set_cardPlayedInTurn(l_diplomacyOrder);

        l_player.get_orderList().add(l_diplomacyOrder);
        l_diplomacyOrder.execute();
        l_player.get_orderList().remove(l_diplomacyOrder);
//        System.out.println(String.format("Order to negotiate peace with player %s has been saved", l_targetPlayer.get_name()));
//        LogManager.logAction(String.format("Order to negotiate peace with player %s has been saved", l_targetPlayer.get_name()));
    }

    /**
     * This method processes the bomb command, which allows a player to bomb a country.
     *
     * @param p_gamePhaseHandler The current game phase handler
     * @param p_commandArray     The user input command array
     * @throws Exception If the command is invalid or an error occurs during processing
     */
    public static void processBombCommand(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        Country l_country = HelperUtil.getCountryByCountryName(p_commandArray[1], l_gameMap);
        Player l_player = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        Order l_bombOrder = new BombOrder(l_player, l_country);
        l_bombOrder.isValid();
        l_player.set_cardPlayedInTurn(l_bombOrder);
        l_player.get_orderList().add(l_bombOrder);
        System.out.println(String.format("Order to bomb country country %s has been saved", l_country.getName()));
        LogManager.logAction(String.format("Order to bomb country country %s has been saved", l_country.getName()));
    }

    /**
     * This method processes the airlift command, which allows a player to airlift armies from one country to another.
     *
     * @param p_gamePhaseHandler The current game phase handler
     * @param p_commandArray     The user input command array
     * @throws Exception If the command is invalid or an error occurs during processing
     */
    public static void processAirLiftCommand(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        Country l_sourceCountry = HelperUtil.getCountryByCountryName(p_commandArray[1], l_gameMap);
        Country l_targetCountry = HelperUtil.getCountryByCountryName(p_commandArray[2], l_gameMap);
        int l_numArmies = Integer.parseInt(p_commandArray[3]);
        Player l_player = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        Order l_airLiftOrder = new AirLiftOrder(l_player, l_sourceCountry, l_targetCountry, l_numArmies);
        l_airLiftOrder.isValid();
        l_player.set_cardPlayedInTurn(l_airLiftOrder);
        l_player.get_orderList().add(l_airLiftOrder);
        System.out.println(String.format("Order to airlift %d armies from %s to %s has been saved", l_numArmies, l_sourceCountry.getName(), l_targetCountry.getName()));
        LogManager.logAction(String.format("Order to airlift %d armies from %s to %s has been saved", l_numArmies, l_sourceCountry.getName(), l_targetCountry.getName()));
    }
}