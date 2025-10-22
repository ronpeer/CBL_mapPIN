package mappin.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackendMap {

    BufferedImage image;

    public Color getPixelColor(int x, int y) {
        try {
            this.image = ImageIO.read(new File("Assets/maps/mapPIN_colors.png"));
        } catch (IOException e) { }
        return new Color(this.image.getRGB(x, y), true);
    }    
}
