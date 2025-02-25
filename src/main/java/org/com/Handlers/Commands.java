package org.com.Handlers;

import org.com.Constants.CommonConstants;

import java.util.Arrays;
import java.util.List;

/**
 * This enum contains all commands and their valid attributes.
 *
 * @author Arvind Lakshmanan
 *
 */

public enum Commands {
    HELP(CommonConstants.HELP_COMMAND, false, null),
    EXIT(CommonConstants.EXIT_COMMAND, false, null),
    SAVE_MAP(CommonConstants.SAVE_MAP_COMMAND, true, null),
    EDIT_MAP(CommonConstants.EDIT_MAP_COMMAND, true, null),
    LOAD_MAP(CommonConstants.LOAD_MAP_COMMAND, true, null),
    SHOW_MAP(CommonConstants.SHOW_MAP_COMMAND, false, null),
    VALIDATE_MAP(CommonConstants.VALIDATE_MAP_COMMAND, true, null),
    EDIT_NEIGHBOUR(CommonConstants.EDIT_NEIGHBOUR, false, Arrays.asList("-add", "-remove")),
    EDIT_COUNTRY(CommonConstants.EDIT_COUNTRY, false, Arrays.asList("-add", "-remove")),
    EDIT_CONTINENT(CommonConstants.EDIT_CONTINENT, false, Arrays.asList("-add", "-remove"));

    public final String d_name;
    public final List d_attributesList;
    public final boolean d_isFileRequired;

    /**
     * Constructor for Commands enum.
     *
     * @param p_name The name of the command.
     * @param p_isFileRequired Indicates if a file is required for the command.
     * @param p_attributesList The list of valid attributes for the command.
     */
    Commands(String p_name, boolean p_isFileRequired, List p_attributesList) {
        this.d_name = p_name;
        this.d_isFileRequired = p_isFileRequired;
        this.d_attributesList = p_attributesList;
    }

    public String getName() {
        return d_name;
    }

    public List getAttributesList() {
        return d_attributesList;
    }

    public boolean isFileRequired()
    {
        return d_isFileRequired;
    }

    public static Commands getCommandByName(String p_name) {
        for (Commands command : Commands.values()) {
            if (command.getName().equalsIgnoreCase(p_name)) {
                return command;
            }
        }
        return null;
    }
}
