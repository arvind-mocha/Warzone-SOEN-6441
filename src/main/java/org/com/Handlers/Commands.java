package org.com.Handlers;

import org.com.Constants.CommonConstants;

import java.util.*;

/**
 * This enum contains all commands and their valid attributes.
 *
 * @author Arvind Lakshmanan
 *
 */

public enum Commands {
    HELP(CommonConstants.HELP_COMMAND, false, null),
    EXIT(CommonConstants.EXIT_COMMAND, false, null),
    SAVE_MAP(CommonConstants.SAVE_MAP_COMMAND, false, null),
    EDIT_MAP(CommonConstants.EDIT_MAP_COMMAND, true, null),
    LOAD_MAP(CommonConstants.LOAD_MAP_COMMAND, true, null),
    SHOW_MAP(CommonConstants.SHOW_MAP_COMMAND, false, null),
    VALIDATE_MAP(CommonConstants.VALIDATE_MAP_COMMAND, true, null),
    EDIT_NEIGHBOUR(CommonConstants.EDIT_NEIGHBOUR_COMMAND, false, CommonConstants.TWO_ATTRIBUTES_TWO_VALUE_EACH),
    EDIT_COUNTRY(CommonConstants.EDIT_COUNTRY_COMMAND, false, CommonConstants.TWO_ATTRIBUTES_TWO_ONE_VALUE),
    EDIT_CONTINENT(CommonConstants.EDIT_CONTINENT_COMMAND, false, CommonConstants.TWO_ATTRIBUTES_TWO_ONE_VALUE),
    ADD_PLAYER(CommonConstants.ADD_PLAYER_COMMAND, false, CommonConstants.TWO_ATTRIBUTES_ONE_VALUE_EACH),
    ASSIGN_COUNTRIES(CommonConstants.ASSIGN_COUNTRIES_COMMAND, false, null),
    ADVANCE_ARMY_COMMAND(CommonConstants.ADVANCE_ARMY_COMMAND, false, null),
    DEPLOY_ARMY_COMMAND(CommonConstants.DEPLOY_ARMIES_COMMAND, false, null),
    COMMIT(CommonConstants.COMMIT, false, null),;



    private final String d_name;
    private final HashMap<String, Integer> d_attributesHashMap;
    private final boolean d_isFileRequired;

    /**
     * Constructor for Commands enum.
     *
     * @param p_name The name of the command.
     * @param p_isFileRequired Indicates if a file is required for the command.
     * @param p_attributesHashMap The list of valid attributes for the command.
     */
    Commands(String p_name, boolean p_isFileRequired, HashMap<String, Integer> p_attributesHashMap) {
        this.d_name = p_name;
        this.d_isFileRequired = p_isFileRequired;
        this.d_attributesHashMap = p_attributesHashMap;
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
     * Checks if filename should be attached with the commands.
     *
     * @return the name of the command.
     */
    public boolean isFileRequired()
    {
        return d_isFileRequired;
    }

    /**
     * Retrieves the command enum if name of the command is provided.
     *
     * @param p_name The name of the command.
     *
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
