package org.com.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tournament implements Serializable {

    private List<String> d_mapList;
    private List<String> d_strategyList;
    private int d_numGames;
    private int d_maxTurns;
    private List<String> d_gameWinners;

    public Tournament() {
        this.d_mapList = new ArrayList<>();
        this.d_strategyList = new ArrayList<>();
        this.d_numGames = 0;
        this.d_maxTurns = 0;
        this.d_gameWinners = new ArrayList<>();
    }

    public List<String> getMapList() {
        return d_mapList;
    }

    public void setMapList(List<String> p_mapList) {
        this.d_mapList = p_mapList;
    }

    public List<String> getStrategyList() {
        return d_strategyList;
    }

    public void setStrategyList(List<String> p_strategyList) {
        this.d_strategyList = p_strategyList;
    }

    public int getNumGames() {
        return d_numGames;
    }

    public void setNumGames(int p_numGames) {
        this.d_numGames = p_numGames;
    }

    public int getMaxTurns() {
        return d_maxTurns;
    }

    public void setMaxTurns(int p_maxTurns) {
        this.d_maxTurns = p_maxTurns;
    }
}
