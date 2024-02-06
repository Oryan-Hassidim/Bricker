package bricker.brick_strategies;

import bricker.gameobjects.Life;
import bricker.utils.AddGameObjectCommand;
import bricker.utils.Services;
import danogl.GameObject;

/**
 * Collision strategy for adding a falling life to the screen.
 *  
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @see Life
 * @author Oryan Hassidim
 */
public class AddLifeCollisionStrategy extends BasicCollisionStrategy {

    /**
     * Adds a life to the screen when a brick is hit.
     * 
     * @param thisObject the brick that was hit
     * @param otherObject the object that hit the brick
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        var life = new Life(thisObject.getCenter());
        Services.getService(AddGameObjectCommand.class).add(life);
    }

}
