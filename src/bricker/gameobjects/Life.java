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
                new Vector2(lives.HEIGHT, lives.HEIGHT),
                Services.getService(ImageReader.class).readImage(lives.HEART_IMAGE_PATH, true));
        setCenter(center);
        setVelocity(Vector2.DOWN.mult(100));
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other instanceof ExtraPaddle || other instanceof BallBase)
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
