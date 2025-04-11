package org.com.GamePage;

import org.com.Constants.CommonConstants;
import org.com.GameLog.LogManager;
import org.com.Handlers.CommandHandler;
import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.TournamentHandler;
import org.com.Models.Tournament;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import org.com.Strategies.CheaterStrategy;
import org.com.Strategies.HumanStrategy;
import org.com.Strategies.Strategy;
=======
>>>>>>> de7a03b (Tournament)
=======
import org.com.Utils.ValidationUtil;
>>>>>>> 118221f (tournaments)
=======
import org.com.Utils.ValidationUtil;
=======
import org.com.Strategies.CheaterStrategy;
import org.com.Strategies.HumanStrategy;
import org.com.Strategies.Strategy;
>>>>>>> 829dd75 (fixed Benevolent and Cheater stratergies - works completely)
>>>>>>> afdec0f (rebasing on main)

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
        var l_console = System.console();
        l_console.println("Welcome to the WarZone edition of Risk.");

        // Getting Input from the players
        l_console.println("Please choose a game mode: Single or Tournament");
        Scanner l_scanner = new Scanner(System.in);
        String l_gameMode = l_scanner.nextLine();

<<<<<<< HEAD
        if (CommonConstants.SINGLE_GAME_MODE.equals(l_gameMode)) {
            GamePhaseHandler l_gamePhaseManager = new GamePhaseHandler();
            GameModeExecuter.gameModeHandler(l_gamePhaseManager, null);
        } else if (CommonConstants.TOURNAMENT_COMMAND.equals(l_gameMode.toLowerCase())) {
            Tournament l_tournamentManager = new Tournament();
            l_console.println("Set up the tournament using the following format:\n" + "tournament -M maps -P players -G number_of_games -D max_turns");
            TournamentHandler.processTournament(l_scanner.nextLine(), l_tournamentManager);
            ValidationUtil.validateTournamentCommand(l_tournamentManager);
            for (int l_gameNum = 0; l_gameNum < l_tournamentManager.getNumGames(); l_gameNum++) {
                GamePhaseHandler l_gamePhaseManager = new GamePhaseHandler();
                String l_loadMap = String.format(CommonConstants.LOAD_MAP + " ", l_tournamentManager.getMapList().get(l_gameNum));
                String l_addPlayers = CommonConstants.ADD_PLAYER_COMMAND + l_tournamentManager.getStrategyList().stream().map(player -> "-add " + player + " " + player).reduce("", (partialString, element) -> partialString + " " + element).trim();
                CommandHandler.processCommand(l_gamePhaseManager, Arrays.asList(l_loadMap, l_addPlayers, CommonConstants.ASSIGN_COUNTRIES_COMMAND));
<<<<<<< HEAD
                GameModeExecuter.gameModeHandler(l_gamePhaseManager);
<<<<<<< HEAD
            } else if (CommonConstants.TOURNAMENT_COMMAND.equals(l_gameMode)) {
                Tournament l_tournamentManager = new Tournament();
                TournamentHandler.processTournament(l_scanner.nextLine(), l_tournamentManager);
                for (int l_gameNum = 0; l_gameNum == l_tournamentManager.getNumGames(); l_gameNum++) {
                    GamePhaseHandler l_gamePhaseManager = new GamePhaseHandler();
                    String l_loadMap = String.format(CommonConstants.LOAD_MAP, l_tournamentManager.getMapList().get(l_gameNum));
                    String l_addPlayers = CommonConstants.ADD_PLAYER_COMMAND + l_tournamentManager.getStrategyList().stream().map(player -> "-add " + player + " " + player).reduce("", (partialString, element) -> partialString + " " + element).trim();
                    CommandHandler.processCommand(l_gamePhaseManager, Arrays.asList(l_loadMap, l_addPlayers, CommonConstants.ASSIGN_COUNTRIES_COMMAND));
                    GameModeExecuter.gameModeHandler(l_gamePhaseManager);
                    l_console.println(String.format("Game %d got ended. Winner: %s", l_gameNum + 1, l_tournamentManager.getGameWinners().get(l_gameNum)));
                }
<<<<<<< HEAD
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
=======
            } else {
                l_console.println("Game mode does not exists");
=======
=======
                GameModeExecuter.gameModeHandler(l_gamePhaseManager, l_tournamentManager);
>>>>>>> 1342fb3 (turns complated)
                l_console.println(String.format("Game %d got ended. Winner: %s", l_gameNum + 1, l_tournamentManager.getGameWinners().get(l_gameNum)));
>>>>>>> 118221f (tournaments)
            }
        } else {
            l_console.println("Game mode does not exists");
        }
        LogManager.logAction("Warzone has been ended");
<<<<<<< HEAD
>>>>>>> de7a03b (Tournament)
=======
=======
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
>>>>>>> 829dd75 (fixed Benevolent and Cheater stratergies - works completely)
>>>>>>> afdec0f (rebasing on main)
        l_console.println("Thanks for giving our game a try! We hope you have an epic time on the battlefield. \uD83D\uDE80\uD83D\uDD25");
    }
}
