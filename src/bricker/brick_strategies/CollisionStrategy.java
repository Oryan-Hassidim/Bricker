package bricker.brick_strategies;

import danogl.GameObject;

/**
 * an interface for collision strategies
 * @author Orayn Hassidim
 */
public interface CollisionStrategy {
    /**
     * the action to be taken when a collision is detected
     * @param thisObject the object that has the strategy
     * @param otherObject the object that collided with the object that has the strategy
     */
    void onCollision(GameObject thisObject, GameObject otherObject);
}
