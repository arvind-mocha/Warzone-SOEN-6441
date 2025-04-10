package org.com.Constants;

import org.com.Strategies.*;

import java.io.Serializable;

public class StrategyConstants implements Serializable {
    public static final String AGGRESSIVE_STRATEGY = "aggressive";
    public static final String BENEVOLENT_STRATEGY = "benevolent";
    public static final String RANDOM_STRATEGY = "random";
    public static final String CHEATER_STRATEGY = "cheater";
    public static final String HUMAN_STRATEGY = "human";

    /**
     * Retrieves the strategy constant by its name.
     *
     * @param strategyName the name of the strategy (case-insensitive)
     * @return the corresponding strategy constant
     * @throws Exception if the strategy name is invalid
     */
    public static Strategy getStrategyByName(String strategyName) throws Exception {
        return switch (strategyName.toLowerCase()) {
            case AGGRESSIVE_STRATEGY -> new AggressiveStrategy();
            case BENEVOLENT_STRATEGY -> new BenevolentStrategy();
            case RANDOM_STRATEGY -> new RandomStrategy();
            case CHEATER_STRATEGY -> new CheaterStrategy();
            case HUMAN_STRATEGY -> new HumanStrategy();
            default -> throw new Exception("Invalid strategy name: " + strategyName);
        };
    }
}
