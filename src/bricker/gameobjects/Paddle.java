package bricker.gameobjects;

import java.awt.event.KeyEvent;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Paddle extends GameObject {
    // #region Constants
    private static final float MOVEMENT_SPEED = 400;
    // #endregion

    // #region fields
    private UserInputListener inputListener;
    private Vector2 windowSize;
    // #endregion


    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
            Renderable renderable,
            UserInputListener inputListener, Vector2 windowSize) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowSize = windowSize;
    }

    @Override
    public void update(float arg0) {
        super.update(arg0);

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
