package org.com.Orders;

import org.com.Constants.Cards;
import org.com.Models.Player;

import java.util.HashMap;

public class DiplomacyOrder implements Order{
    private final Player d_player;
    private final Player d_targetPlayer;

    public DiplomacyOrder(Player d_player, Player d_targetPlayer) {
        this.d_player = d_player;
        this.d_targetPlayer = d_targetPlayer;
    }

    @Override
    public void execute() {
        int l_numOfCards = d_player.get_cards().get(Cards.DIPLOMACY_CARD);
        d_player.get_negotiationPlayer().add(d_targetPlayer);
        d_targetPlayer.get_negotiationPlayer().add(d_player);
        d_player.get_cards().put(Cards.DIPLOMACY_CARD, l_numOfCards - 1);
    }

    @Override
    public void isValid() throws Exception {
        HashMap<String, Integer> l_cards = d_player.get_cards();
        if(!l_cards.containsKey(Cards.DIPLOMACY_CARD) || (l_cards.containsKey(Cards.DIPLOMACY_CARD) && l_cards.get(Cards.DIPLOMACY_CARD) == 0))
        {
            throw new Exception("You do not have diplomacy card to negotiate peace");
        }
        if (d_targetPlayer == null)
        {
            throw new Exception("The player you're trying to negotiate does not exists");
        }
    }
}
