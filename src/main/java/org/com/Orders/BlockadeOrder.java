package org.com.Orders;

import org.com.Constants.Cards;
import org.com.Constants.CommandOutputMessages;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

import java.util.HashMap;

/**
 * Blockade order command functionality and validation are present in this class
 *
 *
 * @author Arvind Nachiappan
 */
public class BlockadeOrder implements Order {

    private Country d_targetCountry;
    private final Player d_player;

    public BlockadeOrder(Country d_country, Player d_player) {
        this.d_targetCountry = d_country;
        this.d_player = d_player;
    }

    @Override
    public void execute() {
        Player d_targetPlayer = d_targetCountry.getOwner();
        if (d_player.get_negotiationPlayer() != null && d_player.get_negotiationPlayer().contains(d_targetPlayer)) {
            System.out.println(String.format(CommandOutputMessages.PLAYER_DIPLOMACY, d_targetPlayer.get_name()));
        }

        int l_numOfCards = d_player.get_cards().get(Cards.BLOCKADE_CARD);
        HelperUtil.setCountryOwnerShip(d_player, d_targetCountry, true);
        d_player.get_cards().put(Cards.BLOCKADE_CARD, l_numOfCards - 1);
    }

    @Override
    public void isValid() throws Exception {
        HashMap<String, Integer> l_cards = d_player.get_cards();
        if(!l_cards.containsKey(Cards.BLOCKADE_CARD) || (l_cards.containsKey(Cards.BLOCKADE_CARD) && l_cards.get(Cards.BLOCKADE_CARD) == 0))
        {
            throw new Exception(String.format("You don't have a blockade card to use. Available cards %s", d_player.get_cards().toString()));
        }
        if(d_player.get_countries().contains(d_targetCountry))
        {
            throw new Exception("Your own country cannot be neutralized");
        }
        if (d_targetCountry.isCountryNeutral())
        {
            throw new Exception("Cannot neutralize a country does not have any ownership");
        }
    }
}
