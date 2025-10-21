    // IMPORTS HERE 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Text.
 */
public class MapPIN { 

    public static void main(String[] args) {
        Player player1 = new Player("Lavi", Color.RED);
        Player player2 = new Player("Ron", Color.BLUE);
        GameManager gameManager = new GameManager(player1, player2);
        gameManager.runGame();
    }
}
