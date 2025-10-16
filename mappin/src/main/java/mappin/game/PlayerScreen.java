package mappin.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerScreen extends JFrame implements MouseListener{

    JPanel topPanel;
    JPanel topSubPanel;
    JPanel mainPanel;
    ImagePanel worldMap;
    JButton quit;
    JLabel timer;
    JLabel name;
    JLabel place;
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

        quit = new JButton("Quit");

        name = new JLabel(player.name, JLabel.CENTER);

        place = new JLabel("AFRICA", JLabel.CENTER);

        timer = new JLabel("00:15:00", JLabel.CENTER);

        topSubPanel.add(name);
        topSubPanel.add(place);
        topSubPanel.add(timer);
        topSubPanel.add(quit);
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
        mainPanel.addMouseListener(this);
        
        this.add(mainPanel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.player.setGuess(e.getX(), e.getY());
        this.worldMap.placePin(e.getX(), e.getY());
        this.worldMap.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("glhf");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("the location is on the map, not outside");
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.player.setGuess(e.getX(), e.getY());  
        this.worldMap.repaint();      
    }

    
}
