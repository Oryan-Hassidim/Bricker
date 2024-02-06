package bricker.gameobjects;

import bricker.utils.Services;
import danogl.util.Vector2;

public class Paddle extends PaddleBase {

    private static final int BUTTOM = 50;

    public Paddle() {
        super(Services.getService(Vector2.class).multX(0.5f).add(Vector2.UP.mult(BUTTOM)));
    }
}
