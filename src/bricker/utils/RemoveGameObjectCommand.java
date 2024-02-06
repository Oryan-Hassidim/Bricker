package bricker.utils;

import danogl.GameObject;
import danogl.collisions.Layer;

/**
 * A command to remove a game object from the game.
 * 
 * @author Orayn Hassidim
 */
public interface RemoveGameObjectCommand {
    /**
     * Removes the given game object from the game.
     * 
     * @param gameObject the game object to remove
     * @param layer      the layer of the game object
     */
    void remove(GameObject gameObject, int layer);

    /**
     * Removes the given game object from the game from the default layer.
     * 
     * @param gameObject the game object to remove
     */
    public default void remove(GameObject gameObject) {
        remove(gameObject, Layer.DEFAULT);
    }
}
