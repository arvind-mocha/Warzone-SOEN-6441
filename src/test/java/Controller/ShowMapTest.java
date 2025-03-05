package Controller;

import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.MapOperationsHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the showMap command.
 * @author Swathi Priya P
 */
class ShowMapTest {

    // The Europe map's file name should be in the appropriate directory.
    private static final String l_testMapFile = "europe.map";

    // GamePhaseHandler instance to manage game phases and store the map
    private GamePhaseHandler d_gamePhaseHandler;

    //The setup method loads the Europe map and initialises the GamePhaseHandler before every test.
    @BeforeEach
    void setUp() {
        d_gamePhaseHandler = new GamePhaseHandler();
    }

    // Verifying showMap command doesn't work when no map is loaded.
    @Test
    void testShowGameMap_NoMapLoaded() {

        String[][] l_actualData = extractMapData(d_gamePhaseHandler);

        // The extracted data should be null or empty
        assertNull(l_actualData, "Extracted map data should be null when no map is loaded.");
        System.out.println("Please load a map before using showMap command ");
    }

    //This test verifies that the extracted map data matches the expected structure.
    @Test
    void testShowGameMap() throws Exception {

        // Load the Europe map before running the test
        MapOperationsHandler.processMap(d_gamePhaseHandler, l_testMapFile, false);

        // Ensure that the map has been successfully loaded
        assertNotNull(d_gamePhaseHandler.getGameMap(), "Game map should be loaded before calling showMap.");

        // Extract actual map data in the same format as processShowGameMap
        String[][] l_actualData = extractMapData(d_gamePhaseHandler);

        // Expected Data Format for the Europe Map where each row represents a country with the following attributes:
        // {Country ID, Country Name, Continent Name, Neighbors, Owner (default "-"), Army Count (default "0")}
        String[][] l_expectedData = {
                {"1", "England", "North_Europe", "8 21 6 7 5 2 3 4", "-", "0"},
                {"2", "Scotland", "North_Europe", "8 1 3", "-", "0"},
                {"3", "N_Ireland", "North_Europe", "1 2", "-", "0"},
                {"4", "Rep_Ireland", "North_Europe", "22 1 5", "-", "0"},
                {"5", "Wales", "North_Europe", "1 4", "-", "0"},
                {"6", "Belgium", "North_Europe", "9 21 24 7 1", "-", "0"},
                {"7", "Netherlands", "North_Europe", "9 6 1", "-", "0"},
                {"8", "Denmark", "East_Europe", "10 9 2 1", "-", "0"},
                {"9", "Germany", "East_Europe", "15 14 11 10 8 7 6 24", "-", "0"},
                {"10", "Poland", "East_Europe", "12 11 8 9", "-", "0"},
                {"11", "Czech_Rep", "East_Europe", "14 12 9 10", "-", "0"},
                {"12", "Slovakia", "East_Europe", "14 13 10 11", "-", "0"},
                {"13", "Hungary", "South_Europe", "14 12", "-", "0"},
                {"14", "Austria", "South_Europe", "16 15 13 9 11 12", "-", "0"},
                {"15", "Switzerland", "South_Europe", "16 14 9 21", "-", "0"},
                {"16", "Italy", "South_Europe", "18 17 15 14", "-", "0"},
                {"17", "Sicily", "South_Europe", "18 16", "-", "0"},
                {"18", "Sardinia", "South_Europe", "20 17 19 16", "-", "0"},
                {"19", "Corsica", "South_Europe", "20 18", "-", "0"},
                {"20", "Majorca", "West_Europe", "22 19 18", "-", "0"},
                {"21", "France", "West_Europe", "22 15 24 6 1", "-", "0"},
                {"22", "Spain", "West_Europe", "23 4 20 21", "-", "0"},
                {"23", "Portugal", "West_Europe", "22", "-", "0"},
                {"24", "Luxembourg", "West_Europe", "9 21 6", "-", "0"},
        };

        // Verify if the extracted map data matches the expected structure and prints the result
        boolean l_isMatch = Arrays.deepEquals(l_expectedData, l_actualData);
        if (l_isMatch) {
            System.out.println("Loaded map and displayed map are the same. Test Passed!");
        } else {
            System.out.println("Loaded map and displayed map are different. Test Failed!");
        }
        // Fail the test if the extracted data does not match the expected data
        assertTrue(l_isMatch, "Loaded map and displayed map should match.");
    }

    /**
     * Extracts the game map data in the same format expected by processShowGameMap.
     *
     * @param p_gamePhaseHandler The game phase handler containing the loaded game map.
     * @return A 2D array representing the game map data.
     */
    private String[][] extractMapData(GamePhaseHandler p_gamePhaseHandler) {
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        if (l_gameMap == null) return null;

        var l_countries = l_gameMap.getCountryMap().vertexSet();
        String[][] l_data = new String[l_countries.size()][6]; // Array to store extracted data
        int index = 0;

        for (Country l_country : l_countries) {
            StringBuilder l_neighbors = new StringBuilder();
            for (int l_neighborID : l_country.getNeighbourCountryIds()) {
                l_neighbors.append(l_neighborID).append(" ");
            }

            // Store extracted l_country data in the expected format
            l_data[index][0] = String.valueOf(l_country.getId());      // Country ID
            l_data[index][1] = l_country.getName();                    // Country Name
            l_data[index][2] = l_country.getContinentName();           // Continent Name
            l_data[index][3] = l_neighbors.toString().trim();          // Neighbors
            l_data[index][4] = "-"; // No owner assigned in this test
            l_data[index][5] = "0"; // No armies assigned
            index++;
        }
        return l_data;
    }
}
