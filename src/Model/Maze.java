package Model;

import Model.Cell;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Randomly generates a maze to a fixed width and height
 * Contains methods to directly manipulate the maze.
 */
public class Maze {

    public static final int MAXCOL = 20;
    public static final int MAXROW = 15;
    private int[][] maze = new int[MAXCOL][MAXROW];
    public ArrayList<Cell> revealedCells = new ArrayList<Cell>();

    public Maze()
    {
        initializeMaze();
        int col = randomColumn();
        int row = randomRow();
        generateMaze(col, row, col, row);
        removeWalls();
    }

    public int[][] getMaze()
    {
        return maze;
    }

    private void initializeMaze(){

        // 0 = Player
        // 1/-1 = Wall / Invis
        // 2/-2 = Empty / Invis
        // 3/ = Cat / Invis
        // 4 = Cheese
        // 5 = Border

        // Initializing border
        for (int c = 0; c < MAXCOL; c++){
            for (int r = 0; r < MAXROW; r++){
                if ((c == 0) || (r == 0) || (c == (MAXCOL - 1)) || (r == (MAXROW - 1))){
                    maze[c][r] = 5;
                }
                else {
                    maze[c][r] = -1;
                }
            }
        }
    }

    private void generateMaze(int currentCol, int currentRow, int prevCol, int prevRow){
        int nextSecondCol = 0;
        int nextSecondRow = 0;
        int nextCol = 0;
        int nextRow = 0;

        Random random = new Random();

        int[] validDirections = new int[4];
        for (int i = 0; i < 4; i++){
            validDirections[i] = 1;
        }

        emptyCell(currentCol, currentRow);
        emptyCell(prevCol, prevRow);

        int randomDirec = random.nextInt(3);

        while (hasValidDirections(validDirections)) {
            switch (randomDirec) {
                case 0: {
                    nextSecondCol = currentCol + 2;
                    nextSecondRow = currentRow;
                    nextCol = currentCol + 1;
                    nextRow = currentRow;
                    break;
                }
                case 1: {
                    nextSecondCol = currentCol;
                    nextSecondRow = currentRow + 2;
                    nextCol = currentCol;
                    nextRow = currentRow + 1;
                    break;
                }
                case 2: {
                    nextSecondCol = currentCol - 2;
                    nextSecondRow = currentRow;
                    nextCol = currentCol - 1;
                    nextRow = currentRow;
                    break;
                }
                case 3: {
                    nextSecondCol = currentCol;
                    nextSecondRow = currentRow - 2;
                    nextCol = currentCol;
                    nextRow = currentRow - 1;
                    break;
                }
                default: break;
            }
            checkNextCell(nextSecondCol, nextSecondRow, validDirections, randomDirec);

            // If direction is valid, go to next cell
            if (validDirections[randomDirec] == 1){
                generateMaze(nextSecondCol, nextSecondRow, nextCol, nextRow);
            }
            randomDirec++;
            if (randomDirec > 3){
                randomDirec = 0;
            }
        }
    }

    private void removeWalls(){
        float wallCount = 0;
        for (int i = 0; i < MAXCOL; i++){
            for (int j = 0; j < MAXROW; j++){
                if (maze[i][j] == -1){
                    wallCount++;
                }
            }
        }

        float wallsRemoved = 0;
        while (((wallCount - wallsRemoved) / wallCount) > 0.70){
            for (int i = randomColumn(); i < MAXCOL; i++){
                for (int j = randomRow(); j < MAXROW; j++){
                    if (maze[i][j] == -1){
                        maze[i][j] = -2;
                        wallsRemoved = wallsRemoved + 1;
                        i = MAXCOL;
                        j = MAXROW;
                    }
                }
            }
        }

        // Emptying out corners
        emptyCell(1, 1);
        emptyCell(1, 2);
        emptyCell(2, 1);
        emptyCell(2, 2);

        emptyCell(1, MAXROW - 2);
        emptyCell(1, MAXROW - 3);
        emptyCell(2, MAXROW - 2);
        emptyCell(2, MAXROW - 3);

        emptyCell(MAXCOL - 2, 1);
        emptyCell(MAXCOL -2, 2);
        emptyCell(MAXCOL - 3, 1);
        emptyCell(MAXCOL - 3, 2);

        emptyCell(MAXCOL - 2, MAXROW - 2);
        emptyCell(MAXCOL - 2, MAXROW - 3);
        emptyCell(MAXCOL - 3, MAXROW - 2);
        emptyCell(MAXCOL - 3, MAXROW - 3);
    }



    private int[] checkNextCell(int col, int row, int[] validDirections, int randomDirec){
        // Checking for ArrayOutOfBoundsException
        if ((col >= MAXCOL - 1) || (col <= 0) || (row >= MAXROW - 1) || (row <= 0)){
            validDirections[randomDirec] = 0;
        }
        else if (maze[col][row] == -2){ // Checking if next cell is already empty
            validDirections[randomDirec] = 0;
        }
        return validDirections;
    }

    // For debugging
    public void printMaze(){
        for (int j = 0; j < MAXROW; j++) {
            System.out.print("\n");
            for (int i = 0; i < MAXCOL; i++) {
                System.out.print("(" + (maze[i][j] * -1) + ")");
            }
        }
        System.out.println("\n\n");
    }

    public static int randomColumn(){
        return ThreadLocalRandom.current().nextInt(1, 18);
    }

    public static int randomRow(){
        return ThreadLocalRandom.current().nextInt(1, 13);
    }

    private void emptyCell(int col, int row){
        maze[col][row] = -2;
    }

    private boolean hasValidDirections(int[] validDirections){
        int count = 0;
        for (int direc : validDirections){
            count += validDirections[direc];
        }
        if (count == 0){
            return false;
        }
        else return true;
    }

    public boolean checkEmpty(int col, int row){
        if (Math.abs(maze[col][row]) == 2){
            return true;
        }
        else return false;
    }

    public boolean isNotWall(int col, int row){
        if (Math.abs(maze[col][row]) == 1 || (Math.abs(maze[col][row]) == 5)){
            return false;
        }
        else return true;
    }

    public void revealCell(int col, int row){
        maze[col][row] = Math.abs(maze[col][row]);
        addRevealedCell(col, row);
    }

    public void setCell(int col, int row, int type){
        maze[col][row] = type;
    }

    private void addRevealedCell(int col, int row){
        Cell cell = new Cell(col, row);
        revealedCells.add(cell);
    }

    public int getCellType(int col, int row){
        return maze[col][row];
    }

}

