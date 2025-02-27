package org.com.GamePhase;

import org.com.Constants.CommandOutputMessages;

import java.util.ArrayList;

public class IssueOrderPhase implements Phase {
    @Override
    public Phase getNextPhase() {
        return null;
    }

    @Override
    public ArrayList<String> getValidCommands() {
        return null;
    }

    @Override
    public String getHelpMessage() {
        return CommandOutputMessages.ISSUE_ORDER_HELP;
    }
}
