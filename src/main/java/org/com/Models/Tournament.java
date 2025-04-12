package org.com.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Tournament class represents a tournament configuration and its results.
 * It implements Serializable to allow object serialization.
 *
 * @author Arvind Lakshmanan
 */
public class Tournament implements Serializable {

    private List<String> d_mapList;
    private List<String> d_strategyList;
    private int d_numGames;
    private int d_maxTurns;
    private HashMap<String, ArrayList<String>> d_gameWinners;

    public Tournament() {
        this.d_mapList = new ArrayList<>();
        this.d_strategyList = new ArrayList<>();
        this.d_numGames = 0;
        this.d_maxTurns = 0;
        this.d_gameWinners = new HashMap<>();
    }

    /**
     * Retrieves the list of maps configured for the tournament.
     *
     * @return A list of map names.
     */
    public List<String> getMapList() {
        return d_mapList;
    }

    /**
     * Sets the list of maps for the tournament.
     *
     * @param p_mapList A list of map names to be set.
     */
    public void setMapList(List<String> p_mapList) {
        this.d_mapList = p_mapList;
    }

    /**
     * Retrieves the list of strategies configured for the tournament.
     *
     * @return A list of strategy names.
     */
    public List<String> getStrategyList() {
        return d_strategyList;
    }

    /**
     * Sets the list of strategies for the tournament.
     *
     * @param p_strategyList A list of strategy names to be set.
     */
    public void setStrategyList(List<String> p_strategyList) {
        this.d_strategyList = p_strategyList;
    }

    /**
     * Retrieves the number of games configured for the tournament.
     *
     * @return The number of games.
     */
    public int getNumGames() {
        return d_numGames;
    }

    /**
     * Sets the number of games for the tournament.
     *
     * @param p_numGames The number of games to be set.
     */
    public void setNumGames(int p_numGames) {
        this.d_numGames = p_numGames;
    }

    /**
     * Retrieves the maximum number of turns allowed for the tournament.
     *
     * @return The maximum number of turns.
     */
    public int getMaxTurns() {
        return d_maxTurns;
    }

    /**
     * Sets the maximum number of turns allowed for the tournament.
     *
     * @param p_maxTurns The maximum number of turns to be set.
     */
    public void setMaxTurns(int p_maxTurns) {
        this.d_maxTurns = p_maxTurns;
    }

    /**
     * Retrieves the game winners for the tournament.
     *
     * @return A HashMap where the key is the game name, and the value is a list of winners.
     */
    public HashMap<String, ArrayList<String>> getGameWinners() {
        return d_gameWinners;
    }
}
