package bricker.brick_strategies;

import java.util.Random;
import bricker.utils.Services;
import danogl.GameObject;

/**
 * A collision strategy that combines two other strategies.
 * 
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @author Orayn Hassidim
 */
public class DualBehaviorCollisionStrategy extends BasicCollisionStrategy {

    /** The amount of behaviors to combine. */
    private static final int BEHAVIORS_AMOUNT = 2;

    /** Choosed strategies collection to combine. */
    private CollisionStrategy[] strategies = new CollisionStrategy[BEHAVIORS_AMOUNT];
    /** The actual amount of strategies combined. */
    private int actualStrategies;

    /**
     * Constructs a new DualBehaviorCollisionStrategy.
     * 
     * @param maxStrategies the maximum amount of strategies to combine
     * @param strategies the strategies to combine
     */
    public DualBehaviorCollisionStrategy(int maxStrategies, CollisionStrategy... strategies) {
        super();
        var rand = Services.getService(Random.class);
        for (int i = 0; i < BEHAVIORS_AMOUNT; i++) {
            // the maximum index to choose from the strategies array.
            var maxIndex = strategies.length;
            // the amount of strategies the inner DualBehaviorCollisionStrategy can combine.
            var nextMax = maxStrategies - actualStrategies - (BEHAVIORS_AMOUNT - i - 1);
            if (nextMax >= BEHAVIORS_AMOUNT)
                maxIndex++; // we can choose a DualBehaviorCollisionStrategy.
            var index = rand.nextInt(maxIndex);

            if (index == strategies.length) { // DualBehaviorCollisionStrategy
                var dual = new DualBehaviorCollisionStrategy(
                        nextMax,
                        strategies);
                this.strategies[i] = dual;
                actualStrategies += dual.actualStrategies;
                continue;
            }
            this.strategies[i] = strategies[index];
            actualStrategies++;
        }
    }

    /**
     * An action to perform when a collision occurs. 
     * 
     * @param thisObject the object that has this strategy
     * @param otherObject the object that collided with this object
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        for (var strategy : strategies) {
            strategy.onCollision(thisObject, otherObject);
        }
    }
}