package bricker.utils;

/**
 * A representation of the number of bricks in the game.
 * 
 * @see bricker.gameobjects.Brick
 * @author Orayn Hassidim
 */
public class BricksNumber {
    private int cols;
    private int rows;

    public BricksNumber(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}
