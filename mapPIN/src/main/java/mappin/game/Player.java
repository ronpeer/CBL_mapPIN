package mappin.game;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    String name;
    Color color;
    ArrayList<Integer> scores;

    Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.scores = new ArrayList();
    }

    public int sumScores() {
        int sum = 0;
        for (int i = 0; i < this.scores.size(); i++) {
            sum += this.scores.get(i);
        }
        return sum;
    }
}
