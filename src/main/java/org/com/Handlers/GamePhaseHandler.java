package org.com.Handlers;

import org.com.GamePhase.LoadMapPhase;
import org.com.GamePhase.Phase;
import org.com.Models.Map;
import org.com.Models.Player;

import java.util.ArrayList;
import java.util.List;

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

    public GamePhaseHandler() {
        this.d_gamePhase = new LoadMapPhase();
        this.d_playerList = new ArrayList<>();
    }

    public Phase getGamePhase() {
        return d_gamePhase;
    }

    public void setGamePhase(Phase p_gamePhase) {
        this.d_gamePhase = p_gamePhase;
    }

    public List<Player> getPlayerList() {
        return d_playerList;
    }

    public void setPlayerList(List<Player> p_playerList) {
        this.d_playerList = d_playerList;
    }

    public int getCurrentPlayer() {
        return d_currentPlayer;
    }

    public void setCurrentPlayer(int p_currentPlayer) {
        this.d_currentPlayer = p_currentPlayer;
    }

    public Map getGameMap() {
        return d_gameMap;
    }

    public void setGameMap(Map p_gameMap) {
        this.d_gameMap = p_gameMap;
    }

    public String getMapFileName() {
        return d_mapFileName;
    }

    public void setMapFileName(String p_mapFileName) {
        this.d_mapFileName = p_mapFileName;
    }
}
