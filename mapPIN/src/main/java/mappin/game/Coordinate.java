public class Coordinate {
    int xCoordinate;
    int yCoordinate;
    int xGameMapCoordinate;
    int yGameMapCoordinate;
    double longitude;
    double latitude;

    Coordinate(int x, int y, String s) {
        if (s.equals("game")) {
            this.xGameMapCoordinate = x;
            this.yGameMapCoordinate = y;
            this.xCoordinate = this.xGameMapToBackendMap(this.xGameMapCoordinate);
            this.yCoordinate = this.yGameMapToBackendMap(this.yGameMapCoordinate);
            this.longitude = this.xBackendMapToLongitude(this.xCoordinate);
            this.latitude = this.yBackendMapToLatitude(this.yCoordinate);
        } else if (s.equals("backend")) {
            this.xCoordinate = x;
            this.yCoordinate = y;
            this.xGameMapCoordinate = this.xBackendMapToGameMap(this.xCoordinate);
            this.yGameMapCoordinate = this.yBackendMapToGameMap(this.yCoordinate);
            this.longitude = this.xBackendMapToLongitude(this.xCoordinate);
            this.latitude = this.yBackendMapToLatitude(this.yCoordinate);
        }   
    }

    Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.xCoordinate = this.longitudeToXBackendMap(longitude);
        this.yCoordinate = this.latitudeToYBackendMap(latitude);
        this.xGameMapCoordinate = xBackendMapToGameMap(this.xCoordinate); 
        this.yGameMapCoordinate = yBackendMapToGameMap(this.yCoordinate);
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

    public int longitudeToXBackendMap(double longitude) {
        // using the Miller cylindrical projection with an error adjustment constant of 0.99545
        return (int)Math.round((4750/Math.PI) * Utility.mapProjectionErrorAdjusment * longitude * Math.PI / 180 + 4750);
    }

    public int latitudeToYBackendMap(double latitude) {
        // using the Miller cylindrical projection with an error adjustment constant of 0.99545
        return (int)Math.round(3004 - (4750/Math.PI) * 1.25 * Math.log(Math.tan(
                Math.PI / 4 + 0.4 * Utility.mapProjectionErrorAdjusment * latitude * Math.PI / 180)));
    }

    public double xBackendMapToLongitude(int x) {
        // using the Miller cylindrical projection (inverted) with an error adjustment constant of 0.99545
        return (x - 4750) / (Math.PI / 180 * Utility.mapProjectionErrorAdjusment * (4750 / Math.PI));
    }

    public double yBackendMapToLatitude(int y) {
        // using the Miller cylindrical projection (inverted) with an error adjustment constant of 0.99545
        return (Math.atan(Math.exp((3004 - y) / ((4750/Math.PI) * 1.25))) - Math.PI / 4) / (0.4 * Utility.mapProjectionErrorAdjusment * Math.PI / 180);
    }

    public double distanceFrom(Coordinate other) {
        // using haversine formula
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

    public static void main(String[] args) {
        Coordinate london = new Coordinate(51.509865, -0.118092);
        Coordinate newyork = new Coordinate(40.730610, -73.935242);
        System.out.println(london.distanceFrom(newyork));
    }
}
