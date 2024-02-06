package bricker.brick_strategies;

import java.util.Random;
import bricker.utils.Services;
import danogl.GameObject;

public class DualBehaviorCollisionStrategy extends BasicCollisionStrategy {

    private static final int BEHAVIORS_AMOUNT = 2;
    private static final int MAX_ACTUAL_BEHAVIORS_AMOUNT = 3;

    private CollisionStrategy[] strategies = new CollisionStrategy[BEHAVIORS_AMOUNT];
    private int actualStrategiesAmount;

    public DualBehaviorCollisionStrategy(int depth, CollisionStrategy... strategies) {
        super();
        var rand = Services.getService(Random.class);
        for (int i = 0; i < BEHAVIORS_AMOUNT
                && actualStrategiesAmount <= MAX_ACTUAL_BEHAVIORS_AMOUNT; i++) {

            var maxIndex = strategies.length;
            if (depth > 0 && actualStrategiesAmount + 2 <= MAX_ACTUAL_BEHAVIORS_AMOUNT)
                maxIndex++;
            var index = rand.nextInt(maxIndex);

            if (index == strategies.length) {
                var dual = new DualBehaviorCollisionStrategy(depth - 1, strategies);
                this.strategies[i] = dual;
                actualStrategiesAmount += dual.actualStrategiesAmount;
                continue;
            }

            this.strategies[i] = strategies[index];
            actualStrategiesAmount++;
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