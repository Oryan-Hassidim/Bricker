package bricker.brick_strategies;

import bricker.utils.ParametrizedCommand;
import danogl.GameObject;

/**
 * a basic collision strategy that prints a message to the console
 * when a collision is detected
 * @author Orayn Hassidim
 * @see CollisionStrategy
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    private ParametrizedCommand<GameObject> remove;

    public BasicCollisionStrategy(ParametrizedCommand<GameObject> removeable) {
        this.remove = removeable;
    }
    /**
     * prints a message to the console when a collision is detected
     * @param thisObject the object that this strategy is attached to
     * @param otherObject the object that this object collided with
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        remove.invoke(thisObject);
    }

}
