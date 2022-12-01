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

        String query = String.format("INSERT INTO %s (movie_name, show_time, seat_num) VALUES (?, ?, ?)", 
        TABLE);
        // create TimeStamp for show_time
        Timestamp show_time = new Timestamp(showTime.getTime());
        show_time.setTime(roundSecondsDown(show_time.getTime()));   //round seconds down to 0
        try {
            PreparedStatement insertTicket = connection.prepareStatement(query);
            // set values
            insertTicket.setString(1, movieName);
            insertTicket.setTimestamp(2, show_time);
            insertTicket.setInt(3, seatNum);
            // statement is ready to execute
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

        //update seat which just got reserved
        MovieDatabaseReader.updateSeat(movieName, showTime, seatNum, 0);
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

        String query = String.format("DELETE FROM %s WHERE movie_name = ?, show_time = ?, seat_num = ?", 
        TABLE);

        try {
            PreparedStatement deleteTicket = connection.prepareStatement(query);
            int rowsChanged = deleteTicket.executeUpdate();
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
        // update seat which just became available
        MovieDatabaseReader.updateSeat(movieName, showTime, seatNum, 1);
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
