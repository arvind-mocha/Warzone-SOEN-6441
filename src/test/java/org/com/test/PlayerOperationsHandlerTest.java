package org.com.test;

import org.com.Constants.Cards;
import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.GamePlayHandler;
import org.com.Handlers.IssueOrderHandler;
import org.com.Handlers.PlayerOperationsHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerOperationsHandlerTest {

    private GamePhaseHandler gamePhaseHandler;

    @BeforeEach
    void setUp() {
        gamePhaseHandler = new GamePhaseHandler();
        initializeMapWithNeighbors();
    }

    private void initializeMapWithNeighbors() {
        Map map = new Map();
        DefaultDirectedGraph<Country, DefaultEdge> countryMap = new DefaultDirectedGraph<>(DefaultEdge.class);

        Country countryA = new Country();
        countryA.setId(1);
        countryA.setName("CountryA");
        countryA.setArmyCount(5);
        countryA.setNeighbourCountryIds(new ArrayList<>(List.of(2)));

        Country countryB = new Country();
        countryB.setId(2);
        countryB.setName("CountryB");
        countryB.setArmyCount(3);
        countryB.setNeighbourCountryIds(new ArrayList<>(List.of(1)));

        countryMap.addVertex(countryA);
        countryMap.addVertex(countryB);

        map.setCountryMap(countryMap);
        gamePhaseHandler.setGameMap(map);
    }

    @Test
    void blockadeOrder_NoCardAvailable() {

        Player player = new Player("player1");
        Country targetCountry = new Country();
        targetCountry.setId(3);
        targetCountry.setName("CountryC");
        targetCountry.setArmyCount(5);
        targetCountry.setOwner(new Player("enemy")); // Country owned by another player

        // Player has no blockade cards
        player.get_cards().put(Cards.BLOCKADE_CARD, 0);  // Player has 0 blockade cards

        // Printing players card count before using Blockade
        System.out.println("Player's Cards before Blockade: " + player.get_cards());

        // Setting up map & game phase
        GamePhaseHandler handler = new GamePhaseHandler();
        Map map = new Map();
        DefaultDirectedGraph<Country, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        graph.addVertex(targetCountry);
        map.setCountryMap(graph);
        handler.setGameMap(map);
        handler.setPlayerList(new ArrayList<>(List.of(player)));

        // Exception is thrown when trying to use the blockade card
        Exception exception = assertThrows(Exception.class, () -> {
            IssueOrderHandler.processBlockadeCommand(handler, new String[]{"blockade", "CountryC"});
        });

        // Message should display that no blockade card is available
        assertTrue(exception.getMessage().contains("You don't have a blockade card to use"));

        // Verify the player’s card count is 0 and no blockade was made
        System.out.println("Player's Cards after Blockade attempt: " + player.get_cards());
        assertEquals(0, player.get_cards().get(Cards.BLOCKADE_CARD));  // Ensure no card was used

        // Verify that the country is not neutralized
        assertFalse(targetCountry.isCountryNeutral(), "The country should not be neutralized as the player had no cards.");
    }

    @Test
    void addPlayer_Success() throws Exception {
        PlayerOperationsHandler.processPlayerManagement("addplayer -add player1", gamePhaseHandler);
        assertEquals(1, gamePhaseHandler.getPlayerList().size());
    }

    @Test
    void addDuplicatePlayer_Failure() {
        assertThrows(Exception.class, () -> {
            PlayerOperationsHandler.processPlayerManagement("addplayer -add player1", gamePhaseHandler);
            PlayerOperationsHandler.processPlayerManagement("addplayer -add player1", gamePhaseHandler);
        });
    }

    @Test
    void assignCountries_Success() throws Exception {
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        gamePhaseHandler.setPlayerList(players);

        PlayerOperationsHandler.processAssignCountries(gamePhaseHandler);
        assertFalse(gamePhaseHandler.getGameMap().getCountryMap().vertexSet().isEmpty());
    }

    @Test
    void deployArmies_Valid() throws Exception {
        Player player = new Player("player1");
        Country country = gamePhaseHandler.getGameMap().getCountryMap().vertexSet().iterator().next();
        country.setOwner(player);
        gamePhaseHandler.setPlayerList(new ArrayList<>(List.of(player)));
        gamePhaseHandler.assignReinforcements();

        int initialPlayerArmies = player.get_armyCount();
        int initialCountryArmies = country.getArmyCount();

        IssueOrderHandler.processDeployArmies(gamePhaseHandler, new String[]{"deploy", "CountryA", "3"});

        assertEquals(initialCountryArmies + 3, country.getArmyCount());
        assertEquals(initialPlayerArmies - 3, player.get_armyCount());
    }

    @Test
    void deployArmies_UnownedCountry() {
        gamePhaseHandler.setPlayerList(new ArrayList<>(List.of(new Player("player1"))));
        assertThrows(Exception.class, () ->
                IssueOrderHandler.processDeployArmies(gamePhaseHandler, new String[]{"deploy", "CountryA", "3"})
        );
    }

    @Test
    void deployArmies_OverDeploy() throws Exception {
        Player player = new Player("player1");
        Country country = gamePhaseHandler.getGameMap().getCountryMap().vertexSet().iterator().next();
        country.setOwner(player);
        gamePhaseHandler.setPlayerList(new ArrayList<>(List.of(player)));
        gamePhaseHandler.assignReinforcements();

        assertThrows(Exception.class, () ->
                IssueOrderHandler.processDeployArmies(gamePhaseHandler, new String[]{"deploy", "CountryA", "10"})
        );
    }

    @Test
    void advanceArmies_Valid() throws Exception {
        Player player = new Player("player1");
        List<Country> countries = new ArrayList<>(gamePhaseHandler.getGameMap().getCountryMap().vertexSet());
        countries.forEach(c -> {
            c.setOwner(player);
            c.setArmyCount(5);
        });
        gamePhaseHandler.setPlayerList(new ArrayList<>(List.of(player)));

        IssueOrderHandler.processAdvanceCommand(gamePhaseHandler,
                new String[]{"advance", "CountryA", "CountryB", "2"}
        );

//        IssueOrderHandler.executeBufferedCommands(gamePhaseHandler);

        assertEquals(3, countries.get(0).getArmyCount());
        assertEquals(7, countries.get(1).getArmyCount());
    }

    @Test
    void advanceArmies_NonNeighbor() {
        Player player = new Player("player1");
        gamePhaseHandler.getGameMap().getCountryMap().vertexSet().iterator().next().setOwner(player);
        gamePhaseHandler.setPlayerList(new ArrayList<>(List.of(player)));

        assertThrows(Exception.class, () ->
                IssueOrderHandler.processAdvanceCommand(gamePhaseHandler,
                        new String[]{"advance", "CountryA", "CountryC", "2"}
                )
        );
    }

    @Test
    void advanceTurn_Valid() throws Exception {
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        gamePhaseHandler.setPlayerList(players);

        GamePlayHandler.advanceTurn(gamePhaseHandler);
        assertEquals(1, gamePhaseHandler.getCurrentPlayer());
    }

    @Test
    void advanceTurn_WrapAround() throws Exception {
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        gamePhaseHandler.setPlayerList(players);
        gamePhaseHandler.setCurrentPlayer(1);

        GamePlayHandler.advanceTurn(gamePhaseHandler);
        assertEquals(0, gamePhaseHandler.getCurrentPlayer());
    }
}
