package mappin;

import java.awt.Color;

/**
 * Main code to start the game.
 * initiates start screen and waits in half a second intervals until names are given.
 * Once names are given starts the game manager.
 */
public class MapPIN { 

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
        GameManager gameManager = new GameManager(player1, player2);
        gameManager.runGame();
    }
}
