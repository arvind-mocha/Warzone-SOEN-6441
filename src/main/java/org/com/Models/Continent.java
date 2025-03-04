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
     * @param p_name    The ID of the continent
     * @param p_value The value of the continent
     */
    public Continent(int p_name, int p_value){
        d_id = p_name;
        d_value = p_value;
        d_countries = new ArrayList<>();
    }

    public int getId(){
        return d_id;
    }

    public void setId(int p_id){
        d_id = p_id;
    }

    public void setName(String p_name){
        d_name = p_name;
    }

    public String getName(){
        return d_name;
    }

    public void addCountry(Country p_country){
        d_countries.add(p_country);
    }

    public void removeCountry(Country p_country){
        d_countries.remove(p_country);
    }

    public List<Country> getCountries(){
        return d_countries;
    }

    public void setValue(int p_value){
        d_value = p_value;
    }

    public int getValue(){
        return d_value;
    }

}



