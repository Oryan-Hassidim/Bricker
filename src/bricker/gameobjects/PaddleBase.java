package bricker.gameobjects;

import java.awt.event.KeyEvent;

import bricker.utils.Services;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

/**
 * A base class for the player's paddle.
 * 
 * @see Paddle
 * @see ExtraPaddle
 * @author Orayn Hassidim
 */
public abstract class PaddleBase extends GameObject {

    /** The path to the paddle image. */
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    /** The size of the paddle. */
    private static final Vector2 PADDEL_SIZE = new Vector2(100, 15);
    /** The speed of the paddle. */
    private static final float MOVEMENT_SPEED = 400;

    /**
     * Constructs a new PaddleBase with the given center.
     * 
     * @param center the center of the paddle
     */
    public PaddleBase(Vector2 center) {
        super(Vector2.ZERO, PADDEL_SIZE,
                Services.getService(ImageReader.class).readImage(PADDLE_IMAGE_PATH, true));
        setCenter(center);
    }

    /**
     * A method which called every frame to update the paddle.
     * The paddle moves left and right according to the user's input.
     */
    @Override
    public void update(float arg0) {
        super.update(arg0);
        var inputListener = Services.getService(UserInputListener.class);
        var windowSize = Services.getService(Vector2.class);

        setVelocity(Vector2.ZERO);
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            setVelocity(Vector2.RIGHT.mult(MOVEMENT_SPEED));
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            setVelocity(Vector2.LEFT.mult(MOVEMENT_SPEED));
        }

        // check if the paddle is out of bounds
        if (getTopLeftCorner().x() < 0) {
            setTopLeftCorner(new Vector2(0, getTopLeftCorner().y()));
        } else if (getTopLeftCorner().x() + getDimensions().x() > windowSize.x()) {
            setTopLeftCorner(new Vector2(
                    windowSize.x() - getDimensions().x(),
                    getTopLeftCorner().y()));
        }
    }
}
