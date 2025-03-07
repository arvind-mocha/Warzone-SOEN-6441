package org.com.Handlers;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonConstants;
import org.com.Constants.CommonErrorMessages;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;
import org.com.Utils.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * All operations related to a Player like<ul><li>Deploy</li></ul>command is written here
 *
 * @author Arvind Lakshmanan
 * @author Barath Sundararaj
 *
 */

public class PlayerOperationsHandler {

    /**
     * This method is used depicts the beginning of the player registration.
     * Performs acctions like:
     * <ul>
     *     <li>Add New Player</li>
     *     <li>Remove Existing Player</li>
     * </ul>
     * @param p_command The user input
     * @param p_gamePhaseHandler Current Game Phase
     * @throws Exception Error in case of a non-existing player is asked to be removed
     */
    public static void processPlayerManagement(String p_command, GamePhaseHandler p_gamePhaseHandler) throws Exception
    {
        List<Player> l_playerList = p_gamePhaseHandler.getPlayerList();
        String[] l_commandsArray = p_command.split(" -");
        for(int l_index=1; l_index<l_commandsArray.length; l_index++) {
            String[] l_attributesArray = l_commandsArray[l_index].split(" -");

            for (String l_attribute : l_attributesArray) {
                String[] l_operationsArray = l_attribute.split(" ");
                String l_attributeOperation = l_operationsArray[0];
                String l_playerName = l_operationsArray[1];
                ValidationUtil.validatePlayerManagement(l_playerList, l_attributeOperation, l_playerName);

                if (l_attributeOperation.equalsIgnoreCase(CommonConstants.ADD_ATTRIBUTE)) {
                    Player l_player = new Player(l_playerName);
                    l_playerList.add(l_player);
                    System.console().println(String.format("Player %s has been added successfully", l_playerName));
                } else if (l_attributeOperation.equalsIgnoreCase(CommonConstants.REMOVE_ATTRIBUTE)) {
                    if(!ValidationUtil.validatePlayerExistence(l_playerList, l_playerName))
                    {
                        throw new Exception(String.format(CommonErrorMessages.PLAYER_NOT_EXISTS_REMOVAL, l_playerName));
                    }
                    l_playerList.removeIf(l_player -> l_player.get_name().equalsIgnoreCase(l_playerName));
                    System.console().println(String.format("Player %s! has been removed successfully", l_playerName));
                }
            }
        }
    }

    /**
     * This method is used to divide the Loaded Map and <b>Assign Countries</b> among the registered players.
     * @param p_gamePhaseHandler Game Phase
     * @throws Exception
     */
    public static void processAssignCountries(GamePhaseHandler p_gamePhaseHandler) throws Exception {
        List<Player> l_playerList = p_gamePhaseHandler.getPlayerList();
        ValidationUtil.validateAssignCountries(l_playerList);
        ArrayList<Country> l_countryArray = new ArrayList<>(p_gamePhaseHandler.getGameMap().getCountryMap().vertexSet());
        int l_totalCountries = l_countryArray.size();
        int l_numPlayers = l_playerList.size();
        int l_countrySplitValue = l_totalCountries / l_numPlayers; // Integer division

        // Assign the base number of countries to each player
        int l_countryIndex = 0;
        for (int l_playerIndex = 0; l_playerIndex < l_numPlayers; l_playerIndex++) {
            Player l_player = l_playerList.get(l_playerIndex);
            for (int i = 0; i < l_countrySplitValue; i++) {
                Country l_currentCountry = l_countryArray.get(l_countryIndex++);
                l_player.addCountry(l_currentCountry);
                l_currentCountry.setOwner(l_player);
            }
        }

        // Distribute any remaining countries
        int l_remainingCountries = l_totalCountries % l_numPlayers;
        for (int i = 0; i < l_remainingCountries; i++) {
            Player l_player =  l_playerList.get(i);
            Country l_currentCountry = l_countryArray.get(l_countryIndex++);
            l_player.addCountry(l_currentCountry);
            l_currentCountry.setOwner(l_player);
        }

        System.console().println("Game has begun!");
        p_gamePhaseHandler.assignReinforcements();
        p_gamePhaseHandler.setCurrentPlayer(0);
        int l_currentPlayerTurn = p_gamePhaseHandler.getCurrentPlayer();
        System.console().println("Player " + l_playerList.get(l_currentPlayerTurn).get_name() + " make your moves");
        p_gamePhaseHandler.setGamePhase(p_gamePhaseHandler.getGamePhase().getNextPhase());
    }


    /**
     * This method marks the beginning of the game where players are asked to <b>deploy all of their armies in their own countries.</b>
     * Only after a player <b>deploys all</b> of his/her armies, the next player's turn begins.
     * @param p_gamePhaseHandler Game Phase
     * @param p_commandArray User Input Command
     * @throws Exception Invalid Deploy Command
     */
    public static void processDeployArmies(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        if (p_commandArray.length != 3) {
            throw new Exception("Invalid deploy command. Usage: deploy <country_name> <num_armies>");
        }

        String l_countryName = p_commandArray[1];
        int l_numArmies = Integer.parseInt(p_commandArray[2]);

        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        Country l_targetCountry = null;

        // Find the country by name
        for (Country l_country : l_gameMap.getCountryMap().vertexSet()) {
            if (l_country.getName().equalsIgnoreCase(l_countryName)) {
                l_targetCountry = l_country;
                break;
            }
        }

        if (l_targetCountry == null) {
            throw new Exception("Invalid country name.");
        }

        if (l_targetCountry.getOwner() != l_currentPlayer) {
            throw new Exception("You can only deploy armies to your own countries.");
        }

        if (l_numArmies > l_currentPlayer.get_armyCount()) {
            throw new Exception("You don't have enough armies to deploy.");
        }

        l_targetCountry.setArmyCount(l_targetCountry.getArmyCount() + l_numArmies);
        l_currentPlayer.set_armyCount(l_currentPlayer.get_armyCount() - l_numArmies);

        System.console().println(String.format("Successfully deployed %d armies to %s", l_numArmies, l_targetCountry.getName()));

        // Check if the player has finished deploying
        if (l_currentPlayer.get_armyCount() == 0) {
            int l_nextPlayerIndex = (p_gamePhaseHandler.getCurrentPlayer() + 1) % p_gamePhaseHandler.getPlayerList().size();
            p_gamePhaseHandler.setCurrentPlayer(l_nextPlayerIndex);
            System.console().println("Next player's turn: " + p_gamePhaseHandler.getPlayerList().get(l_nextPlayerIndex).get_name());
        }
    }

    public static void processAdvanceCommand(GamePhaseHandler p_gamePhaseHandler, String[] p_commandArray) throws Exception {
        if (p_commandArray.length != 4 && !p_commandArray[0].equalsIgnoreCase("commit")) {
            throw new Exception("Invalid advance command. Usage: advance <source_country> <target_country> <num_armies> or commit");
        }

        if (p_commandArray[0].equalsIgnoreCase("commit")) {
            executeBufferedCommands(p_gamePhaseHandler);
            return;
        }

        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());  //Added this line

        String l_sourceCountryName = p_commandArray[1];
        String l_targetCountryName = p_commandArray[2];
        int l_numArmies = Integer.parseInt(p_commandArray[3]);



        Map l_gameMap = p_gamePhaseHandler.getGameMap();
        Country l_sourceCountry = null;
        Country l_targetCountry = null;

        // Find source country by name
        for (Country l_country : l_gameMap.getCountryMap().vertexSet()) {
            if (l_country.getName().equalsIgnoreCase(l_sourceCountryName)) {
                l_sourceCountry = l_country;
                break;
            }
        }

        // Find target country by name
        for (Country l_country : l_gameMap.getCountryMap().vertexSet()) {
            if (l_country.getName().equalsIgnoreCase(l_targetCountryName)) {
                l_targetCountry = l_country;
                break;
            }
        }

        if (l_sourceCountry == null) {
            throw new Exception("Invalid source country name.");
        }

        if (l_targetCountry == null) {
            throw new Exception("Invalid target country name.");
        }

        if (l_sourceCountry.getOwner() != l_currentPlayer) {
            throw new Exception("You can only move armies from your own countries.");
        }

        if (l_targetCountry.getOwner() == l_currentPlayer) {
            // Move armies between owned countries
            if (!l_sourceCountry.getNeighbourCountryIds().contains(l_targetCountry.getId())) {
                throw new Exception("Target country is not a neighbor of the source country.");
            }

            if (l_numArmies <= 0 || l_numArmies >= l_sourceCountry.getArmyCount()) {
                throw new Exception("Invalid number of armies to move.  Must be greater than 0 and less than the source army count.");
            }

            // Command is valid, add it to the buffer
            p_gamePhaseHandler.getAdvanceCommandsBuffer().add(p_commandArray);
            System.console().println(String.format("Advance command added to buffer: move %d armies from %s to %s", l_numArmies, l_sourceCountry.getName(), l_targetCountry.getName()));


        } else {
            System.out.println("You can only advance to your own country");
        }
    }

    // Method to execute the buffered commands
    private static void executeBufferedCommands(GamePhaseHandler p_gamePhaseHandler) throws Exception {
        List<String[]> l_commands = p_gamePhaseHandler.getAdvanceCommandsBuffer();
        if (l_commands.isEmpty()) {
            System.console().println("No advance commands in buffer.");
            return;
        }

        //Iterate through buffered commands
        for (String[] l_commandArray : l_commands) {
            String l_sourceCountryName = l_commandArray[1];
            String l_targetCountryName = l_commandArray[2];
            int l_numArmies = Integer.parseInt(l_commandArray[3]);

            Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
            Map l_gameMap = p_gamePhaseHandler.getGameMap();
            Country l_sourceCountry = null;
            Country l_targetCountry = null;

            // Find source country by name
            for (Country l_country : l_gameMap.getCountryMap().vertexSet()) {
                if (l_country.getName().equalsIgnoreCase(l_sourceCountryName)) {
                    l_sourceCountry = l_country;
                    break;
                }
            }

            // Find target country by name
            for (Country l_country : l_gameMap.getCountryMap().vertexSet()) {
                if (l_country.getName().equalsIgnoreCase(l_targetCountryName)) {
                    l_targetCountry = l_country;
                    break;
                }
            }
            if (l_sourceCountry == null) {
                throw new Exception("Invalid source country name.");
            }

            if (l_targetCountry == null) {
                throw new Exception("Invalid target country name.");
            }

            if (l_sourceCountry.getOwner() != l_currentPlayer) {
                throw new Exception("You can only move armies from your own countries.");
            }

            if (l_targetCountry.getOwner() == l_currentPlayer) {
                // Move armies between owned countries
                if (!l_sourceCountry.getNeighbourCountryIds().contains(l_targetCountry.getId())) {
                    throw new Exception("Target country is not a neighbor of the source country.");
                }

                if (l_numArmies <= 0 || l_numArmies >= l_sourceCountry.getArmyCount()) {
                    throw new Exception("Invalid number of armies to move.  Must be greater than 0 and less than the source army count.");
                }
                l_sourceCountry.setArmyCount(l_sourceCountry.getArmyCount() - l_numArmies);
                l_targetCountry.setArmyCount(l_targetCountry.getArmyCount() + l_numArmies);

                System.console().println(String.format("Moved %d armies from %s to %s", l_numArmies, l_sourceCountry.getName(), l_targetCountry.getName()));

            } else {
                System.out.println("You can only advance to your own country");
            }

        }
        p_gamePhaseHandler.clearAdvanceCommandsBuffer();

        advanceTurn(p_gamePhaseHandler);
    }

    public static void advanceTurn(GamePhaseHandler p_gamePhaseHandler) {
        int l_nextPlayerIndex = (p_gamePhaseHandler.getCurrentPlayer() + 1) % p_gamePhaseHandler.getPlayerList().size();
        p_gamePhaseHandler.setCurrentPlayer(l_nextPlayerIndex);
        System.console().println("Next player's turn: " + p_gamePhaseHandler.getPlayerList().get(l_nextPlayerIndex).get_name());
    }
}
