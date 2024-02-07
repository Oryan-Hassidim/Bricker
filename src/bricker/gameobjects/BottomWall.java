package bricker.gameobjects;

import bricker.utils.Services;
import danogl.GameObject;
import danogl.util.Vector2;

/**
 * A bottom wall of the game.
 * 
 * @author Orayn Hassidim
 */
public abstract class BottomWall extends GameObject {
    /** The width of the wall. */
    private static final int WALL_WIDTH = 40;

    /**
     * Constructs a new BottomWall.
     */
    public BottomWall() {
        super(new Vector2(0, Services.getService(Vector2.class).y()),
                new Vector2(Services.getService(Vector2.class).x(), WALL_WIDTH),
                null);
    }
}