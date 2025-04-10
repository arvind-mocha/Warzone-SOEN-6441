package org.com.Handlers;

import org.com.Constants.CommonConstants;

import java.util.HashMap;

/**
 * This enum contains all commands and their valid attributes.
 *
 * @author Arvind Lakshmanan
 */

public enum Commands {
    HELP(CommonConstants.HELP_COMMAND, false, null, null),
    EXIT(CommonConstants.EXIT_COMMAND, false, null, null),
    SAVE_MAP(CommonConstants.SAVE_MAP_COMMAND, false, null, null),
    EDIT_MAP(CommonConstants.EDIT_MAP_COMMAND, true, null, null),
    LOAD_MAP(CommonConstants.LOAD_MAP_COMMAND, true, null, null),
    SHOW_MAP(CommonConstants.SHOW_MAP_COMMAND, false, null, null),
    VALIDATE_MAP(CommonConstants.VALIDATE_MAP_COMMAND, true, null, null),
    EDIT_NEIGHBOUR(CommonConstants.EDIT_NEIGHBOUR_COMMAND, false, CommonConstants.TWO_ATTRIBUTES_TWO_VALUE_EACH, null),
    EDIT_COUNTRY(CommonConstants.EDIT_COUNTRY_COMMAND, false, CommonConstants.TWO_ATTRIBUTES_TWO_ONE_VALUE, null),
    EDIT_CONTINENT(CommonConstants.EDIT_CONTINENT_COMMAND, false, CommonConstants.TWO_ATTRIBUTES_TWO_ONE_VALUE, null),
    TOURNAMENT(CommonConstants.TOURNAMENT_COMMAND, false, CommonConstants.FOUR_ATTRIBUTES_ONE_VALUE_EACH, null),
    ADD_PLAYER(CommonConstants.ADD_PLAYER_COMMAND, false, CommonConstants.TWO_ATTRIBUTES_TWO_ONE_VALUE, null),
    ASSIGN_COUNTRIES(CommonConstants.ASSIGN_COUNTRIES_COMMAND, false, null, null),
    ADVANCE_ARMY_COMMAND(CommonConstants.ADVANCE_ARMY_COMMAND, false, null, 4),
    DEPLOY_ARMY_COMMAND(CommonConstants.DEPLOY_ARMIES_COMMAND, false, null, 3),
    BLOCKADE_COMMAND(CommonConstants.BLOCKADE_COMMAND, false, null, 2),
    NEGOTIATE_COMMAND(CommonConstants.NEGOTIATE_COMMAND, false, null, 2),
    BOMB_COMMAND(CommonConstants.BOMB_COMMAND, false, null, 2),
    AIRLIFT_COMMAND(CommonConstants.AIRLIFT_COMMAND, false, null, 4),
    COMMIT(CommonConstants.COMMIT, false, null, null),
    SAVE_GAME(CommonConstants.SAVE_GAME_COMMAND, true, null, null),
    LOAD_GAME(CommonConstants.LOAD_GAME_COMMAND, true, null, null);

    private final String d_name;
    private final HashMap<String, Integer> d_attributesHashMap;
    private final boolean d_isFileRequired;
    private final Integer d_commandSize;

    /**
     * Constructor for Commands enum.
     *
     * @param p_name              The name of the command.
     * @param p_isFileRequired    Indicates if a file is required for the command.
     * @param p_attributesHashMap The list of valid attributes for the command.
     */
    Commands(String p_name, boolean p_isFileRequired, HashMap<String, Integer> p_attributesHashMap, Integer p_commandSize) {
        this.d_name = p_name;
        this.d_isFileRequired = p_isFileRequired;
        this.d_attributesHashMap = p_attributesHashMap;
        this.d_commandSize = p_commandSize;
    }

    /**
     * Retrieves the name of the command.
     *
     * @return the name of the command.
     */
    public String getName() {
        return d_name;
    }

    /**
     * Retrieves the attributes of the command.
     *
     * @return the name of the command.
     */
    public HashMap<String, Integer> getAttributesHashMap() {
        return d_attributesHashMap;
    }

    /**
     * Retrieves the command size.
     *
     * @return the command size.
     */
    public Integer getCommandSize() {
        return d_commandSize;
    }

    /**
     * Checks if filename should be attached with the commands.
     *
     * @return the name of the command.
     */
    public boolean isFileRequired() {
        return d_isFileRequired;
    }

    /**
     * Retrieves the command enum if name of the command is provided.
     *
     * @param p_name The name of the command.
     * @return the name of the command.
     */
    public static Commands getCommandByName(String p_name) {
        for (Commands command : Commands.values()) {
            if (command.getName().equalsIgnoreCase(p_name)) {
                return command;
            }
        }
        return null;
    }
}
