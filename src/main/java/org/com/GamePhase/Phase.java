package org.com.GamePhase;

import java.util.ArrayList;

/**
 * This interface is the basic structure of the entire phase management.
 *
 * @author Arvind Lakshmanan
 */
public interface Phase {

    /**
     * Retrieves the next phase of the game.
     *
     * @return the next Phase object.
     */
    Phase getNextPhase();

    /**
     * Retrieves valid commands of this phase.
     *
     * @return the next Phase object.
     */
    ArrayList<String> getValidCommands();

    /**
     * Retrieves commands applicable for this phase of the game.
     *
     * @return the next Phase object.
     */
    String getHelpMessage();

    default String getGameState() {
        return null;
    }
}
