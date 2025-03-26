package org.com.Models;

/**
 * The 'Country' class represents the Country in the game map.
 * Contains the properties of a Country.
 * <p>
 * Country is related to the Continent and the Map classes.
 * @author Devasenan Murugan
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable {
    private int d_id;
    private String d_name;
    private Continent d_continent;
    private Player d_owner;
    private List<Integer> d_neighbourCountryIds;
    private int d_armyCount;

    /**
     * Default constructor for the Country class
     */

    public Country() {
        this.d_continent = new Continent();
        this.d_neighbourCountryIds = new ArrayList<>();
    }

    /**
     * Constructor with parameters for Country class.
     *
     * @param p_name The Name of the country.
     * @param p_soldierCount The number of armies in the country.
     */
    public Country(String p_name, Continent p_continent, int p_soldierCount){
        d_name = p_name;
        d_continent = p_continent;
        d_armyCount = p_soldierCount;
        this.d_neighbourCountryIds = new ArrayList<>();
    }

    /**
     * Gets the ID of the country.
     * @return the country ID.
     */
    public int getId(){ return d_id; }

    /**
     * Sets the ID of the country.
     * @param p_id the new country ID.
     */
    public void setId(int p_id){
        d_id = p_id;
    }

    /**
     * Gets the name of the country.
     * @return the country name.
     */
    public String getName(){
        return d_name;
    }

    /**
     * Sets the name of the country.
     * @param p_name the new country name.
     */
    public void setName(String p_name){
        d_name = p_name;
    }

    /**
     * Gets the name of the continent the country belongs to.
     * @return the continent name.
     */
    public String getContinentName(){ return d_continent.getName(); }

    /**
     * Sets the name of the continent the country belongs to.
     * @param p_continentName the new continent name.
     */
    public void setContinentName(String p_continentName){
        d_continent.setName(p_continentName);
    }

    /**
     * Gets the ID of the continent the country belongs to.
     * @return the continent ID.
     */
    public int getContinentId(){ return d_continent.getId(); }

    /**
     * Sets the ID of the continent the country belongs to.
     * @param p_continentId the new continent ID.
     */
    public void setContinentId(int p_continentId){
        d_continent.setId(p_continentId);
    }

    /**
     * Sets the continent object the country belongs to.
     * @param p_continentObj the new continent object.
     */
    public void setContinent(Continent p_continentObj){
        this.d_continent = p_continentObj;
    }

    /**
     * Gets the number of armies in the country.
     * @return the army count.
     */
    public int getArmyCount(){ return d_armyCount; }

    /**
     * Sets the number of armies in the country.
     * @param p_soldierCount the new army count.
     */
    public void setArmyCount(int p_soldierCount){
        d_armyCount = p_soldierCount;
    }

    /**
     * Gets the owner of the country.
     * @return the country owner.
     */
    public Player getOwner(){
        return d_owner;
    }

    /**
     * Sets the owner of the country.
     * @param p_owner the new country owner.
     */
    public void setOwner(Player p_owner){
        d_owner = p_owner;
    }

    /**
     * Gets the list of neighboring country IDs.
     * @return the list of neighboring country IDs.
     */
    public List<Integer> getNeighbourCountryIds(){
        return d_neighbourCountryIds;
    }

    /**
     * Sets the list of neighboring country IDs.
     * @param p_neighbourCountryIds the new list of neighboring country IDs.
     */
    public void setNeighbourCountryIds(List<Integer> p_neighbourCountryIds){
        d_neighbourCountryIds = p_neighbourCountryIds;
    }

    /**
     * Removes a neighboring country ID from the list.
     * @param p_neighbourCountryId the neighboring country ID to remove.
     */
    public void removeNeighbourCountryId(int p_neighbourCountryId){
        d_neighbourCountryIds.remove(p_neighbourCountryId);
    }

    /**
     * Gets the continent object the country belongs to.
     * @return the continent object.
     */
    public Continent getContinent(){
        return this.d_continent;
    }

    /**
     * Adds a neighboring country ID to the list.
     * @param p_neighbourCountryId the neighboring country ID to add.
     */
    public void addNeighbourCountryId(int p_neighbourCountryId){
        d_neighbourCountryIds.add(p_neighbourCountryId);
    }

    /**
     * Checks if the country is neutral.
     * A country is considered neutral if it has no owner.
     *
     * @return true if the country has no owner, false otherwise.
     */
    public boolean isCountryNeutral()
    {
        return this.d_owner == null;
    }
}
