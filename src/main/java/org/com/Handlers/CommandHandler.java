package org.com.Handlers;

import org.com.Adapter.MapFileHandler;
import org.com.Adapter.MapFileHandlerFactory;
import org.com.Constants.CommonConstants;
import org.com.GameLog.LogManager;
import org.com.GamePhase.Phase;
import org.com.Utils.SaveGameUtil;
import org.com.Utils.ValidationUtil;

import java.io.Serializable;
import java.util.List;


/**
 * This class handles all commands and invocation of all command methods take place here.
 *
 * @author Arvind Lakshmanan
 */

public class CommandHandler implements Serializable {

    /**
     * Processes the given command based on the current game phase.
     *
     * @param p_gamePhaseHandler The game phase manager handling the current game phase.
     * @param p_commandList      The command entered by the user.
     */
    public static void processCommand(GamePhaseHandler p_gamePhaseHandler, List<String> p_commandList) throws Exception {
        if (p_commandList == null || p_commandList.isEmpty() || p_commandList.contains(null)) {
            IssueOrderHandler.processCommitCommand(p_gamePhaseHandler);
            return;
        }

        for (String l_command : p_commandList) {
            String[] l_commandArray = l_command.split("\\s+");
            Phase l_currentGamePhase = p_gamePhaseHandler.getGamePhase();
            ValidationUtil.validateCommand(l_currentGamePhase, l_commandArray, l_command);
            LogManager.logAction("\nProcessing the command: " + l_command);
            switch (l_commandArray[0].toLowerCase()) {
                case CommonConstants.HELP_COMMAND:
                    System.out.println(l_currentGamePhase.getHelpMessage());
                    break;
                case CommonConstants.EDIT_MAP_COMMAND:
                    MapOperationsHandler.editMap(p_gamePhaseHandler, l_commandArray[1]);
                    break;
                case CommonConstants.EDIT_CONTINENT_COMMAND:
                    MapOperationsHandler.editContinent(p_gamePhaseHandler, l_command);
                    break;
                case CommonConstants.EDIT_COUNTRY_COMMAND:
                    MapOperationsHandler.editCountry(p_gamePhaseHandler, l_command);
                    break;
                case CommonConstants.EDIT_NEIGHBOUR_COMMAND:
                    MapOperationsHandler.editNeighbour(p_gamePhaseHandler, l_command);
                    break;
                case CommonConstants.SAVE_MAP_COMMAND:
                    String fileToSave = l_commandArray.length == 1 ? p_gamePhaseHandler.getMapFileName() : l_commandArray[1];
                    MapFileHandler handler = MapFileHandlerFactory.getHandler(fileToSave);
                    handler.saveMap(fileToSave, p_gamePhaseHandler.getGameMap());
                    break;
                case CommonConstants.LOAD_MAP_COMMAND:
                    handler = MapFileHandlerFactory.getHandler(l_commandArray[1]);
                    handler.loadMap(l_commandArray[1], p_gamePhaseHandler);
                    break;
                case CommonConstants.SHOW_MAP_COMMAND:
                    MapOperationsHandler.processShowGameMap(p_gamePhaseHandler);
                    break;
                case CommonConstants.VALIDATE_MAP_COMMAND:
                    MapOperationsHandler.processMap(p_gamePhaseHandler, l_commandArray[1], true, false);
                    break;
                case CommonConstants.ADD_PLAYER_COMMAND:
                    PlayerOperationsHandler.processPlayerManagement(l_command, p_gamePhaseHandler);
                    break;
                case CommonConstants.ASSIGN_COUNTRIES_COMMAND:
                    PlayerOperationsHandler.processAssignCountries(p_gamePhaseHandler);
                    break;
                case CommonConstants.DEPLOY_ARMIES_COMMAND:
                    IssueOrderHandler.processDeployArmies(p_gamePhaseHandler, l_commandArray);
                    break;
                case CommonConstants.ADVANCE_ARMY_COMMAND:
                    IssueOrderHandler.processAdvanceCommand(p_gamePhaseHandler, l_commandArray);
                    break;
                case CommonConstants.BLOCKADE_COMMAND:
                    IssueOrderHandler.processBlockadeCommand(p_gamePhaseHandler, l_commandArray);
                    break;
                case CommonConstants.NEGOTIATE_COMMAND:
                    IssueOrderHandler.processNegotiateCommand(p_gamePhaseHandler, l_commandArray);
                    break;
                case CommonConstants.BOMB_COMMAND:
                    IssueOrderHandler.processBombCommand(p_gamePhaseHandler, l_commandArray);
                    break;
                case CommonConstants.AIRLIFT_COMMAND:
                    IssueOrderHandler.processAirLiftCommand(p_gamePhaseHandler, l_commandArray);
                    break;
                case CommonConstants.SAVE_GAME_COMMAND:
                    SaveGameUtil.saveGame(p_gamePhaseHandler, l_commandArray[1]);
                    break;
                case CommonConstants.LOAD_GAME_COMMAND:
                    GamePhaseHandler l_loadedGame = SaveGameUtil.loadGame(l_commandArray[1]);
                    p_gamePhaseHandler.setGamePhase(l_loadedGame.getGamePhase());
                    p_gamePhaseHandler.setGameMap(l_loadedGame.getGameMap());
                    p_gamePhaseHandler.setPlayerList(l_loadedGame.getPlayerList());
                    p_gamePhaseHandler.setCurrentPlayer(l_loadedGame.getCurrentPlayer());
                    p_gamePhaseHandler.setMapFileName(l_loadedGame.getMapFileName());
                    p_gamePhaseHandler.setTurnsCompleted(l_loadedGame.getTurnsCompleted());
                    p_gamePhaseHandler.setWinnerPlayer(l_loadedGame.getWinnerPlayer());
                    System.out.println("Game state restored from file: " + l_commandArray[1]);
                    break;
                case CommonConstants.COMMIT:
                    IssueOrderHandler.processCommitCommand(p_gamePhaseHandler);
            }
        }
    }
}
