package bricker.gameobjects;

import bricker.utils.AddLifeCommand;
import bricker.utils.RemoveGameObjectCommand;
import bricker.utils.Services;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

public class Life extends GameObject {

    public Life(Vector2 center) {
        super(Vector2.ZERO,
                new Vector2(Lives.HEIGHT, Lives.HEIGHT),
                Services.getService(ImageReader.class).readImage(Lives.HEART_IMAGE_PATH, true));
        setCenter(center);
        setVelocity(Vector2.DOWN.mult(100));
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (!(other instanceof Paddle || other instanceof BottomWall))
            return false;
        return super.shouldCollideWith(other);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Paddle) {
            Services.getService(AddLifeCommand.class).addLife();
        }
        Services.getService(RemoveGameObjectCommand.class).remove(this);
    }

}
