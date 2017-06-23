package Model;

import GameGUI.GameBoard;
import UI.UserUI;

import java.io.File;
import java.util.ArrayList;

/**
 * Handles input from UserUI and checks if Player has collected a cheese.
 */
public class Player extends Entity {

    Player (int row, int column, char lastMove) {
        super(row, column, lastMove);
    }

    public void handleUserInputMove()
    {
        char move = UserUI.getUserInput();
        movePosition(move);
    }

    public boolean checkUserInputMove(){
        char move = UserUI.getUserInput();
        if (isValidMove(move)){
            return true;
        }
        else {
            GameBoard.playSound(new File("src/Sounds/hitwall.WAV"));
            UserUI.invalidMoveMessage();
            return false;
        }
    }

    public boolean collectedCheese(Cheese cheese)
    {
        int cheeseRow = cheese.getRow();
        int cheeseColumn = cheese.getColumn();

        if(super.row == cheeseRow && super.col == cheeseColumn)
        {
            return true;
        }
        else {
            return false;
        }
    }
}
