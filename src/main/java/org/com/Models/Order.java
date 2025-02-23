package org.com.Models;

/**
 * This file is used to represent the "Order" given by a player <b>(Each player will have an order)</b> in the game.
 * Orders are :
 * <ul>
 *   <li>Deploy Orders</li>
 *   <li>Advance Orders</li>
 *   <li>Special Orders : usage of cards</li>
 * </ul>
 * Used in relation with the game <b>Controls.
 */
public class Order {
    private Country d_country;
    private int d_armyCount;

    /**
     * Constructor of the Order class.
     * @param p_country The country on which the order is to be executed
     * @param p_armyCount The number of armies to be deployed
     */
    public Order(Country p_country, int p_armyCount){
        this.d_country = p_country;
        this.d_armyCount = p_armyCount;
    }

    /**
     * Gets the country specified in the order.
     *
     * @return The country on which armies are to be deployed.
     */
    public Country getD_country() {
        return d_country;
    }

    /**
     * Sets the country specified in the order.
     *
     * @param d_country The country on which armies are to be deployed.
     */
    public void setD_country(Country d_country) {
        this.d_country = d_country;
    }

    /**
     * Gets the number of armies deployed
     *
     * @return Number of armies deployed on the country
     */
    public int getD_armyCount() {
        return d_armyCount;
    }

    /**
     * Sets the number of armies deployed
     *
     * @param d_armyCount Number of armies deployed on the country
     */
    public void setD_armyCount(int d_armyCount) {
        this.d_armyCount = d_armyCount;
    }
}
