package org.com.Orders;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonErrorMessages;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

/**
 * Advance order command functionality and validation are present in this class
 *
 *
 * @author Arvind Nachiappan
 */
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

    @Override
    public void execute() {
        Player d_targetPlayer = d_targetCountry.getOwner();
        if (d_player.get_negotiationPlayer() != null && d_player.get_negotiationPlayer().equals(d_targetPlayer)) {
            System.out.println(String.format(CommandOutputMessages.PLAYER_DIPLOMACY, d_targetPlayer.get_name()));
        }

        d_sourceCountry.setArmyCount(d_sourceCountry.getArmyCount() - d_armies);
        if (d_targetCountry.isCountryNeutral()) {
            HelperUtil.setCountryOwnerShip(d_player, d_targetCountry, false);
            d_targetCountry.setArmyCount(d_targetCountry.getArmyCount() + d_armies);
        } else if (d_targetPlayer.equals(d_player)) {
            d_targetCountry.setArmyCount(d_targetCountry.getArmyCount() + d_armies);
        } else {
            int l_armyCountAfterAdvance = d_targetCountry.getArmyCount() - d_armies;
            if (d_armies > d_targetCountry.getArmyCount()) {
                HelperUtil.setCountryOwnerShip(d_player, d_targetCountry, false);
                l_armyCountAfterAdvance = Math.abs(l_armyCountAfterAdvance);
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
        } else if (d_armies == d_sourceCountry.getArmyCount()) {
            throw new Exception(String.format("At least one army my be present in source country. Source army count: %s", d_sourceCountry.getArmyCount()));
        }
    }
}
