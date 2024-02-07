package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.utils.Services;
import bricker.utils.SetCameraCommand;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

/**
 * Collision strategy for changing the camera to follow the ball.
 * 
 * @see BasicCollisionStrategy
 * @see CollisionStrategy
 * @see Ball
 * @see Camera
 * @author Oryan Hassidim
 */
public class CameraChangeStrategy extends BasicCollisionStrategy {

    /*
     * A camera that follows the ball until the ball has collided with a brick 4
     * times.
     */
    private final class BallCamera extends Camera {

        /** The number of collisions the ball will be followed for. */
        private static final int NUMBER_OF_COLLISIONS = 4;

        /** The ball to follow. */
        private final Ball ball;

        /** The number of collisions the ball needs to be followed for. */
        private final int count;

        /**
         * Constructs a new BallCamera.
         * 
         * @param ball the ball to follow
         */
        private BallCamera(Ball ball) {
            super(ball, Vector2.ZERO,
                    Services.getService(Vector2.class).mult(1.2f),
                    Services.getService(Vector2.class));
            this.ball = ball;
            this.count = ball.getCollisionCounter() + NUMBER_OF_COLLISIONS;
        }

        /**
         * Updates the camera to follow the ball
         * until the ball has collided 4 times.
         * 
         * @param delta the time since the last update
         */
        @Override
        public void update(float delta) {
            super.update(delta);
            if (ball == this.getObjectFollowed() && ball.getCollisionCounter() <= count)
                return;
            Services.getService(SetCameraCommand.class).setCamera(null);
            cameraSet = false;
        }
    }

    /**
     * Constructs a new CameraChangeStrategy.
     */
    public CameraChangeStrategy() {
        super();
    }

    /** Whether the camera has been set. */
    private boolean cameraSet = false;

    /**
     * Changes the camera to follow the ball when a brick is hit.
     * 
     * @param thisObject  the brick that was hit
     * @param otherObject the object that hit the brick
     */
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