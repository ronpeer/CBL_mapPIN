package mappin.game;

/**
 * bla bla bla.
 */
public class Main {
    public static void main(String[] args) {
        String dbURL = "jdbc:h2:./todo;AUTO_SERVER=TRUE";
        DBmanager testing = new DBmanager(dbURL);
        System.out.println(testing.getStrQueryResult("select * from cities where name='London'"));
        float[] coordinates = testing.getCityLocation("London");
        System.out.println(coordinates[0] + " " + coordinates[1]);
        int[] rgb = testing.getountryColor("United Kingdom");
        System.out.println(rgb[0] + " " + rgb[1] + " " + rgb[2]);
        System.out.println(testing.getCityById(88));
        System.out.println(testing.getCountryById(88));
    }
}