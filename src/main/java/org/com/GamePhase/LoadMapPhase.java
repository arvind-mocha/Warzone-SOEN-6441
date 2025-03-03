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
    public Phase getNextPhase() {
        return new GameStartUpPhase();
    }

    @Override
    public ArrayList<String> getValidCommands() {
        return new ArrayList<>(Arrays.asList(Commands.LOAD_MAP.getName(), Commands.EDIT_COUNTRY.getName(), Commands.EDIT_CONTINENT.getName(), Commands.VALIDATE_MAP.getName()));
    }

    @Override
    public String getHelpMessage() {
        return CommandOutputMessages.MAP_HELP;
    }

    @Override
    public void constructGameMap(String p_fileName, GamePhaseHandler p_gamePhaseHandler) throws Exception{
        var l_console = System.console();
        p_gamePhaseHandler.getGamePhase().constructGameMap(p_fileName, p_gamePhaseHandler);
        l_console.println("You can now add playes to the game");
        l_console.println(CommandOutputMessages.HELP_DEFAULT_MESSAGE);
    }
}
