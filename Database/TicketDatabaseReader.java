package Database;

import java.text.ParseException;
import java.util.Date;
import Model.Ticket;
import java.sql.*;

public class TicketDatabaseReader extends DatabaseReader {
    final private static String TABLE = "tickets";

    public static boolean addTicket(String movieName, Date showTime, int seatNum) {
        // if fail to connect, failed to add new ticket
        if (!connect()) {
            return false;
        }

        String query = String.format("INSERT INTO %s VALUES (%s, %t, %d)", 
        TABLE, showTime, seatNum);

        try {
            Statement insertTicket = connection.createStatement();
            int rowsChanged = insertTicket.executeUpdate(query);
            insertTicket.close();
            // if rowsChanged == 0, then insert did not occur
            // throw SQLException so it can be caught and false be returned
            if (rowsChanged == 0) {
                throw new SQLException("Ticket was not added.");
            }
        } catch (SQLException e) {
            disconnect();
            return false;
        }
        disconnect();
        
        return true;
    }

    public static boolean removeTicket(String movieName, Date showTime, int seatNum) {
        // if fail to connect, failed to remove ticket
        if (!connect()) {
            return false;
        }

        String query = String.format("DELETE FROM %s WHERE movie_name=%s, show_time=%s, seat_num=%s", 
        TABLE, movieName, showTime, seatNum);

        try {
            Statement deleteTicket = connection.createStatement();
            int rowsChanged = deleteTicket.executeUpdate(query);
            deleteTicket.close();
            // if rowsChanged == 0, then ticket was not deleted
            // so throw SQLException so it can be caught and false be returned
            if (rowsChanged == 0) {
                throw new SQLException("Ticket was not deleted.");
            }
        } catch (SQLException e) {
            disconnect();
            return false;
        }
        disconnect();

        return true;
    }

    public static Ticket getTicket(String movieName, Date showTime, int seatNum) throws ParseException{
        return new Ticket(null, showTime.toString());
    }
}
