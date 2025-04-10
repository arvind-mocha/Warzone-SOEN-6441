package org.com.Handlers;

import org.com.Constants.CommonConstants;
import org.com.Models.Tournament;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TournamentHandler implements Serializable {
    public static void processTournament(String p_command) {
        String[] l_commandsArray = p_command.split(" -");
        List<String> l_mapList = new ArrayList<>();
        List<String> l_strategyList = new ArrayList<>();
        String l_numGames = "0";
        String l_maxTurns = "0";
        for (int l_index = 1; l_index < l_commandsArray.length; l_index++) {
            String[] l_attributesArray = l_commandsArray[l_index].split(" -");

            for (String l_attribute : l_attributesArray) {
                switch (l_attribute) {
                    case CommonConstants.MAP_ATTRIBUTE -> l_mapList.addAll(List.of(l_attribute.split(",")));
                    case CommonConstants.PLAYER_ATTRIBUTE -> l_strategyList.addAll(List.of(l_attribute.split(",")));
                    case CommonConstants.GAME_ATTRIBUTE -> l_numGames = l_attribute;
                    case CommonConstants.TURNS_ATTRIBUTE -> l_maxTurns = l_attribute;
                }
            }
        }
    }
}
