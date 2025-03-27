package org.com.Orders;

import org.com.Constants.Cards;
import org.com.Constants.CommandOutputMessages;
import org.com.GameLog.LogManager;
import org.com.Models.Country;
import org.com.Models.Player;

import java.util.HashMap;

/**
 * Bomb order command functionality and validation are present in this class
 *
 *
 * @author Arvind Nachiappan
 */
public class BombOrder implements Order{

    private final Player d_player;
    private final Country d_targetCountry;

    public BombOrder(Player d_player, Country d_targetCountry) {
        this.d_player = d_player;
        this.d_targetCountry = d_targetCountry;
    }

    @Override
    public void execute() {
        Player d_targetPlayer = d_targetCountry.getOwner();
        if (d_player.get_negotiationPlayer() != null && d_player.get_negotiationPlayer().contains(d_targetPlayer)) {
            System.out.println(String.format(CommandOutputMessages.PLAYER_DIPLOMACY, d_targetPlayer.get_name()));
            LogManager.logAction(String.format(CommandOutputMessages.PLAYER_DIPLOMACY, d_targetPlayer.get_name()));
            return;
        }

//        d_player.get_cards().compute(Cards.BOMB_CARD, (k, l_numOfCards) -> l_numOfCards - 1);
        if (d_targetCountry.getArmyCount() == 0) {
            return;
        }
        d_targetCountry.setArmyCount(Math.floorDivExact(d_targetCountry.getArmyCount(), 2));

        LogManager.logAction(String.format("%s Dropped Bomb on %s, reducing the armies count to half.", d_player.get_name(), d_targetCountry.getName()));
        System.out.println(String.format("%s Dropped Bomb on %s, reducing the armies count to half.", d_player.get_name(), d_targetCountry.getName()));

        int l_numOfCards = d_player.get_cards().get(Cards.BOMB_CARD);
        if(l_numOfCards == 0){
            d_player.get_cards().remove(Cards.BOMB_CARD);
        }else{
            d_player.get_cards().put(Cards.BOMB_CARD, l_numOfCards - 1);
        }


    }

    @Override
    public void isValid() throws Exception {
        HashMap<String, Integer> l_cards = d_player.get_cards();
        if(!l_cards.containsKey(Cards.BOMB_CARD) || (l_cards.containsKey(Cards.BOMB_CARD) && l_cards.get(Cards.BOMB_CARD) == 0)) {
            throw new Exception("You do not have bomb card to negotiate peace");
        }
        if(d_targetCountry.isCountryNeutral()){
            throw new Exception("Cannot bomb neutral countries");
        }
        if (d_targetCountry.getOwner().equals(d_player)){
            throw new Exception("Cannot bomb your own country");
        }
        if (d_player.get_cardPlayedInTurn() != null){
            throw new Exception("Cannot play multiple power cards in a turn!");
        }
    }
}
