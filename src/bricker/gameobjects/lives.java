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

/**
 * A representation of the player's lives.
 * 
 * @author Orayn Hassidim
 */
public class Lives extends GameObject {

    /** The path to the heart image. */
    public static final String HEART_IMAGE_PATH = "assets/heart.png";
    /** The gap between the hearts and the text. */
    private static final int GAP = -4;
    /** The height of the lives. */
    public static final int HEIGHT = 20;

    /** A getter for the number of lives. */
    private Getter<Integer> getLives;
    /** The hearts of the player. */
    private GameObject[] hearts = new GameObject[0];
    /** The text renderable of the text. */
    private TextRenderable textRenderable;
    /** The text object of the text. */
    private GameObject textObject;
    /** The image of the heart. */
    private ImageRenderable heartImage;

    /**
     * Constructs a new Lives with the given top left corner and a getter for the
     * number of lives.
     * 
     * @param topLeftCorner the top left corner of the lives
     * @param getLives      a getter for the number of lives
     */
    public Lives(
            Vector2 topLeftCorner,
            Getter<Integer> getLives) {
        super(topLeftCorner, new Vector2(HEIGHT, HEIGHT), null);
        this.getLives = getLives;

        heartImage = Services.getService(ImageReader.class).readImage(HEART_IMAGE_PATH, true);
        textRenderable = new TextRenderable("", "Comic Sans MS");
        textObject = new GameObject(
                getTopLeftCorner(), Vector2.ZERO,
                textRenderable);
        Services.getService(AddGameObjectCommand.class).add(textObject, Layer.UI);
        LivesChanged();
    }

    /**
     * Initializes the text.
     * 
     * @param l the number of lives
     * @return the width of the text
     */
    private int initializeText(Integer l) {
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
        return textWidth;
    }

    /**
     * Initializes the hearts.
     * 
     * @param l         the number of lives
     * @param textWidth the width of the text
     */
    private void InitializeHearts(Integer l, int textWidth) {
        // hearts
        // remove old hearts
        var remover = Services.getService(RemoveGameObjectCommand.class);
        for (var heart : hearts) {
            remover.remove(heart, Layer.UI);
        }
        var adder = Services.getService(AddGameObjectCommand.class);
        hearts = new GameObject[l];
        for (int i = 0; i < l; i++) {
            var heart = new GameObject(
                    new Vector2(
                            getTopLeftCorner().x() + textWidth + i * (HEIGHT + GAP),
                            getTopLeftCorner().y()),
                    new Vector2(HEIGHT, HEIGHT),
                    heartImage);
            heart.setCoordinateSpace(getCoordinateSpace());
            adder.add(heart, Layer.UI);
            hearts[i] = heart;
        }
    }

    /**
     * A method which is called when the number of lives changes.
     * Updates the hearts and the text.
     */
    public void LivesChanged() {
        var l = getLives.get();
        if (l <= 0) {
            return;
        }
        var textWidth = initializeText(l);

        InitializeHearts(l, textWidth);
    }
}
