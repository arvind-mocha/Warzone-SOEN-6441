package org.com.Handlers;

import org.com.Constants.CommonConstants;
import org.com.Constants.CommonErrorMessages;
import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Utils.DisplayUtil;
import org.com.Utils.HelperUtil;
import org.com.Utils.LogUtil;
import org.com.Utils.ValidationUtil;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 * All important methods which are used for processing a command are written here.
 *
 * @author Arvind Lakshmanan
 * @author Devasenan Murugan
 *
 */

public class MapOperationsHandler {

    /**
     * Processes the map from the given file.
     *
     * @param p_fileName The name of the file containing the map data.
     */
    private static String CLASS_NAME = MapOperationsHandler.class.getName();

    public static void editMap(GamePhaseHandler p_gamePhaseHandler, String p_fileName){
        var l_console = System.console();
        Map l_map;
        try (BufferedReader l_reader = new BufferedReader(new FileReader("src/main/resources/" + p_fileName))) {
            processMap(p_gamePhaseHandler, p_fileName, false);
        } catch (Exception l_e) {
            l_console.println("File not found");
            l_map = new Map();
            p_gamePhaseHandler.setGameMap(l_map);
            l_console.println("A new map has been loaded to be edited..");
            l_console.println("Try executing the editcontinent, editcountry, editneighbor commands!");
        }
    }

    public static void editContinent(GamePhaseHandler p_gamePhaseHandler, String p_command) throws Exception{
        var l_console = System.console();
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        if (l_gameMap == null) {
            throw new Exception(CommonErrorMessages.MAP_NOT_LOADED);
        }

        String[] l_commandsArray = p_command.split(" -");
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentGraph = l_gameMap.getContinentMap();
        Set<Continent> l_continentsSet = l_continentGraph.vertexSet();

        int l_continentID = 0;
        if(!l_continentsSet.isEmpty()) {
            for (Continent l_c: l_continentsSet){
                if (l_continentID < l_c.getId()) {
                    l_continentID = l_c.getId();
                }
            }
        }
        l_continentID += 1;

        for(int l_index=1; l_index<l_commandsArray.length; l_index++) {
            String[] l_operationsArray = l_commandsArray[l_index].split(" ");
            String l_attributeOperation = l_operationsArray[0];
            String l_continentName = l_operationsArray[1];
            String l_continentValue = l_operationsArray[2];
            ValidationUtil.validateContinentManagement(l_gameMap, l_attributeOperation, l_continentName, l_continentValue);

            if(l_attributeOperation.equalsIgnoreCase((CommonConstants.ADD_ATTRIBUTE))){
                Continent l_continent = new Continent(l_continentID, Integer.parseInt(l_continentValue));
                l_continent.setName(l_continentName);
                l_continentGraph.addVertex(l_continent);
                l_console.println(String.format("Continent %d - %s has been added", l_continentID, l_continentName));
            } else if (l_attributeOperation.equalsIgnoreCase((CommonConstants.REMOVE_ATTRIBUTE))){
                Continent l_continent = l_gameMap.getContinentByName(l_continentName);
                l_continentGraph.removeVertex(l_continent);
                l_console.println(String.format("Continent %d - %s has been removed", l_continentID, l_continentName));
            }
        }
    }


    public static void processMap(GamePhaseHandler p_gamePhaseHandler, String p_fileName, boolean p_isMapValidationCommand) throws Exception {
        Map l_gameMap = new Map();
        var l_console = System.console();
        l_console.println("Processing the map from " + p_fileName + " ...");

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
            if(p_isMapValidationCommand)
            {
                l_console.println("The map is valid");
                return;
            }
            l_console.println("The Map has been constructed and validated successfully!");
            LogUtil.Logger(CLASS_NAME, Level.INFO, "The map has been validated");

            p_gamePhaseHandler.setGameMap(l_gameMap);
            p_gamePhaseHandler.setMapFileName(p_fileName);
            p_gamePhaseHandler.setGamePhase(p_gamePhaseHandler.getGamePhase().getNextPhase());
            l_console.println("The Map has been loaded");
        } catch (IOException e) {
            l_console.println("Failed to load the file: " + e.getMessage());
        }
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
            Integer l_continentId  =Integer.parseInt(l_countryInfoArray[2]);
            l_country.setId(Integer.parseInt(l_countryInfoArray[0]));
            l_country.setName(l_countryInfoArray[1]);
            l_country.setContinentId(l_continentId);
            l_country.setContinentName(HelperUtil.getContinentById(p_map.getContinentMap().vertexSet(),l_continentId ));
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

    public static void processShowGameMap(GamePhaseHandler p_gameMap) throws Exception {
        Map l_gameMap = p_gameMap.getGameMap();
        if (l_gameMap == null) {
            throw new Exception(CommonErrorMessages.MAP_NOT_LOADED);
        }

        Set<Country> l_countryVertexes = l_gameMap.getCountryMap().vertexSet();
        String[][] l_data = new String[l_countryVertexes.size()][6];
        int l_rowNum = 0;
        for (Country l_country : l_countryVertexes) {

            Player l_owner = l_country.getOwner();
            StringBuilder l_neighbourCountries = new StringBuilder();
            for (int l_neighbourID : l_country.getNeighbourCountryIds()) {
                l_neighbourCountries.append(l_neighbourID).append(" ");
            }
            l_data[l_rowNum][0] = String.valueOf(l_country.getId());
            l_data[l_rowNum][1] = l_country.getName();
            l_data[l_rowNum][2] = l_country.getContinentName();
            l_data[l_rowNum][3] = l_neighbourCountries.toString().trim();
            l_data[l_rowNum][4] = l_owner == null ? "-" : l_owner.get_name();
            l_data[l_rowNum][5] = String.valueOf(l_country.getArmyCount());
            l_rowNum++;
        }
        DisplayUtil.displayMap(l_data, new String[] {"CountryID", "Country", "Continent", "Neighbours", "Owner", "Armies"}, "Domination Map Viewer");
    }
}
