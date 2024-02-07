
import bricker.brick_strategies.CollisionStrategyGenerator;
import bricker.brick_strategies.Level2CollisionStrategyGenerator;
import bricker.gameobjects.*;
import bricker.utils.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Date;
import java.util.Random;
import java.awt.event.KeyEvent;

/**
 * the game manager for the bricker game
 * 
 * @author Orayn Hassidim
 */
public class BrickerGameManager extends GameManager {

    // #region Constants
    /** The default size of the window. */
    private static final Vector2 DEFAULT_WINDOW_SIZE = new Vector2(700, 500);

    /** The width of the walls. */
    private static final int WALL_WIDTH = 50;
    /** The default number of bricks per row. */
    private static final int DEFAULT_BRICKS_PER_ROW = 8;
    /** The default number of bricks per column. */
    private static final int DEFAULT_BRICKS_PER_COLUMN = 7;
    /** The margin of the bricks area from the window edges. */
    private static final int BRICKS_AREA_MARGIN = 10;
    /** The gap between bricks. */
    private static final int BRICKS_GAP = 5;
    // TODO: I need to make all bricks 15 px height?
    /** The max ratio of the height of the bricks area to the window height. */
    private static final float MAX_BRICKS_AREA_HEIGHT_RATIO = 0.4f;
    /** The message to display when the player loses. */
    private static final String LOSE_MESSAGE = "You lose! Play again?";
    /** The message to display when the player wins. */
    private static final String WIN_MESSAGE = "You win! Play again?";
    /** The maximum number of lives. */
    protected static final int MAX_LIVES = 4;
    /** The default number of balls. */
    private static final int DEFAULT_BALLS_NUMBER = 1;

    // paths
    /** The path to the background image. */
    private static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";
    // #endregion

    // #region fields
    /** The number of bricks remaining. */
    private int bricks;
    /** The number of lives remaining. */
    private int lives;
    /** The number of balls remaining. */
    private int balls;

    /** Whether the game is poused. */
    protected boolean poused;

    /** The time of the last pouse/unpouse. */
    private Date lastPoused = new Date();

    /** The image reader. */
    private ImageReader imageReader;
    /** The input listener. */
    private UserInputListener inputListener;
    /** The window controller. */
    private WindowController windowController;
    /** The dimensions of the window. */
    private Vector2 dims;

    /** The lives display. we need it to update the lives. */
    private Lives livesDisplay;
    // #endregion

    // #region commands

    /** The command to add a game object to the game. */
    private AddGameObjectCommand addGameObjectCommand = new AddGameObjectCommand() {
        @Override
        public void add(GameObject gameObject, int layer) {
            gameObjects().addGameObject(gameObject, layer);
        }
    };
    /**
     * The command to remove a game object from the game.
     * if the game object is a brick, it also updates the number of bricks
     * remaining.
     * if there are no bricks remaining, it calls winGame.
     */
    private RemoveGameObjectCommand removeGameObjectCommand = new RemoveGameObjectCommand() {
        @Override
        public void remove(GameObject gameObject, int layer) {
            var removed = gameObjects().removeGameObject(gameObject, layer);
            if (!removed) {
                Services.getService(Logger.class).logError(
                        "failed to remove game object %s via command", gameObject);
                return;
            }
            Services.getService(Logger.class).logInformation(
                    "%s removed via command",
                    gameObject.getClass().getSimpleName());
            if (!(gameObject instanceof Brick))
                return;
            bricks--;
            if (bricks <= 0)
                winGame();
        }
    };
    /** The command to set the camera of the game. */
    private SetCameraCommand setCameraCommand = new SetCameraCommand() {
        @Override
        public void setCamera(Camera camera) {
            mySetCamera(camera);
        }
    };
    /**
     * The command to add a life to the player.
     * if the number of lives is already at the maximum, it does nothing.
     */
    private AddLifeCommand addLifeCommand = new AddLifeCommand() {
        @Override
        public void addLife() {
            lives = Math.min(lives + 1, MAX_LIVES);
            livesDisplay.LivesChanged();
        }
    };

    // #endregion

    // #region constructors

    /**
     * Constructs a new BrickerGameManager.
     */
    public BrickerGameManager() {
        super("Bricker", DEFAULT_WINDOW_SIZE);
        Services.registerService(Vector2.class, DEFAULT_WINDOW_SIZE);
    }
    // #endregion

    // #region methods

    // #region getters and setters

    /**
     * Gets the poused status of the game.
     * @return the poused status of the game. true if the game is poused,
     * false if it is not.
     */
    public boolean isPoused() {
        return poused;
    }

    /**
     * Sets the poused status of the game.
     * @param poused the poused status to set
     */
    public void setPoused(boolean poused) {
        this.poused = poused;
    }

    // #endregion

    /**
     * Sets the camera of the game.
     * we need this method to set the camera from the setCameraCommand.
     * 
     * @param camera the camera to set
     */
    private void mySetCamera(Camera camera) {
        setCamera(camera);
    }

    /**
     * A method to call when the player wins the game.
     */
    protected void winGame() {
        if (!windowController.openYesNoDialog(WIN_MESSAGE)) {
            windowController.closeWindow();
        }
        windowController.resetGame();
    }

    /**
     * A method to call when the player loses the game.
     */
    protected void loseGame() {
        if (!windowController.openYesNoDialog(LOSE_MESSAGE)) {
            windowController.closeWindow();
        }
        windowController.resetGame();
    }

    /**
     * Adds game objects to the game.
     * 
     * @param objects the game objects to add
     */
    private void addGameObjects(GameObject... objects) {
        for (var obj : objects) {
            this.gameObjects().addGameObject(obj, Layer.DEFAULT);
        }
    }

    /**
     * Configures the services of the game.
     */
    protected void configureServices(
            ImageReader imageReader, SoundReader soundReader,
            UserInputListener inputListener, WindowController windowController) {

        Services.registerService(Logger.class, new BasicLogger());

        Services.registerService(ImageReader.class, imageReader);
        Services.registerService(SoundReader.class, soundReader);
        Services.registerService(UserInputListener.class, inputListener);
        Services.registerService(WindowController.class, windowController);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowController = windowController;

        Services.registerService(CollisionStrategyGenerator.class,
                new Level2CollisionStrategyGenerator());
        Services.registerService(Vector2.class, windowController.getWindowDimensions());

        Services.registerService(AddGameObjectCommand.class, addGameObjectCommand);
        Services.registerService(RemoveGameObjectCommand.class, removeGameObjectCommand);
        Services.registerService(SetCameraCommand.class, setCameraCommand);
        Services.registerService(AddLifeCommand.class, addLifeCommand);

        Services.registerService(Random.class, new Random());
    }

    /**
     * Initializes the background of the game.
     */
    protected void initializeBackground() {
        var background = new GameObject(
                Vector2.ZERO, dims,
                imageReader.readImage(BACKGROUND_IMAGE_PATH, false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * Initializes the walls of the game.
     */
    protected void initializeWalls() {
        Renderable wallImage = null;
        var walls = new GameObject[] {
                new GameObject( // top
                        new Vector2(0, -WALL_WIDTH),
                        new Vector2(dims.x(), WALL_WIDTH),
                        wallImage),
                new GameObject( // left
                        new Vector2(-WALL_WIDTH, 0),
                        new Vector2(WALL_WIDTH, dims.y()),
                        wallImage),
                new GameObject( // right
                        new Vector2(dims.x(), 0),
                        new Vector2(WALL_WIDTH, dims.y()),
                        wallImage)
        };

        addGameObjects(walls);
    }

    /**
     * Initializes the balls of the game.
     */
    protected void initializeBalls() {
        for (int i = 0; i < DEFAULT_BALLS_NUMBER; i++) {
            var ball = new Ball();
            this.addGameObjects(ball);
            balls++;
        }
    }

    /**
     * Initializes the paddle of the game.
     * 
     * @return the paddle
     */
    protected Paddle initializePaddle() {
        var paddle = new Paddle();
        this.addGameObjects(paddle);
        return paddle;
    }

    /**
     * Initializes the bricks of the game.
     */
    protected void initializeBricks() {
        var bricksAreaHeight = (dims.y() - 2 * BRICKS_AREA_MARGIN) * MAX_BRICKS_AREA_HEIGHT_RATIO;
        var bricksPerRow = Services.getService(BricksNumber.class).getCols();
        var bricksPerColumn = Services.getService(BricksNumber.class).getRows();
        var brickSize = new Vector2(
                (dims.x() - 2 * BRICKS_AREA_MARGIN - (bricksPerRow - 1) * BRICKS_GAP) / bricksPerRow,
                Math.min(15, (bricksAreaHeight - (bricksPerColumn - 1) * BRICKS_GAP) / bricksPerColumn));
        bricks = bricksPerRow * bricksPerColumn;
        for (int i = 0; i < bricksPerRow; i++) {
            for (int j = 0; j < bricksPerColumn; j++) {
                var brick = new Brick(
                        new Vector2(
                                BRICKS_AREA_MARGIN + i * (brickSize.x() + BRICKS_GAP),
                                BRICKS_AREA_MARGIN + j * (brickSize.y() + BRICKS_GAP)),
                        brickSize);
                this.addGameObjects(brick);
            }
        }
    }

    /**
     * Initializes the game over wall (the bottom wall).
     */
    protected void initializeGameOverWall() {
        var wall = new BottomWall() {
            @Override
            public void onCollisionEnter(GameObject other, Collision collision) {
                if (other instanceof BallBase) {
                    BallBase ball = (BallBase) other;
                    gameObjects().removeGameObject(ball);
                    Services.getService(Logger.class).logInformation(
                            "%s removed via game over wall", ball.getClass().getSimpleName());
                    if (!(ball instanceof Ball))
                        return;
                    balls--;
                    if (balls > 0)
                        return;
                    lives--;
                    if (camera() != null && camera().getObjectFollowed() == ball)
                        setCamera(null);

                    livesDisplay.LivesChanged();
                    if (lives <= 0) {
                        loseGame();
                    }
                    initializeBalls();
                }
            }
        };
        this.addGameObjects(wall);
    }

    /**
     * Initializes the lives display.
     */
    protected void initializeLivesDisplay() {
        livesDisplay = new Lives(
                new Vector2(5, dims.y() - 25), () -> lives);
        livesDisplay.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(livesDisplay, Layer.UI);
    }

    /**
     * Initializes the game.
     * 
     * @param imageReader      the image reader
     * @param soundReader      the sound reader
     * @param inputListener    the input listener
     * @param windowController the window controller
     */
    @Override
    public void initializeGame(
            ImageReader imageReader,
            SoundReader soundReader,
            UserInputListener inputListener,
            WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        configureServices(imageReader, soundReader, inputListener, windowController);

        dims = windowController.getWindowDimensions();

        lives = 3;

        initializeBackground();

        initializeWalls();

        initializeBalls();

        initializePaddle();

        initializeBricks();

        initializeGameOverWall();

        initializeLivesDisplay();
    }

    /**
     * A method which is called every frame and updates the game.
     * if the player presses Q, the game closes.
     * if the player presses R, the game resets.
     * if the player presses W, the game wins.
     * if the player presses P, the game pouses/unpouses.
     * 
     * @param deltaTime the time passed since the last update
     */
    @Override
    public void update(float deltaTime) {
        if (!poused) {
            super.update(deltaTime);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_Q)) {
            windowController.closeWindow();
        } else if (inputListener.isKeyPressed(KeyEvent.VK_R)) {
            windowController.resetGame();
        } else if (inputListener.isKeyPressed(KeyEvent.VK_W)) {
            winGame();
        } else if (inputListener.isKeyPressed(KeyEvent.VK_P)
                && new Date().getTime() - lastPoused.getTime() > 200) {
            poused = !poused;
            lastPoused = new Date();
        }
    }
    // #endregion

    /**
     * The main method of the game.
     * 
     * @param args the command line arguments
     * @throws Exception if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        try {
            Services.registerService(BricksNumber.class,
                    new BricksNumber(
                            Integer.parseInt(args[0]),
                            Integer.parseInt(args[1])));
        } catch (Exception e) {
            Services.registerService(BricksNumber.class,
                    new BricksNumber(
                            DEFAULT_BRICKS_PER_ROW,
                            DEFAULT_BRICKS_PER_COLUMN));
        }
        BrickerGameManager gm = new BrickerGameManager() {
            @Override
            protected void configureServices(
                    ImageReader ir, SoundReader sr, UserInputListener il, WindowController wc) {
                super.configureServices(ir, sr, il, wc);
            }
        };
        gm.run();
    }
}
