package org.com.GamePhase;

import java.util.ArrayList;

/**
 * This interface is the basic structure of the entire phase management.
 *
 * @author Arvind Lakshmanan
 *
 */
public interface Phase {

    Phase nextPhase();

    ArrayList<String> getValidCommands();


    /**
     * Default methods which doesn't need any separate implementation
     */
    default ArrayList<String> getCommonCommands(){
        return null;
    }
}
