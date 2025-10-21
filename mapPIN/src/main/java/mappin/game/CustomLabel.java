import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {

    CustomLabel(String text, int fontSize) {
        super(text, JLabel.CENTER);
        this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));
    }
}
