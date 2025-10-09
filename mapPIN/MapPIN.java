// IMPORTS HERE 
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
 * Text.
 */
public class MapPIN {

    JFrame screen;
    JPanel mainMenu;
    JButton quit;
    JButton city;
    JButton country;


    /**
     * Loads the player creation and mode choosing process.
     * @return mode to play the game in.
     */
    String chooseMode() {
        screen = new JFrame();
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setTitle("MapPIN - Start");
        screen.setSize(600, 400);
        

        mainMenu = new JPanel();
        mainMenu.setOpaque(true);

        
        city = new JButton("CITY");
        mainMenu.add(city);
        country = new JButton("COUNTRY");
        mainMenu.add(country);
        quit = new JButton("FLAG");
        mainMenu.add(quit);

        screen.add(mainMenu);

        screen.setVisible(true);
        return "nothing yet";
    }

    public static void main(String[] args) {
        MapPIN game = new MapPIN();
        String mode = game.chooseMode();
        System.out.println(mode);
    }
}
