package org.com.Controls;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonConstants;
import org.com.GamePhase.LoadMapPhase;
import org.com.GamePhase.Phase;
import org.com.Utils.LogUtil;

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
     * @param p_gamePhaseManager The game phase manager handling the current game phase.
     * @param p_command The command entered by the user.
     */
    public static void processCommand(GamePhaseHandler p_gamePhaseManager, String p_command)
    {
        var console = System.console();
        String[] l_inputs = p_command.split("\\s+");
        Phase l_currentGamePhase = p_gamePhaseManager.getGamePhase();
        if(!isCommandValid(l_currentGamePhase, l_inputs))
        {
            console.print("Invalid command");
            return;
        }
        LogUtil.Logger(CommandHandler.class.getName(), Level.INFO, "Processing the command: " + p_command);

        switch (l_inputs[0].toLowerCase())
        {
            case CommonConstants.HELP_COMMAND:
                console.print(getHelpInstructionsBasedOnPhase(l_currentGamePhase));
                break;
            case CommonConstants.LOAD_MAP_COMMAND:
                MapOperationsHandler.processMap(l_inputs[1]);
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

    /**
     * Validates the given command based on the current game phase.
     *
     * @param p_currentGamePhase The current game phase.
     * @param p_commands The command entered by the user, split into an array.
     * @return true if the command is valid, false otherwise.
     */
    private static boolean isCommandValid(Phase p_currentGamePhase, String[] p_commands)
    {
        var l_console = System.console();
        String l_baseCommand = p_commands[0]; // 0th value of the array will always be the base command
        Commands l_commandEnum = Commands.getCommandByName(l_baseCommand); // fetching the respective command and its attributes

        // skipping validation for help command since no processing needed for help
        if(l_baseCommand.equalsIgnoreCase(CommonConstants.HELP_COMMAND))
        {
            return true;
        }

        // validating commands based on each phase
        if(!p_currentGamePhase.getValidCommands().contains(l_baseCommand))
        {
            return false;
        }

        // validating the correctness of the file provided for required commands
        if(l_commandEnum.d_isFileRequired)
        {
            try {
                String fileName = p_commands[1];
                if(!new File(System.getProperty("user.dir") + "/src/main/resources/" + fileName).exists())
                {
                   throw new RuntimeException();// Thrown exception will be caught and proper error message will get displayed
                }
            }
            catch (Exception e)
            {
                l_console.print("\nProvide a valid file");
                return false;
            }
        }

        return true;
    }
}
