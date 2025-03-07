package org.com.Models;

import java.util.List;

/**
 * The 'State' class is used to represent the current state of the game.
 * Contains the current state of all objects involved in the game.
 *      - State of the current game
 *      - Current Game Phase
 *      - List of Players added to the game
 *      - Current Player's Turn
 *
 *  @author Devasenan Murugan
 */


// Has the basic setter and getter methods of each of the attributes
public class State {
    private String d_gamePhase;
    private List<Player> d_playerList;
    private int d_currentPlayerTurn;
    private Map d_map;

    /**
     * Gets the current game phase.
     *
     * @return the current game phase
     */
    public String get_gamePhase() {
        return d_gamePhase;
    }

    /**
     * Sets the current game phase.
     *
     * @param d_gamePhase the new game phase
     */
    public void set_gamePhase(String d_gamePhase) {
        this.d_gamePhase = d_gamePhase;
    }

    /**
     * Gets the list of players.
     *
     * @return the list of players
     */
    public List<Player> get_playerList() {
        return d_playerList;
    }

    /**
     * Sets the list of players.
     *
     * @param d_playerList the new list of players
     */
    public void set_playerList(List<Player> d_playerList) {
        this.d_playerList = d_playerList;
    }

    /**
     * Gets the current player's turn.
     *
     * @return the current player's turn
     */
    public int get_currentPlayerTurn() {
        return d_currentPlayerTurn;
    }

    /**
     * Sets the current player's turn.
     *
     * @param d_currentPlayerTurn the new player's turn
     */
    public void set_currentPlayerTurn(int d_currentPlayerTurn) {
        this.d_currentPlayerTurn = d_currentPlayerTurn;
    }

    /**
     * Gets the map.
     *
     * @return the map
     */
    public Map get_map() {
        return d_map;
    }

    /**
     * Sets the map.
     *
     * @param d_map the game map
     */
    public void set_map(Map d_map) {
        this.d_map = d_map;
    }
}
