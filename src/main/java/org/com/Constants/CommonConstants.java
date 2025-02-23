package org.com.Constants;

public class CommonConstants {

    /**
     * The following messages will be displayed at the start of the game.
     */
    public static final String WELCOME_MESSAGE = "Welcome to the game";
    public static final String HELP_MESSAGE = "Enter 'help' to get instructions of available commands";

    /**
     * By entering "help", a list of commands specifying the usage will be displayed for now: (Map commands, Game Play commands)
     */

    public static final String MAP_HELP = "usage: " +
            "\n\t loadmap <filename> :\tLoads a map from the given file path " +
            "\n\t editmap <filename> :\tLoads a map from an existing “domination” map file, or create a new map from scratch if the file does not exist." +

            "\n\n\t editcontinent -add <continentID> <continentvalue> -remove <continentI>" +
            "\t: Add or Remove continents to the map" +
            "\n\t editcountry -add <countryID> <continentID> -remove <countryID>" +
            "\t\t\t: Add or Remove countries to the map" +
            "\n\t editneighbor -add <countryID> <neighborcountryID> \n\t\t\t\t\t\t-remove <countryID> <neighborcountryID>" +
            "\t\t\t\t: Add or Remove neighbour countries to the map" +
            "\n" +
            "\n\t savemap <filename> :\tSaves the user-created map into a file path given" +
            "\n\t validatemap \t\t:\tVerifies the map correctness" +
            "\n" +
            "\n\t showmap\t: Displays the map that is loaded, if valid";

    public static final String GAME_PLAY_HELP = "usage: " +
            "\n\t gameplayer -add <playername> -remove <playername>  :\tAdd or Remove player from the game " +
            "\n\t assigncountries\t\t\t\t\t\t\t:\tAssigns random countries to the players, and starts the game\n";

    public static final String EXIT_KEY = "\t exit : Command to end the game";

    /**
     * Displays the map of the game.
     */
    public static final String IN_GAME_HELP = "\n\t showmap :\tshow all countries and continents, armies on each country, ownership, and connectivity\n";

    /**
     * Displays the usage of command to issue order from a player
     */
    public static final String ISSUE_ORDER_HELP = "usage: " +
            "\n\t deploy <countryID> <num> \t: deploys armies to owned countries" +
            "\n\t showmap \t\t\t\t\t: show all countries and continents, armies on each country, ownership, and connectivity in a way that\n" +
            "\t\t\t\t\t\t\t\t enables efficient game play";
}
