package mappin;

import java.awt.*;

/**
 * a class to represent a player. 
 * contains name of the player
 * and color to use during the game.
 */
public class Player {
    String name;
    Color color;

    /**
     * object constructor for player.
     * @param name name of player
     * @param color color to use to represent player in game.
     */
    Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }
}
