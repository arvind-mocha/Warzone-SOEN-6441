package org.com.Constants;

import java.io.Serializable;

/**
 * This class contains all global error messages.
 *
 * @author Arvind Lakshmanan
 *
 */

public class CommonErrorMessages implements Serializable {
    public static final String INVALID_FILE = "Provide a valid file";
    public static final String INVALID_COMMAND = "Invalid command";
    public static final String INVALID_ATTRIBUTE = "Provided attributes or values are invalid";
    public static final String CONTINENT_UNAVAILABLE = "No continents are available";
    public static final String COUNTRIES_UNAVAILABLE = "No countries are available";
    public static final String MAP_NOT_LOADED = "No map has been loaded";
    public static final String COUNTRYLESS_CONTINENT = "No countries are present in the continent";
    public static final String NEIGHBOURLESS_COUNTRIES = "No neighbour are present in for some countries";
    public static final String IMPROPER_NEIGHBOUR_MAPPING = "Neighbour countries has not been properly mapped";
    public static final String MAX_PLAYER_COUNT_REACHED = "Maximum player count reached";
    public static final String MIN_PLAYER_COUNT_NOT_REACHED = "Minimum player count 2 must be attained to assign countries";
    public static final String PLAYER_ALREADY_EXISTS = "Player %s exists already";
    public static final String CONTINENT_ALREADY_EXISTS = "Continent exists already";
    public static final String COUNTRY_ALREADY_EXISTS = "Country exists already";
    public static final String UNKNOWN_COUNTRY = "Country does not exist";
    public static final String UNKNOWN_CONTINENT = "Continent does not exist";
    public static final String COUNTRY_SET_TO_UNKNOWN_CONTINENT = "Country set with unknown Continent";
    public static final String PLAYER_NOT_EXISTS_REMOVAL = "Player %s does not exist to be removed";
    public static final String NO_PLAYER_EXISTS = "No player exists to be removed";
    public static final String IMPROPER_OWNER_COUNTRY = "Country %s does not belong to the player %s";
    public static final String ARMY_COUNT_EXCEEDS = "You only have %d remaining armies to deploy";
    public static final String NEUTRAL_COUNTRY_DEPLOYMENT = "Cannot deploy armies to a neutral country";
    public static final String ARMY_COUNT_ZERO = "Army count of zero cannot be used for deployment";
    public static final String INVALID_COUNTRY = "Country mentioned does not exists";
    public static final String NO_ADVANCE_COMMAND = "Player %s has no advance commands to be executed";
    public static final String PLAYED_MORE_THAN_ONE_POWER_CARD = "%s cannot be played, as only one power can be played in a turn. %s has already been played!";
}
