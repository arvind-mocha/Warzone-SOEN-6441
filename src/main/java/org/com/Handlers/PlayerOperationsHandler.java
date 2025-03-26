package org.com.Handlers;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonConstants;
import org.com.Constants.CommonErrorMessages;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;
import org.com.Utils.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * All operations related to a Player like<ul><li>Deploy</li></ul>command is written here
 *
 * @author Arvind Lakshmanan
 * @author Barath Sundararaj
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
                ValidationUtil.validatePlayerManagement(l_playerList, l_attributeOperation, l_playerName);

                if (l_attributeOperation.equalsIgnoreCase(CommonConstants.ADD_ATTRIBUTE)) {
                    Player l_player = new Player(l_playerName);
                    l_playerList.add(l_player);
                    System.console().println(String.format("Player %s has been added successfully", l_playerName));
                } else if (l_attributeOperation.equalsIgnoreCase(CommonConstants.REMOVE_ATTRIBUTE)) {
                    if(!ValidationUtil.validatePlayerExistence(l_playerList, l_playerName))
                    {
                        throw new Exception(String.format(CommonErrorMessages.PLAYER_NOT_EXISTS_REMOVAL, l_playerName));
                    }
                    l_playerList.removeIf(l_player -> l_player.get_name().equalsIgnoreCase(l_playerName));
                    System.console().println(String.format("Player %s! has been removed successfully", l_playerName));
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
        ArrayList<Country> l_countryArray = new ArrayList<>(p_gamePhaseHandler.getGameMap().getCountryMap().vertexSet());
        Integer l_totalCountries = l_countryArray.size();
        int l_countrySplitValue = Math.floorDivExact(l_countryArray.size(), l_playerList.size());

        for (int l_playerIndex = 0; l_playerIndex < l_playerList.size(); l_playerIndex++) {
            Player l_player = l_playerList.get(l_playerIndex);
            int l_currentCountryId = l_countrySplitValue * (l_playerIndex);
            Country l_currentCountry = l_countryArray.get(l_currentCountryId >  l_totalCountries ? l_totalCountries : l_currentCountryId);
            l_player.addCountry(l_currentCountry);
            l_currentCountry.setOwner(l_player);
        }
        System.console().println("Game has begun!");
        p_gamePhaseHandler.assignReinforcements();

        p_gamePhaseHandler.setCurrentPlayer(0);
        int l_currentPlayerTurn = p_gamePhaseHandler.getCurrentPlayer();
        Player l_currentPlayer = l_playerList.get(l_currentPlayerTurn);
        System.console().println(String.format(CommandOutputMessages.PLAYER_TURN_INDICATOR, l_currentPlayer.get_name(), l_currentPlayer.get_armyCount()));
        p_gamePhaseHandler.setGamePhase(p_gamePhaseHandler.getGamePhase().getNextPhase());
    }


    /**
     * This method marks the beginning of the game where players are asked to <b>deploy all of their armies in their own countries.</b>
     * Only after a player <b>deploys all</b> of his/her armies, the next player's turn begins.
     * @param p_gamePhaseHandler Game Phase
     * @param p_commandArray User Input Command
     * @throws Exception Invalid Deploy Command
     */
    public static void processDeployArmies(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        Country l_targetCountry = ValidationUtil.validateDeployAndGetCountry(p_gamePhaseHandler, p_commandArray);

        int l_numArmies = Integer.parseInt(p_commandArray[2]);
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        l_targetCountry.setArmyCount(l_targetCountry.getArmyCount() + l_numArmies);
        l_currentPlayer.set_armyCount(l_currentPlayer.get_armyCount() - l_numArmies);
        System.console().println(String.format(CommandOutputMessages.PLAYER_SUCCESSFUL_ARMY_DEPLOYMENT, l_numArmies, l_targetCountry.getName(), l_currentPlayer.get_armyCount()));

        // Check if the player has finished deploying
        if (l_currentPlayer.get_armyCount() == 0) {
            int l_nextPlayerIndex = (p_gamePhaseHandler.getCurrentPlayer() + 1) % p_gamePhaseHandler.getPlayerList().size();
            p_gamePhaseHandler.setCurrentPlayer(l_nextPlayerIndex);
            System.console().println("Next player's turn: " + p_gamePhaseHandler.getPlayerList().get(l_nextPlayerIndex).get_name());
        }
    }

    /**
     * This method processes the advance command, which moves armies from one country to another.
     * The command can either be to move armies or to commit buffered commands.
     * @param p_gamePhaseHandler The current game phase handler
     * @param p_commandArray The user input command array
     * @throws Exception If the command is invalid or an error occurs during processing
     */
    public static void processAdvanceCommand(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        if (p_commandArray[0].equalsIgnoreCase("commit")) {
            executeBufferedCommands(p_gamePhaseHandler);
            return;
        }
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        String l_sourceCountryName = p_commandArray[1];
        String l_targetCountryName = p_commandArray[2];
        int l_numArmies = Integer.parseInt(p_commandArray[3]);
        Country l_sourceCountry = HelperUtil.getCountryByCountryName(l_sourceCountryName, l_gameMap);
        Country l_targetCountry = HelperUtil.getCountryByCountryName(l_targetCountryName, l_gameMap);

        ValidationUtil.validateAdvanceCommand(p_gamePhaseHandler, p_commandArray);
        p_gamePhaseHandler.getAdvanceCommandsBuffer().add(p_commandArray);
        System.console().println(String.format("Advance command added to buffer: move %d armies from %s to %s", l_numArmies, l_sourceCountry.getName(), l_targetCountry.getName()));
    }

    /**
     * Method to execute the buffered advance commands.
     * @param p_gamePhaseHandler The current game phase handler
     * @throws Exception If an error occurs during command execution
     */
    public static void executeBufferedCommands(GamePhaseHandler p_gamePhaseHandler) throws Exception {
        List<String[]> l_commands = p_gamePhaseHandler.getAdvanceCommandsBuffer();
        if (l_commands.isEmpty()) {
            System.console().println("No advance commands in buffer.");
            return;
        }

        //Iterate through buffered commands
        for (String[] l_commandArray : l_commands) {
            int l_numArmies = Integer.parseInt(l_commandArray[3]);
            Country l_sourceCountry = null;
            Country l_targetCountry = null;

            l_sourceCountry.setArmyCount(l_sourceCountry.getArmyCount() - l_numArmies);
            l_targetCountry.setArmyCount(l_targetCountry.getArmyCount() + l_numArmies);
            System.console().println(String.format("Moved %d armies from %s to %s", l_numArmies, l_sourceCountry.getName(), l_targetCountry.getName()));
        }
        p_gamePhaseHandler.clearAdvanceCommandsBuffer();

        advanceTurn(p_gamePhaseHandler);
    }

    /**
     * Advances the turn to the next player.
     * @param p_gamePhaseHandler The current game phase handler
     */
    public static void advanceTurn(GamePhaseHandler p_gamePhaseHandler) {
        int l_nextPlayerIndex = (p_gamePhaseHandler.getCurrentPlayer() + 1) % p_gamePhaseHandler.getPlayerList().size();
        p_gamePhaseHandler.setCurrentPlayer(l_nextPlayerIndex);
        System.console().println("Next player's turn: " + p_gamePhaseHandler.getPlayerList().get(l_nextPlayerIndex).get_name());
    }
}
