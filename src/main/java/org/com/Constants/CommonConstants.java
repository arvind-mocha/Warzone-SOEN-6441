package org.com.Constants;

/**
 * This class contains all global constants.
 *
 * @author Arvind Lakshmanan
 *
 */

public class CommonConstants {

    // Game commands
    public static final String EXIT_COMMAND = "exit";
    public static final String HELP_COMMAND = "help";
    public static final String LOAD_MAP_COMMAND = "loadmap";
    public static final String SAVE_MAP_COMMAND = "savemap";
    public static final String EDIT_MAP_COMMAND = "editmap";
    public static final String SHOW_MAP_COMMAND = "showmap";
    public static final String VALIDATE_MAP_COMMAND = "validatemap";
    public static final String EDIT_COUNTRY = "editcountry";
    public static final String EDIT_NEIGHBOUR = "editneighbour";
    public static final String EDIT_CONTINENT = "editcontinent";

    // Directories
    public static final String GAME_DATA_DIR = System.getProperty("user.dir") + "/src/main/resources/GameData/";

    // Integers
    public static final Integer INTEGER_ZERO = 0;

    // Strings
    public static final String CONTINENTS = "[continents]";
    public static final String COUNTRIES = "[countries]";
    public static final String BORDERS = "[borders]";
}
