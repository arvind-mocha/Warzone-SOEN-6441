package org.com.Controls;

import org.com.Models.Continent;
import org.com.Models.Country;
import org.com.Models.Map;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
     * @return The processed map.
     */
    public static Map processMap(String p_fileName) {
        Map l_map = new Map();
        var l_console = System.console();
        DefaultDirectedGraph<Continent, DefaultEdge> l_continentGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        DefaultDirectedGraph<Country, DefaultEdge> l_countryGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        l_console.print("Loading the map from " + p_fileName + "...");

        try {
            List<String> l_lines = Files.readAllLines(Paths.get("src/main/resources/" + p_fileName));
            for (String l_lineStr : l_lines) {
                if (l_lineStr.isEmpty()) {
                    continue;
                }
                switch (l_lineStr) {
                    case "[continents]":
                        processContinents(l_lines, l_continentGraph, l_map);
                        continue;

                    case "[countries]":
                        processCountries(l_lines, l_countryGraph, l_map);
                        continue;

                    case "[borders]":
                        processBorders(l_lines, l_countryGraph, l_continentGraph, l_map);
                        break;
                }
            }
            l_map.setContinentMap(l_continentGraph);
            l_map.setCountryMap(l_countryGraph);
            l_console.print("Map loaded successfully!");
        } catch (IOException e) {
            l_console.print("Failed to load the file: " + e.getMessage());
        }
        return l_map;
    }

    private static void processBorders(List<String> p_lines, DefaultDirectedGraph<Country, DefaultEdge> p_countryGraph, DefaultDirectedGraph<Continent, DefaultEdge> p_continentGraph, Map p_map) {
    }

    private static void processCountries(List<String> p_lines, DefaultDirectedGraph<Country, DefaultEdge> p_countryGraph, Map p_map) {
    }

    private static void processContinents(List<String> p_lines, DefaultDirectedGraph<Continent, DefaultEdge> p_continentGraph, Map p_map) {
    }

}
