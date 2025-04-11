package org.com.GamePage;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonConstants;
import org.com.GamePhase.IssueOrderPhase;
import org.com.Handlers.CommandHandler;
import org.com.Handlers.GamePhaseHandler;
import org.com.GameLog.LogManager;
import org.com.Handlers.MapOperationsHandler;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Models.Tournament;
import org.com.Strategies.CheaterStrategy;
import org.com.Strategies.HumanStrategy;
import org.com.Strategies.Strategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        Tournament l_tournamentManager = new Tournament();
        GamePhaseHandler l_gamePhaseManager = new GamePhaseHandler();
        LogManager.logAction("Game has begun!!");
        List<String> l_inputCommand;
        do {
            boolean l_isIssueOrderPhase = l_gamePhaseManager.getGamePhase() instanceof IssueOrderPhase;
            List<Player> l_gamePlayerList = l_gamePhaseManager.getPlayerList();
            Player l_currentPlayer = l_gamePlayerList.isEmpty() ? null : l_gamePlayerList.get(l_gamePhaseManager.getCurrentPlayer());
            List<Player> l_ownersMap = new ArrayList<>();

            if(l_gamePhaseManager.getGameMap() != null){
                for (Country l_country : l_gamePhaseManager.getGameMap().getCountryMap().vertexSet()){
                    if(l_country.getOwner() != null) {
                        l_ownersMap.add(l_country.getOwner());
                    }
                }
                l_ownersMap = l_ownersMap.stream().distinct().collect(Collectors.toList());
            }

            // if(l_isIssueOrderPhase && l_gamePhaseManager.getTurnsCompleted() > 400)
            if(l_isIssueOrderPhase && l_currentPlayer.get_countries().size() == l_gamePhaseManager.getGameMap().getCountryMap().vertexSet().size())
//            if(l_isIssueOrderPhase && l_ownersMap.size() <= 1)
            {
                Player l_winner = l_ownersMap.getFirst();
//                l_console.println(String.format("%d, %d", l_winner.get_countries().size(), l_gamePhaseManager.getGameMap().getCountryMap().vertexSet().size()));
                l_console.println(String.format("Hurray!!!. Player %s won the game.", l_winner.get_name()));

                try{
                    MapOperationsHandler.processShowGameMap(l_gamePhaseManager);
                } catch (Exception e){
                    System.out.println(e.toString());
                }
                l_inputCommand = Arrays.asList(CommonConstants.EXIT_COMMAND);
            }
            else if(l_isIssueOrderPhase && l_currentPlayer.get_countries().isEmpty())
            {
                l_inputCommand = Arrays.asList(CommonConstants.COMMIT);
            }
            else if (l_isIssueOrderPhase && !(l_currentPlayer.get_playerStrategy() instanceof HumanStrategy))
            {
                if (l_currentPlayer.get_playerStrategy() instanceof CheaterStrategy && l_isIssueOrderPhase && l_ownersMap.size() <= 1){
                    Player l_winner = l_ownersMap.getFirst();
//                    l_console.println(String.format("%d, %d", l_winner.get_countries().size(), l_gamePhaseManager.getGameMap().getCountryMap().vertexSet().size()));
                    l_console.println(String.format("Cheater!!!. Player %s won the game.", l_winner.get_name()));

                    try{
                        MapOperationsHandler.processShowGameMap(l_gamePhaseManager);
                    } catch (Exception e){
                        System.out.println(e.toString());
                    }
                    l_inputCommand = Arrays.asList(CommonConstants.EXIT_COMMAND);
                } else {
                    Strategy l_playerStrategy = l_currentPlayer.get_playerStrategy();
                    l_inputCommand = l_playerStrategy.createOrder(l_gamePhaseManager, l_currentPlayer);
                }
            }

            else
            {
                l_console.print("> ");
                l_inputCommand = Arrays.asList(l_scanner.nextLine());
            }


            try {
                CommandHandler.processCommand(l_gamePhaseManager, l_inputCommand);
            } catch (Exception e) {
                l_console.println("\u001B[31m-- " + e.getMessage() + " --\u001B[0m");
                LogManager.logAction("\u001B[31m-- " + e.getMessage() + " --\u001B[0m");
                l_console.println(CommandOutputMessages.HELP_DEFAULT_MESSAGE);
            }
        } while(l_inputCommand == null || !l_inputCommand.contains(CommonConstants.EXIT_COMMAND));

        LogManager.logAction("Game has been ended");
        l_console.println("Thanks for giving our game a try! We hope you have an epic time on the battlefield. \uD83D\uDE80\uD83D\uDD25");
    }
}