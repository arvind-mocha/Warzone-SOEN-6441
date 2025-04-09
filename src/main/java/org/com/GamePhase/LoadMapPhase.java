package org.com.GamePhase;

import org.com.Constants.CommandOutputMessages;
import org.com.Handlers.Commands;
import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.MapOperationsHandler;
import org.com.Models.Map;
import org.jgrapht.alg.linkprediction.CommonNeighborsLinkPrediction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Initial phase of the game where the player initialises a map and the code related to this are present here.
 *
 * @author Arvind Lakshmanan
 *
 */

public class LoadMapPhase implements Phase, Serializable {
    @Override
    public Phase getNextPhase() {
        return new GameStartUpPhase();
    }

    @Override
    public ArrayList<String> getValidCommands() {
        return new ArrayList<>(Arrays.asList(Commands.LOAD_MAP.getName(), Commands.EDIT_MAP.getName(), Commands.EDIT_COUNTRY.getName(), Commands.EDIT_CONTINENT.getName(), Commands.EDIT_NEIGHBOUR.getName(), Commands.VALIDATE_MAP.getName(), Commands.SAVE_MAP.getName(), Commands.TOURNAMENT.getName()));
    }

    @Override
    public String getHelpMessage() {
        return CommandOutputMessages.MAP_HELP;
    }
}
