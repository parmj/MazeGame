package Model;

/**
 * Stores location of cheese pieces in the maze.
 */
public class Cheese {

    private int row;
    private int column;

    Cheese (int col, int row)
    {
        updatePosition(col,row);
    }

    public void updatePosition(int col, int row)
    {
        this.column = col;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
