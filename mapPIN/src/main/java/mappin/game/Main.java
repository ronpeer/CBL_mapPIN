package mappin.game;

// IMPORTS HERE 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Text.
 */
public class Main { 

    public static void main(String[] args) {
        // Start screen - player name input
        StartScreen start = new StartScreen();
        while (!start.buttonPressed) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) { 
                System.out.println("Sleep inturrupted, will continue to gameplay");
            }
        }

        // Start game - create players and run game manager.
        String[] playerNames = start.getPlayerNames();
        Player player1 = new Player(playerNames[0], Color.RED);
        Player player2 = new Player(playerNames[1], Color.BLUE);
        GameManager gameManager = new GameManager(player1, player2, start.cityMode);
        gameManager.runGame();
        while (!gameManager.gameOver) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) { 
                System.out.println("Sleep inturrupted, will continue to gameplay");
            }
        }
        gameManager.currentGame.setVisible(false);
        EndScreen endScreen = new EndScreen(player1, player2);
    }
}
