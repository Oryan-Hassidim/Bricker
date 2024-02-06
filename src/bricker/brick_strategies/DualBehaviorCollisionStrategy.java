package bricker.brick_strategies;

import bricker.utils.Services;
import danogl.GameObject;

public class DualBehaviorCollisionStrategy extends BasicCollisionStrategy {
    private int depth;

    public DualBehaviorCollisionStrategy(int depth) {
        super();
        this.depth = depth;
    }

    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        var strategies = Services.getService(CollisionStrategy[].class);
        
        if (depth == 0)
        {
            var rand = Services.getService(java.util.Random.class);

        }
    }

    
}
