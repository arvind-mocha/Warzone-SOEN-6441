package org.com.Adapter;

import org.com.Constants.CommonConstants;
import org.com.Handlers.GamePhaseHandler;
import org.com.Handlers.MapOperationsHandler;
import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;

/**
 * The purpose of this class is to handle the conquest maps
 *
 * @author Barath
 */
public class ConquestMapFileAdapter implements MapFileHandler {

    /**
     * This method is used to load the Conquest map from the specified path
     *
     * @param filePath refers to the path of the map file
     * @param gamePhaseHandler is the current phase of the game and is used to set the next game phase
     * @throws Exception is the exception message thrown
     */
    @Override
    public void loadMap(String filePath, GamePhaseHandler gamePhaseHandler) throws Exception {
        Map gameMap = new Map();
        BufferedReader reader = new BufferedReader(new FileReader(CommonConstants.GAME_DATA_DIR + filePath));
        String line;
        boolean continentSection = false, territorySection = false;
        HashMap<Object, Object> continentMap = new HashMap<>();
        HashMap<Object, Object> countryNeighbours = new HashMap<>();
        HashMap<Object, Object> countryObjects = new HashMap<>();
        int countryId = 1;

        DefaultDirectedGraph<Continent, DefaultEdge> continentGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        DefaultDirectedGraph<Country, DefaultEdge> countryGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            if (line.equalsIgnoreCase("[Continents]")) {
                continentSection = true;
                territorySection = false;
                continue;
            } else if (line.equalsIgnoreCase("[Territories]")) {
                continentSection = false;
                territorySection = true;
                continue;
            }

            if (continentSection) {
                String[] parts = line.split("=");
                if (parts.length != 2) continue;
                String name = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                Continent continent = new Continent(name, value);
                continent.setId(continentMap.size() + 1);
                continentMap.put(name, continent);
                continentGraph.addVertex(continent);
            } else if (territorySection) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                String continentName = parts[3].trim();
                Continent continent = (Continent) continentMap.get(continentName);
                if (continent == null) continue;

                Country country = new Country(name, continent, 2);
                country.setId(countryId++);
                countryObjects.put(name, country);
                continent.addCountry(country);
                countryGraph.addVertex(country);

                List<String> neighbours = new ArrayList<>();
                for (int i = 4; i < parts.length; i++) {
                    neighbours.add(parts[i].trim());
                }
                countryNeighbours.put(name, neighbours);
            }
        }
        reader.close();

        // Resolve neighbours
        for (Entry<Object, Object> entry : countryNeighbours.entrySet()) {
            Country country = (Country) countryObjects.get(entry.getKey());
            List<String> neighbours = (List<String>) entry.getValue();

            for (String neighbourName : neighbours) {
                Country neighbour = (Country) countryObjects.get(neighbourName);
                if (neighbour != null) {
                    country.addNeighbourCountryId(neighbour.getId());
                    countryGraph.addEdge(country, neighbour);
                    if (country.getContinentId() != neighbour.getContinentId()) {
                        continentGraph.addEdge(country.getContinent(), neighbour.getContinent());
                    }
                }
            }
        }

        gameMap.setContinentMap(continentGraph);
        gameMap.setCountryMap(countryGraph);
        gamePhaseHandler.setGameMap(gameMap);
        gamePhaseHandler.setMapFileName(filePath);

        gamePhaseHandler.setGamePhase(gamePhaseHandler.getGamePhase().getNextPhase());

        System.out.println("The map has been validated and loaded!");

    }


    /**
     * This method is used to save the map file
     *
     * @param filePath refers to the path of map file
     * @param map Map object
     * @throws Exception is the exception message thrown
     */
    @Override
    public void saveMap(String filePath, Map map) throws Exception {
        GamePhaseHandler tempHandler = new GamePhaseHandler();
        tempHandler.setGameMap(map);
        tempHandler.setMapFileName(filePath);
        MapOperationsHandler.saveMap(tempHandler, filePath);
    }
}
