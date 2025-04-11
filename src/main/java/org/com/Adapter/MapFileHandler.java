package org.com.Adapter;

import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Map;

/**
 * This is the interface class implementing the adapter pattern between Conquest and Domination maps
 *
 * @author Barath
 */

public interface MapFileHandler {
    void loadMap(String filePath, GamePhaseHandler gamePhaseHandler) throws Exception;
    void saveMap(String filePath, Map map) throws Exception;
}
