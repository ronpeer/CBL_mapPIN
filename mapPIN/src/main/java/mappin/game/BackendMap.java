package mappin.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A class for the backend map. It has a method that reads the map image and returns the color of the requested pixel.
 */
public class BackendMap {

    BufferedImage image;

    public Color getPixelColor(int x, int y) {
        // Receives xy coordinates in the backend map and returns the color of the pixel of that point.
        try {
            this.image = ImageIO.read(new File("Assets/maps/mapPIN_colors.png"));
        } catch (IOException e) { }
        return new Color(this.image.getRGB(x,y), true);
    }    
}
