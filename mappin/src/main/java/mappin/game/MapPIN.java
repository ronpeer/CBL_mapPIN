package mappin.game;

// IMPORTS HERE 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Text.
 */
public class MapPIN {

    public static void main(String[] args) {
        JFrame startScreen = new JFrame();
        startScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startScreen.setTitle("Start");
        startScreen.setSize(1000, 500);
    
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setOpaque(true);
        JLabel gameName = new JLabel("Welcome to MapPIN", JLabel.CENTER);
        gameName.setFont(new Font("Ariel", Font.ITALIC, 30));
        topPanel.add(gameName);
        startScreen.add(topPanel, BorderLayout.NORTH);
        
        JPanel midPanel = new JPanel();
        midPanel.setOpaque(true);
        JTextArea nameEntry = new JTextArea("Enter your name here");
        nameEntry.setToolTipText("Enter your name");
        midPanel.add(nameEntry);
        startScreen.add(midPanel);

        JPanel bottomPanel = new JPanel();
        ActionListener textInput = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player player = new Player(nameEntry.getText(), Color.BLUE);
                PlayerScreen game = new PlayerScreen(player);
                startScreen.setVisible(false);
                game.setVisible(true);        
            }
        };
        JButton startGame = new JButton("Start");
        startGame.addActionListener(textInput);
        bottomPanel.add(startGame);
        startScreen.add(bottomPanel, BorderLayout.SOUTH);

        startScreen.setVisible(true);

        
    }
}