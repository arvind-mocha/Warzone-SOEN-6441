package Controller;

import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.MapOperationsHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

/**
 * Unit test for the processMap method in LoadMap.
 * This test verifies if a valid map file is correctly loaded into the game.
 *
 * @Author Swathi Priya Pasumarthy
 */

public class LoadMapTest {

    private static final String l_testMapFile = "europe.map"; // Temporary map file for testing

    /**
     * Tests if processMap correctly loads a map from a file.
     * Steps:
     * 1. Create a test map file with continents, countries, and borders.
     * 2. Call processMap() to load the file into GamePhaseHandler.
     * 3. Verify that the map is correctly loaded by checking:
     *    - The game map is not null.
     *    - The game map contains at least one country.
     */

    @Test
    public void testProcessMap() throws Exception {
        //createTestMapFile(); // Generate a temporary map file for testing
        GamePhaseHandler p_gamePhaseHandler = new GamePhaseHandler();

        // Call method to load the map
        MapOperationsHandler.processMap(p_gamePhaseHandler, l_testMapFile, false);

        // Assertions to verify correct loading of the map
        assertNotNull(p_gamePhaseHandler.getGameMap(), "Game map should not be null after loading");
        assertFalse(p_gamePhaseHandler.getGameMap().getCountryMap().vertexSet().isEmpty(), "Map should contain countries");

        // Cleanup: Delete the test map file after execution
        new File(l_testMapFile).delete();
    }
}
