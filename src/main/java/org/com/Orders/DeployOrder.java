package org.com.Orders;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonErrorMessages;
import org.com.GameLog.LogManager;
import org.com.Models.Country;
import org.com.Models.Player;

/**
 * Bomb order command functionality and validation are present in this class
 *
 *
 * @author Arvind Nachiappan
 */
public class DeployOrder implements Order{

    private Player d_player;
    private Country d_country;
    private int d_army;

    public DeployOrder(Player d_player, Country d_country, int d_army) {
        this.d_player = d_player;
        this.d_country = d_country;
        this.d_army = d_army;
    }

    /**
     * Gets the player.
     * @return the player
     */
    public Player getPlayer() {
        return d_player;
    }

    /**
     * Sets the player.
     * @param p_player the player to set
     */
    public void setPlayer(Player p_player) {
        this.d_player = p_player;
    }

    /**
     * Gets the country.
     * @return the country
     */
    public Country getCountry() {
        return d_country;
    }

    /**
     * Sets the country.
     * @param p_country the country to set
     */
    public void setCountry(Country p_country) {
        this.d_country = p_country;
    }

    /**
     * Gets the army count.
     * @return the army count
     */
    public int getArmy() {
        return d_army;
    }

    /**
     * Sets the army count.
     * @param p_army the army count to set
     */
    public void setArmy(int p_army) {
        this.d_army = p_army;
    }

    @Override
    public void execute() {
        d_country.setArmyCount(d_country.getArmyCount() + d_army);
        d_player.set_armyCount(d_player.get_armyCount() - d_army);

        d_country.setTurnArmyCount(d_country.getArmyCount());

        System.console().println(String.format(CommandOutputMessages.PLAYER_SUCCESSFUL_ARMY_DEPLOYMENT, d_army, d_country.getName(), d_player.get_armyCount()));
        LogManager.logAction(String.format(CommandOutputMessages.PLAYER_SUCCESSFUL_ARMY_DEPLOYMENT, d_player.get_name(), d_army, d_country.getName(), d_player.get_armyCount()));
    }

    @Override
    public void isValid() throws Exception {
        if (d_country == null) {
            throw new Exception(CommonErrorMessages.INVALID_COUNTRY);
        } else if (d_country.isCountryNeutral()) {
            throw new Exception(CommonErrorMessages.NEUTRAL_COUNTRY_DEPLOYMENT);
        } else if (!d_player.get_countries().contains(d_country)) {
            throw new Exception(String.format(CommonErrorMessages.IMPROPER_OWNER_COUNTRY, d_country.getName(), d_player.get_name()));
        } else if(d_army > d_player.get_armyCount()) {
            throw new Exception(String.format(CommonErrorMessages.ARMY_COUNT_EXCEEDS, d_player.get_armyCount()));
        } else if(d_army == 0){
            throw new Exception(CommonErrorMessages.ARMY_COUNT_ZERO);
        }
    }
}
