package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Brick extends GameObject {

    // #region fields
    private CollisionStrategy collisionStrategy;
    // #endregion

    // #region constructors
    public Brick(
            Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
            CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }
    // #endregion

    // #region methods
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collisionStrategy.onCollision(this, other);
    }
    // #endregion
}
