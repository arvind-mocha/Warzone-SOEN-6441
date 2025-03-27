package org.com.Orders;

import org.com.Constants.Cards;
import org.com.Constants.CommonErrorMessages;
import org.com.GameLog.LogManager;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

/**
 * Airlift order command functionality and validation are present in this class
 *
 *
 * @author Arvind Nachiappan
 */
public class AirLiftOrder implements Order{

    private Player d_player;
    private Country d_sourceCountry;
    private Country d_targetCountry;
    private int d_armies;

    public AirLiftOrder(Player p_player, Country p_sourceCountry, Country p_targetCountry, int p_armies) {
        this.d_player = p_player;
        this.d_sourceCountry = p_sourceCountry;
        this.d_targetCountry = p_targetCountry;
        this.d_armies = p_armies;
    }

    @Override
    public void execute() {

        int l_numOfCards = d_player.get_cards().get(Cards.AIRLIFT_CARD);
        if(l_numOfCards == 0){
            d_player.get_cards().remove(Cards.AIRLIFT_CARD);
        }else{
            d_player.get_cards().put(Cards.AIRLIFT_CARD, l_numOfCards - 1);
        }

        int l_ArmyCount = d_sourceCountry.getArmyCount();
        int l_tempArmyCount = d_sourceCountry.getTurnArmyCount();
        int l_toMoveCount = 0;
        if (l_tempArmyCount > d_armies ){
            l_toMoveCount = d_armies;
        } else {
            l_toMoveCount = l_tempArmyCount - 1;
        }

        d_sourceCountry.setTurnArmyCount(l_tempArmyCount - l_toMoveCount);
        d_sourceCountry.setArmyCount(l_ArmyCount - l_toMoveCount);

        d_targetCountry.setArmyCount(d_targetCountry.getArmyCount() + l_toMoveCount);
        d_targetCountry.setTurnArmyCount(d_targetCountry.getArmyCount());

        LogManager.logAction(String.format("%s Airlifted %d armies from %s to %s", d_player.get_name(), l_toMoveCount, d_sourceCountry.getName(), d_targetCountry.getName()));
        System.out.println(String.format("%s Airlifted %d armies from %s to %s", d_player.get_name(), l_toMoveCount, d_sourceCountry.getName(), d_targetCountry.getName()));

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
        }else if(d_armies > d_sourceCountry.getArmyCount()) {
            throw new Exception(String.format("You do not have the required army count to airlift. Source army count: %s", d_sourceCountry.getArmyCount()));
        } else if(d_armies == 0){
            throw new Exception(CommonErrorMessages.ARMY_COUNT_ZERO);
        } else if (d_armies == d_sourceCountry.getArmyCount()) {
            throw new Exception(String.format("At least one army my be present in source country. Source army count: %s", d_sourceCountry.getArmyCount()));
        } else if (d_targetCountry.isCountryNeutral() || !d_targetCountry.getOwner().equals(d_player)) {
            throw new Exception("Airlift can only be performed on your own countries");
        }
        if (d_player.get_cardPlayedInTurn() != null){
            throw new Exception("Cannot play multiple power cards in a turn!");
        }
    }
}
