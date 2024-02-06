package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import danogl.GameObject;

public class ExtraPaddleStrategy extends BasicCollisionStrategy {
    private ExtraPaddle extraPaddle = new ExtraPaddle();
    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        extraPaddle.initialize();
    }
}