package bricker.utils;

import danogl.GameObject;
import danogl.collisions.Layer;

/**
 * A command to add a game object to the game.
 * 
 * @see RemoveGameObjectCommand
 * @author Orayn Hassidim
 */
public interface AddGameObjectCommand {
    /**
     * Adds the given game object to the game to a given layer.
     * 
     * @param gameObject the game object to add
     * @param layer      the layer of the game object
     */
    void add(GameObject gameObject, int layer);

    /**
     * Adds the given game object to the game to the default layer.
     * 
     * @param gameObject the game object to add
     */
    public default void add(GameObject gameObject) {
        add(gameObject, Layer.DEFAULT);
    }
}
