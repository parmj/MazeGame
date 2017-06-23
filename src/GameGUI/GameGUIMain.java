package GameGUI;

import Model.Maze;
import Model.MazeManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * The main component of the GUI version of the game
 * It will display the game Board and change whenever the user has entered something new
 * It displays the status of the amount of cookies grabbed by the player
 * Will display and end screen if win or lose the game
 */
public class GameGUIMain {

    private static MazeManager mazeManager = new Model.MazeManager();
    private static JFrame frame = new JFrame();
    private static GameBoard gameBoard ;
    private static GameStatus status;
    private static ArrowKeys keys = new ArrowKeys();

    private static final int TOTAL_CHEESE = 5;

    public static void main (String args[])
    {
        updateUI();
        registerAsObserver();
    }

    private static void updateUI()
    {
        frame.setVisible(false);
        frame = new JFrame();

        gameBoard = new GameBoard(mazeManager.mazeArray);
        status = new GameStatus(mazeManager.getTotalCheese(), TOTAL_CHEESE);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.add(keys);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.add(gameBoard);
        frame.add(status);
        frame.pack();

        mazeManager.prepareMoves();
    }


    private static void registerAsObserver() {
        keys.addObserver(new PlayerMoveObserver() {

            public void stateChanged() {

            if (mazeManager.areAllCheeseFound == false && mazeManager.isPlayerCaught == false) {

                if (mazeManager.player.checkUserInputMove()) {
                    mazeManager.player.handleUserInputMove();
                    mazeManager.executeTurn();
                }
                updateUI();
            }
            else {
                if(mazeManager.areAllCheeseFound == true)
                {
                    gameOverScreen(true);
                }
                else {
                    gameOverScreen(false);
                }
            }
            }
        });
    }

    private static void gameOverScreen(boolean won)
    {
        ImageIcon icon;
        if(won) {
            icon = new ImageIcon("src/images/cookie.png");
            GameBoard.playSound(new File("src/Sounds/win.WAV"));
            JOptionPane.showMessageDialog(null, winMessage(), "GameOver" , JOptionPane.INFORMATION_MESSAGE, icon);
        }
        else {
            icon = new ImageIcon("src/images/bug.png");
            GameBoard.playSound(new File("src/Sounds/loss.WAV"));
            JOptionPane.showMessageDialog(null, lossMessage(), "GameOver" , JOptionPane.INFORMATION_MESSAGE,icon);
        }
    }

    private static JLabel winMessage()
    {
        return new JLabel("YOU WON!");
    }
    private static JLabel lossMessage()
    {
        return new JLabel("THEY GOT YOU! *GAME OVER*");
    }
}
