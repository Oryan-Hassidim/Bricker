package bricker.gameobjects;

import bricker.utils.Services;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

/**
 * A representation of the puck in the game.
 *
 * @see BallBase
 * @see bricker.brick_strategies.AddPucksStrategy
 * @author Orayn Hassidim
 */
public class Puck extends BallBase {

    /** The speed of the puck. */
    private static final String PUCK_IMAGE_PATH = "assets/mockBall.png";

    /**
     * Constructs a new Puck with the given center and a random direction.
     * 
     * @param center the center of the puck
     */
    public Puck(Vector2 center) {
        super(center, BallBase.DEFAULT_SIZE.mult(0.75f),
                Services.getService(ImageReader.class).readImage(PUCK_IMAGE_PATH, true));
        var rand = Services.getService(java.util.Random.class);
        this.setVelocity(new Vector2(rand.nextFloat() - 0.5f, 1).normalized().mult(MOVEMENT_SPEED));
    }
}
