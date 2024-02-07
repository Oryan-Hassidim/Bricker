package bricker.brick_strategies;

import bricker.utils.RemoveGameObjectCommand;
import bricker.utils.Services;
import danogl.GameObject;

/**
 * A basic collision strategy that removes the object from the
 * screen when a collision is detected.
 * 
 * @see CollisionStrategy
 * @author Orayn Hassidim
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    /**
     * Constructs a new BasicCollisionStrategy.
     */
    public BasicCollisionStrategy() {
        super();
    }

    /**
     * Removes the object from the screen when a collision is detected.
     * 
     * @param thisObject  the object that this strategy is attached to
     * @param otherObject the object that this object collided with
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        Services.getService(RemoveGameObjectCommand.class).remove(thisObject);
    }

}
