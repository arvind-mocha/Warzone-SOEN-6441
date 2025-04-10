package org.com.Orders;

import org.com.Constants.Cards;
import org.com.Constants.CommandOutputMessages;
import org.com.GameLog.LogManager;
import org.com.Models.Country;
import org.com.Models.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Bomb order command functionality and validation are present in this class
 *
 *
 * @author Arvind Nachiappan
 */
public class BombOrder implements Order, Serializable {

    private final Player d_player;
    private final Country d_targetCountry;

    /**
     * Constructor for the BombOrder class
     *
     * @param d_player      The player giving the Bomb order
     * @param d_targetCountry   The country to which Bomb is dropped on
     */
    public BombOrder(Player d_player, Country d_targetCountry) {
        this.d_player = d_player;
        this.d_targetCountry = d_targetCountry;
    }

    /**
     * Executes the BombOrder command
     */
    @Override
    public void execute() {
        Player d_targetPlayer = d_targetCountry.getOwner();
        if (d_player.get_negotiationPlayer() != null && d_player.get_negotiationPlayer().contains(d_targetPlayer)) {
            System.out.println(String.format(CommandOutputMessages.PLAYER_DIPLOMACY, d_targetPlayer.get_name()));
            LogManager.logAction(String.format(CommandOutputMessages.PLAYER_DIPLOMACY, d_targetPlayer.get_name()));
            return;
        }

        if (d_targetCountry.getArmyCount() == 0) {
            return;
        }
        d_targetCountry.setArmyCount(Math.floorDivExact(d_targetCountry.getArmyCount(), 2));

        LogManager.logAction(String.format("%s Dropped Bomb on %s, reducing the armies count to half.", d_player.get_name(), d_targetCountry.getName()));
        System.out.println(String.format("%s Dropped Bomb on %s, reducing the armies count to half.", d_player.get_name(), d_targetCountry.getName()));

        int l_numOfCards = d_player.get_cards().get(Cards.BOMB_CARD) - 1;
        if(l_numOfCards == 0){
            d_player.get_cards().remove(Cards.BOMB_CARD);
        }else{
            d_player.get_cards().put(Cards.BOMB_CARD, l_numOfCards);
        }


    }

    /**
     * Validates the BomdOrder command against the player
     */
    @Override
    public void isValid() throws Exception {
        HashMap<String, Integer> l_cards = d_player.get_cards();

        ArrayList<Integer> l_tempList = new ArrayList<>(d_targetCountry.getNeighbourCountryIds());
        ArrayList<Integer> l_templist2 = new ArrayList<>();
        for(Country l_cont : d_player.get_countries()){
            l_templist2.add(l_cont.getId());
        }
        l_tempList.retainAll(l_templist2);

        if(!l_cards.containsKey(Cards.BOMB_CARD) || (l_cards.containsKey(Cards.BOMB_CARD) && l_cards.get(Cards.BOMB_CARD) == 0))
        {
            throw new Exception(String.format("You don't have a bomb card to use. Available cards %s", d_player.get_cards().toString()));
        }
        if(d_targetCountry.isCountryNeutral()){
            throw new Exception("Cannot bomb neutral countries");
        }
        if (l_tempList.isEmpty()) {
            throw new Exception("Target country is not a neighbour of any of your territory.");
        }
        if (d_targetCountry.getOwner().equals(d_player)){
            throw new Exception("Cannot bomb your own country");
        }
        if (d_player.get_cardPlayedInTurn() != null){
            throw new Exception("Cannot play multiple power cards in a turn!");
        }
    }
}
