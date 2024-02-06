package bricker.gameobjects;

import bricker.utils.AddGameObjectCommand;
import bricker.utils.RemoveGameObjectCommand;
import bricker.utils.Services;
import bricker.utils.Getter;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.awt.Color;

public class Lives extends GameObject {

    public static final String HEART_IMAGE_PATH = "assets/heart.png";
    private static final int GAP = -4;
    public static final int HEIGHT = 20;

    private Getter<Integer> getLives;
    private GameObject[] hearts = new GameObject[0];
    private TextRenderable textRenderable;
    private GameObject textObject;
    private ImageRenderable heartImage;

    public Lives(
            Vector2 topLeftCorner,
            Getter<Integer> getLives) {
        super(topLeftCorner, new Vector2(20, HEIGHT), null);
        this.getLives = getLives;

        heartImage = Services.getService(ImageReader.class).readImage(HEART_IMAGE_PATH, true);
        textRenderable = new TextRenderable("", "Comic Sans MS");
        textObject = new GameObject(
                getTopLeftCorner(), Vector2.ZERO,
                textRenderable);
        Services.getService(AddGameObjectCommand.class).add(textObject, Layer.UI);
        LivesChanged();
    }

    public void LivesChanged() {

        var remover = Services.getService(RemoveGameObjectCommand.class);
        // remove old hearts
        for (var heart : hearts) {
            remover.remove(heart, Layer.UI);
        }

        var l = getLives.get();
        if (l <= 0) {
            return;
        }
        // text
        var text = String.valueOf(l);
        if (l >= 3) {
            textRenderable.setColor(Color.GREEN);
        } else if (l == 2) {
            textRenderable.setColor(Color.YELLOW);
        } else {
            textRenderable.setColor(Color.RED);
        }
        textRenderable.setString(text);
        var textWidth = text.length() * HEIGHT + GAP;
        textObject.setDimensions(new Vector2(textWidth, HEIGHT));
        textObject.setTopLeftCorner(getTopLeftCorner());
        textObject.setCoordinateSpace(getCoordinateSpace());

        // hearts
        var adder = Services.getService(AddGameObjectCommand.class);
        hearts = new GameObject[l];
        for (int i = 0; i < l; i++) {
            var heart = new GameObject(
                    new Vector2(getTopLeftCorner().x() + textWidth + i * (HEIGHT + GAP), getTopLeftCorner().y()),
                    new Vector2(HEIGHT, HEIGHT),
                    heartImage);
            heart.setCoordinateSpace(getCoordinateSpace());
            adder.add(heart, Layer.UI);
            hearts[i] = heart;
        }
    }
}
