package ui;

import model.GameGallery;

import java.awt.*;

import javax.swing.*;

// a panel that display all games in Game Gallery GUI
public class GamesUI extends JPanel {

    //Constructor
    public GamesUI(GameGallery gameGallery) {
        setLayout(new GridLayout(gameGallery.length(), 1));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Games"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        for (int i = 0; i < gameGallery.length(); i++) {
            add(new JButton(gameGallery.getGameFromIndex(i).getName()));
        }
    }
}
