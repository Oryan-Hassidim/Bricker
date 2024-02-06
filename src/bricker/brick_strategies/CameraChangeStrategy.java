package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.utils.Services;
import bricker.utils.SetCameraCommand;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

public class CameraChangeStrategy extends BasicCollisionStrategy {
    private final class BallCamera extends Camera {
        private final Ball ball;
        private final int count;

        private BallCamera(Ball ball) {
            super(ball, Vector2.ZERO,
                    Services.getService(Vector2.class).mult(1.2f),
                    Services.getService(Vector2.class));
            this.ball = ball;
            this.count = ball.getCollisionCounter() + 4;
        }

        @Override
        public void update(float delta) {
            super.update(delta);
            if (ball.getCollisionCounter() <= count)
                return;
            Services.getService(SetCameraCommand.class).setCamera(null);
            cameraSet = false;
        }
    }

    private boolean cameraSet = false;

    @Override
    public void onCollision(GameObject thisObject, GameObject otherObject) {
        super.onCollision(thisObject, otherObject);
        if (!(otherObject instanceof Ball))
            return;
        if (cameraSet)
            return;

        var ball = (Ball) otherObject;
        Camera camera = new BallCamera(ball);
        Services.getService(SetCameraCommand.class).setCamera(camera);
        cameraSet = true;
    }
}