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
}
