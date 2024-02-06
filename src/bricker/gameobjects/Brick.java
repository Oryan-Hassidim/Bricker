package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.CollisionStrategyGenerator;
import bricker.utils.Services;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

/**
 * A brick in the game.
 * 
 * @author Orayn Hassidim
 */
public class Brick extends GameObject {

    // #region Constants
    /** The path to the brick image. */
    private static final String BRICK_IMAGE_PATH = "assets/brick.png";
    // #endregion

    // #region fields
    /** The collision strategy of the brick. */
    private CollisionStrategy collisionStrategy;
    // #endregion

    // #region constructors
    /**
     * Constructs a new Brick with the given top left corner and dimensions,
     * and the default background image and a generated collision strategy
     * from the <code>CollisionStrategyGenerator</code> service.
     * 
     * @param topLeftCorner the top left corner of the brick
     * @param dimensions    the dimensions of the brick
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions) {
        super(topLeftCorner, dimensions,
                Services.getService(ImageReader.class)
                        .readImage(BRICK_IMAGE_PATH, true));
        this.collisionStrategy = Services.getService(
                CollisionStrategyGenerator.class).generateStrategy();
    }
    // #endregion

    // #region methods
    /**
     * A function which is called when a collision occurs.
     * Calls the collision strategy's <code>onCollision</code> method.
     * 
     * @param other     the other game object
     * @param collision the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collisionStrategy.onCollision(this, other);
    }
    // #endregion
}
