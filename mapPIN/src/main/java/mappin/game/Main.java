package mappin.game;

import java.awt.Color;

/**
 * WELCOME TO THE MAPPIN GAME.
 * in this game, 2 players compete to check their geographical knowlege of europe and asia.
 * players can enter their names, and choose from one of two modes, city mode and country mode.
 * in city mode, each player guesses the location of a given city on a map.
 * you have 30 seconds to do so.
 * once they click a location, it is pointed with a pin, and their score is calculated. 
 * their score is a mixed calculation of distance to the actual city, and time it took to guess.
 * each player has 3 guesses, and once the game is over, the scores of both players will be shown.
 * in country mode, each player guesses the location of a country on the given map.
 * you have 30 seconds to do so.
 * once they click a location, if you clicked on a location inside the country, 
 * youll be given a score based on how long the guess took. 
 * if you click on a location outside the country, no points for you.
 * after each player has 3 guesses, the ending screen showcases the sum of each players points.
 * 
 * Note:
 * the first click picks a location, and once one is chosen it cannot be changed.
 * SO BE CAREFULL!
 * 
 * Good luck, and have fun :)
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
        new EndScreen(player1, player2);
    }
}
