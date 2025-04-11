package org.com.Orders;

import org.com.Constants.Cards;
import org.com.GameLog.LogManager;
import org.com.Models.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Diplomacy order command functionality and validation are present in this class
 *
 *
 * @author Arvind Nachiappan
 */
public class DiplomacyOrder implements Order, Serializable {
    private final Player d_player;
    private final Player d_targetPlayer;

    /**
     * Constructor for the Negotiate class
     *
     * @param d_player      The player giving the Negotiate order
     * @param d_targetPlayer   The Player to whom Negotiation is done
     */
    public DiplomacyOrder(Player d_player, Player d_targetPlayer) {
        this.d_player = d_player;
        this.d_targetPlayer = d_targetPlayer;
    }

    /**
     * Executes the DiplomacyOrder command
     */
    @Override
    public void execute() {

        if (d_player.get_negotiationPlayer() == null) {
            d_player.set_negotiationPlayer(new HashSet<>());  // Assuming setter exists
        }
        if (d_targetPlayer.get_negotiationPlayer() == null) {
            d_targetPlayer.set_negotiationPlayer(new HashSet<>());
        }

        d_player.get_negotiationPlayer().add(d_targetPlayer);
        d_targetPlayer.get_negotiationPlayer().add(d_player);

        int l_numOfCards = d_player.get_cards().get(Cards.DIPLOMACY_CARD) - 1;
        if(l_numOfCards == 0){
            d_player.get_cards().remove(Cards.DIPLOMACY_CARD);
        }else{
            d_player.get_cards().put(Cards.DIPLOMACY_CARD, l_numOfCards);
        }

        System.out.println(String.format("%s negotiated with %s", d_player.get_name(), d_targetPlayer.get_name()));
        LogManager.logAction(String.format("%s negotiated with %s", d_player.get_name(), d_targetPlayer.get_name()));
    }

    /**
     * Validates the DiplomacyOrder command against the player
     */
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
