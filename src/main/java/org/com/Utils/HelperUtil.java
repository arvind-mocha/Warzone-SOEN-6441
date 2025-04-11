package org.com.Utils;

import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;

import java.io.Serializable;
import java.util.*;


/**
 * This class contains helper methods to perform small operations.
 *
 * @author Arvind Lakshmanan
 *
 */
public class HelperUtil implements Serializable {
    /**
     * Constructs a HashMap from the given attribute names and values.
     *
     * @param p_attributeName  Array of attribute names.
     * @param p_attributeValue Array of attribute values.
     * @param p_attributesCount Number of attributes.
     * @return A HashMap with attribute names as keys and attribute values as values.
     */
    public static HashMap<String, Integer> constructAttributeHashMap(String[] p_attributeName, Integer[] p_attributeValue, Integer p_attributesCount)
    {
        HashMap<String, Integer> l_hashMap = new HashMap<>();
        for (int l_i = 0; l_i<p_attributesCount; l_i++)
        {
            l_hashMap.put(p_attributeName[l_i], p_attributeValue[l_i]);
        }
        return l_hashMap;
    }

    /**
     * Retrieves the name of a continent by its ID.
     *
     * @param p_continents Set of Continent objects.
     * @param p_id The ID of the continent to find.
     * @return The name of the continent with the given ID, or null if not found.
     */
    public static String getContinentById(Set<Continent> p_continents, int p_id) {
        for (Continent l_continent : p_continents) {
            if (l_continent.getId() == p_id) {
                return l_continent.getName();
            }
        }
        return null;
    }

    public static Country getCountryByCountryName(String p_countryName, Map p_gameMap)
    {
        // Find the country by name
        for (Country l_country : p_gameMap.getCountryMap().vertexSet()) {
            if (l_country.getName().equalsIgnoreCase(p_countryName)) {
                return l_country;
            }
        }
        return null;
    }

    public static Player getPlayerByName(String p_playerName, List<Player> p_playersList)
    {
        for (Player l_player : p_playersList) {
            if(l_player.get_name().equals(p_playerName))
            {
                return l_player;
            }
        }
        return null;
    }

    public static void setCountryOwnerShip(Player p_player, Country p_country, boolean p_isNeutralize)
    {
        Player l_oldOwner = p_country.getOwner();
        if (l_oldOwner != null){
            l_oldOwner.get_countries().remove(p_country);
        }
        if(p_isNeutralize) {
            p_player.get_countries().remove(p_country);
            p_country.setOwner(null);
            p_country.setArmyCount(0);
        } else {
            p_player.get_countries().add(p_country);
            p_country.setOwner(p_player);
        }
    }

    public static Country getPlayerHighestArmyCountry(Player l_player)
    {
        Country l_highestArmyCountry = null;
        for (Country l_country : l_player.get_countries()) {
            if (l_highestArmyCountry == null || l_country.getArmyCount() > l_highestArmyCountry.getArmyCount()) {
                l_highestArmyCountry = l_country;
            }
        }
        return l_highestArmyCountry;
    }

    /**
     * Retrieves the country with the most armies owned by a player other than the current player.
     *
     * @param p_currentPlayer The current player.
     * @param p_gameMap   The game map.
     * @return The country with the most armies owned by another player, or null if not found.
     */
    public static Country getStrongestNeighbouringEnemyCountry(Player p_currentPlayer, Map p_gameMap) {
        Country l_strongestCountry = null;
        for (Country l_country : p_currentPlayer.get_countries()) {
            for (int l_neighborID : l_country.getNeighbourCountryIds()) {
                Country l_neighbor = p_gameMap.getCountryById(l_neighborID);
                if (!p_currentPlayer.equals(l_neighbor.getOwner()) && l_neighbor.getOwner() != null) {
                    if (l_strongestCountry == null || l_neighbor.getArmyCount() > l_strongestCountry.getArmyCount()) {
                        l_strongestCountry = l_neighbor;
                    }
                }
            }
        }
        return l_strongestCountry;
    }

    /**
     * Retrieves a random neighboring enemy country for the given player and country.
     *
     * @param p_country       The country whose neighbors are being checked.
     * @param p_gameMap       The game map containing all countries and their relationships.
     * @param p_currentPlayer The current player whose ownership is being considered.
     * @return A random neighboring enemy country, or null if none exist.
     */
    public static Country getRandomNeighbouringEnemyCountry(Country p_country, Map p_gameMap, Player p_currentPlayer) {
        List<Country> enemyCountries = new ArrayList<>();
        for (int l_neighborID : p_country.getNeighbourCountryIds()) {
            Country l_neighbor = p_gameMap.getCountryById(l_neighborID);
            if(l_neighbor.getOwner() != null){
                if (!l_neighbor.getOwner().equals(p_currentPlayer)) {
                    enemyCountries.add(l_neighbor);
                }
            }
        }
        if (enemyCountries.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return enemyCountries.get(random.nextInt(enemyCountries.size()));
    }
}
