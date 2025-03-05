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

public class PlayerOperationsHandler {
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

    public static void processAssignCountries(GamePhaseHandler p_gamePhaseHandler)throws Exception {
        List<Player> l_playerList = p_gamePhaseHandler.getPlayerList();
        ValidationUtil.validateAssignCountries(l_playerList);
        ArrayList<Country> l_countryArray = new ArrayList<>(p_gamePhaseHandler.getGameMap().getCountryMap().vertexSet());
        Integer l_totalCountries = l_countryArray.size();
        int l_countrySplitValue = Math.floorDivExact(l_countryArray.size(), l_playerList.size());

        for (int l_playerIndex = 0; l_playerIndex < l_playerList.size(); l_playerIndex++) {
            Player l_player = l_playerList.get(l_playerIndex);
            int l_currentCountryId = l_countrySplitValue * (l_playerIndex);
            Country l_currentCountry = l_countryArray.get(l_currentCountryId >  l_totalCountries ? l_totalCountries : l_currentCountryId);
            l_player.addCountry(l_currentCountry);
            l_currentCountry.setOwner(l_player);
        }
        System.console().println("Game has begun!");
        p_gamePhaseHandler.assignReinforcements();
        p_gamePhaseHandler.setCurrentPlayer(0);
        int l_currentPlayerTurn = p_gamePhaseHandler.getCurrentPlayer();
        System.console().println("Player " + l_playerList.get(l_currentPlayerTurn).get_name() + " make your moves ");
        p_gamePhaseHandler.setGamePhase(p_gamePhaseHandler.getGamePhase().getNextPhase());
    }

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
}
