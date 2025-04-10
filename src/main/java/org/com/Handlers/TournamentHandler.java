package org.com.Handlers;

import org.com.Constants.CommonConstants;
import org.com.Models.Tournament;

import java.io.Serializable;
import java.util.List;

public class TournamentHandler implements Serializable {
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
}
