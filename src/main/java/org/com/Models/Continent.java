package org.com.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The 'Continent' class represents the Continent in the game map.
 * Contains the properties of a Continent.
 * Continent is related to the Country and the Map classes
 *
 *  @author Devasenan Murugan
 */

public class Continent implements Serializable {
    private int d_id;
    private String d_name;
    private int d_value;
    private List<Country> d_countries;

    /**
     * Default constructor for Continent class.
     */
    public Continent() {
        d_countries = new ArrayList<>();
    }

    /**
     * Constructor with parameters for Continent class.
     *
     * @param p_name The ID of the continent
     * @param p_value The value of the continent
     */
    public Continent(String p_name, int p_value){
        d_name = p_name;
        d_value = p_value;
        d_countries = new ArrayList<>();
    }

    /**
     * Gets the ID of the continent.
     *
     * @return The ID of the continent.
     */
    public int getId(){
        return d_id;
    }

    /**
     * Sets the ID of the continent.
     *
     * @param p_id The new ID of the continent.
     */
    public void setId(int p_id){
        d_id = p_id;
    }

    /**
     * Sets the name of the continent.
     *
     * @param p_name The new name of the continent.
     */
    public void setName(String p_name){
        d_name = p_name;
    }

    /**
     * Gets the name of the continent.
     *
     * @return The name of the continent.
     */
    public String getName(){
        return d_name;
    }

    /**
     * Adds a country to the continent.
     *
     * @param p_country The country to add.
     */
    public void addCountry(Country p_country){
        d_countries.add(p_country);
    }

    /**
     * Removes a country from the continent.
     *
     * @param p_country The country to remove.
     */
    public void removeCountry(Country p_country){
        d_countries.remove(p_country);
    }

    /**
     * Gets the list of countries in the continent.
     *
     * @return The list of countries.
     */
    public List<Country> getCountries(){
        return d_countries;
    }

    /**
     * Sets the value of the continent.
     *
     * @param p_value The new value of the continent.
     */
    public void setValue(int p_value){
        d_value = p_value;
    }

    /**
     * Gets the value of the continent.
     *
     * @return The value of the continent.
     */
    public int getValue(){
        return d_value;
    }

}



