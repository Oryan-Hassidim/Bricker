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

    /**
     * Constructs a new BricksNumber.
     * 
     * @param cols the number of columns
     * @param rows the number of rows
     */
    public BricksNumber(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    /**
     * Gets the number of columns.
     * 
     * @return the number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Gets the number of rows.
     * 
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }
}
