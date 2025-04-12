package org.com.GamePhase;

import org.com.Constants.CommandOutputMessages;
import org.com.Handlers.Commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Game startup phase related codes are present here.
 * The <b>GameStartUpPhase</b> implements the <b>Phase Interface</b>
 *
 * @author Arvind Lakshmanan
 */
public class GameStartUpPhase implements Phase, Serializable {

    @Override
    public Phase getNextPhase() {
        return new IssueOrderPhase();
    }

    @Override
    public ArrayList<String> getValidCommands() {
        return new ArrayList<>(Arrays.asList(Commands.ADD_PLAYER.getName(), Commands.ASSIGN_COUNTRIES.getName(), Commands.SAVE_GAME.getName(), Commands.LOAD_GAME.getName()));
    }

    @Override
    public String getHelpMessage() {
        return CommandOutputMessages.GAME_PLAY_HELP;
    }
}
