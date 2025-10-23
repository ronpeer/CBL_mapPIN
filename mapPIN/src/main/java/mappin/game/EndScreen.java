package mappin.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class to represent the ending screen of the game
 * made up of 2 panels, 
 * the top shows Game over text.
 * the bottom panel shows each players name and scores.
 * this is the final screen of the game.
 * from here no new code will be executed, and in order to close the screen click the X button.
 */
public class EndScreen extends JFrame {

    JPanel topPanel;
    JPanel bottomPanel;
    
    /**
     * End screen constructor.
     * it sets the values for the end screen Jframe,
     * then sets the values for both panels as described above.
     * @param player1 player object, containing player name and list of scores.
     * @param player2 player object, containing player name and list of scores.
     */
    public EndScreen(Player player1, Player player2) {
        super();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Final Scores");
        this.setSize(1000, 500);


        // Top panel - Final score text
        this.topPanel = new JPanel();
        this.topPanel.setBackground(Color.WHITE);
        this.topPanel.setOpaque(true);
        JLabel gameName = new JLabel("Game Over", JLabel.CENTER);
        gameName.setFont(new Font("Ariel", Font.ITALIC, 30));
        this.topPanel.add(gameName);
        this.add(topPanel, BorderLayout.NORTH);
        
        // Middle panel - Player Score showcase
        this.bottomPanel = new JPanel();
        this.bottomPanel.setOpaque(true);
        CustomLabel player1Score = new CustomLabel(player1.name + ": " + player1.sumScores(), 15);
        CustomLabel player2Score = new CustomLabel(player2.name + ": " + player2.sumScores(), 15);

        this.bottomPanel.add(player1Score);
        this.bottomPanel.add(new JLabel());
        this.bottomPanel.add(player2Score);
        this.add(bottomPanel);

        this.setVisible(true);
    }
}
