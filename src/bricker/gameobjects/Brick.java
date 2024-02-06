package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.CollisionStrategyGenerator;
import bricker.utils.Services;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

public class Brick extends GameObject {

    // #region Constants
    private static final String BRICK_IMAGE_PATH = "assets/brick.png";
    // #endregion

    // #region fields
    private CollisionStrategy collisionStrategy;
    // #endregion

    // #region constructors
    public Brick(
            Vector2 topLeftCorner, Vector2 dimensions) {
        super(
                topLeftCorner, dimensions,
                Services.getService(ImageReader.class)
                        .readImage(BRICK_IMAGE_PATH, true));
        this.collisionStrategy = Services.getService(
                CollisionStrategyGenerator.class).generateStrategy();
    }
    // #endregion

    // #region methods
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collisionStrategy.onCollision(this, other);
    }
    // #endregion
}
