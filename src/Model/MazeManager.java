package Model;

import GameGUI.GameBoard;
import UI.DisplayMaze;
import UI.UserUI;

import java.io.File;
import java.util.ArrayList;

/**
 * Handles all interactions between the Maze and entities inside the Maze, as well as all
 * decision making and manipulation of the maze
 */
public class MazeManager {

    private ArrayList<Cheese> cheeseList = new ArrayList<Cheese>();
    private Maze maze;
    public int[][] mazeArray;
    private int totalCheese = 0;
    private ArrayList<Cat> catList = new ArrayList<Cat>();
    public Player player;
    public boolean areAllCheeseFound = false;
    public boolean isPlayerCaught = false;
    private Cheese cheese;

    public MazeManager()
    {
        maze = new Maze();
        mazeArray = maze.getMaze();
        populateMaze();
        generateCheese();
        updateEntityLocs();
        revealSurroundings();
    }

    public void prepareMoves(){
        getPossibleMoves(player);
        for (int i = 0; i < catList.size(); i++){
            getPossibleMoves(catList.get(i));
        }
    }

    public void executeTurn(){
        getPossibleMoves(player);
        for (int i = 0; i < catList.size(); i++){
            getPossibleMoves(catList.get(i));
        }
        moveCats();
        updateEntityLocs();
        revealSurroundings();
        checkRevealedCells();
        checkCheeseIncrementation();
        if (isCaught()){
            isPlayerCaught = true;
        }
    }

    // 0 = Model.Player
    // 1/-1 = Wall / Invis
    // 2/-2 = Empty / Invis
    // 3/ = Model.Cat / Invis
    // 4 = Model.Cheese
    // 5 = Border

    private void generateCheese()
    {
        boolean cheeseAdded = false;
        while (!cheeseAdded){
            for (int c = Maze.randomColumn(); c < Maze.MAXCOL; c++){
                for (int r = Maze.randomRow(); r < Maze.MAXROW; r++){
                    if (Math.abs(mazeArray[c][r]) == 2){
                        cheese = new Cheese(c, r);
                        cheeseList.add(cheese);
                        maze.setCell(c, r, 4);
                        cheeseAdded = true;
                        r = Maze.MAXROW;
                        c = Maze.MAXCOL;
                    }
                }
            }
        }
    }

    private void moveCats(){
        for (int i = 0; i < catList.size(); i++){
            catList.get(i).randomMove();
        }
    }

    private void checkCheeseIncrementation()
    {
        for(int i = 0; i < cheeseList.size(); i++)
        {
            int cheeseCol = cheeseList.get(i).getColumn();
            int cheeseRow = cheeseList.get(i).getRow();

            if(player.collectedCheese(cheeseList.get(i)))
            {
                GameBoard.playSound(new File("src/Sounds/DING.WAV"));
                totalCheese++;
                cheeseList.remove(i);
                mazeArray[cheeseCol][cheeseRow] = 0;
                generateCheese();
            }
        }
        if (totalCheese >= 5){
            areAllCheeseFound = true;
        }
    }

    private void populateMaze(){
        player = new Player(1, 1, 'X');
        Cat cat1 = new Cat(1, Maze.MAXROW - 2, 'X');
        Cat cat2 = new Cat(Maze.MAXCOL - 2, 1, 'X');
        Cat cat3 = new Cat(Maze.MAXCOL - 2, Maze.MAXROW - 2, 'X');

        catList.add(cat1);
        catList.add(cat2);
        catList.add(cat3);
    }

    private void getPossibleMoves(Entity entity){

        entity.possibleMoves.clear();

        if (maze.isNotWall(entity.col, entity.row - 1)){
            entity.possibleMoves.add('w');
        }
        if (maze.isNotWall(entity.col - 1, entity.row)){
            entity.possibleMoves.add('a');
        }
        if (maze.isNotWall(entity.col, entity.row + 1)){
            entity.possibleMoves.add('s');
        }
        if (maze.isNotWall(entity.col + 1, entity.row)){
            entity.possibleMoves.add('d');
        }
    }

    private void updateEntityLocs(){
        maze.setCell(player.prevCol, player.prevRow, -2);
        for (int i = 0; i < catList.size(); i++){
            maze.setCell(catList.get(i).prevCol, catList.get(i).prevRow, -2);
        }

        maze.setCell(cheeseList.get(0).getColumn(), cheeseList.get(0).getRow(), 4);
        maze.setCell(player.col, player.row, player.type);
        for (int i = 0; i < catList.size(); i++){
            maze.setCell(catList.get(i).col, catList.get(i).row, catList.get(i).type);
        }
    }

    private void checkRevealedCells(){
        for (int i = 0; i < maze.revealedCells.size(); i++){
            int col = maze.revealedCells.get(i).getCol();
            int row = maze.revealedCells.get(i).getRow();
            int type = maze.getCellType(col, row);
            maze.setCell(col, row, Math.abs(type));
        }
    }

    private void revealSurroundings(){
        System.out.println("revealSurroundings");
        int col = player.col;
        int row = player.row;

        maze.revealCell(col, row);
        maze.revealCell(col + 1, row);
        maze.revealCell(col + 1, row + 1);
        maze.revealCell(col, row + 1);
        maze.revealCell(col - 1, row + 1);
        maze.revealCell(col - 1, row);
        maze.revealCell(col - 1, row - 1);
        maze.revealCell(col, row - 1);
        maze.revealCell(col + 1, row - 1);
    }

    public int getTotalCheese(){
        return totalCheese;
    }

    private boolean isCaught(){
        for (int i = 0; i < catList.size(); i++){
            if (player.col == catList.get(i).col && player.row == catList.get(i).row){
                return true;
            }
            else if (player.col == catList.get(i).prevCol && player.row == catList.get(i).prevRow){
                return true;
            }
        }
        return false;
    }

}
