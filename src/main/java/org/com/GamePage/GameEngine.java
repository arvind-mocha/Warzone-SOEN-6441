package org.com.GamePage;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonConstants;
import org.com.Handlers.CommandHandler;
import org.com.Handlers.GamePhaseHandler;
import org.com.GameLog.LogManager;
//import org.com.Utils.LogUtil;

import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * The main game engine set of codes are present here.
 * Represents the main game loop.
 *
 * @author Arvind Nachiappan
 */

public class GameEngine implements Serializable {
    /**
     * Main method to execute the game logic.
     *
     * @param p_args Command line arguments.
     */
    public static void main(String[] p_args) {

        //The following messages will be displayed at the start of the game.
        var l_console = System.console();
        l_console.println("Welcome to the WarZone edition of Risk.");
        l_console.println(CommandOutputMessages.HELP_DEFAULT_MESSAGE);


        // Getting Input from the players
        Scanner l_scanner = new Scanner(System.in);
        GamePhaseHandler l_gamePhaseManager = new GamePhaseHandler();
        LogManager.logAction("Game has begun!!");
        String l_inputCommand;
        do {
            l_console.print("> ");
            l_inputCommand = l_scanner.nextLine();
            try {
                CommandHandler.processCommand(l_gamePhaseManager, l_inputCommand);
            } catch (Exception e) {
                l_console.println("\u001B[31m-- " + e.getMessage() + " --\u001B[0m");
                l_console.println(CommandOutputMessages.HELP_DEFAULT_MESSAGE);
            }
        } while (!l_inputCommand.equalsIgnoreCase(CommonConstants.EXIT_COMMAND));

        LogManager.logAction("Game has been ended");
        l_console.println("Thanks for giving our game a try! We hope you have an epic time on the battlefield. \uD83D\uDE80\uD83D\uDD25");
    }
}