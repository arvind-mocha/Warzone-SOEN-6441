package org.com.Handlers;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonConstants;
import org.com.Constants.CommonErrorMessages;
import org.com.GamePhase.LoadMapPhase;
import org.com.GamePhase.Phase;
import org.com.Models.Map;
import org.com.Utils.LogUtil;
import org.com.Utils.ValidationUtil;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String[] l_inputs = p_command.split("\\s+");
        Phase l_currentGamePhase = p_gamePhaseHandler.getGamePhase();
        ValidationUtil.validateCommand(l_currentGamePhase, l_inputs);
        LogUtil.Logger(CommandHandler.class.getName(), Level.INFO, "Processing the command: " + p_command);

        switch(l_inputs[0].toLowerCase())
        {
            case CommonConstants.HELP_COMMAND:
                l_console.println(l_currentGamePhase.getHelpMessage());
                break;
            case CommonConstants.LOAD_MAP_COMMAND:
                MapOperationsHandler.processMap(p_gamePhaseHandler, l_inputs[1], false);
                break;
            case CommonConstants.SHOW_MAP_COMMAND:
                MapOperationsHandler.processShowGameMap(p_gamePhaseHandler);
                break;
            case CommonConstants.VALIDATE_MAP_COMMAND:
                MapOperationsHandler.processMap(p_gamePhaseHandler, l_inputs[1], true);
                break;
        }
    }
}
