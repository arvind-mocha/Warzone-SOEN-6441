package org.com.Orders;

import org.com.Constants.CommandOutputMessages;
import org.com.Constants.CommonErrorMessages;
import org.com.GameLog.LogManager;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

/**
 * Advance order command functionality and validation are present in this class
 *
 *
 * @author Arvind Nachiappan
 * @author Devasenan Murugan
 */
public class AdvanceOrder implements Order {

    private Player d_player;
    private Country d_sourceCountry;
    private Country d_targetCountry;
    private int d_armies;

    /**
     * Constructor for the AdvanceOrder class
     *
     * @param p_player      The player giving the advance order
     * @param p_sourceCountry The country from which armies would be advanced
     * @param p_targetCountry   The country to which armies would be advanced
     * @param p_armies         The number of armies involved
     */
    public AdvanceOrder(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_armies) {
        this.d_player = p_player;
        this.d_sourceCountry = p_sourceCountry;
        this.d_targetCountry = p_targetCountry;
        this.d_armies = p_armies;
    }

    /**
        * Executes the AdvanceOrder command
     */
    @Override
    public void execute() {
        Player d_targetPlayer = d_targetCountry.getOwner();
        if (d_player.get_negotiationPlayer() != null && d_player.get_negotiationPlayer().equals(d_targetPlayer)) {
            System.out.println(String.format(CommandOutputMessages.PLAYER_DIPLOMACY, d_targetPlayer.get_name()));
            LogManager.logAction(String.format(CommandOutputMessages.PLAYER_DIPLOMACY, d_targetPlayer.get_name()));
        }

        int l_ArmyCount = d_sourceCountry.getArmyCount();
        int l_toMoveCount = 0;
        if (l_ArmyCount > d_armies ){
            l_toMoveCount = d_armies;
        } else {
            l_toMoveCount = l_ArmyCount - 1;
        }

        d_sourceCountry.setArmyCount(l_ArmyCount - l_toMoveCount);

        if(l_toMoveCount > 0) {
            int l_armyCountAfterAdvance = d_targetCountry.getArmyCount() - l_toMoveCount;
            if (l_armyCountAfterAdvance == 0){
                HelperUtil.setCountryOwnerShip(d_player, d_targetCountry, true);
            } else if (l_armyCountAfterAdvance < 0){
                HelperUtil.setCountryOwnerShip(d_player, d_targetCountry, false);
            }
            l_armyCountAfterAdvance = Math.abs(l_armyCountAfterAdvance);
            d_targetCountry.setArmyCount(l_armyCountAfterAdvance);

            LogManager.logAction(String.format("Player %s successfully advanced %d armies from %s to %s!", d_player.get_name(), l_toMoveCount, d_sourceCountry.getName(), d_targetCountry.getName()));
            System.out.println(String.format("Player %s successfully advanced %d armies from %s to %s!", d_player.get_name(), l_toMoveCount, d_sourceCountry.getName(), d_targetCountry.getName()));

        }

    }

    /**
     * Validates the AdvanceOrder command against the player
     */
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
