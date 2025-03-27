package org.com.GameLog;

/**
 * Shows an observer that can be updated by an observable
 *
 * @author Devasenan Murugan
 */

public interface Observer {
    /**
     * Updates observers
     *
     * @param p_observable observable object
     */
    void update(Observable p_observable);
}