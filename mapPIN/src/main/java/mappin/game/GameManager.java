package mappin.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


/**
 * A class for managing the flow of the game during the map screen. 
 * It handles the rounds of the game (a round is 2 turns), 
 * randomizing each player's target location at the start of a turn, 
 * running the logic of aturn as well as controling a pause between turns to show the score.
 * It also has method to calculate the score based on the mode, guess and time.
 */

public class GameManager {
    Player player1; 
    Player player2; 
    boolean cityMode; // true if playing city mode, false if country mode
    PlayerScreen currentGame; // game map JFrame
    DBmanager mappinDB; // db manager handle queries and return location values
    Random rand; // randomize id for a random location
    boolean player1Turn; // true if it's player1's turn, false if it's player2's turn
    boolean gameOver; // true if played all rounds, false if not
    CountdownTimer turnTimer; 
    ActionListener turnTimerListener; // listener to turnTimer
    CountdownTimer pauseTimer; 
    ActionListener pauseTimerListener; // listener to pauseTimer
    int turnCounter; // counts the rounds passed
    int currentPlaceID; // place id of the current turn
    String currentCityName; // city name of the current turn
    String currentCountryName; // country name of the current turn
    Color currentCountryColor; // color of the country of the current turn

    /**
     * Constructor - Initializes the map JFrame.
     * at initialization contains first player's info, helper objects and instance variables.
     * @param player1 object of starting player.
     * @param player2 object of second player.
     * @param cityMode is city mode chosen or not.
     */
    GameManager(Player player1, Player player2, boolean cityMode) {
        this.player1 = player1;
        this.player2 = player2;
        this.cityMode = cityMode;
        this.currentGame = new PlayerScreen(player1);

        this.mappinDB = new DBmanager("jdbc:h2:./mappin;AUTO_SERVER=TRUE");
        this.rand = new Random();

        this.turnTimerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnTimerActionPerformed();
            }
        };

        this.turnTimer = new CountdownTimer(10, turnTimerListener);

        this.pauseTimerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseTimerActionPerformed();
            }
        };

        this.pauseTimer = new CountdownTimer(10, pauseTimerListener);

        this.turnCounter = 0;
        this.player1Turn = false;
        this.gameOver = false;
    }

    /**
     * Randomizes new values for the next turn. 
     * reinitialized values and switches the player in the JFrame object.
     */
    public void newTurn() {
        // randomizing place
        this.currentPlaceID = this.rand.nextInt(1, 93);
        this.currentCityName = this.mappinDB.getCityById(this.currentPlaceID);
        this.currentCountryName = this.mappinDB.getCountryById(this.currentPlaceID);
        this.currentCountryColor = this.mappinDB.getCountryColor(currentCountryName);

        // updating the place's text in the JFrame according to game mode
        if (this.cityMode) {
            this.currentGame.place.setText(this.currentCityName);
        } else {
            this.currentGame.place.setText(this.currentCountryName);
        }
        // updating current coordinate
        this.currentGame.placeCoordinate = this.mappinDB.getCityLocation(currentCityName);

        // switches players and updates the JFrame, increases the round counter every round
        if (player1Turn) {
            this.currentGame.updatePlayer(this.player2);
            turnCounter++;
        } else {
            this.currentGame.updatePlayer(this.player1);
        }

        // reinitializes pinPlaced to be false at the start of the next turn
        this.currentGame.worldMap.pinPlaced = false;
        // switches turns
        player1Turn = !player1Turn;        
    }

    /**
     * Rendering the game JFrame visible with new turn's values and starting the timer of the game.
     */
    public void runGame() {
        newTurn();

        this.currentGame.setVisible(true);
        this.turnTimer.start();
    }

    /**
     * Runs the turn timer until the turn's length runs out or a pin is placed. 
     * Updating the screen with the new time passed every time. 
     * When the turn's over, calls the score calculating method, 
     * resets the timer and starts the pause timer.
     */
    public void turnTimerActionPerformed() {
        boolean isTurnNotOver = this.turnTimer.currentTimePassed < Utility.turnLengthInMiliseconds; 
        if (isTurnNotOver && !this.currentGame.worldMap.pinPlaced) {
            this.turnTimer.updateValues();
            // updating the screen graphics and time variable
            this.currentGame.updateTimerLabel(this.turnTimer.currentTimePassed);
            this.currentGame.worldMap.currentTimePassed = this.turnTimer.currentTimePassed;
        } else {
            // time ran out or pin placed
            turnTimer.stop();
            if (cityMode) { // calculating score
                updateScoreCity(this.currentGame);
            } else {
                updateScoreCountry(this.currentGame, this.currentCountryColor);
            }
            turnTimer.resetTimer();
            // pause starts
            pauseTimer.start();
        }
    }

    /**
     * Runs the pause timer until pause is over. When game end, it doesn't restart the next turn.
     */
    public void pauseTimerActionPerformed() {
        if (this.pauseTimer.currentTimePassed < Utility.pauseLengthInMiliseconds) {
            this.pauseTimer.updateValues();
        } else {
            // pause time ran out
            this.pauseTimer.stop();
            this.pauseTimer.resetTimer();
            // initializing a new turn and starting the turn timer
            if (turnCounter < Utility.turnsPerPlayer) { 
                newTurn();
                turnTimer.start();
            } else { // finished all rounds
                this.gameOver = true;
            }
        }
    }

    /**
     * Receives a distance a guess from the actual point and a score based on proximity.
     * Max score is returned when the distance is 0, 
     * and after Utility.maxDistanceToGetPoints km of a distance the score is 0.
     * @param distance distance of a guess from the actual location on the globe.
     * @return score for the distance between the guess and the city
     */
    public int distanceScore(double distance) {
        double fracDist = (Math.exp(-1 * distance / Utility.maxDistanceToGetPoints) - Math.exp(-1));
        double initialdistanceScore = (Utility.maxScore *  fracDist / (1 - Math.exp(-1)));
        return (int) Math.max(0, initialdistanceScore);
    }

    /**
     * Receives the time in miliseconds it took the player to answer.
     * returns the score based on speed of response.
     * Max score is returned when the time is 0, and the score is 0 when the time is maximal.
     * @param time amount of miliseconds it took to make a guess.
     * @return score for the response time
     */
    public int timeScore(double time) {
        double fracTime = (Math.exp(-1 * time / Utility.turnLengthInMiliseconds) - Math.exp(-1));
        double initialTimeScore = (Utility.maxScore *  fracTime / (1 - Math.exp(-1)));
        return (int) Math.max(0, initialTimeScore);
    }

    /**
     * Calculates the score of the guess in the city mode.
     * Receives the time in miliseconds it took for the player and the coordinate of the guess.
     * Returns the score based on distance to this (city) coordinate and the time. 
     * If the distance is too far, the score is 0 regardless of the time.
     * @param time amount of miliseconds it took to make a guess.
     * @param guess location of the guess made (using the Coordinate class).
     * @param place location of the target (represented by the coordinate class).
     * @return the turn's score (city mode)
     */
    public int calculateScoreCity(int time, Coordinate guess, Coordinate place) {
        double distance = guess.distanceFrom(place);
        int distanceScore = distanceScore(distance);
        int timeScore = timeScore(time);
        if (distanceScore > 0) {
            return distanceScore + timeScore;
        }
        return distanceScore;
    }

    /**
     * Helper method for calculateScoreCountry. 
     * Receives 2 colors and returns true if they're approximately equal 
     * (with 10 difference in each of the values). Otherwise, returns false.
     * @param firstColor first color to compare
     * @param secondColor second color to compare
     * @return true if colors are close enough, false otherwise
     */
    public boolean compareColors(Color firstColor, Color secondColor) {
        if (Math.abs(firstColor.getRed() - secondColor.getRed()) > 10) {
            return false;
        }
        if (Math.abs(firstColor.getGreen() - secondColor.getGreen()) > 10) {
            return false;
        }
        if (Math.abs(firstColor.getBlue() - secondColor.getBlue()) > 10) {
            return false;
        }
        return true;
    }

    /**
     * Calculates the score of the guess in the country mode.
     * Receives the time it took to guess in miliseconds, 
     * and the color of the pixel of the guess coordinates.
     * Returns the score based on:
     *  the color of the guess pixel (to see if it matches the color of this (country) coordinate). 
     * If the guess doesn't match the country, the score is 0 regardless of the time.
     * @param time time it took to guess a location
     * @param guessColor color of guessed location in the backend map.
     * @param placeColor color of the target location on the backend map.
     * @return this turn's score (country mode)
     */
    public int calculateScoreCountry(int time, Color guessColor, Color placeColor) {
        int timeScore = timeScore(time);
        int countryScore = compareColors(guessColor, placeColor) ? Utility.maxScore : 0;
        if (countryScore > 0) {
            return timeScore + countryScore;
        }
        return countryScore;
    }

    /**
     * Receives the game JFrame. 
     * Calculates the turn's score based on city mode and updates the JFrame score label.
     * Adds the score to this turn's player's list. 
     * @param playerScreen the game JFrame
     */
    public void updateScoreCity(PlayerScreen playerScreen) {
        // coordinates of the guess
        int relativeX = (int) (playerScreen.worldMap.relativePinX * Utility.gameMapWidth);
        int relativeY = (int) (playerScreen.worldMap.relativePinY * Utility.gameMapHeight);
        Coordinate guess = new Coordinate(relativeX, relativeY, "game");
        int score = 0;
        // default score 0, if pin is placed calculates the score based on guess
        if (playerScreen.worldMap.pinPlaced) {
            int timeOfGuess = playerScreen.worldMap.timeOfGuess;
            score = calculateScoreCity(timeOfGuess, guess, playerScreen.placeCoordinate);
        }
        playerScreen.scoreJLabel.setText("" + score); // updates the score label
        // adds score the the correct player's list
        if (player1Turn) {
            player1.scores.add(score);
        } else {
            player2.scores.add(score);
        }
    }

    /**
     * Receives the game JFrame and country color. 
     * Calculates the turn's score based on country mode and updates the 
     * JFrame score label. Adds the score to this turn's player's list. 
     * @param playerScreen the game JFrame
     * @param color color of the target country
     */
    public void updateScoreCountry(PlayerScreen playerScreen, Color color) {
        // coordinates of the guess
        int relativeX = (int) (playerScreen.worldMap.relativePinX * Utility.gameMapWidth);
        int relativeY = (int) (playerScreen.worldMap.relativePinY * Utility.gameMapHeight);
        Coordinate guess = new Coordinate(relativeX, relativeY, "game");
        int score = 0;
        // default score 0, if pin is placed calculates the score based on guess
        if (playerScreen.worldMap.pinPlaced) {
            score = calculateScoreCountry(playerScreen.worldMap.timeOfGuess, guess.color, color);
        }
        playerScreen.scoreJLabel.setText("" + score); // updates the score label
        // adds score the the correct player's list
        if (player1Turn) {
            player1.scores.add(score);
        } else {
            player2.scores.add(score);
        }
    }
}
