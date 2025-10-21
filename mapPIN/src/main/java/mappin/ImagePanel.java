package mappin;

import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel implements MouseListener{

    BufferedImage image;
    boolean pinPlaced;
    float relativePinX;
    float relativePinY;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), this);
        if (pinPlaced) {
            int pinWidth = (int) (this.getWidth() * 0.02);
            int pinHeight = (int) (this.getHeight() * 0.06);
            int pinX = (int) (this.getWidth() * this.relativePinX);
            int pinY = (int) (this.getHeight() * this.relativePinY);
            int[] pinXPoints = new int[3];
            pinXPoints[0] = pinX;
            pinXPoints[1] = pinX - pinWidth;
            pinXPoints[2] = pinX + pinWidth;
            int[] pinYPoints = new int[3];
            pinYPoints[0] = pinY;
            pinYPoints[1] = pinY - pinHeight;
            pinYPoints[2] = pinY - pinHeight;
            g.drawPolygon(pinXPoints, pinYPoints, 3);

        }
    }

    public void placePin(int locationX, int locationY) {
        this.pinPlaced = true;
        this.relativePinX = (float) locationX / this.getWidth();
        this.relativePinY = (float) locationY / this.getHeight();
        System.out.println(locationX + " " + locationY + " " + this.relativePinX + " " + this.relativePinY);
    }

    public ImagePanel() {
        try {
            File gamemap = new File("mapPIN_final_game_map.png");
            this.image = ImageIO.read(gamemap);
            this.pinPlaced = false;
            this.addMouseListener(this);
        } catch (IOException e) { e.printStackTrace();}
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("dgr");
     }

    public void mouseEntered(MouseEvent e) {
        System.out.println("glhf");
    }

    public void mouseExited(MouseEvent e) {
        System.out.println("the location is on the map, not outside");
    }

    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) {
        this.placePin(e.getX(), e.getY());  
        this.repaint();      
    }
}