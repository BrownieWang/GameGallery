package ui;

import javax.swing.*;
import java.awt.*;

// A class for displaying splash screen before the GUI
// It references code from https://www.tutorialspoint.com/how-can-we-implement-a-splash-screen-using-jwindow-in-java

public class SplashScreen extends JWindow {
    Image splashScreen;
    ImageIcon imageIcon;

    // Constructor
    // EFFECTS: Creates the Splash window with the correct size and location
    public SplashScreen() {
        splashScreen = Toolkit.getDefaultToolkit().getImage("./data/SplashScreen2.png");
        // Create ImageIcon from Image
        imageIcon = new ImageIcon(splashScreen);  // .getScaledInstance(700, 500, 1)
        // Set JWindow size from image size
        setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        // System.out.println("Width" + imageIcon.getIconWidth() + "Height" + imageIcon.getIconHeight());
        // Get current screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getSize().width) / 2;
        int y = (screenSize.height - getSize().height) / 2;
        setLocation(x, y);
        setVisible(true);
    }

    // EFFECTS: Paint image onto JWindow
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(splashScreen, 0, 0, this);
    }
}
