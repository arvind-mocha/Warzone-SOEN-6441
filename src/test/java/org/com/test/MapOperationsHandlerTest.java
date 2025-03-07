package org.com.test;

import org.com.Handlers.MapOperationsHandler;
import org.com.Models.Map;
import org.com.Handlers.GamePhaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapOperationsHandlerTest {

    private GamePhaseHandler gamePhaseHandler;

    @BeforeEach
    void setUp() {
        gamePhaseHandler = new GamePhaseHandler();
    }

    @Test
    void testEditMapWithValidFile() {
        assertDoesNotThrow(() -> MapOperationsHandler.editMap(gamePhaseHandler, "valid_map.map"));
    }

    @Test
    void testEditMapWithInvalidFile() {
        assertDoesNotThrow(() -> MapOperationsHandler.editMap(gamePhaseHandler, "invalid_file.map"));
    }

    @Test
    void testEditContinent_Add() throws Exception {
        gamePhaseHandler.setGameMap(new Map());
        assertDoesNotThrow(() -> MapOperationsHandler.editContinent(gamePhaseHandler, "add ContinentA 5"));
    }

    @Test
    void testEditContinent_Remove() throws Exception {
        gamePhaseHandler.setGameMap(new Map());
        assertDoesNotThrow(() -> MapOperationsHandler.editContinent(gamePhaseHandler, "remove ContinentA"));
    }

    @Test
    void testEditCountry_Add() throws Exception {
        gamePhaseHandler.setGameMap(new Map());
        assertDoesNotThrow(() -> MapOperationsHandler.editCountry(gamePhaseHandler, "add CountryA ContinentA"));
    }

    @Test
    void testEditCountry_Remove() throws Exception {
        gamePhaseHandler.setGameMap(new Map());
        assertDoesNotThrow(() -> MapOperationsHandler.editCountry(gamePhaseHandler, "remove CountryA"));
    }

    @Test
    void testEditNeighbour_Add() throws Exception {
        gamePhaseHandler.setGameMap(new Map());
        assertDoesNotThrow(() -> MapOperationsHandler.editNeighbour(gamePhaseHandler, "add CountryA CountryB"));
    }

    @Test
    void testEditNeighbour_Remove() throws Exception {
        gamePhaseHandler.setGameMap(new Map());
        assertDoesNotThrow(() -> MapOperationsHandler.editNeighbour(gamePhaseHandler, "remove CountryA CountryB"));
    }

    @Test
    void testSaveMap_Valid() throws Exception {
        gamePhaseHandler.setGameMap(new Map());
        assertDoesNotThrow(() -> MapOperationsHandler.saveMap(gamePhaseHandler, "save valid_map.map"));
    }

    @Test
    void testSaveMap_Invalid() throws Exception {
        gamePhaseHandler.setGameMap(new Map());
        assertDoesNotThrow(() -> MapOperationsHandler.saveMap(gamePhaseHandler, "save invalid_map.map"));
    }
}
