package bricker.gameobjects;

import bricker.utils.AddLifeCommand;
import bricker.utils.RemoveGameObjectCommand;
import bricker.utils.Services;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

/**
 * A falling life in the game.
 * 
 * @see bricker.brick_strategies.AddLifeCollisionStrategy
 * @author Orayn Hassidim
 */
public class Life extends GameObject {

    /**
     * Constructs a new Life with the given center.
     * 
     * @param center the center of the life
     */
    public Life(Vector2 center) {
        super(Vector2.ZERO,
                new Vector2(Lives.HEIGHT, Lives.HEIGHT),
                Services.getService(ImageReader.class).readImage(Lives.HEART_IMAGE_PATH, true));
        setCenter(center);
        setVelocity(Vector2.DOWN.mult(100));
    }

    /**
     * A method which checks if the life should collide with the given game object.
     * If the game object is not a paddle or a bottom wall, the life should not collide with it.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (!(other instanceof Paddle || other instanceof BottomWall))
            return false;
        return super.shouldCollideWith(other);
    }

    /**
     * A method which is called when a collision occurs.
     * On collision with a paddle, a life is added to the player and the life is removed.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Paddle) {
            Services.getService(AddLifeCommand.class).addLife();
        }
        Services.getService(RemoveGameObjectCommand.class).remove(this);
    }

}
