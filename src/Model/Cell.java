package Model;

/**
 * Stores location of an individual Cell in the maze.
 */
public class Cell {
    private int col;
    private int row;

    public Cell(int col, int row){
        this.col = col;
        this.row = row;
    }

    public int getCol(){
        return col;
    }

    public int getRow(){
        return row;
    }
}
