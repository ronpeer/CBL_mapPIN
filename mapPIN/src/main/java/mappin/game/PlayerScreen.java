import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerScreen extends JFrame{

    JPanel topPanel;
    JPanel topSubPanel;
    JPanel mainPanel;
    ImagePanel worldMap;
    CustomLabel timerLabel;
    CustomLabel scoreJLabel;
    CustomLabel name;
    CustomLabel place;   
    Player player;

    PlayerScreen(Player player) {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("MapPIN");
        this.setSize(1000, 500);
        this.player = player;

        topPanel = new JPanel();
        topPanel.setBackground(player.color);
        topPanel.setOpaque(true);
        topPanel.setPreferredSize(new Dimension(1000, 50));
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        topSubPanel = new JPanel();
        topSubPanel.setLayout(new GridLayout(1, 4, 5, 5));
        topSubPanel.setBackground(new Color(50, 150, 255));

        this.name = new CustomLabel(player.name, 20);

        place = new CustomLabel("AFRICA", 20);

        scoreJLabel = new CustomLabel("", 20);

        timerLabel = new CustomLabel("00:" + Utility.turnLengthInMiliseconds / 1000 + ":00", 20);

        topSubPanel.add(name);
        topSubPanel.add(place);
        topSubPanel.add(scoreJLabel);
        topSubPanel.add(timerLabel);
        topPanel.add(topSubPanel);
        
        this.add(topPanel, BorderLayout.NORTH);

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

    public void updateTimerLabel(int currentTimePassed) {
        int currentTimeRemained = Utility.turnLengthInMiliseconds - currentTimePassed;
        int secondsLeft = currentTimeRemained / 1000;
        int milisecondsLeft = currentTimeRemained % 1000;
        this.timerLabel.setText("00:"+"0".repeat(Math.max(0,1 - secondsLeft / 10))+ secondsLeft + ":" + "0".repeat(Math.max(0, 1 - milisecondsLeft / 100))+ milisecondsLeft / 10);
    }

    public void changePlace(String place) {
        this.place.setText(place);
    }

    public void addScore() {
        this.scoreJLabel.setText("1000");
        super.repaint();
    }

    public void resetScore() {
        this.scoreJLabel.setText("");
        super.repaint();
    }

    public void resetPlayer() {
        this.name.setText(this.player.name);
        this.topPanel.setBackground(player.color);
        this.mainPanel.setBackground(player.color);

        super.repaint();
    }
}
