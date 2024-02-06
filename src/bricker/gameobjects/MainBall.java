package bricker.gameobjects;

import danogl.gui.ImageReader;
import danogl.util.Vector2;
import bricker.utils.Services;

/**
 * The main ball of the game.
 * 
 * @see BallBase
 * @author Orayn Hassidim
 */
public class MainBall extends BallBase {

    /** The path to the ball image. */
    private static final String BALL_IMAGE_PATH = "assets/ball.png";

    /**
     * Constructs a new MainBall.
     */
    public MainBall() {
        super(
                Services.getService(Vector2.class).mult(0.5f),
                BallBase.DEFAULT_SIZE,
                Services.getService(ImageReader.class).readImage(BALL_IMAGE_PATH, true));
        var rand = Services.getService(java.util.Random.class);
        this.setVelocity(new Vector2(rand.nextFloat() - 0.5f, 1).normalized().mult(MOVEMENT_SPEED));
    }
}
