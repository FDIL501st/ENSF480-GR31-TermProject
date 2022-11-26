package Database;

import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

public class MovieDatabaseReader extends DatabaseReader{
    private static String TABLE = "theatre1";

    public static void setTable(String theatreName) {
        TABLE = theatreName;
    }

    public static ArrayList<Date> getAllShowTimes(String movieName) {
        // return nothing if failed to connect
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT time FROM %s WHERE movie_name=%s",
        TABLE, movieName);

        Statement fetchAllTimes = null;
        ResultSet allTimes = null;
        ArrayList<Date> showTimes = new ArrayList<>();
        try {
            fetchAllTimes = connection.createStatement();
            allTimes = fetchAllTimes.executeQuery(query);

            while (allTimes.next()) {
                Date showTime = allTimes.getDate(2);    //colum 2 is show time of movie
                showTimes.add(showTime);
            }

            fetchAllTimes.close();
            allTimes.close();

        } catch (SQLException e) {
            disconnect();
            return null;
        }

        disconnect();
        return showTimes;
    }

    public static ArrayList<Integer> getSeats(String movieName, Date time) {
        final int NUM_OF_SEATS = 30;
        // return nothing if failed to connect
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT * FROM %s WHERE movie_name=%s, time=%s", 
        TABLE, movieName, time);

        Statement fetchSeats = null;
        ResultSet allSeats = null;
        ArrayList<Integer> seats = new ArrayList<>();
        try {
            fetchSeats = connection.createStatement();
            allSeats = fetchSeats.executeQuery(query);

            // only expecting one row to be returned, so use only first row
            if (allSeats.next()) {
                // loop through all the seats of the row
                for (int i = 1; i <=NUM_OF_SEATS; i++) {
                    String seatNum = "seat" + Integer.toString(i);
                    Integer seatStatus = allSeats.getInt(seatNum);  //call colum by name
                    seats.add(seatStatus);
                }
            }

            fetchSeats.close();
            allSeats.close();

        } catch (SQLException e) {
            disconnect();
            return null;
        }

        disconnect();
        return seats;
    }

}
