package Controller;

import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.MapOperationsHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

/**
 * Unit test for the processMap method in LoadMap.
 * This test verifies if a valid map file is correctly loaded into the game.
 * @author Swathi Priya P
 */

public class LoadMapTest {

    private static final String l_testMapFile = "europe.map"; // Temporary map file for testing

    //Tests if processMap correctly loads a map from a file.

    @Test
    public void testProcessMap() throws Exception {

        GamePhaseHandler d_gamePhaseHandler = new GamePhaseHandler();

        // Call method to load the map
        MapOperationsHandler.processMap(d_gamePhaseHandler, l_testMapFile, false);

        // Assertions to verify correct loading of the map
        assertNotNull(d_gamePhaseHandler.getGameMap(), "Game map should not be null after loading");
        assertFalse(d_gamePhaseHandler.getGameMap().getCountryMap().vertexSet().isEmpty(), "Map should contain countries");

        // Delete the test map file after execution
        new File(l_testMapFile).delete();
    }
}
