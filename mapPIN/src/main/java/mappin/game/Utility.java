package mappin.game;

public class Utility {
    static int gameMapWidth = 5986;
    static int gameMapHeight = 3410;

    // constants 
    int answerTimeWindow = 15000; // in miliseconds
    static double mapProjectionErrorAdjusment = 0.99545;
    static double earthAvgRadius = 6371;
    static int turnLengthInMiliseconds = 30000; 
    static int pauseLengthInMiliseconds = 3000;
    static int maxDistanceToGetPoints = 5000;
    static int turnsPerPlayer = 4;

    public static double round4Decimals(double num) {
        return (int) (num * 10000) / 10000.0;
    }
}
