package Database;

import java.sql.*;

public abstract class DatabaseReader {
    final static private String DB_NAME = "movie_database";
    final static private String DB_USER = "student";
    final static private String DB_PASSWORD = "ensf";
    
    static protected Connection connection = null;
    
    /** Attempts to connect to movie_database. 
     * @return true if was able to connect. false if an SQLException was thrown.
     */
    final static protected boolean connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/"+DB_NAME, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            return false;
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
}
