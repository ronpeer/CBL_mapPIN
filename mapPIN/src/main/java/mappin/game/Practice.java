package mappin.game;

import javax.swing.*;
import java.awt.*;

public class Practice {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Practice");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane layered = new JLayeredPane();
        layered.setSize(frame.getWidth() / 2, 500);
        layered.setBackground(Color.RED);
        layered.setOpaque(true);
        layered.setLayout(new BorderLayout());

        System.out.println(layered.getWidth());

        JLabel label = new JLabel("Shalom");
        label.setPreferredSize(new Dimension(500, 100));
        label.setBackground(Color.BLUE);
        label.setOpaque(true);

        layered.add(label, BorderLayout.LINE_START);
        frame.add(layered);


        frame.setVisible(true);

    }
}
