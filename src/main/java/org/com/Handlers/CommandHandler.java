package org.com.Handlers;

import org.com.Constants.CommonConstants;
import org.com.GamePhase.Phase;
import org.com.Utils.LogUtil;
import org.com.Utils.ValidationUtil;

import java.util.logging.Level;

/**
 * This class handles all commands and invocation of all command methods take place here.
 *
 * @author Arvind Lakshmanan
 *
 */

public class CommandHandler {

    /**
     * Processes the given command based on the current game phase.
     *
     * @param p_gamePhaseHandler The game phase manager handling the current game phase.
     * @param p_command The command entered by the user.
     */
    public static void processCommand(GamePhaseHandler p_gamePhaseHandler, String p_command)throws Exception
    {
        var l_console = System.console();
        String[] l_commandArray = p_command.split("\\s+");
        Phase l_currentGamePhase = p_gamePhaseHandler.getGamePhase();
        ValidationUtil.validateCommand(l_currentGamePhase, l_commandArray, p_command);
        LogUtil.Logger(CommandHandler.class.getName(), Level.INFO, "Processing the command: " + p_command);
        switch(l_commandArray[0].toLowerCase())
        {
            case CommonConstants.HELP_COMMAND:
                l_console.println(l_currentGamePhase.getHelpMessage());
                break;
            case CommonConstants.EDIT_MAP_COMMAND:
                MapOperationsHandler.editMap(p_gamePhaseHandler, l_commandArray[1]);
                break;
            case CommonConstants.EDIT_CONTINENT_COMMAND:
                MapOperationsHandler.editContinent(p_gamePhaseHandler, p_command);
                break;
            case CommonConstants.EDIT_COUNTRY_COMMAND:
                MapOperationsHandler.editCountry(p_gamePhaseHandler, p_command);
                break;
            case CommonConstants.EDIT_NEIGHBOUR_COMMAND:
                MapOperationsHandler.editNeighbour(p_gamePhaseHandler, p_command);
                break;
            case CommonConstants.SAVE_MAP_COMMAND:
                MapOperationsHandler.saveMap(p_gamePhaseHandler, p_command);
                break;
            case CommonConstants.LOAD_MAP_COMMAND:
                MapOperationsHandler.processMap(p_gamePhaseHandler, l_commandArray[1], false, false);
                break;
            case CommonConstants.SHOW_MAP_COMMAND:
                MapOperationsHandler.processShowGameMap(p_gamePhaseHandler);
                break;
            case CommonConstants.VALIDATE_MAP_COMMAND:
                MapOperationsHandler.processMap(p_gamePhaseHandler, l_commandArray[1], true, false);
                break;
            case CommonConstants.ADD_PLAYER_COMMAND:
                PlayerOperationsHandler.processPlayerManagement(p_command, p_gamePhaseHandler);
                break;
            case CommonConstants.ASSIGN_COUNTRIES_COMMAND:
                PlayerOperationsHandler.processAssignCountries(p_gamePhaseHandler);
                break;
            case CommonConstants.DEPLOY_ARMIES_COMMAND:
                PlayerOperationsHandler.processDeployArmies(p_gamePhaseHandler, l_commandArray);
                break;
//            case CommonConstants.ADVANCE_ARMY_COMMAND:
//                PlayerOperationsHandler.processAdvanceCommand(p_gamePhaseHandler, l_commandArray);
//                break;
        }
    }
}
