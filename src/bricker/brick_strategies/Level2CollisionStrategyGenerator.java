package bricker.brick_strategies;

import bricker.utils.Logger;
import bricker.utils.Services;
import java.util.Random;

/**
 * A collision strategy generator for level 2 of the game, by the instructions
 * of the exercise.
 * 
 * @see CollisionStrategyGenerator
 * @see CollisionStrategy
 * @see BasicCollisionStrategy
 * @see AddPucksStrategy
 * @see ExtraPaddleStrategy
 * @see CameraChangeStrategy
 * @see AddLifeCollisionStrategy
 * @see DualBehaviorCollisionStrategy
 * @author Orayn Hassidim
 */
public final class Level2CollisionStrategyGenerator implements CollisionStrategyGenerator {

    /** The maximum amount of actual behaviors to combine. */
    private static final int MAX_ACTUAL_BEHAVIORS_AMOUNT = 3;
    /** The basic collision strategy. */
    private BasicCollisionStrategy basicStrategy = new BasicCollisionStrategy();
    /** The add pucks collision strategy. */
    private AddPucksStrategy addPucksStrategy = new AddPucksStrategy();
    /** The extra paddle collision strategy. */
    private ExtraPaddleStrategy extraPaddleStrategy = new ExtraPaddleStrategy();
    /** The camera change collision strategy. */
    private CameraChangeStrategy cameraChangeStrategy = new CameraChangeStrategy();
    /** The add life collision strategy. */
    private AddLifeCollisionStrategy addLifeStrategy = new AddLifeCollisionStrategy();
    /**
     * The strategies to choose from, with a weighted balance.
     * The last one being a <code>DualBehaviorCollisionStrategy.</code>
     */
    private CollisionStrategy[] strategies = new CollisionStrategy[] {
            basicStrategy,
            basicStrategy,
            basicStrategy,
            basicStrategy,
            basicStrategy,
            addPucksStrategy,
            extraPaddleStrategy,
            cameraChangeStrategy,
            addLifeStrategy,
            null
    };

    /**
     * Constructs a new Level2CollisionStrategyGenerator.
     */
    public Level2CollisionStrategyGenerator() {
        super();
    }

    /**
     * Generates a random collision strategy.
     * 
     * @return the collision strategy
     */
    @Override
    public CollisionStrategy generateStrategy() {
        var rand = Services.getService(Random.class);
        var index = rand.nextInt(strategies.length);
        CollisionStrategy strategy;
        if (index == strategies.length - 1) {
            strategy = new DualBehaviorCollisionStrategy(
                    MAX_ACTUAL_BEHAVIORS_AMOUNT,
                    addPucksStrategy, extraPaddleStrategy, cameraChangeStrategy, addLifeStrategy);
        } else {
            strategy = strategies[index];
        }
        Services.getService(Logger.class).logInformation(
                "Generated strategy: %s", strategy.getClass().getSimpleName());
        return strategy;
    }
}
