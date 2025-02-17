package org.com.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * This java file is used to define a player in the game
 * Contains all the attributes of a player.
 */

public class Player {

    private String d_name;
    private int d_armyCount;
    private List<Country>d_countries;   //Countries owned by a particular player
    private List<Continent>d_continents;   //Continents owned by a particular player
    private List<Order> d_orderList;
    private Order d_currentOrder;


    /**
     * This is a default constructor which is used in the beginning of the game to get player's name and
     * assign random countries.
     * @param p_name Input from user requesting the Player's name
     */
    public Player(String p_name){
        this.d_name = p_name;
        this.d_armyCount = 0;
        this.d_countries = new ArrayList<>();
        this.d_continents = new ArrayList<>();
    }

    /**
     * When a player takes over a country, this method is used to add that country in their Country list.
     * Can also be used to add country to player's list.
     * @param p_country Country name
     */
    public void addCountry(Country p_country){
        this.d_countries.add(p_country);
    }
}
