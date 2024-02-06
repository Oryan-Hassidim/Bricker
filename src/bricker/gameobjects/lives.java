package bricker.gameobjects;

import bricker.utils.ParametrizedCommand;
import bricker.utils.getter;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.awt.Color;

public class lives extends GameObject {

    private static final String HEART_IMAGE_PATH = "assets/heart.png";
    private static final int GAP = 2;
    private getter<Integer> getLives;
    private ImageRenderable heartImage;
    private int height;
    private GameObject[] hearts = new GameObject[0];
    private ParametrizedCommand<GameObject> addGameObject;
    private ParametrizedCommand<GameObject> removeGameObject;
    private TextRenderable textRenderable;
    private GameObject textObject;

    public lives(
            Vector2 topLeftCorner, int height,
            getter<Integer> getLives, ImageReader imageReader,
            ParametrizedCommand<GameObject> addGameObject,
            ParametrizedCommand<GameObject> removeGameObject) {
        super(topLeftCorner, new Vector2(20, height), null);
        this.height = height;
        this.getLives = getLives;
        this.addGameObject = addGameObject;
        this.removeGameObject = removeGameObject;
        heartImage = imageReader.readImage(HEART_IMAGE_PATH, true);
        textRenderable = new TextRenderable("", "Comic Sans MS");
        textObject = new GameObject(
            getTopLeftCorner(), Vector2.ZERO,
            textRenderable);
            addGameObject.invoke(textObject);
        LivesChanged();
    }
    
    public void LivesChanged() {
        
        // remove old hearts
        for (var heart : hearts) {
            removeGameObject.invoke(heart);
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
        var textWidth = text.length() * height + GAP;
        textObject.setDimensions(new Vector2(textWidth, height));
        textObject.setTopLeftCorner(getTopLeftCorner());

        // hearts
        hearts = new GameObject[l];
        for (int i = 0; i < l; i++) {
            var heart = new GameObject(
                    new Vector2(getTopLeftCorner().x() + textWidth + i * (height + GAP), getTopLeftCorner().y()),
                    new Vector2(height, height),
                    heartImage);
            addGameObject.invoke(heart);
            hearts[i] = heart;
        }
    }
}
