package org.com.Handlers;

import org.com.Constants.CommonConstants;
import org.com.Constants.CommonErrorMessages;
import org.com.Models.Tournament;
import org.com.Utils.DisplayUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * The `TournamentHandler` class is responsible for managing tournament-related operations.
 * It includes methods to process tournament configurations and display tournament results.
 * This class implements `Serializable` to allow its instances to be serialized.
 *
 * @author Arvind Lakshmanan
 */
public class TournamentHandler implements Serializable {

    /**
     * Processes the tournament configuration based on the input command.
     * Parses the command to extract attributes such as maps, players, number of games, and maximum turns,
     * and updates the tournament object accordingly.
     *
     * @param p_command   The input command containing tournament configuration details.
     * @param l_tournament The tournament object to be updated with the parsed configuration.
     * @throws Exception If there is an error in parsing or invalid input.
     */
    public static void processTournament(String p_command, Tournament l_tournament) throws Exception {
        String[] l_commandsArray = p_command.split(" -");
        for (int l_index = 1; l_index < l_commandsArray.length; l_index++) {
            String l_attribute = l_commandsArray[l_index].substring(0, 1);
            String l_value = l_commandsArray[l_index].substring(2, l_commandsArray[l_index].length());
            switch (l_attribute) {
                case CommonConstants.MAP_ATTRIBUTE -> l_tournament.setMapList(List.of(l_value.split(",\\s*")));
                case CommonConstants.PLAYER_ATTRIBUTE -> l_tournament.setStrategyList(List.of(l_value.split(",\\s*")));
                case CommonConstants.GAME_ATTRIBUTE -> l_tournament.setNumGames(Integer.parseInt(l_value));
                case CommonConstants.TURNS_ATTRIBUTE -> l_tournament.setMaxTurns(Integer.parseInt(l_value));
            }
        }
    }

    /**
     * Processes and displays the results of a tournament in a tabular format.
     *
     * @param p_tournament The tournament object containing the results.
     * @throws Exception If the tournament has not been played or no results are available.
     */
    public static void processTournamentResult(Tournament p_tournament) throws Exception {
        if (p_tournament == null || p_tournament.getGameWinners().isEmpty()) {
            throw new Exception(CommonErrorMessages.TOURNAMENT_NOT_PLAYED);
        }

        int l_totalGames = p_tournament.getNumGames();
        List<String> l_columns = new ArrayList<>();
        l_columns.add("Maps");
        for (int i = 1; i <= l_totalGames; i++) {
            l_columns.add("Game " + i);
        }

        HashMap<String, ArrayList<String>> p_gameWinners = p_tournament.getGameWinners();
        String[][] l_data = new String[p_gameWinners.size()][l_columns.size()];
        Set<String> l_maps = p_gameWinners.keySet();
        int l_rowNum = 0;
        for (String l_map : l_maps) {
            l_data[l_rowNum][0] = l_map; // First column is the map name
            ArrayList<String> l_winners = p_gameWinners.get(l_map);
            for (int l_game = 0; l_game < l_winners.size(); l_game++) {
                l_data[l_rowNum][l_game + 1] = l_winners.get(l_game); // Populate game winners
            }
            l_rowNum++;
        }

        DisplayUtil.displayData(l_data, l_columns.toArray(new String[0]), String.format("Tournament result"));
    }
}
