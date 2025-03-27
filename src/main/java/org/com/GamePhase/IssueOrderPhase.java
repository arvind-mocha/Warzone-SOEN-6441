package org.com.GamePhase;

import org.com.Constants.CommandOutputMessages;
import org.com.Handlers.Commands;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Issue Order Phase implements the Phase interface and contains code related to the command given by the Player.
 *
 * @author Arvind Lakshmanan
 * @author Barath Sundararaj
 *
 */

public class IssueOrderPhase implements Phase {
    @Override
    public Phase getNextPhase() {
        return null;
    }

    @Override
    public ArrayList<String> getValidCommands() {
        return new ArrayList<>(Arrays.asList(Commands.DEPLOY_ARMY_COMMAND.getName(), Commands.ADVANCE_ARMY_COMMAND.getName(), Commands.COMMIT.getName(), Commands.BLOCKADE_COMMAND.getName()));
    }

    @Override
    public String getHelpMessage() {
        return CommandOutputMessages.ISSUE_ORDER_HELP;
    }
}
