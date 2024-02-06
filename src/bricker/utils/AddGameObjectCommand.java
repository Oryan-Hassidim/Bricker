package bricker.utils;

import danogl.GameObject;
import danogl.collisions.Layer;

public interface AddGameObjectCommand {
    void add(GameObject gameObject, int layer);
    public default void add(GameObject gameObject)
    {
        add(gameObject, Layer.DEFAULT);
    }
}
