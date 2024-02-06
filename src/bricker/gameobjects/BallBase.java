package bricker.gameobjects;

import java.awt.event.KeyEvent;

import bricker.utils.Logger;
import bricker.utils.Services;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public abstract class BallBase extends GameObject {

    // #region Constants
    protected static final float MOVEMENT_SPEED = 200;
    protected static final Vector2 DEFAULT_SIZE = new Vector2(20, 20);
    /**
     * from https://soundbible.com/2067-Blop.html
     */
    protected static final String COLLISION_SOUND_PATH = "assets/Bubble5_4.wav";
    // #endregion

    // #region fields
    protected Sound collisionSound;
    protected Logger logger = Services.getService(Logger.class);
    private int collisionCounter = 0;
    // #endregion

    // #region constructors
    public BallBase(Vector2 center, Vector2 dimensions, Renderable renderable) {
        super(Vector2.ZERO, dimensions, renderable);
        setCenter(center);
        collisionSound = Services.getService(SoundReader.class).readSound(COLLISION_SOUND_PATH);
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
        var inputListener = Services.getService(UserInputListener.class);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            this.setVelocity(getVelocity().normalized().mult(3 * MOVEMENT_SPEED));
        } else {
            this.setVelocity(getVelocity().normalized().mult(MOVEMENT_SPEED));
        }
    }
    // #endregion

}
