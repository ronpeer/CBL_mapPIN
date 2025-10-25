package mappin.game;

import java.awt.Color;

/**
 * A class for the coordinates of a point. 
 * It holds 3 representations of the same point: 
 * xy in the game map, 
 * xy in the backend color map 
 * and latitude and longitude on the globe, 
 * along with the color of the pixel in the backend map.
 * Contains conversion methods from one representation to another, 
 * and distance method between cooridnates.
 */
public class Coordinate {
    int xCoordinate; // x coordinate on the full backend map
    int yCoordinate; // y coordinate on the full backend map
    int xGameMapCoordinate; // x coordinate on the presented map during the game
    int yGameMapCoordinate; // y coordinate on the presented map during the game
    double longitude;
    double latitude;
    Color color;

    /**
     * Constructor - receives x and y coordinates on the game map, and a string.
     * If the string is "game" (to distinguish from the lat-long constructor), 
     * it updates the game x y coordinates, converts and updates the other 2 types. 
     * Sets the color of the coordinate to be the color of pixel in the backend map.
     * @param gameMapX x value of coordinate on the game map (loaded on screen)
     * @param gameMapY y value of coordinate on the game map (loaded on screen)
     * @param s string representing current program state
     */
    Coordinate(int gameMapX, int gameMapY, String s) {
        if (s.equals("game")) {
            this.xGameMapCoordinate = gameMapX;
            this.yGameMapCoordinate = gameMapY;
            this.xCoordinate = this.gameToBackendX(this.xGameMapCoordinate);
            this.yCoordinate = this.gameToBackendY(this.yGameMapCoordinate);
            this.longitude = this.backendToLongitudeX(this.xCoordinate);
            this.latitude = this.backendToLatitudeY(this.yCoordinate);
        } 

        this.color = new BackendMap().getPixelColor(this.xCoordinate, this.yCoordinate);
    }

    /**
     * Constructor - receives latitude and longitude values of the point on the globe.
     * updates the lat-long coordinates and converts to both backend and game map representations.
     * Sets the color of the coordinates to be the color of the pixel in the backend map. 
     * @param latitude int latitude of point on the globe
     * @param longitude int longitude of point on the globe
     */
    Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.xCoordinate = this.longitudeToXBackendMap(longitude);
        this.yCoordinate = this.latitudeToYBackendMap(latitude);
        this.xGameMapCoordinate = backendToGameX(this.xCoordinate); 
        this.yGameMapCoordinate = backendToGameY(this.yCoordinate);

        this.color = new BackendMap().getPixelColor(this.xCoordinate, this.yCoordinate);
    }

    /**
     * Receives x coordinate of a point in the backend map.
     * returns the x coordinate of the point in the game map.
     * @param x backend map x coordinate
     * @return game map x coordinate
     */
    public int backendToGameX(int x) {
        if (x <= 404 && x >= 11) {
            // roll the x value since russia's top right part loops over a globe.
            return x - 11 + 5592;
        } else if (x >= 3896 && x <= 9499) {
            // the game map starts at index 3896 of original map
            return x - 3896;
        }
        return -1; // easy error management
    }

    /**
     * Receives x coordinate of a point in the game map.
     * returns the x coordinate of the point in the backend map.
     * @param x game map x coordinate
     * @return backend map x coordinate
     */
    public int gameToBackendX(int x) {
        if (x <= 5591 && x >= 0) {
            // the game map starts at index 3896 of original map
            return x + 3896;
        } else if (x >= 5592 && x <= 5985) {
            // roll the x value since russia's top right part loops over a globe.
            return x - 5592 + 11;
        }
        return -1; // easy error management
    }

    /**
     * Receives y coordinate of a point in the backend map.
     * returns the y coordinate of the point in the game map.
     * @param y backend map y coordinate
     * @return game map y coordinate
     */
    public int backendToGameY(int y) {
        if (y >= 0 && y <= 3409) {
            // the game map is only 3410 pixels tall
            return y;
        }
        return -1; // easy error management
    }

    /**
     * Receives y coordinate of a point in the game map.
     * returns the y coordinate of the point in the backend map.
     * @param y game map y coordinate
     * @return backend map y coordinate
     */
    public int gameToBackendY(int y) {
        if (y >= 0 && y <= 3409) {
            // the  game map is only 3410 pixels tall
            return y;
        }
        return -1; // easy error management
    }

    /**
     * Receives longitude value of a point on the globe.
     * returns the x coordinate of the point in the backend map.
     * Using the Miller cylindrical projection formula,
     * with an error adjustment constant of Utility.mapProjectionErrorAdjustment
     * @param longitude int longitude of point on the globe
     * @return corresponding backend map x coordinate
     */
    public int longitudeToXBackendMap(double longitude) {
        return (int) Math.round((4750 / Math.PI) * Utility.mapProjectionErrorAdjusment * longitude * Math.PI / 180 + 4750);
    }

    /**
     * Receives latitude value of a point on the globe.
     * returns the y coordinate of the point in the backend map.
     * Using the Miller cylindrical projection formula,
     * with an error adjustment constant of Utility.mapProjectionErrorAdjustment
     * @param latitude int latitude of point on the globe
     * @return corresponding backend map y coordinate
     */
    public int latitudeToYBackendMap(double latitude) {
        return (int) Math.round(3004 - (4750 / Math.PI) * 1.25 * Math.log(Math.tan(
                Math.PI / 4 + 0.4 * Utility.mapProjectionErrorAdjusment * latitude * Math.PI / 180)));
    }
    
    /**
     * Receives x coordinate of a point in the backend map.
     * returns the longitude value of a point on the globe.
     * Using the Miller cylindrical projection formula (inverted),
     * with an error adjustment constant of Utility.mapProjectionErrorAdjustment
     * @param x backend map x coordinate
     * @return corresponding globe longitude (x) coordinate 
     */
    public double backendToLongitudeX(int x) {
        return (x - 4750) / (Math.PI / 180 * Utility.mapProjectionErrorAdjusment * (4750 / Math.PI));
    }

    /**
     * Receives y coordinate of a point in the backend map.
     * returns the latitude value of a point on the globe.
     * Using the Miller cylindrical projection formula (inverted),
     * with an error adjustment constant of Utility.mapProjectionErrorAdjustment
     * @param y backend map y coordinate
     * @return corresponding globe latitude (y) coordinate
     */
    public double backendToLatitudeY(int y) {
        return (Math.atan(Math.exp((3004 - y) / ((4750 / Math.PI) * 1.25))) - Math.PI / 4) / (0.4 * Utility.mapProjectionErrorAdjusment * Math.PI / 180);
    }

    /**
     * Receives a coordinate. 
     * returns the true distance between this coordinate
     * to the paramteter one on the globe using haversine formula.
     * @param other coordinate
     * @return distance on the globe between this and other coordinate 
     */
    public double distanceFrom(Coordinate other) {
        double lat1R = this.latitude * Math.PI / 180;
        double long1R = this.longitude * Math.PI / 180;
        double lat2R = other.latitude * Math.PI / 180;
        double long2R = other.longitude * Math.PI / 180;
        double deltaLat = lat1R - lat2R;
        double deltaLong = long1R - long2R;
        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(lat1R) * Math.cos(lat2R) * Math.pow(Math.sin(deltaLong / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return Utility.earthAvgRadius * c;
    } 
}
