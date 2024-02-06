package bricker.gameobjects;

import bricker.utils.Services;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

public class Puck extends BallBase {

    private static final String PUCK_IMAGE_PATH = "assets/mockBall.png";

    public Puck(Vector2 center) {
        super(center, BallBase.DEFAULT_SIZE.mult(0.75f),
                Services.getService(ImageReader.class).readImage(PUCK_IMAGE_PATH, true));
        var rand = Services.getService(java.util.Random.class);
        this.setVelocity(new Vector2(rand.nextFloat() - 0.5f, 1).normalized().mult(MOVEMENT_SPEED));
    }

}
