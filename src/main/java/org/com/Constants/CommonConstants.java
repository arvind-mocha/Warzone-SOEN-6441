package org.com.Constants;

import org.com.Orders.AdvanceOrder;
import org.com.Orders.BombOrder;
import org.com.Orders.Order;
import org.com.Utils.HelperUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains all global constants.
 *
 * @author Arvind Lakshmanan
 *
 */

public class CommonConstants implements Serializable {

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
    public static final String TOURNAMENT_COMMAND = "tournament";
    public static final String ADD_PLAYER_COMMAND = "gameplayer";
    public static final String ASSIGN_COUNTRIES_COMMAND = "assigncountries";
    public static final String DEPLOY_ARMIES_COMMAND = "deploy";
    public static final String ADVANCE_ARMY_COMMAND = "advance";
    public static final String COMMIT = "commit";
    public static final String BLOCKADE_COMMAND = "blockade";
    public static final String NEGOTIATE_COMMAND = "negotiate";
    public static final String BOMB_COMMAND = "bomb";
    public static final String AIRLIFT_COMMAND = "airlift";
    public static final String SAVE_GAME_COMMAND = "savegame";
    public static final String LOAD_GAME_COMMAND = "loadgame";


    // Directories
    public static final String GAME_DATA_DIR = System.getProperty("user.dir") + "/src/main/resources/GameData/";

    // Integers
    public static final Integer INTEGER_ZERO = 0;
    public static final Integer MAX_PLAYER_COUNT = 5;
    public static final Integer MIN_PLAYER_COUNT = 2;

    // Game Mode
    public static final String SINGLE_GAME_MODE = "Single";
    public static final String TOURNAMENT_GAME_MODE = "Tournament";

    // Strings
    public static final String CONTINENTS = "[continents]";
    public static final String COUNTRIES = "[countries]";
    public static final String BORDERS = "[borders]";

    // Computer Commands
    public static final String DEPLOY = DEPLOY_ARMIES_COMMAND + " %s %d";
    public static final String ADVANCE = ADVANCE_ARMY_COMMAND + " %s %s %d";
    public static final String BOMB = BOMB_COMMAND + " %s";
    public static final String BLOCKADE = BLOCKADE_COMMAND + " %s";
    public static final String AIRLIFT = AIRLIFT_COMMAND + " %s %s %d";
    public static final String NEGOTIATE = NEGOTIATE_COMMAND + " %s";
    public static final String LOAD_MAP = LOAD_MAP_COMMAND + " %s";


    // Attributes
    public static final String ADD_ATTRIBUTE = "add";
    public static final String REMOVE_ATTRIBUTE = "remove";
    public static final String MAP_ATTRIBUTE = "M";
    public static final String PLAYER_ATTRIBUTE = "P";
    public static final String GAME_ATTRIBUTE = "G";
    public static final String TURNS_ATTRIBUTE = "D";
    public static final HashMap<String, Integer> TWO_ATTRIBUTES_TWO_ONE_VALUE;
    public static final HashMap<String, Integer> TWO_ATTRIBUTES_TWO_VALUE_EACH;
    public static final HashMap<String, Integer> FOUR_ATTRIBUTES_ONE_VALUE_EACH;


    static {
        TWO_ATTRIBUTES_TWO_ONE_VALUE = HelperUtil.constructAttributeHashMap(new String[]{ADD_ATTRIBUTE, REMOVE_ATTRIBUTE}, new Integer[]{2,1}, 2);
        TWO_ATTRIBUTES_TWO_VALUE_EACH = HelperUtil.constructAttributeHashMap(new String[]{ADD_ATTRIBUTE, REMOVE_ATTRIBUTE}, new Integer[]{2,2}, 2);
        FOUR_ATTRIBUTES_ONE_VALUE_EACH = HelperUtil.constructAttributeHashMap(new String[]{MAP_ATTRIBUTE, PLAYER_ATTRIBUTE, GAME_ATTRIBUTE, TURNS_ATTRIBUTE}, new Integer[]{1,1,1,1}, 4);
    }
}
