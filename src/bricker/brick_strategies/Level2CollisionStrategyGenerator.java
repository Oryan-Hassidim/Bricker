package bricker.brick_strategies;

import bricker.utils.Services;
import java.util.Random;

/**
 * A collision strategy generator for level 2 of the game, by the instructions of the exercise.
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
    protected BasicCollisionStrategy basicStrategy = new BasicCollisionStrategy();
    /** The add pucks collision strategy. */
    protected AddPucksStrategy addPucksStrategy = new AddPucksStrategy();
    /** The extra paddle collision strategy. */
    protected ExtraPaddleStrategy extraPaddleStrategy = new ExtraPaddleStrategy();
    /** The camera change collision strategy. */
    protected CameraChangeStrategy cameraChangeStrategy = new CameraChangeStrategy();
    /** The add life collision strategy. */
    protected AddLifeCollisionStrategy addLifeStrategy = new AddLifeCollisionStrategy();
    /** The strategies to choose from, with a weighted balance.
     * The last one being a <code>DualBehaviorCollisionStrategy.</code> */
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
     * Generates a random collision strategy.
     * 
     * @return the collision strategy
     */
    @Override
    public CollisionStrategy generateStrategy() {
        var rand = Services.getService(Random.class);
        var index = rand.nextInt(strategies.length);
        if (index == strategies.length - 1) {
            return new DualBehaviorCollisionStrategy(
                    MAX_ACTUAL_BEHAVIORS_AMOUNT, 
                    addPucksStrategy, extraPaddleStrategy, cameraChangeStrategy, addLifeStrategy);
        }
        return strategies[index];
    }
}
