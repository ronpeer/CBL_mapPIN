package mappin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class StartScreen  extends JFrame {

    JPanel topPanel;
    JPanel midPanel;
    JPanel bottomPanel;
    JButton startButton;
    boolean buttonPressed;
    String player1Name;
    String player2Name;

    StartScreen() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Start");
        this.setSize(1000, 500);
        this.buttonPressed = false;

        this.topPanel = new JPanel();
        this.topPanel.setBackground(Color.WHITE);
        this.topPanel.setOpaque(true);
        JLabel gameName = new JLabel("Welcome to MapPIN", JLabel.CENTER);
        gameName.setFont(new Font("Ariel", Font.ITALIC, 30));
        this.topPanel.add(gameName);
        this.add(topPanel, BorderLayout.NORTH);
        
        this.midPanel = new JPanel();
        this.midPanel.setOpaque(true);
        JTextArea name1Entry = new JTextArea("Player1");
        name1Entry.setToolTipText("Enter Player name");
        JTextArea name2Entry = new JTextArea("Player2");
        name1Entry.setToolTipText("Enter Player name");
        this.midPanel.add(name1Entry);
        this.midPanel.add(name2Entry);
        this.add(midPanel);

        this.bottomPanel = new JPanel();
        ActionListener textInput = new ActionListener() {
        
            @Override
        public void actionPerformed(ActionEvent e) {
                player1Name = name1Entry.getText();
                player2Name = name2Entry.getText();
                buttonPressed = true;      
            }
        };

        this.startButton = new JButton("Start");
        this.startButton.addActionListener(textInput);
        this.bottomPanel.add(startButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public String[] getPlayerNames() {
        String[] playerNames = new String[2];
        playerNames[0] = player1Name;
        playerNames[1] = player2Name;
        this.setVisible(false);
        return playerNames; 
    }

    public boolean getButtonState() {
        return this.buttonPressed;
    }
}
