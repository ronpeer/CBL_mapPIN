package mappin.game;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A class representing a player of the game.
 * Each player has a name, color representing them, and an arraylist of scores.
 */
public class Player {
    String name;
    Color color;
    ArrayList<Integer> scores;

    /**
     * object constructor.
     * if no names are chosen in the start screen, the defaults are Player1 and Player2.
     * defualt colors are red for player1 and blue for player2.
     * @param name name of player.
     * @param color color to use on screen.
     */
    Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.scores = new ArrayList<Integer>();
    }

    /**
     * return the sum of all scores a user has collected in the game.
     * @return an integer representing the sum of all scores in the game for this player.
     */
    public int sumScores() {
        int scoreSum = 0;
        for (int i = 0; i < this.scores.size(); i++) {
            scoreSum += this.scores.get(i);
        }
        return scoreSum;
    }
}
