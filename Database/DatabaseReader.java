package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public abstract class DatabaseReader {
    final static private String DB_NAME = "movie_database";
    final static private String DB_USER = "student";
    final static private String DB_PASSWORD = "ensf";
    
    static protected Connection connection = null;
    
    /** Attempts to connect to movie_database. 
     * @return true if was able to connect. false if an SQLException was thrown.
     */
    final static protected boolean connect() {
        // connection should be null when trying to make a new connection
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/"+DB_NAME, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                return false;
            }
        }
        
        return true;
    }

    /** Attempts to disconnect from movie_database and make connection variable null.
     * @return true if connection to database was closed and connection variable is made null. 
     * Will also return true if connection variable was already null.
     * Returns false if an SQLException was thrown, connection variable will not be made null in this case.
     */
    final static protected boolean disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Rounds down the seconds to 0.
     * @param time time provided in miliseconds as per {@link java.util.Date} parameters are
     * @return time with seconds rounded down to 0
     */
    final static public long roundSecondsDown(long time) {
        long time_seconds = time;  
        // round seconds down to 0
        // this means any seconds [59,0] becomes 0 or seconds [99,60] becomes 60
        // need to deal with least 5 significant digits.
        long seconds = time_seconds % 100_000;

        //2 cases:
        // digits less than 60 000 ms (60 s), so make those 2 digits 00
        // rest are equal or greater than 60 000 ms (60 s), so make those 2 digits 60 000
        if (seconds < 60_000) {
            time_seconds -= seconds;
        }
        else {
            seconds -= 60_000;
            time_seconds -= seconds;
        }
        // finally return
        return time_seconds;
    }

    public static void main(String[] args) {
        // testing roundsSecondsDown()
        Date date = new Date();
        long l = date.getTime();
        System.out.println(date.toString());
        System.out.println(l);
        System.out.println(DatabaseReader.roundSecondsDown(l));
        date.setTime(DatabaseReader.roundSecondsDown(l));
        System.out.println(date.toString());
        // Currently seems to work
    }
}
