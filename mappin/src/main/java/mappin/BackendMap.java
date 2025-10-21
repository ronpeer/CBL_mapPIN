package mappin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackendMap {

    static BufferedImage image;

    public static void backendMap() {
        try {
            image = ImageIO.read(new File("mapPIN_final_game_map.png"));
        } catch (IOException e) { }

    }

    public static Color getPixelColor(int x, int y) {
        return new Color(image.getRGB(x, y), true);
    }

    public static void main(String[] args) {
        backendMap();
        System.out.println(getPixelColor(2550, 1500).toString());
    }
    
}
