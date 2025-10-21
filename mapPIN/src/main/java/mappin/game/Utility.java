public class Utility {
    int MAPWIDTH = 5986;
    int MAPLENGTH = 3410;

    // constants 
    int answerTimeWindow = 15000; // in miliseconds
    static double mapProjectionErrorAdjusment = 0.99545;
    static double earthAvgRadius = 6371;
    static int turnLengthInMiliseconds = 5000; 

    public static double round4Decimals(double num) {
        return (int) (num * 10000) / 10000.0;
    }
}
