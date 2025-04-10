package org.com.Handlers;

import org.com.Constants.CommonConstants;
import org.com.Models.Tournament;
import org.com.Utils.ValidationUtil;

import java.io.Serializable;
import java.util.List;

public class TournamentHandler implements Serializable {
    public static void processTournament(String p_command, Tournament l_tournament) throws Exception {
        String[] l_commandsArray = p_command.split(" -");
        ValidationUtil.validateTournamentCommand(l_commandsArray);
        for (int l_index = 1; l_index < l_commandsArray.length; l_index++) {
            String[] l_attributesArray = l_commandsArray[l_index].split(" -");

            for (String l_attribute : l_attributesArray) {
                switch (l_attribute) {
                    case CommonConstants.MAP_ATTRIBUTE -> l_tournament.setMapList(List.of(l_attribute.split(",")));
                    case CommonConstants.PLAYER_ATTRIBUTE -> l_tournament.setStrategyList(List.of(l_attribute.split(",")));
                    case CommonConstants.GAME_ATTRIBUTE -> l_tournament.setNumGames(Integer.parseInt(l_attribute));
                    case CommonConstants.TURNS_ATTRIBUTE -> l_tournament.setMaxTurns(Integer.parseInt(l_attribute));
                }
            }
        }
    }
}
