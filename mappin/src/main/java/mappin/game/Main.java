package mappin.game;

/**
 * bla bla bla.
 */
public class Main {
    public static void main(String[] args) {
        String dbURL = "jdbc:h2:./todo;AUTO_SERVER=TRUE";
        DBmanager testing = new DBmanager(dbURL);
        System.out.println(testing.getStrQueryResult("select * from country_colors"));
    }
}