package bricker.brick_strategies;

import danogl.GameObject;

/**
 * An interface for collision strategies
 * 
 * @see BasicCollisionStrategy
 * @author Orayn Hassidim
 */
public interface CollisionStrategy {
    /**
     * An action to be performed when a collision is detected.
     * 
     * @param thisObject the object that has the strategy
     * @param otherObject the object that collided with the object that has the strategy
     */
    void onCollision(GameObject thisObject, GameObject otherObject);
}
