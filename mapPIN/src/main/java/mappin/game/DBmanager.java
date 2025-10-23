package mappin.game;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * this class handles all connections and queries to the DB.
 * it contains generic methods for executing commands and queries on the DB,
 * as well as specific methods for the queries necessary for the game.
 * it requires a URL to connect to, and will not work outside of a java project.
 * for more info read the documentation of specific methods.
 * the DB contains two tables:
 * table cities, with the collumns of:
 * id(integer), name(varchar), latitude(decimal), longitude(decimal), country(varchar)
 * table country_colors with collumns of:
 * id(integer), name(varchar), red(integer), green(integer), blue(integer).
 */
public class DBmanager {

    private Connection connection;
    private Statement statement;

    /**
     * object constructor.
     * When given a url to connect to it creates both a connection and statement object.
     * these will not be accesible outside the class itself, as there should be no access
     * to the DB outside of this class.
     * @param connectionUrl url of DB to connect to.
     */
    DBmanager(String connectionUrl) {
        try {
            this.connection = DriverManager.getConnection(connectionUrl);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * a method to execute statements on the DB.
     * this method was used to populate the DB itself, as well as updaet or remove values.
     * However, it has no need during the gameplay itself.
     * in case more locations will be added, removed or chagned manually in the DB it may be used.
     * @param dbCommandStr command to run through DB connection.
     */
    public void executeStatement(String dbCommandStr) {
        try {    
            this.statement.execute(dbCommandStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * a method to execute any query given by a string.
     * this will have no use during gameplay, as other methods contain all specific 
     * queries used in the gameplay.
     * @param queryStr string of query to run.
     * @return result set of query result. if fails, null.
     */
    private ResultSet executeQuery(String queryStr) {
        try {
            return this.statement.executeQuery(queryStr);   
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * return a result of a query to a DB as a string, so it could be easily printed.
     * uses the executeQuery method to get a result set. 
     * once it is received, the method iterates line by line on the result set 
     * (moving line to line by using resultset.next()), and gathers all objects in each entry.
     * all objects gatheres are then turned to strings and consutructed into a line of text,
     * which will be a part of the final resultString.
     * @param queryStr query to execute with DB.
     * @return string of result from db
     */
    public String getStrQueryResult(String queryStr) {
        String resulString;
        // obtain query result
        ResultSet resultSet = executeQuery(queryStr);
        try {
            resulString = "";
            //obtain number of collumns (which is also the num of objects in each line)
            ResultSetMetaData meta = resultSet.getMetaData();
            int collumns = meta.getColumnCount();
            // iterate over the resultset entry by entry.
            while (resultSet.next()) { 
                String line = "";
                // add all data in an entry to a line of text
                for (int i = 1; i < collumns; i++) {
                    line += resultSet.getObject(i).toString() + " ";
                }
                line += resultSet.getObject(collumns).toString();
                // based if there are any more entries, add newline character or not.
                if (resultSet.isLast()) {
                    resulString += line;
                } else {
                    resulString += line + "\n";
                }
            }
            return resulString;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * return the coordinates of a city, using its name,
     * by executing a query on the DB.
     * @param city name of city to get coordinates for.
     * @return array of floats representing coordinates.
     */
    public Coordinate getCityLocation(String city) {
        String query = "select latitude, longitude from cities where name='%s'".formatted(city);
        ResultSet locationResult = executeQuery(query);
        try {
            locationResult.next();
            float latitude = locationResult.getFloat("latitude");
            float longitude = locationResult.getFloat("longitude");
            return new Coordinate(latitude, longitude);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * return the color of a country in the beckend map, using its name.
     * the color is represented via RGB integer values.
     * do this via a query to the DB.
     * @param country country to find.
     * @return int array of rgb color breakdown.
     */
    public Color getCountryColor(String country) {
        String query = "select red, green, blue from country_colors where name='%s'";
        query = query.formatted(country);
        ResultSet colorResult = executeQuery(query);
        try {
            colorResult.next();
            int red = colorResult.getInt("red");
            int green = colorResult.getInt("green");
            int blue = colorResult.getInt("blue");
            return new Color(red, green, blue);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * given a number, return the relevant city name in the DB.
     * each city is designated a unique numeric id in the DB.
     * @param id number of city in the DB.
     * @return string name ofcity with given id.
     */
    public String getCityById(Integer id) {
        String query = "select name from cities where id='%s'".formatted(id);
        ResultSet nameResult = executeQuery(query);
        try {
            nameResult.next();
            return nameResult.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * given a number, return the relevant country name in the DB.
     * each country is designated a unique numeric id in the DB.
     * @param id number of country in the DB.
     * @return string name of country with given id.
     */
    public String getCountryById(Integer id) {
        String query = "select name from country_colors where id='%s'".formatted(id);
        ResultSet nameResult = executeQuery(query);
        try {
            nameResult.next();
            return nameResult.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}