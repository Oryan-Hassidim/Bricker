package bricker.gameobjects;

import bricker.utils.Services;
import danogl.util.Vector2;

/**
 * A representation of the player's paddle.
 * 
 * @see PaddleBase
 * @author Orayn Hassidim
 */
public class Paddle extends PaddleBase {

    /** The distance of the paddle from the bottom of the screen. */
    private static final int BUTTOM = 50;

    /**
     * Constructs a new Paddle.
     */
    public Paddle() {
        super(Services.getService(Vector2.class).multX(0.5f).add(Vector2.UP.mult(BUTTOM)));
    }
}
