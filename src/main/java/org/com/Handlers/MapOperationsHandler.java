package org.com.Handlers;

import org.com.Constants.CommonConstants;
import org.com.Constants.CommonErrorMessages;
import org.com.GameLog.LogManager;
import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Utils.DisplayUtil;
import org.com.Utils.ValidationUtil;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * All important methods which are used for processing a map related command are written here.
 *
 * @author Arvind Lakshmanan
 * @author Devasenan Murugan
 *
 */

public class MapOperationsHandler {

    private static String CLASS_NAME = MapOperationsHandler.class.getName();

    /**
     * Processes the map from the given file.
     *
     * @param p_fileName The name of the file containing the map data.
     * @param p_gamePhaseHandler Game Phase.
     */

    public static void editMap(GamePhaseHandler p_gamePhaseHandler, String p_fileName){
        var l_console = System.console();
        Map l_map;
        p_gamePhaseHandler.setMapFileName(p_fileName);

        try (BufferedReader l_reader = new BufferedReader(new FileReader(CommonConstants.GAME_DATA_DIR + p_fileName))) {
            processMap(p_gamePhaseHandler, p_fileName, false, true);
        } catch (Exception l_e) {
            l_console.println("File not found");
            l_map = new Map();
            p_gamePhaseHandler.setGameMap(l_map);
            l_console.println("A new map has been loaded to be edited..");
            LogManager.logAction("A new map has been loaded to be edited..");
            l_console.println("Try executing the editcontinent, editcountry, editneighbor commands!");
        }
    }


    /**
     * This method is used to perform the following operations after proper validation:
     * <ul>
     *     <li>Add Continent</li>
     *     <li>Remove Continent</li>
     * </ul>
     * @param p_gamePhaseHandler Defines the current game phase
     * @param p_command User Input Command
     * @throws Exception Map Not loaded
     */
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

        for(int l_index=1; l_index<l_commandsArray.length; l_index++) {
            String[] l_operationsArray = l_commandsArray[l_index].split(" ");
            String l_attributeOperation = l_operationsArray[0];
            String l_continentName = l_operationsArray[1];
            String l_continentValue;
            try{
                l_continentValue = l_operationsArray[2];
            }catch(IndexOutOfBoundsException e){
                l_continentValue = "0";
            }
            ValidationUtil.validateContinentManagement(l_gameMap, l_attributeOperation, l_continentName, l_continentValue);
            l_continentID += 1;

            if(l_attributeOperation.equalsIgnoreCase((CommonConstants.ADD_ATTRIBUTE))){
                Continent l_continent = new Continent(l_continentName, Integer.parseInt(l_continentValue));
                l_continent.setId(l_continentID);
                l_continentGraph.addVertex(l_continent);
                l_console.println(String.format("Continent %d - %s has been added", l_continent.getId(), l_continent.getName()));
                LogManager.logAction(String.format("Continent %d - %s has been added", l_continent.getId(), l_continent.getName()));
            } else if (l_attributeOperation.equalsIgnoreCase((CommonConstants.REMOVE_ATTRIBUTE))){
                Continent l_continent = l_gameMap.getContinentByName(l_continentName);
                l_continentGraph.removeVertex(l_continent);
                l_console.println(String.format("Continent %d - %s has been removed", l_continent.getId(), l_continent.getName()));
                LogManager.logAction(String.format("Continent %d - %s has been removed", l_continent.getId(), l_continent.getName()));

                for (Country l_c: l_gameMap.getCountryMap().vertexSet()){
                    if(l_c.getContinentId() == l_continent.getId()){
                        l_gameMap.getCountryMap().removeVertex(l_c);
                    }
                }
            }
        }
    }

    /**
     * This method is used to edit countries and perform the following actions after validation:
     * <ul>
     *     <li>Add Country</li>
     *     <li>Remove Country</li>
     * </ul>
     *
     * @param p_gamePhaseHandler Defines the Current Game Phase
     * @param p_command User Input Command
     * @throws Exception Map Not Loaded
     */
    public static void editCountry(GamePhaseHandler p_gamePhaseHandler, String p_command) throws Exception{
        var l_console = System.console();
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        if (l_gameMap == null) {
            throw new Exception(CommonErrorMessages.MAP_NOT_LOADED);
        }

        String[] l_commandsArray = p_command.split(" -");
        DefaultDirectedGraph<Country, DefaultEdge> l_countryGraph = l_gameMap.getCountryMap();
        Set<Country> l_countriesSet = l_countryGraph.vertexSet();

        int l_countryID = 0;
        if(!l_countriesSet.isEmpty()) {
            for (Country l_c: l_countriesSet){
                if (l_countryID < l_c.getId()) {
                    l_countryID = l_c.getId();
                }
            }
        }

        for(int l_index=1; l_index<l_commandsArray.length; l_index++) {
            String[] l_operationsArray = l_commandsArray[l_index].split(" ");
            String l_attributeOperation = l_operationsArray[0];
            String l_countryName = l_operationsArray[1];
            String l_inContinent;

            try{
                l_inContinent = l_operationsArray[2];
            }catch(IndexOutOfBoundsException e){
                l_inContinent = "0";
            }

            ValidationUtil.validateCountryManagement(l_gameMap, l_attributeOperation, l_countryName, l_inContinent);
            l_countryID += 1;

            Continent l_continentObj = l_gameMap.getContinentByName(l_inContinent);
            if(l_attributeOperation.equalsIgnoreCase((CommonConstants.ADD_ATTRIBUTE))){
                int l_defaultSoldierCount = 0;
                Country l_country = new Country(l_countryName, l_continentObj, l_defaultSoldierCount);
                l_country.setId(l_countryID);

                l_countryGraph.addVertex(l_country);
                l_continentObj.addCountry(l_country);
                l_console.println(String.format("Country %d - %s has been added in %s", l_country.getId(), l_country.getName(), l_continentObj.getName()));
                LogManager.logAction(String.format("Country %d - %s has been added in %s", l_country.getId(), l_country.getName(), l_continentObj.getName()));
            } else if (l_attributeOperation.equalsIgnoreCase((CommonConstants.REMOVE_ATTRIBUTE))){
                Country l_country = l_gameMap.getCountryByName(l_countryName);
                l_continentObj = l_country.getContinent();

                l_countryGraph.removeVertex(l_country);
                l_continentObj.removeCountry(l_country);
                l_console.println(String.format("Country %d - %s has been removed", l_country.getId(), l_country.getName()));
                LogManager.logAction(String.format("Country %d - %s has been removed", l_country.getId(), l_country.getName()));
            }
        }
    }

    /**
     * This method is used to add or remove a specific country as a neighbour to an existing country in the map.
     *
     * @param p_gamePhaseHandler Defines the game phase
     * @param p_command User Input Command
     * @throws Exception Map Not Loaded
     */
    public static void editNeighbour(GamePhaseHandler p_gamePhaseHandler, String p_command) throws Exception{
        var l_console = System.console();
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        if (l_gameMap == null) {
            throw new Exception(CommonErrorMessages.MAP_NOT_LOADED);
        }

        DefaultDirectedGraph<Country, DefaultEdge> l_countryGraph = l_gameMap.getCountryMap();
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentGraph = l_gameMap.getContinentMap();

        String[] l_commandsArray = p_command.split(" -");
        for(int l_index=1; l_index<l_commandsArray.length; l_index++) {
            String[] l_operationsArray = l_commandsArray[l_index].split(" ");
            String l_attributeOperation = l_operationsArray[0];
            String l_countryName = l_operationsArray[1];
            Country l_country = l_gameMap.getCountryByName(l_countryName);
            if(l_country == null)
            {
                throw new Exception(CommonErrorMessages.UNKNOWN_COUNTRY);
            }
            List<Integer> l_existingNeighbours = l_country.getNeighbourCountryIds();

            String l_neighbourCountryName;

            for(int l_jndex=2; l_jndex<l_operationsArray.length; l_jndex++) {
                l_neighbourCountryName = l_operationsArray[l_jndex];
                Country l_neighbourCountry = l_gameMap.getCountryByName(l_neighbourCountryName);

                if (l_neighbourCountry == null){
                    l_console.println(String.format("Country %s not found to add/remove as neighbour, try with that country again!", l_neighbourCountryName));
                    LogManager.logAction(String.format("Country %s not found to add/remove as neighbour, try with that country again!", l_neighbourCountryName));
                } else if (l_neighbourCountry.getName().equalsIgnoreCase(l_country.getName())){
                    continue;
                } else {
                    if (l_attributeOperation.equalsIgnoreCase((CommonConstants.ADD_ATTRIBUTE))) {
                        if (!l_existingNeighbours.contains(l_neighbourCountry.getId())) {
                            l_country.addNeighbourCountryId(l_neighbourCountry.getId());
                            if(!l_neighbourCountry.getNeighbourCountryIds().contains(l_country.getId())) {
                                l_neighbourCountry.addNeighbourCountryId(l_country.getId());
                            }
                            l_countryGraph.addEdge(l_country, l_neighbourCountry);
                            l_countryGraph.addEdge(l_neighbourCountry, l_country);
                            if(l_country.getContinentId() != l_neighbourCountry.getContinentId()){
                                l_continentGraph.addEdge(l_country.getContinent(), l_neighbourCountry.getContinent());
                            }
                        }
                    } else if (l_attributeOperation.equalsIgnoreCase((CommonConstants.REMOVE_ATTRIBUTE))) {
                        if (l_existingNeighbours.contains(l_neighbourCountry.getId())) {
                            l_country.removeNeighbourCountryId(l_neighbourCountry.getId());
                            if(l_neighbourCountry.getNeighbourCountryIds().contains(l_country.getId())) {
                                l_neighbourCountry.removeNeighbourCountryId(l_country.getId());
                            }
                            l_countryGraph.removeEdge(l_country, l_neighbourCountry);
                            l_countryGraph.removeEdge(l_neighbourCountry, l_country);
                            if(l_country.getContinentId() != l_neighbourCountry.getContinentId()) {
                                l_continentGraph.removeEdge(l_country.getContinent(), l_neighbourCountry.getContinent());
                            }
                        }
                    }
                }
            }

            if(l_attributeOperation.equalsIgnoreCase(CommonConstants.ADD_ATTRIBUTE)){
                l_console.println("Added the given Neighbour Countries!");
                LogManager.logAction("Added the given Neighbour Countries!");
            } else if (l_attributeOperation.equalsIgnoreCase((CommonConstants.REMOVE_ATTRIBUTE))) {
                l_console.println("Removed the listed Neighbour Countries!");
                LogManager.logAction("Removed the listed Neighbour Countries!");
            }
        }
    }

    /**
     * Saves the edited map after proper validation.
     *
     * @param p_gamePhaseHandler Current Game Phase
     * @param p_command User Input Command
     * @throws Exception Map Not Loaded
     */
    public static void saveMap(GamePhaseHandler p_gamePhaseHandler, String p_command) throws Exception{
        var l_console = System.console();
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        if (l_gameMap == null) {
            throw new Exception(CommonErrorMessages.MAP_NOT_LOADED);
        }

        String[] l_commandArray = p_command.split("\\s+");
        String l_fileName;
        if(l_commandArray.length == 1){
            l_fileName = p_gamePhaseHandler.getMapFileName();
        } else{
            l_fileName = l_commandArray[1];
        }

        try {
            ValidationUtil.validateMap(l_gameMap);
            l_console.println("The map has been validated!");
            LogManager.logAction("The map has been validated!");
        } catch (Exception e){
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss_ddMMyyyy");
            l_fileName += "_inValidMap_" + now.format(formatter) + ".map";
        }

        try (BufferedWriter l_writer = new BufferedWriter(new FileWriter(CommonConstants.GAME_DATA_DIR + l_fileName))) {
            //writing the details of continents in the file
            l_writer.write("[continents]\n");
            for (Continent l_continent : l_gameMap.getContinentMap().vertexSet()) {
                l_writer.write(l_continent.getName() + " " + l_continent.getValue()+"\n");
//                        + " blue\n");
            }
            l_writer.write("\n");

            //writing the details of countries in the file
            l_writer.write("[countries]\n");
            for (Country l_country : l_gameMap.getCountryMap().vertexSet()) {
                l_writer.write(l_country.getId()
                        + " " + l_country.getName() + " " + l_country.getContinentId()+"\n");
//                + " 100 100\n");
            }
            l_writer.write("\n");

            //writing the details of the edge in the graph about countries and it's neighbour
            l_writer.write("[borders]\n");
            for (Country l_country : l_gameMap.getCountryMap().vertexSet()) {
                StringBuilder l_borderData = new StringBuilder();
                //Adding the country ID
                l_borderData.append(l_country.getId());

                //Adding the neighbouring countries of the country
                for (int l_neighbourID : l_country.getNeighbourCountryIds()) {
                    l_borderData.append(" ");
                    l_borderData.append(l_neighbourID);
                }
                l_writer.write(String.valueOf(l_borderData));
                l_writer.write("\n");
            }
            System.out.println(String.format("Map saved successfully as %s!", l_fileName));
            LogManager.logAction(String.format("Map saved successfully as %s!", l_fileName));
        } catch (Exception l_e) {
            System.out.println("Error saving the map: " + l_e.getMessage());
            LogManager.logAction("Error saving the map: " + l_e.getMessage());
        }

    }

    /**
     * This method is the key for processing a map for validation phase.
     *
     * @param p_gamePhaseHandler Game Phase
     * @param p_fileName Map File Name
     * @param p_isMapValidationCommand Boolean value depicting the validation.
     * @param p_isEditMapCommand Boolean value depicting the edit map command.
     * @throws Exception Failed to load the file
     */
    public static void processMap(GamePhaseHandler p_gamePhaseHandler, String p_fileName, boolean p_isMapValidationCommand, boolean p_isEditMapCommand) throws Exception {
        Map l_gameMap = new Map();
        var l_console = System.console();
        l_console.println("Processing the map from " + p_fileName + " ...");
        LogManager.logAction("Processing the map from " + p_fileName + " ...");

        try {
            List<String> l_file = Files.readAllLines(Paths.get(CommonConstants.GAME_DATA_DIR + p_fileName));
            for (int l_lineNum=0; l_lineNum < l_file.size(); l_lineNum++) {
                if (l_file.get(l_lineNum).isEmpty()) {
                    continue;
                }
                if(l_file.get(l_lineNum).equalsIgnoreCase(CommonConstants.CONTINENTS))
                {
                    l_lineNum = processContinents(l_file, l_gameMap, l_lineNum+1);
                    LogManager.logAction("The continents object has been processed");
                }
                else if(l_file.get(l_lineNum).equalsIgnoreCase(CommonConstants.COUNTRIES))
                {
                    l_lineNum = processCountries(l_file, l_gameMap, l_lineNum+1);
                    LogManager.logAction("The countries object has been processed");
                }
                else if(l_file.get(l_lineNum).equalsIgnoreCase(CommonConstants.BORDERS))
                {
                    l_lineNum = processBorders(l_file, l_gameMap, l_lineNum+1);
                    LogManager.logAction("The continents and countries has been has connected using borders");
                }
            }
            LogManager.logAction("The map object has been constructed");

            try{
                ValidationUtil.validateMap(l_gameMap);
            } catch (Exception e){
                l_console.println("The Map Validation has failed and has the following error");
                LogManager.logAction("The Map Validation has failed and has the following error");
                if(p_isEditMapCommand){
                    l_console.println(e);
                    LogManager.logAction(e.toString());
                    p_gamePhaseHandler.setGameMap(l_gameMap);
                    p_gamePhaseHandler.setMapFileName(p_fileName);
                    l_console.println("Map has to be edited accordingly to play with this map!");
                    LogManager.logAction("Map has to be edited accordingly to play with this map!");
                    return;
                }
                throw e;
            }
            if(p_isMapValidationCommand){
                l_console.println("The map is valid");
                return;
            }

            l_console.println("The Map has been validated and constructed successfully!");
            LogManager.logAction("The map has been validated");

            p_gamePhaseHandler.setGameMap(l_gameMap);
            p_gamePhaseHandler.setMapFileName(p_fileName);

            if(p_isEditMapCommand){
                return;
            }

            p_gamePhaseHandler.setGamePhase(p_gamePhaseHandler.getGamePhase().getNextPhase());
            l_console.println("The Map has been loaded");
            LogManager.logAction("The Map has been loaded");
        } catch (IOException e) {
            l_console.println("Failed to load the file: " + e.getMessage());
            LogManager.logAction("Failed to load the file: " + e.getMessage());
        }
    }

    /**
     * Processes the borders from the given file and updates the map.
     *
     * @param p_file The list of strings representing the file content.
     * @param p_map The map object to be updated.
     * @param p_lineNum The starting line number for processing.
     * @return The line number after processing the borders.
     */
    private static int processBorders(List<String> p_file, Map p_map, int p_lineNum) {

        DefaultDirectedGraph<Country, DefaultEdge> l_countryGraph = p_map.getCountryMap();
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentGraph = p_map.getContinentMap();
        for(int l_lineNum = p_lineNum; l_lineNum < p_file.size(); l_lineNum++) {
            String[] l_borderInfoArray = p_file.get(l_lineNum).split(" ");

            if (l_borderInfoArray.length == 1) {
                return l_lineNum;
            }

            Country l_currentCountry = p_map.getCountryById(Integer.parseInt(l_borderInfoArray[0]));
            List<Integer> l_neighbourCountryIDList = new ArrayList<>();

            for (int l_borderIndex = 1; l_borderIndex < l_borderInfoArray.length; l_borderIndex++) {
                int l_neighbourCountryID = Integer.parseInt(l_borderInfoArray[l_borderIndex]);
                l_neighbourCountryIDList.add(l_neighbourCountryID);
                Country l_neighbourCountry = p_map.getCountryById(l_neighbourCountryID);
                l_countryGraph.addEdge(l_currentCountry, l_neighbourCountry);
                if (l_currentCountry.getContinentId() != l_neighbourCountry.getContinentId()) {
                    l_continentGraph.addEdge(l_currentCountry.getContinent(), l_neighbourCountry.getContinent());
                }
            }
            l_currentCountry.setNeighbourCountryIds(l_neighbourCountryIDList);
        }
        return p_file.size();
    }

    /**
     * Processes the countries from the given file and updates the map.
     *
     * @param p_file The list of strings representing the file content.
     * @param p_map The map object to be updated.
     * @param p_lineNum The starting line number for processing.
     * @return The line number after processing the countries.
     */
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
            l_country.setContinent(l_continent);
            l_continent.addCountry(l_country);
            l_countryGraph.addVertex(l_country);
        }
        return p_lineNum;
    }

    /**
     * Processes the continents from the given file and updates the map.
     *
     * @param p_file The list of strings representing the file content.
     * @param p_map The map object to be updated.
     * @param p_lineNum The starting line number for processing.
     * @return The line number after processing the continents.
     */
    private static int processContinents(List<String> p_file, Map p_map, int p_lineNum) {

        DefaultDirectedGraph<Continent, DefaultEdge> l_continentGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        for(int l_lineNum = p_lineNum; l_lineNum < p_file.size(); l_lineNum++) {
            String l_continentInfo = p_file.get(l_lineNum);
            if (l_continentInfo.isEmpty()) {
                return l_lineNum;
            }

            String[] l_continentInfoArray = l_continentInfo.split("\\s+");
            Continent l_continent = new Continent();
            l_continent.setName(l_continentInfoArray[0]);
            l_continent.setValue(Integer.parseInt(l_continentInfoArray[1]));
            l_continent.setId(l_lineNum-p_lineNum+1); // Line number is being used as continent id
            l_continentGraph.addVertex(l_continent);
            p_map.setContinentMap(l_continentGraph);
        }
        return CommonConstants.INTEGER_ZERO;
    }


    /**
     * Displays the current game map in a tabular format.
     *
     * @param p_gameMap Game phase.
     * @throws Exception If the map is not loaded.
     */
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
