package org.com.Constants;

import java.io.Serializable;
import java.util.*;

public class Cards implements Serializable {
    /**
     * Bomb Card Constant
     */
    public static final String BOMB_CARD = "bomb-card";
    /**
     * Blockade Card Constant
     */
    public static final String BLOCKADE_CARD = "blockade-card";
    /**
     * Airlift Card Constant
     */
    public static final String AIRLIFT_CARD = "airlift-card";
    /**
     * Diplomacy Card Constant
     */
    public static final String DIPLOMACY_CARD = "diplomacy-card";

    /**
     * List of all card constants
     */
    public static final List<String> CARDS_LIST = Arrays.asList(BOMB_CARD, BLOCKADE_CARD, AIRLIFT_CARD, DIPLOMACY_CARD);

    /**
     * Retrieves a random card from the list of card constants.
     *
     * @return A randomly selected card constant.
     */
    public static String getRandomCard(HashMap<String, Integer> p_cards)
    {
        Random random = new Random();
        if (p_cards != null) {
            if(p_cards.isEmpty())
            {
                return null;
            }
            List<String> l_availableCards = new ArrayList<>(p_cards.keySet());
            int index = random.nextInt(l_availableCards.size());
            return l_availableCards.get(index);
        }
        int index = random.nextInt(CARDS_LIST.size());
        Collections.shuffle(CARDS_LIST);
        return CARDS_LIST.get(index);
    }
}
