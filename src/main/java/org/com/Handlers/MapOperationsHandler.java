package org.com.Handlers;

import org.com.Constants.CommonConstants;
import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Utils.LogUtil;
import org.com.Utils.ValidationUtil;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * All important methods which are used for processing a command are written here.
 *
 * @author Arvind Lakshmanan
 *
 */

public class MapOperationsHandler {

    /**
     * Processes the map from the given file.
     *
     * @param p_fileName The name of the file containing the map data.
     */
    private static String CLASS_NAME = MapOperationsHandler.class.getName();

    public static Map processMap(GamePhaseHandler p_gamePhaseHandler,String p_fileName) throws Exception {
        Map l_gameMap = new Map();
        var l_console = System.console();
        l_console.print("Loading the map from " + p_fileName + " ...");

        try {
            List<String> l_file = Files.readAllLines(Paths.get(CommonConstants.GAME_DATA_DIR + p_fileName));
            for (int l_lineNum=0; l_lineNum < l_file.size(); l_lineNum++) {
                if (l_file.get(l_lineNum).isEmpty()) {
                    continue;
                }
                if(l_file.get(l_lineNum).equalsIgnoreCase(CommonConstants.CONTINENTS))
                {
                    l_lineNum = processContinents(l_file, l_gameMap);
                    LogUtil.Logger(CLASS_NAME, Level.INFO, "The continents object has been processed");
                }
                else if(l_file.get(l_lineNum).equalsIgnoreCase(CommonConstants.COUNTRIES))
                {
                    l_lineNum = processCountries(l_file, l_gameMap, l_lineNum+1);
                    LogUtil.Logger(CLASS_NAME, Level.INFO, "The countries object has been processed");
                }
                else if(l_file.get(l_lineNum).equalsIgnoreCase(CommonConstants.BORDERS))
                {
                    l_lineNum = processBorders(l_file, l_gameMap, l_lineNum+1);
                    LogUtil.Logger(CLASS_NAME, Level.INFO, "The continents and countries has been has connected using borders");
                }
            }
            LogUtil.Logger(CLASS_NAME, Level.INFO, "The map object has been constructed");
            ValidationUtil.validateMap(l_gameMap);
            l_console.print("\nThe Map has been constructed and validated successfully!");
            LogUtil.Logger(CLASS_NAME, Level.INFO, "The map has been validated");

            p_gamePhaseHandler.setGameMap(l_gameMap);
            p_gamePhaseHandler.setMapFileName(p_fileName);
            p_gamePhaseHandler.setGamePhase(p_gamePhaseHandler.getGamePhase().nextPhase());
            l_console.print("\nThe Map has been loaded");
        } catch (IOException e) {
            l_console.print("Failed to load the file: " + e.getMessage());
        }
        return l_gameMap;
    }

    private static int processBorders(List<String> p_file, Map p_map, int p_lineNum) {

        DefaultDirectedGraph<Country, DefaultEdge> l_countryGraph = p_map.getCountryMap();
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentGraph = p_map.getContinentMap();
        for(int l_lineNum = p_lineNum; l_lineNum < p_file.size(); l_lineNum++) {
            String[] l_borderInfoArray = p_file.get(l_lineNum).split(" ");
            Country l_currentCountry = p_map.getCountryById(Integer.parseInt(l_borderInfoArray[0]));
            List<Integer> l_neighbourCountryIDList = new ArrayList<>();

            for (int l_borderIndex = 1; l_borderIndex < l_borderInfoArray.length; l_borderIndex++) {
                int l_neighbourCountryID = Integer.parseInt(l_borderInfoArray[l_borderIndex]);
                l_neighbourCountryIDList.add(l_neighbourCountryID);
                Country l_neighbourCountry = p_map.getCountryById(l_neighbourCountryID);
                l_countryGraph.addEdge(l_currentCountry, l_neighbourCountry);

                if (l_currentCountry.getContinentId() != l_neighbourCountry.getContinentId()) {
                    l_continentGraph.addEdge(p_map.getContinentById(l_currentCountry.getContinentId()), p_map.getContinentById(l_neighbourCountry.getContinentId()));
                }
            }
            l_currentCountry.setNeighbourCountryIds(l_neighbourCountryIDList);
        }
        return p_file.size();
    }

    private static int processCountries(List<String> p_file, Map p_map, int p_lineNum) {

        DefaultDirectedGraph<Country, DefaultEdge> l_countryGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        for(int l_lineNum = p_lineNum; l_lineNum < p_file.size(); l_lineNum++) {
            String l_countryInfo = p_file.get(l_lineNum);
            if (l_countryInfo.isEmpty()) {
                p_map.setCountryMap(l_countryGraph);
                return l_lineNum;
            }
            String[] l_countryInfoArray = l_countryInfo.split(" ");
            Country l_country = new Country();
            Continent l_continent = p_map.getContinentById(Integer.parseInt(l_countryInfoArray[2]));
            l_country.setId(Integer.parseInt(l_countryInfoArray[0]));
            l_country.setName(l_countryInfoArray[1]);
            l_country.setContinentId(Integer.parseInt(l_countryInfoArray[2]));
            l_countryGraph.addVertex(l_country);
            l_continent.addCountry(l_country);
        }
        return p_lineNum;
    }

    private static int processContinents(List<String> p_file, Map p_map) {

        DefaultDirectedGraph<Continent, DefaultEdge> l_continentGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        for(int l_lineNum = 1; l_lineNum < p_file.size(); l_lineNum++) {
            String l_continentInfo = p_file.get(l_lineNum);
            if (l_continentInfo.isEmpty()) {
                return l_lineNum;
            }

            String[] l_continentInfoArray = l_continentInfo.split("\\s+");
            Continent l_continent = new Continent();
            l_continent.setName(l_continentInfoArray[0]);
            l_continent.setValue(Integer.parseInt(l_continentInfoArray[1]));
            l_continent.setId(l_lineNum); // Line number is being used as continent id
            l_continentGraph.addVertex(l_continent);
            p_map.setContinentMap(l_continentGraph);
        }
        return CommonConstants.INTEGER_ZERO;
    }
}
