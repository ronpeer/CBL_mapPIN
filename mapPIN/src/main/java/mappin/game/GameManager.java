package mappin.game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;

import org.w3c.dom.events.MouseEvent;

public class GameManager implements ActionListener {
    Player player1;
    Player player2;
    PlayerScreen currentGame;
    boolean player1Turn = true;
    CountdownTimer timer;
    int turnCounter;

    GameManager(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentGame = new PlayerScreen(player1);
        this.timer = new CountdownTimer(10, this);
        this.turnCounter = 0;
    }

    public void newTurn() {
       if (player1Turn) {
            this.currentGame.player = player2;
            this.currentGame.updatePlayer();
            turnCounter++;
       } else {
            this.currentGame.player = player1;
            this.currentGame.updatePlayer();
       }
       this.currentGame.worldMap.pinPlaced = false;
       player1Turn = !player1Turn;        
    }

    public void runGame() {
        this.currentGame.setVisible(true);
        this.timer.turnTimer = true;
        this.timer.start();
    }
        
    public void actionPerformed(ActionEvent e) {
        if (this.timer.turnTimer) {
            if (this.timer.currentTimePassed < Utility.turnLengthInMiliseconds &&! this.currentGame.worldMap.pinPlaced) {
                this.timer.currentTimePassed += 10;
                this.currentGame.updateTimerLabel(this.timer.currentTimePassed);
                this.currentGame.worldMap.currentTimePassed = this.timer.currentTimePassed;
            }
            else {
                timer.stop();
                this.currentGame.calculateScore();
                timer.currentTimePassed = 0;
                timer.turnTimer = false;
                timer.start();
            }
        } else {
            if (this.timer.currentTimePassed < Utility.pauseLengthInMiliseconds) {
                this.timer.currentTimePassed += 10;
            }
            else {
                this.timer.stop();
                this.timer.currentTimePassed = 0;
                if (turnCounter < Utility.turnsPerPlayer) {
                    newTurn();
                    timer.turnTimer = true;
                    timer.start();
                }
            }
        }

    }
}
