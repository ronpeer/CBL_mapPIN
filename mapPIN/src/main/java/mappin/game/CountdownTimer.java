import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CountdownTimer {
    int currentTimePassed;
    int delay;

    ActionListener listener;
    Timer timer;

    CountdownTimer(int delay, ActionListener listener) {
        this.currentTimePassed = 0;

        this.listener = listener;
        this.delay = delay;
        timer = new Timer(this.delay, this.listener);
    }

    public void updateValues() {
        this.currentTimePassed += this.delay;
    }

    public void resetTimer () {
        this.currentTimePassed = 0;
    }
}
