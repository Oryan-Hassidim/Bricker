package bricker.gameobjects;

import bricker.utils.AddGameObjectCommand;
import bricker.utils.Logger;
import bricker.utils.RemoveGameObjectCommand;
import bricker.utils.Services;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.util.Vector2;

/**
 * An extra paddle in the game.
 * 
 * @see PaddleBase
 * @see bricker.brick_strategies.ExtraPaddleStrategy
 * @author Orayn Hassidim
 */
public class ExtraPaddle extends PaddleBase {

    /** The number of collisions before the paddle disappears. */
    private static final int COLLISIONS_BEFORE_DISAPEERING = 4;
    /** The number of collisions before the paddle disappears. */
    private int collisions;

    /**
     * Constructs a new ExtraPaddle.
     */
    public ExtraPaddle() {
        super(Vector2.ZERO);
    }

    /**
     * A method which is called when a collision occurs.
     * On collision with a ball, the number of collisions is decreased.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (!(other instanceof BallBase)) {
            return;
        }
        collisions--;
        if (collisions == 0) {
            Services.getService(RemoveGameObjectCommand.class).remove(this);
        }
    }

    /**
     * Initializes the extra paddle.
     * If the number of collisions is initialized, the paddle is not initialized.
     */
    public void initialize() {
        if (collisions > 0) {
            return;
        }
        collisions = COLLISIONS_BEFORE_DISAPEERING;
        setCenter(Services.getService(Vector2.class).mult(0.5f));
        Services.getService(Logger.class).logInformation("extra paddle initialized");
        Services.getService(AddGameObjectCommand.class).add(this);
    }
}
