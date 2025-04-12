package org.com.GamePage;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonConstants;
import org.com.GameLog.LogManager;
import org.com.GamePhase.IssueOrderPhase;
import org.com.Handlers.CommandHandler;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Models.Tournament;
import org.com.Strategies.CheaterStrategy;
import org.com.Strategies.HumanStrategy;
import org.com.Strategies.Strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The game mode executer class is used to switch between single or tournament game mode.
 *
 * @author Arvind Nachiappan
 */
public class GameModeExecuter {

    /**
     * Handles the game mode execution for both single and tournament modes.
     * <p>
     * This method manages the main game loop, processes player commands, and determines
     * the winner of the game. It supports both single-game and tournament modes,
     * handling different player strategies and game phases.
     *
     * @param l_gamePhaseManager  The handler for managing the current game phase.
     * @param l_tournamentHandler The tournament object containing tournament-specific data (can be null for single mode).
     * @param l_mapName           The name of the map being played (used in tournament mode).
     */
    public static void gameModeHandler(GamePhaseHandler l_gamePhaseManager, Tournament l_tournamentHandler, String l_mapName) {
        System.out.println(CommandOutputMessages.HELP_DEFAULT_MESSAGE);
        Scanner l_scanner = new Scanner(System.in);
        Player l_winner = null;
        LogManager.logAction("Game has begun!!");
        List<String> l_inputCommand = null;
        boolean l_maxNotTurnsCompleted;
        do {
            boolean l_isIssueOrderPhase = l_gamePhaseManager.getGamePhase() instanceof IssueOrderPhase;
            List<Player> l_gamePlayerList = l_gamePhaseManager.getPlayerList();
            Player l_currentPlayer = l_gamePlayerList.isEmpty() ? null : l_gamePlayerList.get(l_gamePhaseManager.getCurrentPlayer());
            List<Player> l_ownersMap = new ArrayList<>();

            if (l_gamePhaseManager.getGameMap() != null) {
                for (Country l_country : l_gamePhaseManager.getGameMap().getCountryMap().vertexSet()) {
                    if (l_country.getOwner() != null) {
                        l_ownersMap.add(l_country.getOwner());
                    }
                }
                l_ownersMap = l_ownersMap.stream().distinct().collect(Collectors.toList());
            }

            if (l_isIssueOrderPhase && l_currentPlayer.get_countries().size() == l_gamePhaseManager.getGameMap().getCountryMap().vertexSet().size()) {
                l_winner = l_ownersMap.getFirst();
                System.out.println(String.format("Hurray!!!. Player %s won the game.", l_winner.get_name()));
                l_tournamentHandler.getGameWinners().computeIfAbsent(l_mapName, k -> new ArrayList<String>()).add(l_winner.get_name());
                l_inputCommand = Arrays.asList(CommonConstants.EXIT_COMMAND);
            } else if (l_isIssueOrderPhase && l_currentPlayer.get_countries().isEmpty()) {
                l_inputCommand = Arrays.asList(CommonConstants.COMMIT);
            } else if (l_isIssueOrderPhase && !(l_currentPlayer.get_playerStrategy() instanceof HumanStrategy)) {
                if (l_currentPlayer.get_playerStrategy() instanceof CheaterStrategy && l_isIssueOrderPhase && l_ownersMap.size() <= 1) {
                    l_winner = l_ownersMap.getFirst();
                    System.out.println(String.format("Cheater!!!. Player %s won the game.", l_winner.get_name()));
                    l_tournamentHandler.getGameWinners().computeIfAbsent(l_mapName, k -> new ArrayList<String>()).add(l_winner.get_name());
                    l_inputCommand = Arrays.asList(CommonConstants.EXIT_COMMAND);
                } else {
                    Strategy l_playerStrategy = l_currentPlayer.get_playerStrategy();
                    l_inputCommand = l_playerStrategy.createOrder(l_gamePhaseManager, l_currentPlayer);
                }
            } else {
                System.out.print("> ");
                l_inputCommand = Arrays.asList(l_scanner.nextLine());
            }
            try {
                CommandHandler.processCommand(l_gamePhaseManager, l_inputCommand);
            } catch (Exception l_exception) {
                System.out.println("\u001B[31m-- " + l_exception.getMessage() + " --\u001B[0m");
                LogManager.logAction("\u001B[31m-- " + l_exception.getMessage() + " --\u001B[0m");
                System.out.println(CommandOutputMessages.HELP_DEFAULT_MESSAGE);
            }
            l_maxNotTurnsCompleted = l_tournamentHandler == null || l_gamePhaseManager.getTurnsCompleted() < l_tournamentHandler.getMaxTurns();
        } while (l_inputCommand == null || l_maxNotTurnsCompleted && !l_inputCommand.contains(CommonConstants.EXIT_COMMAND));
    }
}
