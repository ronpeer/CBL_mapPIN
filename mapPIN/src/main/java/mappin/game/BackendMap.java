package mappin.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A class for the backend map. 
 * It reads the map image and returns the color of the requested pixel.
 * This backend map is used for measurements of real distance between points on the game map.
 * this map is accurate to real world distances, and each country is colored differently.
 * it will be used in city mode for awarding points based on distance,
 * and country mode to spot if a guess is correct based on the color corresponding to the 
 * clicked location.
 */
public class BackendMap {

    BufferedImage image;

    /**
     * method to return the color of a country in the backend map, based on coordinates.
     * @param x x coordinate in the backend map
     * @param y y coordinate in the backend map
     * @return color of the pixel at the point of guess (represented by x and y)
     */
    public Color getPixelColor(int x, int y) {
        try {
            this.image = ImageIO.read(new File("Assets/maps/mapPIN_colors.png"));
        } catch (IOException e) { 
            System.out.println("failed loading picture!");
        }
        return new Color(this.image.getRGB(x, y), true);
    }    
}
