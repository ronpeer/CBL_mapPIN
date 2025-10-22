package mappin.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndScreen extends JFrame {

    JPanel topPanel;
    JPanel midPanel;
    JPanel bottomPanel;
    
    public EndScreen(Player player1, Player player2) {
        super();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Start");
        this.setSize(1000, 500);


        // Top panel - welcome text
        this.topPanel = new JPanel();
        this.topPanel.setBackground(Color.WHITE);
        this.topPanel.setOpaque(true);
        JLabel gameName = new JLabel("Game Over", JLabel.CENTER);
        gameName.setFont(new Font("Ariel", Font.ITALIC, 30));
        this.topPanel.add(gameName);
        this.add(topPanel, BorderLayout.NORTH);
        
        // Middle panel - Player name entry
        this.midPanel = new JPanel();
        this.midPanel.setOpaque(true);
        CustomLabel player1Score = new CustomLabel(player1.name + ": " + player1.sumScores(), 15);
        CustomLabel player2Score = new CustomLabel(player2.name + ": " + player2.sumScores(), 15);

        this.midPanel.add(player1Score);
        this.midPanel.add(new JLabel());
        this.midPanel.add(player2Score);
        this.add(midPanel);

        this.setVisible(true);
    }
}
