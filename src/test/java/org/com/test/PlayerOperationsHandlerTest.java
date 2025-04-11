package org.com.test;

import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.PlayerOperationsHandler;
import org.com.Models.Map;
import org.com.Models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerOperationsHandlerTest {

    private GamePhaseHandler gamePhaseHandler;

    @BeforeEach
    void setUp() {
        gamePhaseHandler = new GamePhaseHandler();
        gamePhaseHandler.setGameMap(new Map());
    }

    @Test
    void testAddPlayerWithStrategy() throws Exception {
        String command = "gameplayer -add player1 human";
        assertDoesNotThrow(() -> PlayerOperationsHandler.processPlayerManagement(command, gamePhaseHandler));

        List<Player> players = gamePhaseHandler.getPlayerList();
        assertEquals(1, players.size());
        assertEquals("player1", players.get(0).get_name());
    }

    @Test
    void testRemoveExistingPlayer() throws Exception {
        String addCommand = "gameplayer -add player1 human";
        String removeCommand = "gameplayer -remove player1";

        PlayerOperationsHandler.processPlayerManagement(addCommand, gamePhaseHandler);
        assertEquals(1, gamePhaseHandler.getPlayerList().size());

        assertDoesNotThrow(() -> PlayerOperationsHandler.processPlayerManagement(removeCommand, gamePhaseHandler));
        assertEquals(0, gamePhaseHandler.getPlayerList().size());
    }

    @Test
    void testRemoveNonExistentPlayerThrowsException() throws Exception {
        String removeCommand = "gameplayer -remove ghost";

        Exception exception = assertThrows(Exception.class, () ->
                PlayerOperationsHandler.processPlayerManagement(removeCommand, gamePhaseHandler)
        );

        assertFalse(exception.getMessage().contains("does not exist"));
    }

    @Test
    void testAssignCountriesWithInsufficientPlayersThrowsException() {
        Exception exception = assertThrows(Exception.class, () ->
                PlayerOperationsHandler.processAssignCountries(gamePhaseHandler)
        );
        assertFalse(exception.getMessage().contains("minimum"));
    }
}
