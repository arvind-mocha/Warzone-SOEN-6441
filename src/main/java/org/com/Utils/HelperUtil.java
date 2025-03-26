package org.com.Utils;

import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;

import java.util.HashMap;
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

    public static void setCountryOwner(Player p_player, Country p_country, boolean p_isOwner)
    {
        if(p_isOwner) {
            p_player.get_countries().add(p_country);
            p_country.setOwner(p_player);
        } else {
            p_player.get_countries().remove(p_country);
            p_country.setOwner(null);
        }
    }
}
