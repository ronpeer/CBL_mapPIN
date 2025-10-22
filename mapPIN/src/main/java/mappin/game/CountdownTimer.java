package mappin.game;

import java.awt.event.ActionListener;
import javax.swing.*;

public class CountdownTimer extends Timer {
    int currentTimePassed;
    int delay;
    boolean turnTimer; // true if it's a turn timer, false if for pause;

    CountdownTimer(int delay, ActionListener listener) {
        super(delay, listener);
    }

    public void updateValues() {
        this.currentTimePassed += this.delay;
    }

    public void resetTimer() {
        this.currentTimePassed = 0;
    }
}
