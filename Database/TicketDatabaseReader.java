package Database;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import Model.Movie;
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

    public static boolean addTicket(Ticket ticket) {
        return addTicket(ticket.getMovie().getMovieName(), ticket.getTime(), ticket.getSeatNum());
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

    public static boolean removeTicket(Ticket ticket) {
        return removeTicket(ticket.getMovie().getMovieName(), ticket.getTime(), ticket.getSeatNum());
    }

    public static Ticket getTicket(String movieName, Date showTime, int seatNum) {
        // if fail to connect, can't return a Ticket object
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT movie_name from %s WHERE movie_name=%s, show_time=%s, seat_num=%s", 
        TABLE, movieName, showTime, seatNum);
        // first need to see if ticket actually exists in database
        String movie_name = null;
        try {
            Statement fetchTicket = connection.createStatement();
            ResultSet foundTicket = fetchTicket.executeQuery(query);

            //expecting 1 Ticket/row to be found
            if (foundTicket.next()) {
                movie_name = foundTicket.getString(1);  //column 1 is movie_name
            }

            connection.close();
        } catch (SQLException e) {
            disconnect();
            return null;
        }
        disconnect();
        // if movie_name is null, then ticket not found
        if (movie_name == null) {
            return null;
        }
        // Now make find Movie
        Movie movie = MovieDatabaseReader.getMovie(movieName);

        return new Ticket(movie, showTime, seatNum, 1);
    }
}
