// IMPORTS HERE 
import java.awt.*;
import javax.swing.*;


/**
 * Text.
 */
public class MapPIN {

    JFrame screen;

    /**
     * Loads the player creation and mode choosing process.
     * @return mode to play the game in.
     */
    String chooseMode() {
        screen = new JFrame();
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setTitle("MapPIN - Start");
        screen.setSize(600, 400);
        screen.setLayout(null);
        screen.setVisible(true);
        return "nothing yet";
    }

    public static void main(String[] args) {
        MapPIN game = new MapPIN();
        String mode = game.chooseMode();
        System.out.println(mode);
    }
}
