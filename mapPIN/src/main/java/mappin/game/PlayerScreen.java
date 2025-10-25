package mappin.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * A class responsible for the main game JFrame graphics.
 * Made up of 2 panels.
 * The top shows the player's name, place to find, score, and time left for the turn.
 * The main panel shows the map of europe and asia.
 * This is the main screen of the game.
 * In order to quit the game click the X button.
 */
public class PlayerScreen extends JFrame {

    JPanel topPanel;
    JPanel topSubPanel;
    JPanel mainPanel;
    ImagePanel worldMap;
    CustomLabel timerLabel;
    CustomLabel scoreJLabel;
    CustomLabel name;
    CustomLabel place;   
    Coordinate placeCoordinate;

    /**
     * Consturctor - generates a window with the 2 described panels. Initalizes player name based on input and
     * the timer label. Creates the ImagePanel object that is the mouse listener world map.
     * @param player
     */
    PlayerScreen(Player player) {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("MapPIN");
        this.setSize(1000, 500);

        // top panel containing player's name, place to find, score, and timer label.
        topPanel = new JPanel();
        topPanel.setBackground(player.color);
        topPanel.setOpaque(true);
        topPanel.setPreferredSize(new Dimension(1000, 50));
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        topSubPanel = new JPanel();
        topSubPanel.setLayout(new GridLayout(1, 4, 5, 5));
        topSubPanel.setBackground(new Color(50, 150, 255));

        name = new CustomLabel(player.name, 20);
        place = new CustomLabel("", 20);
        scoreJLabel = new CustomLabel("", 20);
        timerLabel = new CustomLabel("00:" + Utility.turnLengthInMiliseconds / 1000 + ":00", 20);

        topSubPanel.add(name);
        topSubPanel.add(place);
        topSubPanel.add(scoreJLabel);
        topSubPanel.add(timerLabel);
        topPanel.add(topSubPanel);
        
        this.add(topPanel, BorderLayout.NORTH);

        // main panel containing the map object
        mainPanel = new JPanel();
        mainPanel.setBackground(player.color);
        mainPanel.setOpaque(true);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setPreferredSize(new Dimension(1000, 450));

        worldMap = new ImagePanel();
        mainPanel.add(worldMap);
        
        this.add(mainPanel);
    }

    /**
     * Receives the time passed since the start of the turn and updats the timer label with
     * the remaining time for the turn
     * @param currentTimePassed
     */
    public void updateTimerLabel(int currentTimePassed) {
        int currentTimeRemained = Utility.turnLengthInMiliseconds - currentTimePassed;
        int secondsLeft = currentTimeRemained / 1000;
        int milisecondsLeft = currentTimeRemained % 1000;
        this.timerLabel.setText("00:"+"0".repeat(Math.max(0,1 - secondsLeft / 10))+ secondsLeft + ":" + "0".repeat(Math.max(0, 1 - milisecondsLeft / 100))+ milisecondsLeft / 10);
    }

    /**
     * Receices a player's object and updates the JFrame background color and player's name to match.
     * Reinitializes the score label.
     * @param player
     */  
    public void updatePlayer(Player player) {
        this.name.setText(player.name);
        this.topPanel.setBackground(player.color);
        this.mainPanel.setBackground(player.color);
        this.scoreJLabel.setText("");
    }
}
