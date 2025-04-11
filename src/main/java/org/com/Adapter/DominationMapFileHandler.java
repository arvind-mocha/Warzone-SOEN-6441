package org.com.Adapter;

import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.MapOperationsHandler;
import org.com.Models.Map;


/**
 * This class is used to handle domination maps implementing the MapFileHandler interface
 *
 * @author Barath
 */
public class DominationMapFileHandler implements MapFileHandler {

    /**
     * The loadMap method here is used to initiate the processMap in MapOperationsHandler
     *
     * @param filePath specifies the path of the file to be loaded
     * @param gamePhaseHandler specifies the phase of the game
     * @throws Exception exception
     */
    @Override
    public void loadMap(String filePath, GamePhaseHandler gamePhaseHandler) throws Exception {
        MapOperationsHandler.processMap(gamePhaseHandler, filePath, false, false);
    }


    /**
     * The saveMap as the name suggests is used to save the map using the MapOperationsHandler
     *
     * @param filePath is the path of the map file
     * @param map Map object
     * @throws Exception Exception message
     */
    @Override
    public void saveMap(String filePath, Map map) throws Exception {
        // You can refactor MapOperationsHandler.saveMap logic to move here.
        // Or just reuse the static method as is

        GamePhaseHandler tempHandler = new GamePhaseHandler();
        tempHandler.setGameMap(map);
        tempHandler.setMapFileName(filePath);
        MapOperationsHandler.saveMap(tempHandler, filePath);
    }
}
