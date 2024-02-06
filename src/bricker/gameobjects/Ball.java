package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

import bricker.utils.Logger;
import bricker.utils.Services;

/**
 * a ball game object
 * 
 * @author Orayn Hassidim
 */
public class Ball extends GameObject {

    // #region Constants
    private static final float MOVEMENT_SPEED = 200;
    // #endregion

    // #region fields
    private int collisionCounter = 0;
    protected Sound collisionSound;
    private UserInputListener inputListener;
    private Logger logger = Services.getService(Logger.class);
    // #endregion

    // #region constructors
    /**
     * creates a new ball
     * 
     * @param topLeftCorner  the top left corner of the ball
     * @param dimensions     the dimensions of the ball
     * @param background     the renderable of the ball
     * @param collisionSound
     */
    public Ball(
            Vector2 topLeftCorner, Vector2 dimensions,
            Renderable background, Sound collisionSound,
            Vector2 defaultVelocityDirection,
            UserInputListener inputListener) {
        super(topLeftCorner, dimensions, background);
        this.collisionSound = collisionSound;
        this.inputListener = inputListener;
        this.setVelocity(defaultVelocityDirection.normalized().mult(MOVEMENT_SPEED));
    }
    // #endregion

    // #region properties
    /**
     * gets the number of collisions the ball has had
     * 
     * @return the number of collisions the ball has had
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }
    // #endregion

    // #region methods
    /**
     * function called when the ball collides with another game object
     * 
     * @param other     the other game object
     * @param collision the collision that occurred
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        var newVelocity = this.getVelocity().flipped(collision.getNormal());
        this.setVelocity(newVelocity);
        this.collisionCounter++;
        this.collisionSound.play();
        logger.logInformation("Ball collided with %s. collisions count so on: %d", other.getClass().getSimpleName(),
                collisionCounter);
    }

    @Override
    public void update(float arg0) {
        super.update(arg0);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            this.setVelocity(getVelocity().normalized().mult(3 * MOVEMENT_SPEED));
        } else {
            this.setVelocity(getVelocity().normalized().mult(MOVEMENT_SPEED));
        }
    }
    // #endregion
}
