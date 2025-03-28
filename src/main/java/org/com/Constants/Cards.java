package org.com.Constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Cards {
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

    public static final List<String> CARDS_LIST = Arrays.asList(BOMB_CARD, BLOCKADE_CARD, AIRLIFT_CARD, DIPLOMACY_CARD);

    public static String getRandomCard()
    {
        Collections.shuffle(CARDS_LIST);
        Random random = new Random();
        int index = random.nextInt(CARDS_LIST.size());
        return CARDS_LIST.get(index);
    }
}
