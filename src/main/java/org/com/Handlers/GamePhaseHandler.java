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
    private List<String[]> d_advanceCommandsBuffer;

    public GamePhaseHandler() {
        this.d_gamePhase = new LoadMapPhase();
        this.d_playerList = new ArrayList<>();
        d_advanceCommandsBuffer = new ArrayList<>();
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

    public void assignReinforcements() {
        int l_numArmies = Math.max(Math.divideExact(d_gameMap.getCountryMap().vertexSet().size(), d_playerList.size()), 3);
        for (Player l_player : d_playerList) {
            l_player.set_armyCount(l_player.get_armyCount() + l_numArmies);
            System.console().println("Army count assigned to player : "+l_player.get_name() + " is \t: " + l_player.get_armyCount());
            for (Continent l_continent : l_player.get_continents()) {
                l_player.set_armyCount(l_player.get_armyCount() + l_continent.getValue());
            }
        }
//        System.console().println("Armies have been assigned to all player");
        LogUtil.Logger(GamePhaseHandler.class.getName(), Level.INFO, "Armies have been assigned to all player");
    }

    public List<String[]> getAdvanceCommandsBuffer() {  //Add Getter
        return d_advanceCommandsBuffer;
    }

    public void clearAdvanceCommandsBuffer() {  //Add Clear
        d_advanceCommandsBuffer.clear();
    }

}
