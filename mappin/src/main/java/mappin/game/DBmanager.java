package mappin.game;

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

    public float[] getCityLocation(String city) {
        return null;
    }

    public float[] getountryColor(String country) {
        return null;
    }

    public String getCityById(Integer id) {
        return null;
    }

    public String getCountryById(Integer id) {
        return null;
    }

}