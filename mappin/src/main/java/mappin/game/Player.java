package mappin.game;

import java.awt.*;

public class Player {
    String name;
    Color color;
    private int[] currentGuess;
    private float score;

    Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.currentGuess = new int[2];
    }

    public void setGuess(int guessX, int guessY) {
        this.currentGuess[0] = guessX;
        this.currentGuess[1] = guessY;
    }
}
