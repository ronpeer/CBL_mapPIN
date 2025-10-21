public class Algorithms {

    // constants 
    int answerTimeWindow = 15000; // in miliseconds
    double mapProjectionErrorAdjusment = 0.99545;

    public int distanceScore(double distance) {
        // shape of scoring is roughly x^0.25 adjusted to have 0 distance score 5000km a value of 0; 
        return (int) Math.round (5000 / 2414.5331 * Math.max(0, (1 / Math.pow(0.005 * (distance + 1000),0.25) - 0.427287) / 0.0001));
    }

    public int timeScore(double time) {
        // shape of scoring is rougly e^-x adjusted to have 0 at 15000 miliseconds and scaled to 5000 max.
        return (int) Math.round(5000 / 7768.6984 * Math.max(0, (Math.exp(-0.0001 * time) - Math.exp(-1.5)) / 0.0001));
    }

    public int scoreCity(double time, Coordinate guess, Coordinate city) {
        // overall score of 10000 - max 5000 from distance and 5000 from time
        double distance = guess.distanceFrom(city);
        System.out.println(distance);
        //int distanceScore = (int) Math.round(Math.max(0,(1 / ((distance * 0.00096) + 0.2) - 0.2) / 0.00096));
        int distanceScore = distanceScore(distance);
        int timeScore = timeScore(time);
        System.out.println(timeScore);

        return distanceScore + timeScore;
    }

    public int xBackendMapToGameMap(int x) {
        // since russia's top right part is in the left section of the backendmap we roll the x value
        if (x <= 404 && x >= 11) {
            return x - 11 + 5592;
        } else if (x >= 3896 && x <= 9499) {
            // the game map starts at index 3896 of original map
            return x - 3896;
        }
        return -1;
    }

    public int xGameMapToBackendMap(int x) {
        if (x <= 5591 && x >= 0) {
            // the game map starts at index 3896 of original map
            return x + 3896;
        }
        else if (x >= 5592 && x <= 5985) {
            // since russia's top right part is in the left section of the backendmap we roll the x value
            return x - 5592 + 11;
        }
        return -1;
    }

    public int yBackendMapToGameMap(int y) {
        // the  game map is only 3409 pixels tall
        if (y >= 0 && y <= 3409) {
            return y;
        }
        return -1;
    }

    public int yGameMapToBackendMap(int y) {
        // the  game map is only 3409 pixels tall
        if (y >= 0 && y <= 3409) {
            return y;
        }
        return -1;
    }

    public int xLongLatToBackendMap(double longitude) {
        // using the winkel-tripel projection with an error adjustment constant of 0.99545
        return (int)Math.round((4750/Math.PI) * mapProjectionErrorAdjusment * longitude * Math.PI / 180 + 4750);
    }

    public int yLongLatToBackendMap(double latitude) {
        // using the winkel-tripel projection with an error adjustment constant of 0.99545
        return (int)Math.round(3004 - (4750/Math.PI) * 1.25 * Math.log(Math.tan(
                Math.PI / 4 + 0.4 * mapProjectionErrorAdjusment * latitude * Math.PI / 180)));
    }

    public double xBackendMapToLongLat(int x) {
        // using the winkel-tripel projection (inverted) with an error adjustment constant of 0.99545
        return (x - 4750) / (Math.PI / 180 * mapProjectionErrorAdjusment * (4750 / Math.PI));
    }

    public double yBackendMapToLongLat(int y) {
        // using the winkel-tripel projection (inverted) with an error adjustment constant of 0.99545
        return (Math.atan(Math.exp((3004 - y) / ((4750/Math.PI) * 1.25))) - Math.PI / 4) / (0.4 * mapProjectionErrorAdjusment * Math.PI / 180);
    }

    public static void main(String[] args) {
        Algorithms a = new Algorithms();
        // System.out.println(a.xGameMapToBackendMap(1582));
        // System.out.println(a.xBackendMapToGameMap(5141));
        // System.out.println(a.xGameMapToBackendMap(2423) + "" + a.yGameMapToBackendMap(1760));
        // System.out.println(a.yLongLatToBackendMap(67.0164819409626));
        // System.out.println(a.xLongLatToBackendMap(-174.966666926642));
        // System.out.println(a.xBackendMapToLongLat(5669));
        // System.out.println(a.yBackendMapToLongLat(2109));
        // Coordinate city = new Coordinate(1582, 5141);

        Coordinate london = new Coordinate(51.509865, -0.118092);
        Coordinate newyork = new Coordinate(40.730610, -73.935242);
        Coordinate boston = new Coordinate(42.361145, -71.057083);
        Coordinate rome = new Coordinate( 41.902782, 12.496366);
        Coordinate guess = new Coordinate(1186, 1841, "game");
        System.out.println(a.scoreCity(10000, rome, guess));

    }
}


