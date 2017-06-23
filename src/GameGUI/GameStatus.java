package GameGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a Jlabel that displays the current number of objects needed to collect
 */
public class GameStatus extends JPanel {

    public GameStatus(int currentCheese, int totalCheese) {

        setLayout(new BorderLayout());
        JLabel label = new JLabel("You have: " + currentCheese + " of " + totalCheese);
        add(label);
    }
}
