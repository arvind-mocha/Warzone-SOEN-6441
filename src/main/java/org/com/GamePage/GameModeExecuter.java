package org.com.GamePage;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonConstants;
import org.com.GameLog.LogManager;
import org.com.GamePhase.IssueOrderPhase;
import org.com.Handlers.CommandHandler;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Player;
import org.com.Strategies.HumanStrategy;
import org.com.Strategies.Strategy;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameModeExecuter {
    public static void gameModeHandler(GamePhaseHandler l_gamePhaseManager) {
        var l_console = System.console();
        l_console.println(CommandOutputMessages.HELP_DEFAULT_MESSAGE);
        Scanner l_scanner = new Scanner(System.in);
        LogManager.logAction("Game has begun!!");
        List<String> l_inputCommand = null;
        do {
            boolean l_isIssueOrderPhase = l_gamePhaseManager.getGamePhase() instanceof IssueOrderPhase;
            List<Player> l_gamePlayerList = l_gamePhaseManager.getPlayerList();
            Player l_currentPlayer = l_gamePlayerList.isEmpty() ? null : l_gamePlayerList.get(l_gamePhaseManager.getCurrentPlayer());
            if (l_isIssueOrderPhase && l_currentPlayer.get_countries().size() == l_gamePhaseManager.getGameMap().getCountryMap().vertexSet().size()) {
                l_console.println(String.format("Hurray!!!. Player %s won the game.", l_currentPlayer.get_name()));
                l_inputCommand = Arrays.asList(CommonConstants.EXIT_COMMAND);
            } else if (l_isIssueOrderPhase && l_currentPlayer.get_countries().isEmpty()) {
                l_inputCommand = Arrays.asList(CommonConstants.COMMIT);
            } else if (l_isIssueOrderPhase && !(l_currentPlayer.get_playerStrategy() instanceof HumanStrategy)) {
                Strategy l_playerStrategy = l_currentPlayer.get_playerStrategy();
                l_inputCommand = l_playerStrategy.createOrder(l_gamePhaseManager, l_currentPlayer);
            } else {
                l_console.print("> ");
                l_inputCommand = Arrays.asList(l_scanner.nextLine());
            }
            try {
                CommandHandler.processCommand(l_gamePhaseManager, l_inputCommand);
            } catch (Exception l_exception) {
                l_console.println("\u001B[31m-- " + l_exception.getMessage() + " --\u001B[0m");
                LogManager.logAction("\u001B[31m-- " + l_exception.getMessage() + " --\u001B[0m");
                l_console.println(CommandOutputMessages.HELP_DEFAULT_MESSAGE);
            }
        } while (l_inputCommand == null || !l_inputCommand.contains(CommonConstants.EXIT_COMMAND));
    }
}
