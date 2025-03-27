package org.com.Orders;

import org.com.Constants.CommonErrorMessages;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

public class AdvanceOrder implements Order {

    private Player d_player;
    private Country d_sourceCountry;
    private Country d_targetCountry;
    private int d_armies;

    public AdvanceOrder(Player d_player, Country d_sourceCountry, Country d_targetCountry, int d_armies) {
        this.d_player = d_player;
        this.d_sourceCountry = d_sourceCountry;
        this.d_targetCountry = d_targetCountry;
        this.d_armies = d_armies;
    }

    /**
     * Gets the player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return d_player;
    }

    /**
     * Sets the player.
     *
     * @param p_player the player to set
     */
    public void setPlayer(Player p_player) {
        this.d_player = p_player;
    }

    /**
     * Gets the source country.
     *
     * @return the source country
     */
    public Country getSourceCountry() {
        return d_sourceCountry;
    }

    /**
     * Sets the source country.
     *
     * @param p_sourceCountry the source country to set
     */
    public void setSourceCountry(Country p_sourceCountry) {
        this.d_sourceCountry = p_sourceCountry;
    }

    /**
     * Gets the target country.
     *
     * @return the target country
     */
    public Country getTargetCountry() {
        return d_targetCountry;
    }

    /**
     * Sets the target country.
     *
     * @param p_targetCountry the target country to set
     */
    public void setTargetCountry(Country p_targetCountry) {
        this.d_targetCountry = p_targetCountry;
    }

    /**
     * Gets the number of armies.
     *
     * @return the number of armies
     */
    public int getNum() {
        return d_armies;
    }

    /**
     * Sets the number of armies.
     *
     * @param p_num the number of armies to set
     */
    public void setNum(int p_num) {
        this.d_armies = p_num;
    }

    @Override
    public void execute() {
        d_sourceCountry.setArmyCount(d_sourceCountry.getArmyCount() - d_armies);

        if(d_targetCountry.isCountryNeutral()) {
            HelperUtil.setCountryOwnerShip(d_player, d_targetCountry, false);
            d_targetCountry.setArmyCount(d_targetCountry.getArmyCount() + d_armies);
        } else if (d_targetCountry.getOwner().equals(d_player)) {
            d_targetCountry.setArmyCount(d_targetCountry.getArmyCount() + d_armies);
        } else {
            int l_armyCountAfterAdvance = Math.abs(d_targetCountry.getArmyCount() - d_armies);
            if(d_armies > d_targetCountry.getArmyCount()) {
                HelperUtil.setCountryOwnerShip(d_player, d_targetCountry, false);
            } else if (l_armyCountAfterAdvance == 0) {
                HelperUtil.setCountryOwnerShip(d_player, d_targetCountry, true);
            }
            d_targetCountry.setArmyCount(l_armyCountAfterAdvance);
        }
    }

    @Override
    public void isValid() throws Exception {
        if (d_sourceCountry == null) {
            throw new Exception("Source country does not exists.");
        } else if (d_targetCountry == null) {
            throw new Exception("Target country does not exists.");
        } else if (d_sourceCountry.getName().equals(d_targetCountry.getName())) {
            throw new Exception("Target and source country cannot be the same");
        } else if (d_sourceCountry.getOwner() != d_player) {
            throw new Exception("You can only move armies from your own countries.");
        } else if (!d_sourceCountry.getNeighbourCountryIds().contains(d_targetCountry.getId())) {
            throw new Exception("Target country is not a neighbour of the source country.");
        } else if(d_armies > d_sourceCountry.getArmyCount()) {
            throw new Exception(String.format("You do not have the required army count to advance. Source army count: %s", d_sourceCountry.getArmyCount()));
        } else if(d_armies == 0){
            throw new Exception(CommonErrorMessages.ARMY_COUNT_ZERO);
        }
    }
}
