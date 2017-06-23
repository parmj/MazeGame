package GameGUI;

import Model.Maze;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Takes in a two dimensional array and displays the array based on the values
 * It will display images based on the values in the array
 */
public class GameBoard extends JPanel {

    private int[][] maze;

    public GameBoard(int[][] maze) {

        this.maze = maze;

        int NUMBER_ROWS = Maze.MAXROW ;
        int NUMBER_COLS = Maze.MAXCOL ;

        setLayout(new GridLayout(NUMBER_ROWS, NUMBER_COLS));
        for (int row = 0; row < NUMBER_ROWS; row++) {
            for (int col = 0; col < NUMBER_COLS; col++) {

                ImageIcon image = getImage(row,col);
                String text = row + ", " + col;
                JLabel label = new JLabel(getScaleImageIcon(image,35,35));
                add(label);
            }
        }

    }

    private ImageIcon getImage(int row, int col)
    {
        if(maze[col][row] < 0)
             return new ImageIcon("src/images/s.png");
        else if(maze[col][row] == 1 || maze[col][row] == 5)
            return new ImageIcon("src/images/wall.png");
        else if(maze[col][row] == 2)
            return new ImageIcon("src/images/emptyspace.png");
        else if(maze[col][row] == 0)
            return new ImageIcon("src/images/player.png");
        else if(maze[col][row] == 3)
            return new ImageIcon("src/images/bug.png");
        else if(maze[col][row] == 4)
            return new ImageIcon("src/images/cookie.png");

        return null;
    }

    static public void playSound(File sound) {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    static public ImageIcon getScaleImageIcon(ImageIcon icon, int width, int height) {
        return new ImageIcon(getScaledImage(icon.getImage(), width, height));
    }
    static private Image getScaledImage(Image srcImg, int width, int height){
        BufferedImage resizedImg =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();
        return resizedImg;
    }
}
