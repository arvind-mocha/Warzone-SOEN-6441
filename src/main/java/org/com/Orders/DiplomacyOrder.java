package org.com.Orders;

import org.com.Constants.Cards;
import org.com.GameLog.LogManager;
import org.com.Models.Player;

import java.util.HashMap;

/**
 * Diplomacy order command functionality and validation are present in this class
 *
 *
 * @author Arvind Nachiappan
 */
public class DiplomacyOrder implements Order{
    private final Player d_player;
    private final Player d_targetPlayer;

    public DiplomacyOrder(Player d_player, Player d_targetPlayer) {
        this.d_player = d_player;
        this.d_targetPlayer = d_targetPlayer;
    }

    @Override
    public void execute() {

        d_player.get_negotiationPlayer().add(d_targetPlayer);
        d_targetPlayer.get_negotiationPlayer().add(d_player);

        int l_numOfCards = d_player.get_cards().get(Cards.DIPLOMACY_CARD);
        if(l_numOfCards == 0){
            d_player.get_cards().remove(Cards.DIPLOMACY_CARD);
        }else{
            d_player.get_cards().put(Cards.DIPLOMACY_CARD, l_numOfCards - 1);
        }

        System.out.println(String.format("%s negotiated with %s", d_player.get_name(), d_targetPlayer.get_name()));
        LogManager.logAction(String.format("%s negotiated with %s", d_player.get_name(), d_targetPlayer.get_name()));
    }

    @Override
    public void isValid() throws Exception {
        HashMap<String, Integer> l_cards = d_player.get_cards();
        if(!l_cards.containsKey(Cards.DIPLOMACY_CARD) || (l_cards.containsKey(Cards.DIPLOMACY_CARD) && l_cards.get(Cards.DIPLOMACY_CARD) == 0))
        {
            throw new Exception(String.format("You don't have a diplomacy card to use. Available cards %s", d_player.get_cards().toString()));
        }
        if (d_targetPlayer == null)
        {
            throw new Exception("The player you're trying to negotiate does not exists");
        }
        if (d_player.get_cardPlayedInTurn() != null){
            throw new Exception("Cannot play multiple power cards in a turn!");
        }
    }
}
