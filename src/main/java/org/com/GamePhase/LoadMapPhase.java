package org.com.GamePhase;

import org.com.Controls.Commands;

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
}
