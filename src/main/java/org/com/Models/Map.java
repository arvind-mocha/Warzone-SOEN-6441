package org.com.Models;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The `Map` class represents the game map, which includes continents and countries.
 * It uses `DefaultDirectedGraph` from the JGraphT library to model the relationships
 * between continents and countries.
 *
 * This class provides methods to retrieve continents and countries by their IDs,
 * as well as methods to get and set the continent and country maps.
 *
 * Implements `Serializable` to allow the map to be serialized.
 *
 * @author Arvind Lakshmanan
 */
public class Map implements Serializable {
    private DefaultDirectedGraph<Continent, DefaultEdge> d_continentMap;
    private DefaultDirectedGraph<Country, DefaultEdge> d_countryMap;
    private List<Continent> d_continents = new ArrayList<>();

    public Map(){
        d_continentMap = new DefaultDirectedGraph<>(DefaultEdge.class);
        d_countryMap = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    /**
     * Retrieves a continent by its ID.
     *
     * @param p_continentId the ID of the continent
     * @return the continent with the specified ID, or null if not found
     */
    public Continent getContinentById(int p_continentId){
        for (Continent l_continent : d_continentMap.vertexSet()){
            if (l_continent.getId() == p_continentId)
                return l_continent;
        }
        return null;
    }

    /**
     * Retrieves a continent by its name.
     *
     * @param p_continentName the name of the continent
     * @return the continent with the specified name, or null if not found
     */
    public Continent getContinentByName(String p_continentName){
        for (Continent l_continent: d_continentMap.vertexSet()){
            if(l_continent.getName().equalsIgnoreCase(p_continentName)) {
                return l_continent;
            }
        }
        return null;
    }

    /**
     * Retrieves a country by its ID.
     *
     * @param p_countryId the ID of the country
     * @return the country with the specified ID, or null if not found
     */
    public Country getCountryById(int p_countryId){
        for (Country l_country : d_countryMap.vertexSet()){
            if (l_country.getId() == p_countryId)
                return l_country;
        }
        return null;
    }

    /**
     * Retrieves a country by its name.
     *
     * @param p_countryName the name of the country
     * @return the country with the specified name, or null if not found
     */
    public Country getCountryByName(String p_countryName){
        for (Country l_country : d_countryMap.vertexSet()){
            if (l_country.getName().equalsIgnoreCase(p_countryName))
                return l_country;
        }
        return null;
    }

    /**
     * Gets the country map.
     *
     * @return the country map
     */
    public DefaultDirectedGraph<Country, DefaultEdge> getCountryMap(){
        return d_countryMap;
    }

    /**
     * Sets the country map.
     *
     * @param p_countryMap the new country map
     */
    public void setCountryMap(DefaultDirectedGraph<Country, DefaultEdge> p_countryMap){
        d_countryMap = p_countryMap;
    }

    /**
     * Gets the continent map.
     *
     * @return the continent map
     */
    public DefaultDirectedGraph<Continent, DefaultEdge> getContinentMap(){
        return d_continentMap;
    }

    /**
     * Gets the list of continents.
     *
     * @return the list of continents
     */
    public List<Continent> getContinents() {
        return d_continents;
    }

    /**
     * Sets the list of continents.
     *
     * @param p_continents the new list of continents
     */
    public void setContinents(List<Continent> p_continents) {
        this.d_continents = p_continents;
    }

    /**
     * Sets the continent map.
     *
     * @param p_continentMap the new continent map
     */
    public void setContinentMap(DefaultDirectedGraph<Continent, DefaultEdge> p_continentMap){
        d_continentMap = p_continentMap;
    }
}
