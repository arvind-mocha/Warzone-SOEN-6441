package org.com.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable {
    private int d_id;
    private String d_name;
    private int d_continentId;
    private String d_continentName;
    private Player d_owner;
    private List<Integer> d_neighbourCountryIds;
    private int d_armyCount;

    public Country() {
        this.d_neighbourCountryIds = new ArrayList<>();
    }

    public Country(int p_id, List<Integer> p_neighbourCountryIds, int p_soldierCount){
        d_id = p_id;
        d_neighbourCountryIds = p_neighbourCountryIds;
        d_armyCount = p_soldierCount;
    }

    public int getId(){ return d_id;}

    public void setId(int p_id){
        d_id = p_id;
    }

    public String getName(){
        return d_name;
    }

    public void setName(String p_name){
        d_name = p_name;
    }

    public String getContinentName(){ return d_continentName; }

    public void setContinentName(String p_continentName){
        d_continentName = p_continentName;
    }

    public int getContinentId(){ return d_continentId; }

    public void setContinentId(int p_continentId){
        d_continentId = p_continentId;
    }

    public int getArmyCount(){ return d_armyCount; }

    public void setArmyCount(int p_soldierCount){
        d_armyCount = p_soldierCount;
    }

    public Player getOwner(){
        return d_owner;
    }

    public void setOwner(Player p_owner){
        d_owner = p_owner;
    }

    public List<Integer> getNeighbourCountryIds(){
        return d_neighbourCountryIds;
    }

    public void setNeighbourCountryIds(List<Integer> p_neighbourCountryIds){
        d_neighbourCountryIds = p_neighbourCountryIds;
    }
}
