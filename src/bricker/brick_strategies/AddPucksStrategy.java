package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import bricker.utils.AddGameObjectCommand;
import bricker.utils.Services;
import danogl.GameObject;

/**
 * Collision strategy for adding 2 pucks to the screen.
 *  
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @see Puck
 * @author Oryan Hassidim
 */
public class AddPucksStrategy extends BasicCollisionStrategy {
    /** The number of pucks to add. */
    private static final int ADD_PUCKS_NUMBER = 2;

    /**
     * Constructs a new AddPucksStrategy.
     */
    public AddPucksStrategy() {
        super();
    }
    /**
     * Adds 2 pucks to the screen when a brick is hit.
     * 
     * @param thisObject the brick that was hit
     * @param otherObject the object that hit the brick
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        for (int i = 0; i < ADD_PUCKS_NUMBER; i++) {
            var puck = new Puck(thisObject.getCenter());
            Services.getService(AddGameObjectCommand.class).add(puck);
        }
    }
}