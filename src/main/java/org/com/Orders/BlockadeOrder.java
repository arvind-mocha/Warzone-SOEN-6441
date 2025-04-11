package org.com.Orders;

import org.com.Constants.Cards;
import org.com.Constants.CommandOutputMessages;
import org.com.GameLog.LogManager;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Blockade order command functionality and validation are present in this class
 *
 *
 * @author Arvind Nachiappan
 */
public class BlockadeOrder implements Order, Serializable {

    private Country d_targetCountry;
    private final Player d_player;

    /**
     * Constructor for the Blockade class
     *
     * @param d_country The country on which blockade is played
     * @param d_player The player giving the Blockade order
     */
    public BlockadeOrder(Country d_country, Player d_player) {
        this.d_targetCountry = d_country;
        this.d_player = d_player;
    }

    /**
     * Executes the Blockade command
     */
    @Override
    public void execute() {

        int d_blockadeCount = d_targetCountry.getArmyCount()*3;
        HelperUtil.setCountryOwnerShip(d_player, d_targetCountry, true);
        d_targetCountry.setArmyCount(d_blockadeCount);

        System.out.println(String.format("%s played Blockade card on %s, neutralizing the country", d_player.get_name(), d_targetCountry.getName()));
        LogManager.logAction(String.format("%s played Blockade card on %s, neutralizing the country", d_player.get_name(), d_targetCountry.getName()));

        int l_numOfCards = d_player.get_cards().get(Cards.BLOCKADE_CARD) - 1;
        if(l_numOfCards == 0){
            d_player.get_cards().remove(Cards.BLOCKADE_CARD);
        }else{
            d_player.get_cards().put(Cards.BLOCKADE_CARD, l_numOfCards);
        }
    }

    /**
     * Validates the BlockadeOrder command against the player
     */
    @Override
    public void isValid() throws Exception {
        HashMap<String, Integer> l_cards = d_player.get_cards();
        if(!l_cards.containsKey(Cards.BLOCKADE_CARD) || (l_cards.containsKey(Cards.BLOCKADE_CARD) && l_cards.get(Cards.BLOCKADE_CARD) == 0))
        {
            throw new Exception(String.format("You don't have a blockade card to use. Available cards %s", d_player.get_cards().toString()));
        }
//        if(!d_player.get_countries().contains(d_targetCountry))
        if(d_targetCountry.getOwner() == null)
        {
            throw new Exception("Can only be played on your territory");
        }
        if (d_targetCountry.isCountryNeutral())
        {
            throw new Exception("Cannot blockade a country, that is not your territory");
        }
        if (d_player.get_cardPlayedInTurn() != null){
            throw new Exception("Cannot play multiple power cards in a turn!");
        }
    }
}
