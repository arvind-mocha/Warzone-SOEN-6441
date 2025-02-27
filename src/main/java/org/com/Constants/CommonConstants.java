package org.com.Constants;

import org.com.Utils.HelperUtil;

import java.util.HashMap;
import java.util.regex.Pattern;

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
    public static final String EDIT_COUNTRY_COMMAND = "editcountry";
    public static final String EDIT_NEIGHBOUR_COMMAND = "editneighbour";
    public static final String EDIT_CONTINENT_COMMAND = "editcontinent";
    public static final String ADD_PLAYER_COMMAND = "gameplayer";
    public static final String ASSIGN_COUNTRIES_COMMAND = "assigncountries";


    // Directories
    public static final String GAME_DATA_DIR = System.getProperty("user.dir") + "/src/main/resources/GameData/";

    // Integers
    public static final Integer INTEGER_ZERO = 0;
    public static final Integer MAX_PLAYER_COUNT = 5;
    public static final Integer MIN_PLAYER_COUNT = 2;

    // Strings
    public static final String CONTINENTS = "[continents]";
    public static final String COUNTRIES = "[countries]";
    public static final String BORDERS = "[borders]";

    // Attributes
    public static final String ADD_ATTRIBUTE = "add";
    public static final String REMOVE_ATTRIBUTE = "remove";
    public static final HashMap<String, Integer> TWO_ATTRIBUTES_TWO_ONE_VALUE;
    public static final HashMap<String, Integer> TWO_ATTRIBUTES_ONE_VALUE_EACH;
    public static final HashMap<String, Integer> TWO_ATTRIBUTES_TWO_VALUE_EACH;


    static {
        TWO_ATTRIBUTES_TWO_ONE_VALUE = HelperUtil.constructAttributeHashMap(new String[]{CommonConstants.ADD_ATTRIBUTE, CommonConstants.REMOVE_ATTRIBUTE}, new Integer[]{2,1}, 2);
        TWO_ATTRIBUTES_ONE_VALUE_EACH = HelperUtil.constructAttributeHashMap(new String[]{CommonConstants.ADD_ATTRIBUTE, CommonConstants.REMOVE_ATTRIBUTE}, new Integer[]{1,1}, 2);
        TWO_ATTRIBUTES_TWO_VALUE_EACH = HelperUtil.constructAttributeHashMap(new String[]{CommonConstants.ADD_ATTRIBUTE, CommonConstants.REMOVE_ATTRIBUTE}, new Integer[]{2,2}, 2);
    }
}
