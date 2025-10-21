package mappin;

import java.awt.Font;
import javax.swing.JLabel;

/**
 * Custom label class to make a chosen sized label, which is centered.
 */
public class CustomLabel extends JLabel {

    /**
     * constructor for the custom label.
     * @param text text for label.
     * @param fontSize size of font in label.
     */
    CustomLabel(String text, int fontSize) {
        super(text, JLabel.CENTER);
        this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));
    }
}
