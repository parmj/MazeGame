package Model;

import java.util.ArrayList;

/**
 * Stores current location, previous location, and possible movements of an Entity in the Maze.
 * Handles all movement.
 */
public class Entity {

   protected int row;
   protected int col;
   protected char lastMove;
   protected int prevCol;
   protected int prevRow;
   protected ArrayList<Character> possibleMoves;
   protected int type;
   Maze maze = new Maze();

   Entity(int col, int row, char lastMove)
   {
       this.col = col;
       this.row = row;
       prevCol = col;
       prevRow = row;
       this.lastMove = lastMove;
       this.type = 0;
       possibleMoves = new ArrayList<Character>();
   }

   public void moveLeft()
   {
       lastMove = 'a';
       col -= 1;
   }

    public void moveRight()
    {
        lastMove = 'd';
        col += 1;
    }

    public void moveUp()
    {
        lastMove = 'w';
        row -= 1;
    }

    public void moveDown()
    {
        lastMove = 's';
        row += 1;
    }

    public boolean isValidMove(char move)
    {
        if(possibleMoves.contains(move)) {
            return true;
        }
        else {
            return false;
        }
    }

    // 0 = Model.Player
    // 1/-1 = Wall / Invis
    // 2/-2 = Empty / Invis
    // 3/-3 = Model.Cat / Invis
    // 4 = Model.Cheese
    // 5 = Border


    protected void movePosition(char move)
    {
        prevCol = col;
        prevRow = row;

        switch (move) {
            case 'a':
                moveLeft();
                break;
            case 'd':
                moveRight();
                break;
            case 'w':
                moveUp();
                break;
            case 's':
                moveDown();
                break;
        }
    }
}
