package mappin.game;

import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * A class for a countdown timer. 
 */
public class CountdownTimer extends Timer {
    
    // keeps track of the time passed
    int currentTimePassed; 
    int delay; 

    /**
     * Constructor - intialize the Timer object and instance variables.
     * @param delay time frame between event handling (updating the currentTimePassed)
     * @param listener action listener object for timer.
     */
    CountdownTimer(int delay, ActionListener listener) {
        super(delay, listener);
        this.currentTimePassed = 0;
        this.delay = delay;
    }

    /**
     * Incrementing the time passed after each delay.
     */
    public void updateValues() {
        this.currentTimePassed += this.delay;
    }

    public void resetTimer() {
        this.currentTimePassed = 0;
    }
}
