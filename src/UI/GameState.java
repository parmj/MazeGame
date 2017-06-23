package UI;

import Model.MazeManager;

/**
 * Handles all high level functionality of the game. Stores all UI and Maze logic elements.
 */
public class GameState {

    public static void main(String args[]){
        UserUI userUI = new UserUI();
        Model.MazeManager mazeManager = new Model.MazeManager();
        DisplayMaze displayMaze = new UI.DisplayMaze(mazeManager.mazeArray);
        userUI.displayStartMenu();

        while (mazeManager.areAllCheeseFound == false && mazeManager.isPlayerCaught == false){
            displayMaze.printMaze();
            mazeManager.prepareMoves();
            boolean validMove = false;
            while (!validMove){
                userUI.displayNextTurn(mazeManager.getTotalCheese());
                if (mazeManager.player.checkUserInputMove()){
                    validMove = true;
                    mazeManager.player.handleUserInputMove();
                }
            }
            mazeManager.executeTurn();
            displayMaze.getDisplayMaze(mazeManager.mazeArray);
        }
        DisplayMaze.printWholeMaze();
        if (mazeManager.areAllCheeseFound){
            userUI.playerWonMessage();
        }
        else if (mazeManager.isPlayerCaught){
            userUI.playerCaughtMessage();
        }
    }
}
