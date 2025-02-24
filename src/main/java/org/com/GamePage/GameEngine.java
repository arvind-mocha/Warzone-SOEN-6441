package org.com.GamePage;

import org.com.Constants.CommonConstants;
import org.com.Controls.CommandHandler;
import org.com.Controls.GamePhaseHandler;
import org.com.Utils.LogUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.Serializable;
import java.util.logging.Level;

/**
 * The main game engine set of codes are present here.
 * Represents the main game loop.
 *
 * @author Arvind Nachiappan
 *
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
        l_console.print("Welcome to the WarZone edition of Risk.");
        l_console.print("\nEnter 'help' to get instructions of available commands");

        //Deleting the log file if exists
        try {
            Files.deleteIfExists(Paths.get(LogUtil.LOG_FILE_DIR));
        }
        catch (Exception e)
        {
            l_console.print("Issue occurred while deleting the log file");
        }

        // Getting Input from the players
        Scanner l_scanner = new Scanner(System.in);
        GamePhaseHandler l_gamePhaseManager = new GamePhaseHandler();
        LogUtil.Logger(GameEngine.class.getName(), Level.INFO, "Game has begun!!");
        String l_inputCommand;
        do {
            l_console.print("\n> ");
            l_inputCommand = l_scanner.nextLine();
            CommandHandler.processCommand(l_gamePhaseManager, l_inputCommand);
        } while (!l_inputCommand.equalsIgnoreCase(CommonConstants.EXIT_COMMAND));

        LogUtil.Logger(GameEngine.class.getName(), Level.INFO, "Game has been ended");
        l_console.print("Thanks for giving our game a try! We hope you have an epic time on the battlefield. \uD83D\uDE80\uD83D\uDD25");
    }
}