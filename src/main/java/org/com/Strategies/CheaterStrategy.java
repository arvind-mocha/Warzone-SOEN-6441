package org.com.Strategies;

import org.com.GameLog.LogManager;
import org.com.Handlers.GamePhaseHandler;
import org.com.Models.Country;
import org.com.Models.Map;
import org.com.Models.Player;

<<<<<<< HEAD
<<<<<<< HEAD
import java.util.ArrayList;
=======
import java.io.Serializable;
>>>>>>> 804f482 (Made all the files serializable)
=======
import java.io.Serializable;
=======
import java.util.ArrayList;
>>>>>>> 829dd75 (fixed Benevolent and Cheater stratergies - works completely)
>>>>>>> afdec0f (rebasing on main)
import java.util.List;

public class CheaterStrategy implements Strategy, Serializable {
    @Override
    public List<String> createOrder(GamePhaseHandler p_gamePhaseHandler, Player p_currentPlayer) {
        Player l_currentPlayer = p_gamePhaseHandler.getPlayerList().get(p_gamePhaseHandler.getCurrentPlayer());
        if (l_currentPlayer.get_countries().isEmpty()) return null;
        boolean l_conquredEnemy = false;
        List<Country> originalCountries = new ArrayList<>(l_currentPlayer.get_countries());
        for (Country l_country : originalCountries) {
            for (int l_neighbourCountryId : l_country.getNeighbourCountryIds()) {
                Country l_neighbour = p_gamePhaseHandler.getGameMap().getCountryById(l_neighbourCountryId);
                if (l_neighbour.getOwner() != l_currentPlayer && !l_neighbour.isCountryNeutral()) {
                    l_neighbour.getOwner().get_countries().remove(l_neighbour);
                    l_neighbour.setOwner(l_currentPlayer);
                    l_currentPlayer.addCountry(l_neighbour);
                    l_conquredEnemy = true;
                    LogManager.logAction("CountryID " + l_neighbour.getId() + " conquered by " + l_currentPlayer.get_name());
                }
            }
            if(l_conquredEnemy){
                l_country.setArmyCount(2 * l_country.getArmyCount());
                l_conquredEnemy = false;
            }
        }
        return null;
    }

    @Override
    public String generateCardOrder(GamePhaseHandler p_gameManager, Player p_currentPlayer, String p_prioritizeCard) {
        // In CheaterStrategy, we assume the player can always play a Bomb card
        // Regardless of whether the player actually has the card, we return a "Bomb" card order
        if ("Bomb".equals(p_prioritizeCard)) {
            return "Bomb " + p_currentPlayer.get_cards().getOrDefault("Bomb", 0);  // Return Bomb order with available count
        }

        // You can add similar checks for other cards (e.g., Airlift, Blockade) if needed

        return "";  // Return empty string for unsupported card types
    }


    @Override
    public Country getNeighbour(Country p_country, Map p_gameMap, Player p_currentPlayer) {
        return null;
    }
}
