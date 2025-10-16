    // IMPORTS HERE 
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Text.
 */
public class MapPIN {

    public static void main(String[] args) {
        Player player = new Player("Lavi", Color.BLUE);
        PlayerScreen game = new PlayerScreen(player);
        game.setVisible(true);
    }
}
