package mappin.game;

import java.awt.Color;

/**
 * A class for the coordinates of a point. It holds 3 representations of the same point: xy 
 * in the game map, xy in the backend color map and latitude and longitude on the globe, along with
 * the color of the pixel in the backend map.
 * Contains conversion methods from one representation to another, distance method between cooridnates.
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
     * If the string is "game" (to distinguish from the lat-long constructor), it updates
     * the game xy coordinates, converts and updates the other 2 types. Sets the color of the coordinate
     * to be the color of pixel in the backend map.
     * @param gameMapX
     * @param gameMapY
     * @param s
     */
    Coordinate(int gameMapX, int gameMapY, String s) {
        if (s.equals("game")) {
            this.xGameMapCoordinate = gameMapX;
            this.yGameMapCoordinate = gameMapY;
            this.xCoordinate = this.xGameMapToBackendMap(this.xGameMapCoordinate);
            this.yCoordinate = this.yGameMapToBackendMap(this.yGameMapCoordinate);
            this.longitude = this.xBackendMapToLongitude(this.xCoordinate);
            this.latitude = this.yBackendMapToLatitude(this.yCoordinate);
        } 

        this.color = new BackendMap().getPixelColor(this.xCoordinate, this.yCoordinate);
    }

    /**
     * Constructor - receives latitude and longitude values of the point on the globe,
     * @param latitude
     * @param longitude
     */
    Coordinate(double latitude, double longitude) {
        // Constructor - receives latitude and longitude values of the point on the globe,
        // updates the lat-long coordinates and converts and updates the 2 other types.
        // Sets the color of the coordinates to be the color of the pixel in the backend map.
        this.latitude = latitude;
        this.longitude = longitude;
        this.xCoordinate = this.longitudeToXBackendMap(longitude);
        this.yCoordinate = this.latitudeToYBackendMap(latitude);
        this.xGameMapCoordinate = xBackendMapToGameMap(this.xCoordinate); 
        this.yGameMapCoordinate = yBackendMapToGameMap(this.yCoordinate);

        this.color = new BackendMap().getPixelColor(this.xCoordinate, this.yCoordinate);
    }

    /**
     * Receives x coordinate of a point in the backend map and returns the x coordinate of the point in the game map.
     * @param x backend map x coordinate
     * @return game map x coordinate
     */
    public int xBackendMapToGameMap(int x) {
        if (x <= 404 && x >= 11) {
            // Since russia's top right part is in the left section of the backendmap we roll the x value.
            return x - 11 + 5592;
        } else if (x >= 3896 && x <= 9499) {
            // the game map starts at index 3896 of original map
            return x - 3896;
        }
        return -1; // easy error management
    }

    /**
     * Receives x coordinate of a point in the game map and returns the x coordinate of the point in the backend map.
     * @param x game map x coordinate
     * @return backend map x coordinate
     */
    public int xGameMapToBackendMap(int x) {
        if (x <= 5591 && x >= 0) {
            // the game map starts at index 3896 of original map
            return x + 3896;
        }
        else if (x >= 5592 && x <= 5985) {
            // Since russia's top right part is in the left section of the backendmap we roll the x value
            return x - 5592 + 11;
        }
        return -1; // easy error management
    }

    /**
     * Receives y coordinate of a point in the backend map and returns the y coordinate of the point in the game map.
     * @param y backend map y coordinate
     * @return game map y coordinate
     */
    public int yBackendMapToGameMap(int y) {
        if (y >= 0 && y <= 3409) {
            // the game map is only 3410 pixels tall
            return y;
        }
        return -1; // easy error management
    }

    /**
     * Receives y coordinate of a point in the game map and returns the y coordinate of the point in the backend map.
     * @param y game map y coordinate
     * @return backend map y coordinate
     */
    public int yGameMapToBackendMap(int y) {
        // Receives y coordinate of a point in the game map and returns the y coordinate of the point in the backend map.
        if (y >= 0 && y <= 3409) {
            // the  game map is only 3410 pixels tall
            return y;
        }
        return -1; // easy error management
    }

    /**
     * Receives longitude value of a point on the globe and returns the x coordinate of the point in the backend map.
     * Using the Miller cylindrical projection formula with an error adjustment constant of Utility.mapProjectionErrorAdjustment
     * @param longitude
     * @return backend map x coordinate
     */
    public int longitudeToXBackendMap(double longitude) {
        return (int) Math.round((4750/Math.PI) * Utility.mapProjectionErrorAdjusment * longitude * Math.PI / 180 + 4750);
    }

    /**
     * Receives latitude value of a point on the globe and returns the y coordinate of the point in the backend map.
     * Using the Miller cylindrical projection formula with an error adjustment constant of Utility.mapProjectionErrorAdjustment
     * @param latitude
     * @return
     */
    public int latitudeToYBackendMap(double latitude) {
        return (int)Math.round(3004 - (4750/Math.PI) * 1.25 * Math.log(Math.tan(
                Math.PI / 4 + 0.4 * Utility.mapProjectionErrorAdjusment * latitude * Math.PI / 180)));
    }
    
    /**
     * Receives x coordinate of a point in the backend map and returns the longitude value of a point on the globe.
     * Using the Miller cylindrical projection formula (inverted) with an error adjustment constant of Utility.mapProjectionErrorAdjustment
     * @param x backend map x coordinate
     * @return logitude 
     */
    public double xBackendMapToLongitude(int x) {
        return (x - 4750) / (Math.PI / 180 * Utility.mapProjectionErrorAdjusment * (4750 / Math.PI));
    }

    /**
     * Receives y coordinate of a point in the backend map and returns the latitude value of a point on the globe.
     * Using the Miller cylindrical projection formula (inverted) with an error adjustment constant of Utility.mapProjectionErrorAdjustment
     * @param y
     * @return latitude
     */
    public double yBackendMapToLatitude(int y) {
        return (Math.atan(Math.exp((3004 - y) / ((4750/Math.PI) * 1.25))) - Math.PI / 4) / (0.4 * Utility.mapProjectionErrorAdjusment * Math.PI / 180);
    }

    /**
     * Receives a coordinate and returns the true distance between this coordinate to the paramteter one
     * on the globe using haversine formula.
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
        double a = Math.pow(Math.sin(deltaLat / 2),2) + Math.cos(lat1R) * Math.cos(lat2R) * Math.pow(Math.sin(deltaLong / 2),2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return Utility.earthAvgRadius * c;
    } 
}
