package org.com.Constants;

import org.com.Strategies.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * This class defines constants and utility methods related to game strategies.
 * It provides a list of supported strategies and a method to retrieve
 * the corresponding strategy implementation by name.
 *
 *
 * The class ensures that only valid strategies are used in the game.
 * @author Arvind Lakshmanan
 */
public class StrategyConstants implements Serializable {
    public static final String AGGRESSIVE_STRATEGY = "aggressive";
    public static final String BENEVOLENT_STRATEGY = "benevolent";
    public static final String RANDOM_STRATEGY = "random";
    public static final String CHEATER_STRATEGY = "cheater";
    public static final String HUMAN_STRATEGY = "human";
    public static final List<String> SUPPORTED_STRATEGIES = Arrays.asList(AGGRESSIVE_STRATEGY, BENEVOLENT_STRATEGY, RANDOM_STRATEGY, CHEATER_STRATEGY, HUMAN_STRATEGY);

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
