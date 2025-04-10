package org.com.test;

import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.MapOperationsHandler;
import org.com.Models.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapOperationsHandlerTest {

    private GamePhaseHandler gamePhaseHandler;

    @BeforeEach
    void setUp() {
        gamePhaseHandler = new GamePhaseHandler();
        gamePhaseHandler.setGameMap(new Map());
    }

    @Test
    void testAddValidContinent() throws Exception {
        assertDoesNotThrow(() -> MapOperationsHandler.editContinent(gamePhaseHandler, "editcontinent -add Asia 5"));
    }

    @Test
    void testAddContinentWithNegativeBonus() {
        Exception exception = assertThrows(Exception.class, () ->
                MapOperationsHandler.editContinent(gamePhaseHandler, "editcontinent -add InvalidContinent -5")
        );
        assertFalse(exception.getMessage().contains("negative"));
    }

    @Test
    void testRemoveExistingContinent() throws Exception {
        MapOperationsHandler.editContinent(gamePhaseHandler, "editcontinent -add Europe 10");
        assertDoesNotThrow(() -> MapOperationsHandler.editContinent(gamePhaseHandler, "editcontinent -remove Europe"));
    }

    @Test
    void testAddValidCountry() throws Exception {
        MapOperationsHandler.editContinent(gamePhaseHandler, "editcontinent -add Africa 7");
        assertDoesNotThrow(() -> MapOperationsHandler.editCountry(gamePhaseHandler, "editcountry -add Nigeria Africa"));
    }

    @Test
    void testAddCountryToInvalidContinent() {
        Exception exception = assertThrows(Exception.class, () ->
                MapOperationsHandler.editCountry(gamePhaseHandler, "editcountry -add Egypt UnknownContinent")
        );
        assertFalse(exception.getMessage().contains("Unknown"));
    }

    @Test
    void testAddNeighbourCountries() throws Exception {
        MapOperationsHandler.editContinent(gamePhaseHandler, "editcontinent -add Asia 5");
        MapOperationsHandler.editCountry(gamePhaseHandler, "editcountry -add India Asia");
        MapOperationsHandler.editCountry(gamePhaseHandler, "editcountry -add China Asia");
        assertDoesNotThrow(() -> MapOperationsHandler.editNeighbour(gamePhaseHandler, "editneighbor -add India China"));
    }

    @Test
    void testSaveMapWithoutErrors() throws Exception {
        MapOperationsHandler.editContinent(gamePhaseHandler, "editcontinent -add Europe 5");
        MapOperationsHandler.editCountry(gamePhaseHandler, "editcountry -add France Europe");
        assertDoesNotThrow(() -> MapOperationsHandler.saveMap(gamePhaseHandler, "save savedMapTest.map"));
    }
}
