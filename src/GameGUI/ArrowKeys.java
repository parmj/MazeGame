package GameGUI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


/**
 * THIS CODE WAS PROVIDED BY BRIAN FRASER
 * It adds the feature of clicking an arrow key to a JFrame with the creation of a JPanel
 */
@SuppressWarnings("serial")
public class ArrowKeys extends JPanel {
    // Names of arrow key actions.
    private static final String[] KEYS = {"UP", "DOWN", "LEFT", "RIGHT"};

    JLabel label = new JLabel("Press an arrow key to see it displayed here!\n");

    public ArrowKeys() {
        add(label);
        add(makeNewButton());
        registerKeyPresses();

    }
    private Component makeNewButton() {
        JButton btn = new JButton("Reset");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Reset... (does nothing)!");
            }
        });

        // Ensure button does not grab focus and eat up arrow keys!
        // (Try commenting this out, clicking the button, and then
        //  using the arrow keys!)
        btn.setFocusable(false);
        return btn;
    }
    public void registerKeyPresses() {
        for (int i = 0; i < KEYS.length; i++) {
            String key = KEYS[i];
            this.getInputMap().put(KeyStroke.getKeyStroke(key), key);
            this.getActionMap().put(key, getKeyListener(key));
        }
    }

    public AbstractAction getKeyListener(final String move) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                // Do your work here...
                if(move.equals("UP"))
                UI.UserUI.setUserInput('w');

                if(move.equals("DOWN"))
                    UI.UserUI.setUserInput('s');

                if(move.equals("LEFT"))
                    UI.UserUI.setUserInput('a');

                if(move.equals("RIGHT"))
                    UI.UserUI.setUserInput('d');

                notifyObservers();
                label.setText("You pressed " + move);
            }
        };
    }

    private static ArrayList<PlayerMoveObserver> observers = new ArrayList<PlayerMoveObserver>();

    public static void addObserver(PlayerMoveObserver observer) {
        observers.add(observer);
    }
    private void notifyObservers() {
        for (PlayerMoveObserver observer : observers) {
            observer.stateChanged();
        }
    }
}