package org.com.Handlers;

import org.com.GamePhase.LoadMapPhase;
import org.com.GamePhase.Phase;
import org.com.Models.Continent;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the game phase and changes after each players turn.
 *
 * @author Arvind Lakshmanan
 *
 */

public class GamePhaseHandler {

    private Phase d_gamePhase;
    private List<Player> d_playerList;
    private int d_currentPlayer;
    private Map d_gameMap;
    private String d_mapFileName;
    private int d_turnsCompleted;

    /**
     * Constructor for GamePhaseHandler.
     * Initializes the game phase to LoadMapPhase and sets up player list and advance commands buffer.
     */
    public GamePhaseHandler() {
        this.d_gamePhase = new LoadMapPhase();
        this.d_playerList = new ArrayList<>();
        this.d_turnsCompleted = 0;
    }

    /**
     * Retrieves the current game phase.
     *
     * @return the current Phase object.
     */
    public Phase getGamePhase() {
        return d_gamePhase;
    }

    /**
     * Sets the current game phase.
     *
     * @param p_gamePhase the new Phase object to set.
     */
    public void setGamePhase(Phase p_gamePhase) {
        this.d_gamePhase = p_gamePhase;
    }

    /**
     * Retrieves the list of players.
     *
     * @return the list of Player objects.
     */
    public List<Player> getPlayerList() {
        return d_playerList;
    }

    /**
     * Sets the list of players.
     *
     * @param p_playerList the new list of Player objects to set.
     */
    public void setPlayerList(List<Player> p_playerList) {
        this.d_playerList = p_playerList;
    }

    /**
     * Retrieves the current player index.
     *
     * @return the index of the current player.
     */
    public int getCurrentPlayer() {
        return d_currentPlayer;
    }

    /**
     * Sets the current player index.
     *
     * @param p_currentPlayer the new index of the current player to set.
     */
    public void setCurrentPlayer(int p_currentPlayer) {
        this.d_currentPlayer = p_currentPlayer;
    }

    /**
     * Retrieves the game map.
     *
     * @return the Map object representing the game map.
     */
    public Map getGameMap() {
        return d_gameMap;
    }

    /**
     * Sets the game map.
     *
     * @param p_gameMap the new Map object to set.
     */
    public void setGameMap(Map p_gameMap) {
        this.d_gameMap = p_gameMap;
    }

    /**
     * Retrieves the map file name.
     *
     * @return the name of the map file.
     */
    public String getMapFileName() {
        return d_mapFileName;
    }

    /**
     * Sets the map file name.
     *
     * @param p_mapFileName the new name of the map file to set.
     */
    public void setMapFileName(String p_mapFileName) {
        this.d_mapFileName = p_mapFileName;
    }

    /**
     * Assigns reinforcements to all players based on the number of countries they own and the continents they control.
     */
    public void assignReinforcements() {
        int l_numArmies = Math.max(Math.divideExact(d_gameMap.getCountryMap().vertexSet().size(), d_playerList.size()+1), 3);
        for (Player l_player : d_playerList) {
            for (Continent l_continent : l_player.get_continents()) {
                l_numArmies = l_numArmies + l_continent.getValue();
            }
            l_player.set_armyCount(l_player.get_armyCount() + l_numArmies);
            System.console().println("Army count :: " + l_numArmies + "\tAssigned to player :: "+l_player.get_name());
        }
        LogUtil.Logger(GamePhaseHandler.class.getName(), Level.INFO, "Armies have been assigned to all player");
    }

    /**
     * Retrieves the number of turns completed.
     *
     * @return the number of turns completed.
     */
    public int getTurnsCompleted() {
        return d_turnsCompleted;
    }

    /**
     * Sets the number of turns completed.
     *
     * @param p_turnsCompleted the new number of turns completed to set.
     */
    public void setTurnsCompleted(int p_turnsCompleted) {
        this.d_turnsCompleted = p_turnsCompleted;
    }
}
