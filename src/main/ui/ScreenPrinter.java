package ui;

import model.*;

import java.awt.Component;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Game Gallery GUI
// This class references code from CPSC210/AlarmSystem
// Link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

// Represents a screen printer for printing game names to the screen or other messages
public class ScreenPrinter extends JInternalFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private JTextArea logArea;

    // Constructor
    // EFFECTS: sets up window in which the names or message will be printed on screen
    public ScreenPrinter(Component parent, String title) {
        super(title, false, true, false, false);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane);
        setSize(WIDTH, HEIGHT);
        setPosition(parent);
        setVisible(true);
    }

    // EFFECTS: print game names in the gameGallery to the screen
    public void printGames(GameGallery gameGallery) {
        for (Game next : gameGallery.getGames()) {
            logArea.setText(logArea.getText() + next.getName() + "\n\n");
        }
        repaint();
    }

    // EFFECTS: print message to the screen
    public void printMessage(String message) {
        logArea.setText(logArea.getText() + message + "\n\n");
        repaint();
    }

    // EFFECTS: Sets the position of window in which log will be printed relative to parent
    private void setPosition(Component parent) {
        setLocation(parent.getWidth() - getWidth() - 20,
                parent.getHeight() - getHeight() - 20);
    }
}
