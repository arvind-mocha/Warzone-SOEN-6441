package org.com.Constants;

/**
 * This class contains output messages of each command.
 *
 * @author Arvind Lakshmanan
 *
 */

public class CommandOutputMessages {

    // Message to get all instructions
    public static final String HELP_DEFAULT_MESSAGE = "Enter 'help' to get instructions of available commands";

    // Map commands help messages
    public static final String MAP_HELP = "usage: " +
            "\n\t loadmap <filename> :\tLoads a map from the given file path " +
            "\n\t editmap <filename> :\tLoads a map from an existing “domination” map file, or create a new map from scratch if the file does not exist." +
            "\n\n\t editcontinent -add <continentName> <continentvalue> -remove <continentName>" +
            "\t: Add or Remove continents to the map" +
            "\n\t editcountry -add <countryName> <continentName> -remove <countryName>" +
            "\t\t\t: Add or Remove countries to the map" +
            "\n\t editneighbor -add <countryID> <neighborcountryID> \n\t\t\t\t\t\t-remove <countryID> <neighborcountryID>" +
            "\t\t\t\t: Add or Remove neighbour countries to the map" +
            "\n\t savemap <filename> :\tSaves the user-created map into a file path given" +
            "\n\t validatemap \t\t:\tVerifies the correctness of the map" +
            "\n\t showmap\t: Displays the map that is loaded, if valid";

    // Game play commands help messages
    public static final String GAME_PLAY_HELP = "usage: " +
            "\n\t gameplayer -add <playername> -remove <playername>  :\tAdd or Remove player from the game " +
            "\n\t assigncountries\t\t\t\t\t\t\t:\tAssigns random countries to the players, and starts the game\n";

    // Exit command help message
    public static final String EXIT_KEY = "\t exit : Command to end the game";

    // In-game help message
    public static final String IN_GAME_HELP = "\n\t showmap :\tshow all countries and continents, armies on each country, ownership, and connectivity\n";

    // Issue order help message
    public static final String ISSUE_ORDER_HELP = "usage: " +
            "\n\t deploy <countryID> <num> \t: deploys armies to owned countries" +
            "\n\t showmap \t\t\t\t\t: show all countries and continents, armies on each country, ownership, and connectivity in a way that\n" +
            "\t\t\t\t\t\t\t\t enables efficient game play";
}