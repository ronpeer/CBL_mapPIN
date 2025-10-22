package mappin.game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;

import org.w3c.dom.events.MouseEvent;

public class GameManager implements ActionListener {
    Player player1;
    Player player2;
    boolean cityMode;
    PlayerScreen currentGame;
    DBmanager mappinDB;
    Random rand;
    boolean player1Turn = true;
    boolean gameOver = false;
    CountdownTimer timer;
    int turnCounter;
    int currentPlaceID;
    String currentCityName;
    String currentCountryName;
    Color currentCountryColor;

    GameManager(Player player1, Player player2, boolean cityMode) {
        this.player1 = player1;
        this.player2 = player2;
        this.cityMode = cityMode;
        this.currentGame = new PlayerScreen(player1);
        this.mappinDB = new DBmanager("jdbc:h2:./mappin;AUTO_SERVER=TRUE");
        this.rand = new Random();
        this.timer = new CountdownTimer(10, this);
        this.turnCounter = 0;
    }

    public void newTurn() {
        this.currentPlaceID = this.rand.nextInt(1, 93);
        this.currentCityName = this.mappinDB.getCityById(this.currentPlaceID);
        this.currentCountryName = this.mappinDB.getCountryById(this.currentPlaceID);
        this.currentCountryColor = this.mappinDB.getCountryColor(currentCountryName);


        if (this.cityMode) {
            this.currentGame.place.setText(this.currentCityName);
        } else {
            this.currentGame.place.setText(this.currentCountryName);
        }
        this.currentGame.placeCoordinate = this.mappinDB.getCityLocation(currentCityName);

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
        this.currentPlaceID = this.rand.nextInt(1, 93);
        this.currentCityName = this.mappinDB.getCityById(this.currentPlaceID);
        this.currentCountryName = this.mappinDB.getCountryById(this.currentPlaceID);
        this.currentCountryColor = this.mappinDB.getCountryColor(currentCountryName);

        if (this.cityMode) {
            this.currentGame.place.setText(this.currentCityName);
        } else {
            this.currentGame.place.setText(this.currentCountryName);
        }
        this.currentGame.placeCoordinate = this.mappinDB.getCityLocation(currentCityName);

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
                if (cityMode) {
                    this.currentGame.calculateScoreCity();
                } else {
                    this.currentGame.calculateScoreCountry(this.currentCountryColor);
                }
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
                } else {
                    this.gameOver = true;
                    System.out.println("game over");
                }
            }
        }

    }
}
