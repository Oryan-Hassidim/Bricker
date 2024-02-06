package bricker.utils;

import danogl.gui.rendering.Camera;

/**
 * A command to set the camera of the game.
 * 
 * @see Camera
 * @see bricker.brick_strategies.CameraChangeStrategy
 * @author Orayn Hassidim
 */
public interface SetCameraCommand {
    /**
     * Sets the camera of the game.
     * 
     * @param camera the camera to set
     */
    void setCamera(Camera camera);
}
