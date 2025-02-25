package org.com.GamePhase;

import org.com.Constants.CommandOutputMessages;
import org.com.Handlers.Commands;
import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.MapOperationsHandler;
import org.com.Models.Map;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Initial phase of the game where the player initialises a map and the code related to this are present here.
 *
 * @author Arvind Lakshmanan
 *
 */

public class LoadMapPhase implements Phase{
    @Override
    public Phase nextPhase() {
        return new GameStartUpPhase();
    }

    @Override
    public ArrayList<String> getValidCommands() {
        return new ArrayList<>(Arrays.asList(Commands.LOAD_MAP.d_name, Commands.EDIT_COUNTRY.d_name, Commands.EDIT_CONTINENT.d_name));
    }

    @Override
    public void constructGameMap(String p_fileName, GamePhaseHandler p_gamePhaseHandler) throws Exception{
        var console = System.console();
        Map l_gameMap = MapOperationsHandler.processMap(p_gamePhaseHandler, p_fileName);
//        MapOperationsHandler.validateMap(l_gameMap);

        //Set the game phase to start up phase
        p_gamePhaseHandler.setGamePhase(this.nextPhase());
        p_gamePhaseHandler.setGameMap(l_gameMap);
        console.print("Next, add players to the game");
        console.print(CommandOutputMessages.HELP_DEFAULT_MESSAGE);
    }
}
