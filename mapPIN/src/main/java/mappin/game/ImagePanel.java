package mappin.game;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * A class responsible for the game map JPanel of the window. It presents the map, tracks the mouse 
 * and updates the graphics when a pin is placed.
 */

public class ImagePanel extends JPanel implements MouseListener {

    BufferedImage image;
    boolean pinPlaced;
    float relativePinX;
    float relativePinY;
    int currentTimePassed; // current time passed in the turn - used to track time (updated from outside of object)
    int timeOfGuess;
    
    /**
     * Constructor - reads the map image file from the folder, initializes instance variables and implements the 
     * mouse listening.
     */
    public ImagePanel() {
        try {
            this.image = ImageIO.read(new File("Assets/maps/mapPIN_final_game_map.png"));
        } catch (IOException e) { }
        this.pinPlaced = false;
        this.addMouseListener(this);
        this.currentTimePassed = 0;
    }

    /**
     * Overriding the paintComponent method to paint the map image, as well as the pin triangle
     * @param g graphics object
     */
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

            // paints a triangle on the screen where the mouse was pinned
            g.drawPolygon(pinXPoints, pinYPoints, 3);
        }
    }

    /**
     * Receives the xy coordinates of the pin (mouse click) and updates the relative location to the image.
     * Updates pinPlaced boolean to true.
     * @param locationX
     * @param locationY
     */
    public void placePin(int locationX, int locationY) {
        this.pinPlaced = true;
        this.relativePinX = (float) locationX / this.getWidth();
        this.relativePinY = (float) locationY / this.getHeight();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) { }

    /**
     * Implements MouseListener - when a mouse is released (long/short click or drag - final location matters) 
     * and a pin hasn't been placed yet, calls to paint the pin on the the mouse's location. Updates the timeOfGuess
     * to be the time where the mouse was released.
     */
    public void mouseReleased(MouseEvent e) {
        if (!pinPlaced) {
            this.placePin(e.getX(), e.getY());  
            this.repaint();      
            this.timeOfGuess = currentTimePassed;
        }
    }
}