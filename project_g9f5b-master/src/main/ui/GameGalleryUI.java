package ui;


import model.Event;
import model.EventLog;
import model.Game;
import model.GameGallery;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

// Game Gallery GUI
// This class references code from CPSC210/AlarmSystem
// Link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class GameGalleryUI extends JFrame {
    private static final String JSON_STORE = "./data/gameGallery.json";
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private static final int BARHEIGHT = 40;
    private GameGallery gameGallery;
    private JComboBox<String> printCombo;
    private JDesktopPane desktop;
    private JPanel gameCentrePanel;
    private GamesUI gamesPanel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Constructor
    // EFFECTS: sets up Game Center panel with buttons and Games panel that displays default game gallery.
    public GameGalleryUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        init();

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());

        gamesPanel = new GamesUI(gameGallery);
        gamesPanel.setBounds(2 * WIDTH / 3, 0, WIDTH / 3, BARHEIGHT * gameGallery.length());

        setContentPane(desktop);
        setTitle("My Game Gallery");
        setSize(WIDTH, HEIGHT);

        gamesPanel.setVisible(true);
        desktop.add(gamesPanel, BorderLayout.EAST);

        addGameCentre();

//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        printLog();
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initialize the game gallery and adds the three default games to the game gallery
    private void init() {
        gameGallery = new GameGallery("My Game Gallery");

        Game game1 = new Game("Overcooked 2", "Co-op", "E for Everyone", 9);
        Game game2 = new Game("Human Fall Flat", "Co-op", "4+", 8);
        Game game3 = new Game("WipEout", "Racing", "7+", 8);
        gameGallery.addGame(game1);
        gameGallery.addGame(game2);
        gameGallery.addGame(game3);
        //gameGallery.getEventLog().clear();
    }

    // MODIFIES: this
    // EFFECTS: add Game Centre panel with buttons to the desktop
    private void addGameCentre() {
        gameCentrePanel = new JPanel();
        gameCentrePanel.setLayout(new GridLayout(4, 2));
        gameCentrePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Game Center"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        gameCentrePanel.add(new JButton(new AddGameAction()));
        gameCentrePanel.add(new JLabel());
        gameCentrePanel.add(new JButton(new Print1CategoryAction()));
        gameCentrePanel.add(createPrintCombo());
        gameCentrePanel.add(new JButton(new SaveGalleryAction()));
        gameCentrePanel.add(new JLabel());
        gameCentrePanel.add(new JButton(new LoadGalleryAction()));
        gameCentrePanel.add(new JLabel());

        gameCentrePanel.setVisible(true);
        gameCentrePanel.setBounds(0, 0, 2 * WIDTH / 3, HEIGHT / 3);
        desktop.add(gameCentrePanel, BorderLayout.WEST);
    }


    // EFFECTS: create print options combo box with game categories to select from
    private JComboBox<String> createPrintCombo() {
        printCombo = new JComboBox<String>();
        List<String> listCategories = gameGallery.getListOfCategories();

        for (String next : listCategories) {
            printCombo.addItem(next);
        }
        return printCombo;
    }


    // MODIFIES: this
    // EFFECTS: centre main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }


    // Represents the action to be taken when the user wants to add a new game to the gallery.
    private class AddGameAction extends AbstractAction {

        // Constructor
        AddGameAction() {
            super("Add Game");
        }

        // MODIFIES: this
        // EFFECTS: creates popup windows and add a new game according to user's input
        @Override
        @SuppressWarnings("methodlength")
        public void actionPerformed(ActionEvent evt) {
            String gameName = getInput("Game name: ", "Enter Game name");

            if (gameName != null) {
                while (gameGallery.hasGame(gameName)) {
                    gameName = getInput("Please add another Game - Game name: ",
                            "Game " + gameName + " already in Game Gallery");
                }
            }

            String gameCategory = null;
            String gameAgeRating = null;
            String gameReviewRating = null;

            if (gameName != null) {
                gameCategory = getInput("Game category: ", "Enter Game Category");
                if (gameCategory != null) {
                    gameAgeRating = getInput("Game age-rating: ", "Enter Game Age-rating");
                    if (gameAgeRating != null) {
                        gameReviewRating = getInput("Game review-rating: ",
                                "Enter Game Review-rating (out of 10)");
                    }
                }
            }

            try {
                if (gameAgeRating != null) {
                    Game g = new Game(gameName, gameCategory, gameAgeRating, Integer.parseInt(gameReviewRating));
                    gameGallery.addGame(g);
                    updateGUI();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Review Rating must be integers from 1 - 10", "Failed: Add Game",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: update the GUI
    private void updateGUI() {
        desktop.remove(gamesPanel);
        gamesPanel = new GamesUI(gameGallery);
        gamesPanel.setBounds(2 * WIDTH / 3, 0, WIDTH / 3, BARHEIGHT * gameGallery.length());
        desktop.add(gamesPanel);
        desktop.remove(gameCentrePanel);
        addGameCentre();
        desktop.repaint();
    }


    // helper function to get input from the user
    // EFFECTS: creates a pop-up window with title and message and get user's input
    private String getInput(String message, String title) {
        String input = JOptionPane.showInputDialog(null,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE);

        return input;
    }


    // Represents the action to be taken when the user wants to save the current Game Gallery
    private class SaveGalleryAction extends AbstractAction {
        // Constructor
        SaveGalleryAction() {
            super("Save My Game Gallery");
        }

        // EFFECTS: save the current Game Gallery to file
        @Override
        public void actionPerformed(ActionEvent evt) {
            ScreenPrinter sp = new ScreenPrinter(GameGalleryUI.this, "");
            desktop.add(sp);
            sp.toFront();
            try {
                jsonWriter.open();
                jsonWriter.write(gameGallery);
                jsonWriter.close();
                sp.printMessage("Game Gallery saved successfully!");
            } catch (FileNotFoundException e) {
                sp.printMessage("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // Represents the action to be taken when the user wants to load the Game Gallery saved earlier
    private class LoadGalleryAction extends AbstractAction {
        // Constructor
        LoadGalleryAction() {
            super("Load My Game Gallery");
        }

        // MODIFIES: this
        // EFFECTS: load the Game Gallery saved before and update the GUI accordingly
        @Override
        public void actionPerformed(ActionEvent evt) {

            try {
                gameGallery = jsonReader.read();
                desktop.remove(gamesPanel);  // repetitive
                updateGUI();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read from file: " + JSON_STORE, "Failed: Load Game Gallery",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // Represents the action to be taken when the user wants to print the games that belong to a certain category
    private class Print1CategoryAction extends AbstractAction {
        // Constructor
        Print1CategoryAction() {
            super("View games in Category: ");
        }

        // EFFECTS: Creates a popup window displaying game names in a certain category
        @Override
        public void actionPerformed(ActionEvent evt) {
            String selected = (String) printCombo.getSelectedItem();
            ScreenPrinter sp;
            GameGallery ggCategory = gameGallery.getCategory(selected);
            sp = new ScreenPrinter(GameGalleryUI.this, "Category: " + selected);
            desktop.add(sp);
            sp.toFront();
            sp.printGames(ggCategory);
        }
    }

    // Represents action to be taken when user clicks desktop to switch focus. (Needed for key handling.)
    private class DesktopFocusAction extends MouseAdapter {
        // EFFECTS: switch focus to where the mouse clicked
        @Override
        public void mouseClicked(MouseEvent e) {
            GameGalleryUI.this.requestFocusInWindow();
        }
    }

    // display the splash screen and starts the application
    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen();

        try {
            // Make JWindow appear for 10 seconds before disappear
            Thread.sleep(3000);
            //new GameGalleryUI();
            splash.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new GameGalleryUI();
    }

    // EFFECTS: print the event log to console after exiting the GUI
    private void printLog() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                EventLog eventLog = gameGallery.getEventLog();
                for (Event next : eventLog) {
                    System.out.println(next.toString() + "\n");
                }
                System.exit(0);
            }
        });
    }
}
