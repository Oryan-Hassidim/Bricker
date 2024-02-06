package bricker.gameobjects;

import bricker.utils.AddGameObjectCommand;
import bricker.utils.Logger;
import bricker.utils.RemoveGameObjectCommand;
import bricker.utils.Services;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.util.Vector2;

public class ExtraPaddle extends PaddleBase {

    private static final int COLLISIONS_BEFORE_DISAPEERING = 4;

    private int collisions;

    public ExtraPaddle() {
        super(Services.getService(Vector2.class).mult(0.5f));
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (!(other instanceof BallBase)) {
            return;
        }
        collisions--;
        if (collisions == 0) {
            Services.getService(RemoveGameObjectCommand.class).remove(this);
        }
    }

    public void initialize() {
        if (collisions > 0) {
            return;
        }
        collisions = COLLISIONS_BEFORE_DISAPEERING;
        Services.getService(Logger.class).logInformation("extra paddle initialized");
        Services.getService(AddGameObjectCommand.class).add(this);
    }
}
