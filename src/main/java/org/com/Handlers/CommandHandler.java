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
                l_console.print(getHelpInstructionsBasedOnPhase(l_currentGamePhase));
                break;
            case CommonConstants.LOAD_MAP_COMMAND:
                String l_fileName = l_inputs[1];
                MapOperationsHandler.processMap(p_gamePhaseHandler, l_fileName);
                break;
        }
    }

    /**
     * Returns help instructions based on the current game phase.
     *
     * @param p_currentGamePhase The current game phase.
     * @return The help instructions for the current game phase.
     */
    private static String getHelpInstructionsBasedOnPhase(Phase p_currentGamePhase) {
        if(p_currentGamePhase instanceof LoadMapPhase)
        {
            return CommandOutputMessages.MAP_HELP;
        }
        return null;
    }
}
