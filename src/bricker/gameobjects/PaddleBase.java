package bricker.gameobjects;

import java.awt.event.KeyEvent;

import bricker.utils.Services;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

public abstract class PaddleBase extends GameObject {
// #region Constants
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static final Vector2 PADDEL_SIZE = new Vector2(100, 15);
    private static final float MOVEMENT_SPEED = 400;
    // #endregion

    public PaddleBase(Vector2 center) {
        super(Vector2.ZERO, PADDEL_SIZE,
                Services.getService(ImageReader.class).readImage(PADDLE_IMAGE_PATH, true));
        setCenter(center);
    }

    @Override
    public void update(float arg0) {
        super.update(arg0);
        var inputListener = Services.getService(UserInputListener.class);
        var windowSize = Services.getService(Vector2.class);

        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            setVelocity(Vector2.RIGHT.mult(MOVEMENT_SPEED));
        } else if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            setVelocity(Vector2.LEFT.mult(MOVEMENT_SPEED));
        } else {
            setVelocity(Vector2.ZERO);
        }

        // check if the paddle is out of bounds
        if (getTopLeftCorner().x() < 0) {
            setTopLeftCorner(new Vector2(0, getTopLeftCorner().y()));
        } else if (getTopLeftCorner().x() + getDimensions().x() > windowSize.x()) {
            setTopLeftCorner(new Vector2(
                    windowSize.x() - getDimensions().x(),
                    getTopLeftCorner().y()));
        }
    }
}
