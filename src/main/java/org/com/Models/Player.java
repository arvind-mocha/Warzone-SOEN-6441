package org.com.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This java file is used to define a player in the game
 * Contains all the attributes of a player.
 *
 * @author Barath Sundararaj
 */

public class Player implements Serializable {

    private String d_name;
    private int d_armyCount;
    private List<Country>d_countries;           //Countries owned by a particular player
    private List<Continent>d_continents;        //Continents owned by a particular player
    private List<Order> d_orderList;            //An order list to contain all the orders given by the current player
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
        this.d_orderList = new ArrayList<>();
    }

    /**
     * When a player takes over a country, this method is used to add that country in their Country list.
     * Can also be used to add country to player's list.
     * @param p_country Country name
     */
    public void addCountry(Country p_country){
        this.d_countries.add(p_country);
    }

    /**
     * <b>d_currentOrder.getD_armyCount() -></b> Input given by the user indicating the number of soldiers they want to deploy<br>
     * <b>d_armyCount -></b> is the actual size of army present with the user
     * <br>If successful, then the order is added to the order list and army count is reduced.
     */
    public void issueOrder(){
        if(d_currentOrder.getD_armyCount() > d_armyCount){
            throw new IllegalArgumentException("Not enough armies to issue order");
        }
        d_orderList.add(d_currentOrder);
        d_armyCount -= d_currentOrder.getD_armyCount();
    }


    /**
     * Moves to the next order
     *
     * @return The next order, or null if no orders are left
     */
    public Order nextOrder() {
        Order l_order = null;
        if (!d_orderList.isEmpty()) {
            l_order = d_orderList.get(0);
            d_orderList.remove(0);
        }
        return l_order;
    }


    /**
     * Gets the player's name
     *
     * @return d_name Player's name
     */
    public String get_name() {
        return d_name;
    }

    /**
     * Sets the player's name
     *
     * @param p_name Used to set the new player's name
     */
    public void set_name(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Gets the current player's army count
     *
     * @return d_armyCount Size of the army the current player holds
     */
    public int get_armyCount() {
        return d_armyCount;
    }

    /**
     * Sets the size of current player's army
     *
     * @param p_armyCount Size of army
     */
    public void set_armyCount(int p_armyCount) {
        this.d_armyCount = p_armyCount;
    }

    /**
     * Gets the list of countries owned by the player
     *
     * @return d_countries List of Countries
     */
    public List<Country> get_countries() {
        return d_countries;
    }

    /**
     * Sets the list of countries held by the player
     *
     * @param p_countries List of Country objects under player's ownership
     */
    public void set_countries(List<Country> p_countries) {
        this.d_countries = p_countries;
    }

    /**
     * Gets the list of orders issued by the player
     *
     * @return d_orderList List of orders issued by the player
     */
    public List<Order> get_orderList() {
        return d_orderList;
    }

    /**
     * Sets the list of orders issued by the player
     *
     * @param p_orderList List of orders issued by the player
     */
    public void set_orderList(List<Order> p_orderList) {
        this.d_orderList = p_orderList;
    }

    /**
     * Gets the current Order of the current player that is being issued
     *
     * @return d_currentOrder of the current player that is being issued
     */
    public Order get_currentOrder() {
        return d_currentOrder;
    }

    /**
     * Sets the current Order of the current player that is being issued
     *
     * @param p_currentOrder current Order of the current player that is being issued
     */
    public void set_currentOrder(Order p_currentOrder) {
        this.d_currentOrder = p_currentOrder;
    }

    /**
     * Gets the list of continents held by the player
     *
     * @return d_continents List of continents the player owns
     */
    public List<Continent> get_continents() {
        return d_continents;
    }

    /**
     * Sets the list of continents held by the player
     *
     * @param p_continents List of Continent objects under player's ownership
     */
    public void set_continents(List<Continent> p_continents) {
        this.d_continents = p_continents;
    }
}
