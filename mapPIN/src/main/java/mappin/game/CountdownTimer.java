package mappin.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A class for a countdown timer. 
 */
public class CountdownTimer extends Timer {
    
    int currentTimePassed; // keeps track of the time passed
    int delay; // time frame between event handling (updating the currentTimePassed)

    /**
     * Constructor - intialize the Timer object and instance variables
     * @param delay
     * @param listener
     */
    CountdownTimer(int delay, ActionListener listener) {
        super(delay, listener);
        this.currentTimePassed = 0;
        this.delay = delay;
    }

    /**
     * Incrementing the time passed after each delay
     */
    public void updateValues() {
        this.currentTimePassed += this.delay;
    }

    public void resetTimer () {
        this.currentTimePassed = 0;
    }
}
