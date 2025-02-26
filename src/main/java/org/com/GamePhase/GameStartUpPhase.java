package org.com.GamePhase;

import org.com.Constants.CommandOutputMessages;
import org.com.Handlers.Commands;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Game startup phase related codes are present here.
 *
 * @author Arvind Lakshmanan
 *
 */
public class GameStartUpPhase implements Phase {

    @Override
    public Phase getNextPhase() {
        return null;
    }

    @Override
    public ArrayList<String> getValidCommands() {
        return new ArrayList<>(Arrays.asList());
    }

    @Override
    public String getHelpMessage() {
        return CommandOutputMessages.GAME_PLAY_HELP;
    }
}
