package org.com.GamePhase;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonErrorMessages;
import org.com.Handlers.GamePhaseHandler;

import java.util.ArrayList;

/**
 * This interface is the basic structure of the entire phase management.
 *
 * @author Arvind Lakshmanan
 *
 */
public interface Phase {

    Phase getNextPhase();

    ArrayList<String> getValidCommands();

    String getHelpMessage();
    /**
     * Default methods which doesn't need any separate implementation
     */
    default ArrayList<String> getCommonCommands(){
        return null;
    }

    default void constructGameMap(String p_fileName, GamePhaseHandler p_gamePhaseHandler) throws Exception{
        System.console().println(CommonErrorMessages.INVALID_COMMAND);
    }
}
