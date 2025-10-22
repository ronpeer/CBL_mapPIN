package mappin.game;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * bla bla bla.
 */
public class DBmanager {

    private Connection connection;
    private Statement statement;

    /**
     * bla.
     * @param connectionUrl url of db to connect to.
     */
    DBmanager(String connectionUrl) {
        try {
            this.connection = DriverManager.getConnection(connectionUrl);
            this.statement = connection.createStatement();   
            System.out.println(this.connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * bla.
     * @param dbCommandStr command to run on db.
     */
    public void executeStatement(String dbCommandStr) {
        try {    
            this.statement.execute(dbCommandStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * bla.
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
     * bla.
     * @param queryStr query to execute with DB.
     * @return string of result from db
     */
    public String getStrQueryResult(String queryStr) {
        String resulString;
        ResultSet resultSet = executeQuery(queryStr);
        try {
            resulString = "";
            ResultSetMetaData meta = resultSet.getMetaData();
            int collumns = meta.getColumnCount();
            while (resultSet.next()) { 
                String line = "";
                for (int i = 1; i < collumns; i++) {
                    line += resultSet.getObject(i).toString() + " ";
                }
                line += resultSet.getObject(collumns).toString();
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
     * bla.
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
     * bla.
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
     * bla.
     * @param id id chosen randomly.
     * @return name of city.
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
     * bla.
     * @param id bla.
     * @return bla.
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