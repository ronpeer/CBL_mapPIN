package mappin.game;

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
}


