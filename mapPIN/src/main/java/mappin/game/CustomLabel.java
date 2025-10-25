package mappin.game;

import java.awt.Font;
import javax.swing.JLabel;


/**
 * A class for a custom Jlabel with non default text and fontsize.
 */
public class CustomLabel extends JLabel {

    /**
     * Object constructor, creates a JLabel according to given parameters.
     * @param text String for label to showcase.
     * @param fontSize Size of text in the label.
     */
    CustomLabel(String text, int fontSize) {
        super(text, JLabel.CENTER);
        this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));
    }
}
