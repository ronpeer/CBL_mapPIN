package mappin;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class GameManager {
    Player player1;
    Player player2;
    PlayerScreen currentGame;
    boolean player1Turn = true;
    CountdownTimer countdownTimerPlayer;
    CountdownTimer countdownTimerBetweenTurns;
    ActionListener turnListener;
    ActionListener betweenTurnListener;
    DBmanager mappinDB;
    Random rand;

    GameManager(Player player1, Player player2) {
        this.rand = new Random();
        this.player1 = player1;
        this.player2 = player2;
        this.mappinDB = new DBmanager("jdbc:h2:./mappin;AUTO_SERVER=TRUE");
        this.currentGame = new PlayerScreen(player1);
    }


    public void switchPlayers() {
        if (player1Turn) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) { }
            currentGame.addScore();
            this.countdownTimerPlayer.timer.start();
            this.currentGame.player = player2;
            Integer cityID = this.rand.nextInt(1, 93);
            this.currentGame.changePlace(this.mappinDB.getCityById(cityID));
            this.currentGame.resetPlayer();
            this.countdownTimerPlayer.resetTimer();
        } else {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) { }
            this.currentGame.player = player1;
            Integer cityID = this.rand.nextInt(1, 93);
            this.currentGame.changePlace(this.mappinDB.getCityById(cityID));
            this.currentGame.resetPlayer();
            this.countdownTimerPlayer.resetTimer();
            this.countdownTimerPlayer.timer.start();
        }
        player1Turn = !player1Turn;        
    }

    public void runGame() {
        Integer cityID = this.rand.nextInt(1, 93);
        this.currentGame.changePlace(this.mappinDB.getCityById(cityID));
        this.turnListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (countdownTimerPlayer.currentTimePassed < Utility.turnLengthInMiliseconds) {
                    countdownTimerPlayer.updateValues();
                    currentGame.updateTimerLabel(countdownTimerPlayer.currentTimePassed);
                }
                else {
                    countdownTimerPlayer.timer.stop();
                    switchPlayers();
                }
            }
        };

        this.countdownTimerPlayer = new CountdownTimer(10, this.turnListener);
        //this.countdownTimerBetweenTurns = new CountdownTimer(5000, this.betweenTurnListener);

        this.currentGame.setVisible(true);
        this.countdownTimerPlayer.timer.start();
    }
}
