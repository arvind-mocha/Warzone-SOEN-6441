package org.com.GamePage;

import org.com.Constants.CommonConstants;
import org.com.GameLog.LogManager;
import org.com.Handlers.CommandHandler;
import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.TournamentHandler;
import org.com.Models.Tournament;
import org.com.Utils.ValidationUtil;

import java.io.Serializable;
import java.util.Arrays;
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
    public static void main(String[] p_args) throws Exception {

        //The following messages will be displayed at the start of the game.
        System.out.println("Welcome to the WarZone edition of Risk.");

        // Getting Input from the players
        System.out.println("Please choose a game mode: Single or Tournament");
        Scanner l_scanner = new Scanner(System.in);
        String l_gameMode = l_scanner.nextLine();

        try {

            if (CommonConstants.SINGLE_GAME_MODE.equals(l_gameMode)) {
                GamePhaseHandler l_gamePhaseManager = new GamePhaseHandler();
                GameModeExecuter.gameModeHandler(l_gamePhaseManager, null);
            } else if (CommonConstants.TOURNAMENT_COMMAND.equals(l_gameMode.toLowerCase())) {
                Tournament l_tournamentManager = new Tournament();
                System.out.println("Set up the tournament using the following format:\n" + "tournament -M maps -P players -G number_of_games -D max_turns");
                TournamentHandler.processTournament(l_scanner.nextLine(), l_tournamentManager);
                ValidationUtil.validateTournamentCommand(l_tournamentManager);
                for (int l_gameNum = 0; l_gameNum < l_tournamentManager.getNumGames(); l_gameNum++) {
                    GamePhaseHandler l_gamePhaseManager = new GamePhaseHandler();
                    String l_loadMap = String.format(CommonConstants.LOAD_MAP + " ", l_tournamentManager.getMapList().get(l_gameNum));
                    String l_addPlayers = CommonConstants.ADD_PLAYER_COMMAND + " " + l_tournamentManager.getStrategyList().stream().map(player -> "-add " + player + " " + player).reduce("", (partialString, element) -> partialString + " " + element).trim();
                    CommandHandler.processCommand(l_gamePhaseManager, Arrays.asList(l_loadMap, l_addPlayers, CommonConstants.ASSIGN_COUNTRIES_COMMAND));
                    GameModeExecuter.gameModeHandler(l_gamePhaseManager, l_tournamentManager);
                    System.out.println(String.format("Game %d got ended. Winner: %s", l_gameNum + 1, l_tournamentManager.getGameWinners().get(l_gameNum)));
                }
            } else {
                System.out.println("Game mode does not exists");
            }
        }
        catch (Exception l_exception)
        {
            System.out.println("\u001B[31m-- " + l_exception.getMessage() + ". Start the game again." + " --\u001B[0m");
            LogManager.logAction("\u001B[31m-- " + l_exception.getMessage() + " --\u001B[0m");
        }
        LogManager.logAction("Warzone has been ended");
        System.out.println("Thanks for giving our game a try! We hope you have an epic time on the battlefield. \uD83D\uDE80\uD83D\uDD25");
    }
}
