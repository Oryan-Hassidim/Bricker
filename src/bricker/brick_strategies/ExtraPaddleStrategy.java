package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import danogl.GameObject;

/**
 * A collision strategy that adds an extra paddle to the game.
 * 
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @see ExtraPaddle
 * @author Orayn Hassidim
 */
public class ExtraPaddleStrategy extends BasicCollisionStrategy {
    /** The extra paddle to add. */
    private ExtraPaddle extraPaddle = new ExtraPaddle();

    /**
     * Constructs a new ExtraPaddleStrategy.
     */
    public ExtraPaddleStrategy() {
        super();
    }

    /**
     * An action to perform when a collision occurs. 
     * 
     * @param thisObject the object that has this strategy
     * @param otherObject the object that collided with this object
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        extraPaddle.initialize();
    }
}