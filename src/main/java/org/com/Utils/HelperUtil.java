package org.com.Utils;

import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * This class contains helper methods to perform small operations.
 *
 * @author Arvind Lakshmanan
 *
 */
public class HelperUtil {
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
        return l_player.get_countries().stream()
                .max((c1, c2) -> Integer.compare(c1.getArmyCount(), c2.getArmyCount()))
                .orElse(null);
    }

    /**
     * Retrieves the country with the most armies owned by a player other than the current player.
     *
     * @param p_currentPlayer The current player.
     * @param p_playersList   The list of all players.
     * @return The country with the most armies owned by another player, or null if not found.
     */
    public static Country getStrongestCountryOtherThanCurrentPlayer(Player p_currentPlayer, List<Player> p_playersList) {
        Country l_strongestCountry = null;
        for (Player l_player : p_playersList) {
            if (!l_player.equals(p_currentPlayer)) {
                for (Country l_country : l_player.get_countries()) {
                    if (l_strongestCountry == null || l_country.getArmyCount() > l_strongestCountry.getArmyCount()) {
                        l_strongestCountry = l_country;
                    }
                }
            }
        }
        return l_strongestCountry;
    }

    public static Country getRandomNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer) {
        List<Integer> l_neighbourIds = p_country.getNeighbourCountryIds();
        Country l_selectedCountry = null;
        int l_maxUnownedNeighbours = -1;

        for (int l_neighbourId : l_neighbourIds) {
            Country l_neighbour = p_gameMap.getCountryById(l_neighbourId);
            if (l_neighbour != null) {
                int l_unownedNeighboursCount = (int) l_neighbour.getNeighbourCountryIds().stream()
                        .map(p_gameMap::getCountryById)
                        .filter(l_neighbourObj -> l_neighbourObj != null && !p_currentPlayer.equals(l_neighbourObj.getOwner()))
                        .count();

                if (l_unownedNeighboursCount > l_maxUnownedNeighbours) {
                    l_maxUnownedNeighbours = l_unownedNeighboursCount;
                    l_selectedCountry = l_neighbour;
                }
            }
        }
        return l_selectedCountry;
    }
}
