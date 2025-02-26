package org.com.Models;

import java.util.List;

public class State {
    private String d_gamePhase;
    private List<Player> d_playerList;
    private int d_currentPlayerTurn;
    private Map d_map;

    public String get_gamePhase() {
        return d_gamePhase;
    }

    public void set_gamePhase(String d_gamePhase) {
        this.d_gamePhase = d_gamePhase;
    }

    public List<Player> get_playerList() {
        return d_playerList;
    }

    public void set_playerList(List<Player> d_playerList) {
        this.d_playerList = d_playerList;
    }

    public int get_currentPlayerTurn() {
        return d_currentPlayerTurn;
    }

    public void set_currentPlayerTurn(int d_currentPlayerTurn) {
        this.d_currentPlayerTurn = d_currentPlayerTurn;
    }

    public Map get_map() {
        return d_map;
    }

    public void set_map(Map d_map) {
        this.d_map = d_map;
    }
}
