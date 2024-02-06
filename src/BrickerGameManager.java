import java.awt.Color;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.*;
import bricker.utils.AddGameObjectCommand;
import bricker.utils.BasicLogger;
import bricker.utils.Logger;
import bricker.utils.ParametrizedCommand;
import bricker.utils.RemoveGameObjectCommand;
import bricker.utils.Services;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.util.Random;
import java.awt.event.KeyEvent;

/**
 * the game manager for the bricker game
 * 
 * @author Orayn Hassidim
 */
public class BrickerGameManager extends GameManager {

    // #region inner classes
    // #endregion

    // #region Constants
    private static final int WALL_WIDTH = 50;
    private static final int DEFAULT_BRICKS_PER_ROW = 8;
    private static final int DEFAULT_BRICKS_PER_COLUMN = 7;
    private static final int BRICKS_AREA_MARGIN = 10;
    private static final int BRICKS_GAP = 5;
    // TODO: I need to make all bricks 15 px height?
    private static final float BRICKS_AREA_HEIGHT_RATIO = 0.4f;
    private static final String LOSE_MESSAGE = "You lose! Play again?";
    private static final String WIN_MESSAGE = "You win! Play again?";

    // paths
    private static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String BRICK_IMAGE_PATH = "assets/brick.png";

    // #endregion

    // #region fields
    private Logger logger = Services.getService(Logger.class);
    
    private int bricksPerRow;
    private int bricksPerColumn;
    
    private int bricks;
    private int lives;
    protected int balls;
    
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Vector2 dims;
    
    private lives livesDisplay;
    // #endregion

    // #region commands

    private AddGameObjectCommand addGameObjectCommand = new AddGameObjectCommand() {
        @Override
        public void add(GameObject gameObject, int layer) {
            gameObjects().addGameObject(gameObject, layer);
        }
    };
    private RemoveGameObjectCommand removeGameObjectCommand = new RemoveGameObjectCommand() {
        @Override
        public void remove(GameObject gameObject, int layer) {
            gameObjects().removeGameObject(gameObject, layer);
        }
    };

    // #endregion

    // #region collision strategies
    private BasicCollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy();
    private BasicCollisionStrategy addPucksStrategy = new BasicCollisionStrategy() {
        @Override
        public void onCollision(GameObject thisObject, GameObject otherObject) {
            super.onCollision(thisObject, otherObject);
            if (!(thisObject instanceof Ball))
            {
                logger.logError("ball collision strategy called with non-ball object");
                return;
            }
            if (!(otherObject instanceof Puck))
            {
                logger.logError("ball collision strategy called with non-puck object");
                return;
            }
            Ball ball = (Ball) thisObject;
            Puck puck = (Puck) otherObject;
            ball.setVelocity(ball.getVelocity().add(puck.getVelocity()));
        }
    };
    private CollisionStrategy[] collisionStrategies = new CollisionStrategy[] {
            basicCollisionStrategy,
            basicCollisionStrategy,
            basicCollisionStrategy,
            basicCollisionStrategy,
            basicCollisionStrategy,
            // add new balls collision strategy
            new BasicCollisionStrategy() {
                @Override
                public void onCollision(GameObject thisObject, GameObject otherObject) {
                    super.onCollision(thisObject, otherObject);
                    if (!(thisObject instanceof Brick))
                    {
                        logger.logError("ball collision strategy called with non-brick object");
                        return;
                    }
                    
                }
            }
    };

    // #endregion

    // #region constructors

    /**
     * creates a new bricker game manager with a given title, window size,
     * and number of bricks per row and column
     * 
     * @param title           the title of the game
     * @param windowSize      the size of the window
     * @param bricksPerRow    the number of bricks per row
     * @param bricksPerColumn the number of bricks per column
     */
    public BrickerGameManager(
            String title, Vector2 windowSize,
            int bricksPerRow, int bricksPerColumn) {
        super(title, windowSize);
        this.dims = windowSize;
        this.bricksPerRow = bricksPerRow;
        this.bricksPerColumn = bricksPerColumn;
    }

    /**
     * creates a new bricker game manager with a given title and window size
     * 
     * @param title      the title of the game
     * @param windowSize the size of the window
     */
    public BrickerGameManager(String title, Vector2 windowSize) {
        this(title, windowSize, DEFAULT_BRICKS_PER_ROW, DEFAULT_BRICKS_PER_COLUMN);
    }
    // #endregion

    // #region methods

    protected void winGame() {
        if (!windowController.openYesNoDialog(WIN_MESSAGE)) {
            windowController.closeWindow();
        }
        windowController.resetGame();
    }

    protected void loseGame() {
        if (!windowController.openYesNoDialog(LOSE_MESSAGE)) {
            windowController.closeWindow();
        }
        windowController.resetGame();
    }

    /**
     * adds game objects to the game
     * 
     * @param objects the game objects to add
     */
    protected void addGameObjects(GameObject... objects) {
        for (var obj : objects) {
            this.gameObjects().addGameObject(obj, Layer.DEFAULT);
        }
    }

    private void configureServices(
            ImageReader imageReader, SoundReader soundReader,
            UserInputListener inputListener, WindowController windowController) {
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        Services.registerService(ImageReader.class, imageReader);
        Services.registerService(SoundReader.class, soundReader);
        Services.registerService(UserInputListener.class, inputListener);
        Services.registerService(WindowController.class, windowController);
        Services.registerService(Vector2.class, dims);
        
        Services.registerService(AddGameObjectCommand.class, addGameObjectCommand);
        Services.registerService(RemoveGameObjectCommand.class, removeGameObjectCommand);

        Services.registerService(Random.class, new Random());
    }

    private void initializeBackground() {
        var background = new GameObject(
                Vector2.ZERO, dims,
                imageReader.readImage(BACKGROUND_IMAGE_PATH, false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void initializeWalls() {
        var wallImage = new RectangleRenderable(Color.red);
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

    private Ball initializeBall() {
        var ball = new Ball();
        this.addGameObjects(ball);
        balls++;
        return ball;
    }

    private Paddle initializePaddle() {
        var paddle = new Paddle(new Vector2(dims.x() / 2, dims.y() - 50));
        this.addGameObjects(paddle);
        return paddle;
    }

    private void removeBrick(GameObject param) {
        var removed = gameObjects().removeGameObject(param);
        if (!removed)
            logger.log("failed to remove game object" + param);
        bricks--;
        if (bricks <= 0)
            winGame();
    }

    private void initializeBricks() {
        var bricksAreaHeight = (dims.y() - 2 * BRICKS_AREA_MARGIN) * BRICKS_AREA_HEIGHT_RATIO;
        var brickSize = new Vector2(
                (dims.x() - 2 * BRICKS_AREA_MARGIN - (bricksPerRow - 1) * BRICKS_GAP) / bricksPerRow,
                (bricksAreaHeight - (bricksPerColumn - 1) * BRICKS_GAP) / bricksPerColumn);
        var brickImage = imageReader.readImage(BRICK_IMAGE_PATH, true);
        bricks = bricksPerRow * bricksPerColumn;
        for (int i = 0; i < bricksPerRow; i++) {
            for (int j = 0; j < bricksPerColumn; j++) {
                var brick = new Brick(
                        new Vector2(
                                BRICKS_AREA_MARGIN + i * (brickSize.x() + BRICKS_GAP),
                                BRICKS_AREA_MARGIN + j * (brickSize.y() + BRICKS_GAP)),
                        brickSize, brickImage, basicCollisionStrategy);
                this.addGameObjects(brick);
            }
        }
    }

    private void initializeGameOverWall() {
        var brickSize = new Vector2(dims.x(), 20);
        var brick = new GameObject(new Vector2(0, dims.y()),
                brickSize, null) {
            @Override
            public void onCollisionEnter(GameObject other, Collision collision) {
                if (other instanceof BallBase) {
                    BallBase ball = (BallBase) other;
                    gameObjects().removeGameObject(ball);
                    if (!(ball instanceof Ball))
                        return;
                    balls--;
                    if (balls > 0)
                        return;
                    lives--;
                    livesDisplay.LivesChanged();
                    if (lives <= 0) {
                        loseGame();
                    }
                    initializeBall();
                }
            }
        };
        this.addGameObjects(brick);
    }

    private void initializeLivesDisplay() {
        livesDisplay = new lives(
                new Vector2(5, dims.y() - 25), 20, () -> lives, imageReader,
                new ParametrizedCommand<GameObject>() {
                    @Override
                    public void invoke(GameObject param) {
                        gameObjects().addGameObject(param, Layer.UI);
                    }
                }, new ParametrizedCommand<GameObject>() {
                    @Override
                    public void invoke(GameObject param) {
                        gameObjects().removeGameObject(param, Layer.UI);
                    }
                });
        this.gameObjects().addGameObject(livesDisplay, Layer.UI);
    }

    /**
     * initializes the game
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

        initializeBall();

        initializePaddle();

        initializeBricks();

        initializeGameOverWall();

        initializeLivesDisplay();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (inputListener.isKeyPressed(KeyEvent.VK_Q)) {
            windowController.closeWindow();
        } else if (inputListener.isKeyPressed(KeyEvent.VK_R)) {
            windowController.resetGame();
        } else if (inputListener.isKeyPressed(KeyEvent.VK_W)) {
            winGame();
        }
    }
    // #endregion

    public static void main(String[] args) throws Exception {

        Services.registerService(Logger.class, new BasicLogger());

        BrickerGameManager gm;
        try {
            gm = new BrickerGameManager("Bricker", new Vector2(700, 500), Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]));
        } catch (Exception e) {
            gm = new BrickerGameManager("Bricker", new Vector2(700, 500));
        }
        gm.run();
    }
}
