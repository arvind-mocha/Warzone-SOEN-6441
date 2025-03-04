package Controller;

import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.MapOperationsHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit test for the processMap method in LoadMap.
 * This test verifies a valid map file which is correctly loaded into the game is retrieved.
 *
 * @Author Swathi Priya Pasumarthy
 */
class ShowMapTest {

    private static final String l_testMapFile = "europe.map"; // Temporary map file for testing
    private GamePhaseHandler p_gamePhaseHandler;

    @BeforeEach
    void setUp() throws Exception {
        p_gamePhaseHandler = new GamePhaseHandler();
        MapOperationsHandler.processMap(p_gamePhaseHandler, l_testMapFile, false);
    }

    @Test
    void testShowGameMap() throws Exception {
        // **Verify that the map has loaded correctly.**
        assertNotNull(p_gamePhaseHandler.getGameMap(), "The game map must be fully loaded before invoking showMap.");

        // Invoke the method to show the map.
        MapOperationsHandler.processShowGameMap(p_gamePhaseHandler);

       //Keeps the output window open for 6 seconds
        Thread.sleep(6000);

        // **Cleanup: After execution, remove the test map file.**
        new File(l_testMapFile).delete();
    }
}
