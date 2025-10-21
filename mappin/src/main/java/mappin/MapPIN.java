package mappin;

// IMPORTS HERE 
import java.awt.*;


/**
 * Text.
 */
public class MapPIN { 

    public static void main(String[] args) {
        StartScreen start = new StartScreen();
        while (!start.buttonPressed) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) { 
                System.out.println("Sleep inturrupted, will continue to gameplay");
            }
            
        }
        String[] playerNames = start.getPlayerNames();
        Player player1 = new Player(playerNames[0], Color.RED);
        Player player2 = new Player(playerNames[1], Color.BLUE);
        GameManager gameManager = new GameManager(player1, player2);
        gameManager.runGame();
    }
}
