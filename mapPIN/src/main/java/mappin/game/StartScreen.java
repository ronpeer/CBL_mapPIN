package mappin.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 * a class for the starting screen of theg game. 
 * the user will input the player names into this screen.
 * after which they will press the start button to start the game itself.
 */
public class StartScreen  extends JFrame {

    JPanel topPanel;
    JPanel midPanel;
    JPanel bottomPanel;
    JButton startButton;
    boolean buttonPressed;
    JRadioButton cityRadio;
    JRadioButton countryRadio;
    boolean cityMode;
    String player1Name;
    String player2Name;

    /**
     * class constructor.
     * it generates the jframe values and the included panels. 
     * the top panel contains a welcome message.
     * the middle panel contains the text fields for name input.
     * the bottom panel contains a button which once pressed will 
     * capture the given player names and set the buttonPressed boolean to true.
     */
    StartScreen() {
        // General frame construction
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Start");
        this.setSize(1000, 500);
        this.buttonPressed = false;
        this.cityMode = true;

        // Top panel - welcome text
        this.topPanel = new JPanel();
        this.topPanel.setBackground(Color.WHITE);
        this.topPanel.setOpaque(true);
        CustomLabel gameName = new CustomLabel("Welcome to MapPIN", 30);
        this.topPanel.add(gameName);
        this.add(topPanel, BorderLayout.NORTH);
        
        // Middle panel - Player name entry
        this.midPanel = new JPanel();
        this.midPanel.setOpaque(true);
        JTextArea name1Entry = new JTextArea("Player1");
        name1Entry.setToolTipText("Enter Player name");
        JTextArea name2Entry = new JTextArea("Player2");
        name1Entry.setToolTipText("Enter Player name");
        this.cityRadio = new JRadioButton();
        this.cityRadio.setLabel("City Mode");
        this.cityRadio.setSelected(true);
        this.countryRadio = new JRadioButton();
        this.countryRadio.setLabel("Country Mode");
        ButtonGroup modeSelection = new ButtonGroup();
        modeSelection.add(cityRadio);
        modeSelection.add(countryRadio);

        this.midPanel.add(name1Entry);
        this.midPanel.add(name2Entry);
        this.midPanel.add(cityRadio);
        this.midPanel.add(countryRadio);
        this.add(midPanel);

        // Bottom panel - Start game button
        this.bottomPanel = new JPanel();
        ActionListener textInput = new ActionListener() {
        
            @Override
        public void actionPerformed(ActionEvent e) {
                player1Name = name1Entry.getText();
                player2Name = name2Entry.getText();
                buttonPressed = true;
                if (countryRadio.isSelected()) {
                    cityMode = false;
                }
            }
        };

        this.startButton = new JButton("Start");
        this.startButton.addActionListener(textInput);
        this.bottomPanel.add(startButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    /**
     * retrieve names of players.
     * when this is pressed the game should start,
     * which means this screen should no longer be visable.
     * @return an array of all player names.
     */
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
