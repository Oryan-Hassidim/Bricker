package bricker.brick_strategies;

import bricker.utils.Services;
import java.util.Random;

public class Level2CollisionStrategyGenerator implements CollisionStrategyGenerator {

    private static final int MAX_ACTUAL_BEHAVIORS_AMOUNT = 3;

    protected BasicCollisionStrategy basicStrategy = new BasicCollisionStrategy();

    protected AddPucksStrategy addPucksStrategy = new AddPucksStrategy();

    protected ExtraPaddleStrategy extraPaddleStrategy = new ExtraPaddleStrategy();

    protected CameraChangeStrategy cameraChangeStrategy = new CameraChangeStrategy();

    protected AddLifeCollisionStrategy addLifeStrategy = new AddLifeCollisionStrategy();

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
