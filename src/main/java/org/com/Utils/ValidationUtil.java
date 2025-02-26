package org.com.Utils;

import org.com.Constants.CommonConstants;
import org.com.Constants.CommonErrorMessages;
import org.com.GamePhase.Phase;
import org.com.Handlers.Commands;
import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.File;
import java.util.List;

public class ValidationUtil {

    /**
     * Validates the given command based on the current game phase.
     *
     * @param p_currentGamePhase The current game phase.
     * @param p_commands The command entered by the user, split into an array.
     * @return true if the command is valid, false otherwise.
     */
    public static void validateCommand(Phase p_currentGamePhase, String[] p_commands)throws Exception
    {
        String l_baseCommand = p_commands[0]; // 0th value of the array will always be the base command
        Commands l_commandEnum = Commands.getCommandByName(l_baseCommand); // fetching the respective command and its attributes

        // skipping validation for help command since no processing needed for help
        if(l_baseCommand.equalsIgnoreCase(CommonConstants.HELP_COMMAND) || l_baseCommand.equalsIgnoreCase(CommonConstants.EXIT_COMMAND) ||l_baseCommand.equalsIgnoreCase(CommonConstants.SHOW_MAP_COMMAND) )
        {
            return;
        }

        // validating commands based on each phase
        if(!p_currentGamePhase.getValidCommands().contains(l_baseCommand))
        {
            throw new Exception(CommonErrorMessages.INVALID_COMMAND);
        }

        // validating the correctness of the file provided for required commands
        if(l_commandEnum.d_isFileRequired)
        {
            if(p_commands.length < 2 || !new File(CommonConstants.GAME_DATA_DIR + p_commands[1]).exists())
            {
                throw new Exception(CommonErrorMessages.INVALID_FILE);// Thrown exception will be caught and proper error message will get displayed
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
}
