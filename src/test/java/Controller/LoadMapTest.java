package Controller;

import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.MapOperationsHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the processMap method in LoadMap.
 * Validates that a correct map file loads successfully into the game while invalid maps do not load.
 * @author Swathi Priya P
 */
public class LoadMapTest {

    private static final String l_validMap = "europe.map"; // A valid test map file
    private static final String l_emptyMap = "empty.map"; // A file with no data
    private static final String l_invalidMap = "nonexistent.map"; // A file with no data
    private static final String l_malformedMap = "malformed.map"; // A file with an incorrect structure

    private GamePhaseHandler d_gamePhaseHandler;

    @BeforeEach
    void setUp() {
        d_gamePhaseHandler = new GamePhaseHandler();
    }

    //Checking the Load Map feature when attempting to load a valid map file
    @Test
    public void testValidMap() throws Exception {

        // Call method to load the map
        MapOperationsHandler.processMap(d_gamePhaseHandler, l_validMap, false);

        // Assertions to verify map is loaded and contains valid data
        assertNotNull(d_gamePhaseHandler.getGameMap(), "Game map should not be null after loading");
        assertFalse(d_gamePhaseHandler.getGameMap().getCountryMap().vertexSet().isEmpty(), "Map should contain countries");

    }

    // Checking the Load Map feature when attempting to load a non-existent map file
    @Test
    void testLoadNonExistentMap() throws Exception {

        // loading a non-existent map
        MapOperationsHandler.processMap(d_gamePhaseHandler, l_invalidMap, false);

        // Since processMap() does not throw an exception, we check if the map failed to load
        assertNull(d_gamePhaseHandler.getGameMap(), "Game map should be null for non-existent files.");
    }

    // Checking the Load Map feature when attempting to load a empty map file
    @Test
    void testLoadEmptyMap() {
        Exception exception = assertThrows(Exception.class, () -> {
            MapOperationsHandler.processMap(d_gamePhaseHandler, l_emptyMap, false);
        });
        System.out.println("Loading Map failed as Map should not be empty.");
    }

    // Checking the Load Map feature when attempting to load a Malformed map file (Invalid Structure)
    @Test
    void testLoadMalformedMap() throws Exception {

        // Loading a malformed map
        MapOperationsHandler.processMap(d_gamePhaseHandler, l_malformedMap, false);

        // check if map is invalid
        assertTrue(d_gamePhaseHandler.getGameMap() == null ||
                        d_gamePhaseHandler.getGameMap().getCountryMap().vertexSet().isEmpty(),
                "Malformed map should not load correctly.");
    }
}
