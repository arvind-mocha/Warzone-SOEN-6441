package org.com.Models;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;




public class Map {
    private DefaultDirectedGraph<Continent, DefaultEdge> d_continentMap;
    private DefaultDirectedGraph<Country, DefaultEdge> d_countryMap;

    public Map(){
        d_continentMap = new DefaultDirectedGraph<>(DefaultEdge.class);
        d_countryMap = new DefaultDirectedGraph<>(DefaultEdge.class);
    }

    public Continent getContinentById(int p_continentId){
        for (Continent l_continent : d_continentMap.vertexSet()){
            if (l_continent.getId() == p_continentId)
                return l_continent;
        }
        return null;
    }

    public Country getCountryById(int p_countryId){
        for (Country l_country : d_countryMap.vertexSet()){
            if (l_country.getId() == p_countryId)
                return l_country;
        }
        return null;
    }

    public DefaultDirectedGraph<Country, DefaultEdge> getCountryMap(){
        return d_countryMap;
    }

    public void setCountryMap(DefaultDirectedGraph<Country, DefaultEdge> p_countryMap){
        d_countryMap = p_countryMap;
    }

    public DefaultDirectedGraph<Continent, DefaultEdge> getContinentMap(){
        return d_continentMap;
    }

    public void setContinentMap(DefaultDirectedGraph<Continent, DefaultEdge> p_continentMap){
        d_continentMap = p_continentMap;
    }

}
