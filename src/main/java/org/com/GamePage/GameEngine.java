package org.com.GamePage;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonConstants;
import org.com.GamePhase.IssueOrderPhase;
import org.com.Handlers.CommandHandler;
import org.com.Handlers.GamePhaseHandler;
import org.com.GameLog.LogManager;
import org.com.Models.Player;
import org.com.Models.Tournament;
import org.com.Strategies.HumanStrategy;
import org.com.Strategies.Strategy;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
//        var l_console = System.console();
        System.out.println("Welcome to the WarZone edition of Risk.");
        System.out.println(CommandOutputMessages.HELP_DEFAULT_MESSAGE);


        // Getting Input from the players
        Scanner l_scanner = new Scanner(System.in);
        Tournament l_tournamentManager = new Tournament();
        GamePhaseHandler l_gamePhaseManager = new GamePhaseHandler();
        LogManager.logAction("Game has begun!!");
        List<String> l_inputCommand;
        do {
            boolean l_isIssueOrderPhase = l_gamePhaseManager.getGamePhase() instanceof IssueOrderPhase;
            List<Player> l_gamePlayerList = l_gamePhaseManager.getPlayerList();
            Player l_currentPlayer = l_gamePlayerList.isEmpty() ? null : l_gamePlayerList.get(l_gamePhaseManager.getCurrentPlayer());
            if(l_isIssueOrderPhase && l_currentPlayer.get_countries().size() == l_gamePhaseManager.getGameMap().getCountryMap().vertexSet().size())
            {
                System.out.println(String.format("Hurray!!!. Player %s won the game.", l_currentPlayer.get_name()));
                l_inputCommand = Arrays.asList(CommonConstants.EXIT_COMMAND);
            }
            else if(l_isIssueOrderPhase && l_currentPlayer.get_countries().isEmpty())
            {
                l_inputCommand = Arrays.asList(CommonConstants.COMMIT);
            }
            else if (l_isIssueOrderPhase && !(l_currentPlayer.get_playerStrategy() instanceof HumanStrategy))
            {
                Strategy l_playerStrategy = l_currentPlayer.get_playerStrategy();
                l_inputCommand = l_playerStrategy.createOrder(l_gamePhaseManager, l_currentPlayer);
            }
            else
            {
                System.out.print("> ");
                l_inputCommand = Arrays.asList(l_scanner.nextLine());
            }
            try {
                CommandHandler.processCommand(l_gamePhaseManager, l_inputCommand);
            } catch (Exception e) {
                System.out.println("\u001B[31m-- " + e.getMessage() + " --\u001B[0m");
                LogManager.logAction("\u001B[31m-- " + e.getMessage() + " --\u001B[0m");
                System.out.println(CommandOutputMessages.HELP_DEFAULT_MESSAGE);
            }
        } while(l_inputCommand == null || !l_inputCommand.contains(CommonConstants.EXIT_COMMAND));

        LogManager.logAction("Game has been ended");
        System.out.println("Thanks for giving our game a try! We hope you have an epic time on the battlefield. \uD83D\uDE80\uD83D\uDD25");
    }
}