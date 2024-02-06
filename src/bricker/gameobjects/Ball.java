package bricker.gameobjects;

import danogl.gui.ImageReader;
import danogl.util.Vector2;
import bricker.utils.Services;

/**
 * a ball game object
 * 
 * @author Orayn Hassidim
 */
public class Ball extends BallBase {

    private static final String BALL_IMAGE_PATH = "assets/ball.png";

    // #region constructors
    /**
     * creates a new ball
     * 
     * @param topLeftCorner  the top left corner of the ball
     * @param dimensions     the dimensions of the ball
     * @param background     the renderable of the ball
     * @param collisionSound
     */
    public Ball() {
        super(
                Services.getService(Vector2.class).mult(0.5f),
                BallBase.DEFAULT_SIZE,
                Services.getService(ImageReader.class).readImage(BALL_IMAGE_PATH, true));
        var rand = Services.getService(java.util.Random.class);
        this.setVelocity(new Vector2(rand.nextFloat() - 0.5f, 1).normalized().mult(MOVEMENT_SPEED));
    }
    // #endregion
}
