package bricker.brick_strategies;

import java.util.Random;
import bricker.utils.Services;
import danogl.GameObject;

public class DualBehaviorCollisionStrategy extends BasicCollisionStrategy {

    private static final int BEHAVIORS_AMOUNT = 2;

    private CollisionStrategy[] strategies = new CollisionStrategy[BEHAVIORS_AMOUNT];
    private int actualStrategies;

    public DualBehaviorCollisionStrategy(int maxStrategies, CollisionStrategy... strategies) {
        super();
        var rand = Services.getService(Random.class);
        for (int i = 0; i < BEHAVIORS_AMOUNT
                && actualStrategies <= maxStrategies; i++) {

            var maxIndex = strategies.length;
            var nextMax = maxStrategies - actualStrategies - (BEHAVIORS_AMOUNT - i - 1);
            if (nextMax >= BEHAVIORS_AMOUNT)
                maxIndex++;
            var index = rand.nextInt(maxIndex);

            if (index == strategies.length) {
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

    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        for (var strategy : strategies) {
            strategy.onCollision(thisObject, otherObject);
        }
    }
}