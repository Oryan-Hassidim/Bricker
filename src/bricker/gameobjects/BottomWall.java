package bricker.gameobjects;

import bricker.utils.Services;
import danogl.GameObject;
import danogl.util.Vector2;

public abstract class BottomWall extends GameObject {
    private static final int WALL_WIDTH = 40;

    public BottomWall() {
        super(new Vector2(0, Services.getService(Vector2.class).y()),
                new Vector2(Services.getService(Vector2.class).x(), WALL_WIDTH),
                null);
    }
}