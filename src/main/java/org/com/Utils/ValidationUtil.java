package org.com.Utils;

import org.com.Constants.CommonConstants;
import org.com.Constants.CommonErrorMessages;
import org.com.GamePhase.Phase;
import org.com.Handlers.Commands;
import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ValidationUtil {

    /**
     * Validates the given command based on the current game phase.
     *
     * @param p_currentGamePhase The current game phase.
     * @param p_commandsArray The command entered by the user, split into an array.
     * @return true if the command is valid, false otherwise.
     */
    public static void validateCommand(Phase p_currentGamePhase, String[] p_commandsArray, String p_command)throws Exception
    {
        String l_baseCommand = p_commandsArray[0]; // 0th value of the array will always be the base command
        Commands l_commandEnum = Commands.getCommandByName(l_baseCommand); // fetching the respective command and its attributes

        // Skipping validation for some command since no processing needed
        if(l_baseCommand.equalsIgnoreCase(CommonConstants.HELP_COMMAND) || l_baseCommand.equalsIgnoreCase(CommonConstants.EXIT_COMMAND) ||l_baseCommand.equalsIgnoreCase(CommonConstants.SHOW_MAP_COMMAND) )
        {
            if(p_commandsArray.length > 1)
            {
                throw new Exception(CommonErrorMessages.INVALID_COMMAND);
            }
            return;
        }

        // Validating commands based on each phase
        if(!p_currentGamePhase.getValidCommands().contains(l_baseCommand))
        {
            throw new Exception(CommonErrorMessages.INVALID_COMMAND);
        }

        HashMap<String, Integer> l_attributesHashMap = l_commandEnum.getAttributesHashMap();
        // Validating the correctness of the file provided for required commands
        if(l_commandEnum.isFileRequired())
        {
//            if(p_commandsArray.length < 2 || !new File(CommonConstants.GAME_DATA_DIR + p_commandsArray[1]).exists())
            if(p_commandsArray.length < 2)
            {
                throw new Exception(CommonErrorMessages.INVALID_FILE);// Thrown exception will be caught and proper error message will get displayed
            }
        }

        // Validating the attributes of the command
        if(l_attributesHashMap != null)
        {
            String[] l_commandsArray = p_command.split(" -");
            if(l_commandsArray.length < 2)
            {
                throw new Exception(CommonErrorMessages.INVALID_ATTRIBUTE);
            }

            for(int l_index=1; l_index<l_commandsArray.length; l_index++)
            {
                String[] l_attributesArray = l_commandsArray[l_index].split(" ");
                String l_attribute = l_attributesArray[0];
                // Checking whether the attribute is provided and the provided attributes and values are valid
                if(l_attribute == null || !l_attributesHashMap.containsKey(l_attribute) || l_attributesHashMap.get(l_attribute) != l_attributesArray.length-1)
                {
                    throw new Exception(CommonErrorMessages.INVALID_ATTRIBUTE);
                }
            }
        }
    }

    public static void validateMap(Map p_gameMap) throws Exception
    {
        // Checking whether the map is empty
        if (p_gameMap == null) {
            throw new Exception(CommonErrorMessages.MAP_NOT_LOADED);
        }

        // Continent validation
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentGraph = p_gameMap.getContinentMap();
        // Checking whether the continents are present
        if(l_continentGraph.vertexSet().isEmpty())
        {
            throw new Exception(CommonErrorMessages.CONTINENT_UNAVAILABLE);
        }

        // Checking whether the continents contain countries
        for (Continent l_continent : l_continentGraph.vertexSet()) {
            if (l_continent.getCountries() == null) {
                throw new Exception(CommonErrorMessages.CONUTRYLESS_CONTINENT);
            }
        }

        // Countries validation
        DefaultDirectedGraph<Country, DefaultEdge> l_countriesGraph = p_gameMap.getCountryMap();
        // Checking whether countries are present
        if(l_countriesGraph.vertexSet().isEmpty())
        {
            throw new Exception(CommonErrorMessages.COUNTRIES_UNAVAILABLE);
        }

        // Checking whether countries have neighbouring countries
        for (Country l_country : l_countriesGraph.vertexSet()) {
            if (l_country.getNeighbourCountryIds().isEmpty()) {
                throw new Exception(CommonErrorMessages.NEIGHBOURLESS_COUNTRIES);
            }
        }

        // Checking whether the neighbouring countries are properly mapped
        for (Country l_i : l_countriesGraph.vertexSet()) {
            List<Integer> l_neighbourList = l_i.getNeighbourCountryIds();
            for (int l_neighbourIds : l_neighbourList) {
                Country l_country = p_gameMap.getCountryById(l_neighbourIds);
                if (!l_countriesGraph.containsEdge(l_country, l_i)) {
                    throw new Exception(CommonErrorMessages.IMPROPER_NEIGHBOUR_MAPPING);
                }
            }
        }
    }

    public static void validatePlayerManagement(List<Player> p_playerList, String p_operation, String p_playerName) throws Exception
    {
        if(p_operation.equalsIgnoreCase(CommonConstants.ADD_ATTRIBUTE)) {
            if(validatePlayerExistence(p_playerList, p_playerName))
            {
                throw new Exception(CommonErrorMessages.PLAYER_ALREADY_EXISTS);
            }
            if (p_playerList.size() >= CommonConstants.MAX_PLAYER_COUNT) {
                throw new Exception(CommonErrorMessages.MAX_PLAYER_COUNT_REACHED);
            }
        } else if (p_operation.equalsIgnoreCase(CommonConstants.REMOVE_ATTRIBUTE)) {
            if(p_playerList.isEmpty())
            {
                throw new Exception(CommonErrorMessages.NO_PLAYER_EXISTS);
            }
        }
    }

    public static void validateContinentManagement(Map p_gameMap, String p_operation, String p_continentName, String p_continentValue) throws Exception{
        try {
            Integer l_continentValue = Integer.parseInt(p_continentValue);
        } catch(NumberFormatException e){
            throw new Exception(CommonErrorMessages.INVALID_ATTRIBUTE);
        }

        Continent l_checkContinentExists = p_gameMap.getContinentByName(p_continentName);
        if(p_operation.equalsIgnoreCase(CommonConstants.ADD_ATTRIBUTE)) {
            if(l_checkContinentExists != null)
            {
                throw new Exception(CommonErrorMessages.CONTINENT_ALREADY_EXISTS);
            }
        } else if (p_operation.equalsIgnoreCase(CommonConstants.REMOVE_ATTRIBUTE)) {
            // Checking whether the continents are present
            if(l_checkContinentExists == null)
            {
                throw new Exception(CommonErrorMessages.CONTINENT_UNAVAILABLE);
            }
        }
    }

    public static void validateAssignCountries(List<Player> p_playerList)throws Exception {
        if (p_playerList.size() < CommonConstants.MIN_PLAYER_COUNT) {
            throw new Exception(CommonErrorMessages.MIN_PLAYER_COUNT_NOT_REACHED);
        }
    }

    public static boolean validatePlayerExistence(List<Player> p_playerList, String p_playerName)
    {
        for (Player player : p_playerList) {
            if (player.get_name().equalsIgnoreCase(p_playerName)) {
                return true;
            }
        }
        return false;
    }

}
