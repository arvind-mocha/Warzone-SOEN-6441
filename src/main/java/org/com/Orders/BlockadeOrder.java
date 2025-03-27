package org.com.Orders;

import org.com.Constants.Cards;
import org.com.Models.Country;
import org.com.Models.Player;
import org.com.Utils.HelperUtil;

import java.util.HashMap;

public class BlockadeOrder implements Order {

    private Country d_country;
    private final Player d_player;

    public BlockadeOrder(Country d_country, Player d_player) {
        this.d_country = d_country;
        this.d_player = d_player;
    }

    @Override
    public void execute() {
        d_player.get_cards().get(Cards.BLOCKADE_CARD);
        HelperUtil.setCountryOwnerShip(d_player, d_country, true);
    }

    @Override
    public void isValid() throws Exception {
        HashMap<String, Integer> l_cards = d_player.get_cards();
        if(!l_cards.containsKey(Cards.BLOCKADE_CARD) || (l_cards.containsKey(Cards.BLOCKADE_CARD) && l_cards.get(Cards.BLOCKADE_CARD) == 0))
        {
            throw new Exception(String.format("You don't have a blockade card to use. Available cards %s", d_player.get_cards().toString()));
        }
        if(d_player.get_countries().contains(d_country))
        {
            throw new Exception("Your own country cannot be neutralized");
        }
        if (d_country.isCountryNeutral())
        {
            throw new Exception("Cannot neutralize a country does not have any ownership");
        }
    }
}
