package org.com.Handlers;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonConstants;
import org.com.Constants.CommonErrorMessages;
import org.com.Constants.StrategyConstants;
import org.com.GameLog.LogManager;
import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Utils.ValidationUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * All operations related to a Player like<ul><li>Deploy</li></ul>command is written here
 *
 * @author Arvind Lakshmanan
 * @author Barath Sundararaj
 * @author Devasenan Murugan
 *
 */

public class PlayerOperationsHandler {

    /**
     * This method is used depicts the beginning of the player registration.
     * Performs acctions like:
     * <ul>
     *     <li>Add New Player</li>
     *     <li>Remove Existing Player</li>
     * </ul>
     * @param p_command The user input
     * @param p_gamePhaseHandler Current Game Phase
     * @throws Exception Error in case of a non-existing player is asked to be removed
     */
    public static void processPlayerManagement(String p_command, GamePhaseHandler p_gamePhaseHandler) throws Exception
    {
        List<Player> l_playerList = p_gamePhaseHandler.getPlayerList();
        String[] l_commandsArray = p_command.split(" -");
        for(int l_index=1; l_index<l_commandsArray.length; l_index++) {
            String[] l_attributesArray = l_commandsArray[l_index].split(" -");

            for (String l_attribute : l_attributesArray) {
                String[] l_operationsArray = l_attribute.split(" ");
                String l_attributeOperation = l_operationsArray[0];
                String l_playerName = l_operationsArray[1];
                String l_playerStrategy = l_operationsArray[2];
                ValidationUtil.validatePlayerManagement(l_playerList, l_attributeOperation, l_playerName);

                if (l_attributeOperation.equalsIgnoreCase(CommonConstants.ADD_ATTRIBUTE)) {
                    Player l_player = new Player(l_playerName);
                    l_player.set_playerStrategy(StrategyConstants.getStrategyByName(l_playerStrategy));
                    l_playerList.add(l_player);
                    System.console().println(String.format("Player %s has been added successfully. Strategy :: %s", l_playerName, l_playerStrategy));
                    LogManager.logAction(String.format("Player %s has been added successfully. Strategy :: %s", l_playerName, l_playerStrategy));
                } else if (l_attributeOperation.equalsIgnoreCase(CommonConstants.REMOVE_ATTRIBUTE)) {
                    if(!ValidationUtil.validatePlayerExistence(l_playerList, l_playerName))
                    {
                        throw new Exception(String.format(CommonErrorMessages.PLAYER_NOT_EXISTS_REMOVAL, l_playerName));
                    }
                    l_playerList.removeIf(l_player -> l_player.get_name().equalsIgnoreCase(l_playerName));
                    System.console().println(String.format("Player %s! has been removed successfully", l_playerName));
                    LogManager.logAction(String.format("Player %s! has been removed successfully", l_playerName));
                }
            }
        }
    }

    /**
     * This method is used to divide the Loaded Map and <b>Assign Countries</b> among the registered players.
     * @param p_gamePhaseHandler Game Phase
     * @throws Exception
     */
    public static void processAssignCountries(GamePhaseHandler p_gamePhaseHandler)throws Exception {
        List<Player> l_playerList = p_gamePhaseHandler.getPlayerList();
        ValidationUtil.validateAssignCountries(l_playerList);

        ArrayList<Continent> l_continentsArray = new ArrayList<>(p_gamePhaseHandler.getGameMap().getContinentMap().vertexSet());
        Collections.shuffle(l_continentsArray);
        for (int i=0; i < l_playerList.size(); i++){
            Continent l_randomContinent = l_continentsArray.get(i);
            Player l_player = l_playerList.get(i);
            ArrayList<Country> l_countryArray = new ArrayList<>(l_randomContinent.getCountries());
            Collections.shuffle(l_countryArray);
            for(int j = 0; j < l_countryArray.size()/2; j++){
                Country l_country = l_countryArray.get(j);
                l_player.addCountry(l_country);
                l_country.setOwner(l_player);
                l_country.setArmyCount(0);
            }
        }

        System.console().println("Game has begun!");
        LogManager.logAction("Game has begun!");
        p_gamePhaseHandler.assignReinforcements(true);

        p_gamePhaseHandler.setCurrentPlayer(0);
        int l_currentPlayerTurn = p_gamePhaseHandler.getCurrentPlayer();
        Player l_currentPlayer = l_playerList.get(l_currentPlayerTurn);
        System.console().println(String.format(CommandOutputMessages.PLAYER_TURN_INDICATOR, l_currentPlayer.get_name(), l_currentPlayer.get_armyCount(), l_currentPlayer.get_cards().toString()));
        LogManager.logAction(String.format(CommandOutputMessages.PLAYER_TURN_INDICATOR, l_currentPlayer.get_name(), l_currentPlayer.get_armyCount(), l_currentPlayer.get_cards().toString()));
        p_gamePhaseHandler.setGamePhase(p_gamePhaseHandler.getGamePhase().getNextPhase());
    }
}
