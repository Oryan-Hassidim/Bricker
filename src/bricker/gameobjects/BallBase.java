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

/**
 * A base class for the game's balls.
 * 
 * @see GameObject
 * @see BallBase
 * @see MainBall
 * @author Orayn Hassidim
 */
public abstract class BallBase extends GameObject {

    // #region Constants
    protected static final float MOVEMENT_SPEED = 200;
    protected static final Vector2 DEFAULT_SIZE = new Vector2(20, 20);
    /** The path to the collision sound.  
     * downloaded from https://soundbible.com/2067-Blop.html */
    protected static final String COLLISION_SOUND_PATH = "assets/Bubble5_4.wav";
    // #endregion

    // #region fields
    /** The sound to play when a collision occurs. */
    protected Sound collisionSound;
    /** The logger. */
    protected Logger logger = Services.getService(Logger.class);
    /** The number of collisions the ball has had. */
    private int collisionCounter = 0;
    // #endregion

    // #region constructors
    /**
     * Constructs a new BallBase.
     * 
     * @param center     the center of the ball
     * @param dimensions the dimensions of the ball
     * @param renderable the renderable of the ball
     */
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
     * An action called when a collision occurs.
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
        logger.logInformation("%s collided with %s. collisions counter: %d",
                this.getClass().getSimpleName(),
                other.getClass().getSimpleName(),
                collisionCounter);
    }

    /**
     * A function which called every frame.
     * If the space key is pressed, the ball moves faster.
     * 
     * @param delta the time passed since the last update
     */
    @Override
    public void update(float delta) {
        super.update(delta);
        var inputListener = Services.getService(UserInputListener.class);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            this.setVelocity(getVelocity().normalized().mult(3 * MOVEMENT_SPEED));
        } else {
            this.setVelocity(getVelocity().normalized().mult(MOVEMENT_SPEED));
        }
    }
    // #endregion

}
