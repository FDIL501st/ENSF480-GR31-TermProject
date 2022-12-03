package Database;

import java.util.ArrayList;
import java.util.Date;
import Model.Movie;
import Model.Ticket;
import java.sql.*;

public class TicketDatabaseReader extends DatabaseReader {
    final private static String TABLE = "tickets";
    private static ArrayList<Ticket> allTickets = fetchAllTickets();

    /**
     * Getter for allTickets.
     * @return an arraylist of all ticket objects stored within the database
     */
    public static ArrayList<Ticket> getAllTickets() {
        return allTickets;
    }

    /**
     * fetches all the tickets stored within the database
     * @return an arrayList of Ticket objects created from all the rows of the database
     */
    private static ArrayList<Ticket> fetchAllTickets() {
        // if fail to connect, return null as failed operation
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT * FROM %s", TABLE);
        // below will store all information needed to make ticket objects
        ArrayList<String> movie_names = new ArrayList<>();
        ArrayList<Timestamp> show_times = new ArrayList<>();
        ArrayList<Integer> seat_nums = new ArrayList<>();

        try {
            Statement selectAll = connection.createStatement();
            ResultSet allResult = selectAll.executeQuery(query);
            // now fill up our arrays
            while (allResult.next()) {
                movie_names.add(allResult.getString("movie_name"));
                show_times.add(allResult.getTimestamp("show_time"));
                seat_nums.add(allResult.getInt("seat_num"));
            }
            selectAll.close();
        } catch (Exception e) {
            disconnect();
            return null;
        }
        disconnect();
        // assuming size of all 3 arrays are the same, so can make that many tickets and append
        int n = movie_names.size();
        ArrayList<Ticket> tickets = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            // first make a Date object from Timestamp so can call getTicket
            Date showTime = Date.from(show_times.get(i).toInstant());
            // now just call getTicket and append
            tickets.add(getTicket(movie_names.get(i), showTime, seat_nums.get(i)));
        }
        return tickets;
    }
    
    /**
     * Adds a new ticket to the database.
     * @param movieName name of the movie
     * @param showTime the show time of the movie
     * @param seatNum the seat number the ticket reserves
     * @return true if ticket was added to the database. false if the operation failed.
     */
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
            int rowsChanged = insertTicket.executeUpdate();
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

        // need to sync up allTickets
        allTickets = fetchAllTickets();
        return true;
    }

    /**
     * Adds a new ticket to the database.
     * @param ticket the ticket to add the the database
     * @return true of ticket was added to the database. false if the operation failed.
     */
    public static boolean addTicket(Ticket ticket) {
        return addTicket(ticket.getMovie().getMovieName(), ticket.getTime(), ticket.getSeatNum());
    }

    /**
     * Removes the ticket from the database
     * @param movieName the name of the movie
     * @param showTime the time of the showing of the movie
     * @param seatNum the seat number the ticket reserves
     * @return true if the row was removed from the database. false if the operation failed.
     */
    public static boolean removeTicket(String movieName, Date showTime, int seatNum) {
        // if fail to connect, failed to remove ticket
        if (!connect()) {
            return false;
        }

        String query = String.format("DELETE FROM %s WHERE movie_name = ? AND show_time = ? AND seat_num = ?", 
        TABLE);
        // create TimeStamp object from showTime
        Timestamp show_time = new Timestamp(showTime.getTime());
        // round down seconds to 0
        show_time.setTime(roundSecondsDown(show_time.getTime()));
        try {
            PreparedStatement deleteTicket = connection.prepareStatement(query);
            // set values
            deleteTicket.setString(1, movieName);
            deleteTicket.setTimestamp(2, show_time);
            deleteTicket.setInt(3, seatNum);
            // statement is ready to execute
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
        // need to sync up allTickets
        allTickets = fetchAllTickets();
        return true;
    }

    /**
     * Removes the ticket from the database.
     * @param ticket the ticket to remove from the database
     * @return true if the ticket was removed from the database. false if the operation failed.
     */
    public static boolean removeTicket(Ticket ticket) {
        return removeTicket(ticket.getMovie().getMovieName(), ticket.getTime(), ticket.getSeatNum());
    }

    /**
     * Gets a ticket from the database.
     * @param movieName the name of the movie
     * @param showTime the show time of the movie
     * @param seatNum the seat number that the ticket reserves.
     * @return the ticket object retrieved from the database.
     */
    public static Ticket getTicket(String movieName, Date showTime, int seatNum) {
        // if fail to connect, can't return a Ticket object
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT movie_name from %s WHERE movie_name = ? AND show_time = ? AND seat_num = ?", 
        TABLE);
        //create TimeStamp object from date
        Timestamp show_time = Timestamp.from(showTime.toInstant());
        show_time.setTime(roundSecondsDown(show_time.getTime()));   //round seconds down to 0
        // first need to see if ticket actually exists in database
        String movie_name = null;
        try {
            PreparedStatement fetchTicket = connection.prepareStatement(query);
            // set values
            fetchTicket.setString(1, movieName);
            fetchTicket.setTimestamp(2, show_time);
            fetchTicket.setInt(3, seatNum);
            // statement ready to execute
            ResultSet foundTicket = fetchTicket.executeQuery();

            //expecting 1 Ticket/row to be found
            if (foundTicket.next()) {
                movie_name = foundTicket.getString(1);  //column 1 is movie_name
            }

            fetchTicket.close();
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

    public static void main(String[] args) {
        //testing TicketDatabaseReader
        String movieName = "m1";
        Date showTime = new Date();
        showTime.setTime(roundSecondsDown(showTime.getTime()));

        MovieDatabaseReader.addMovie(movieName, showTime, showTime);
        
        System.out.println(addTicket(movieName, showTime, 1));

        System.out.println(addTicket(movieName, showTime, 3));

        //System.out.println(removeTicket(movieName, showTime, 1));
        
        Ticket t1 = getTicket(movieName, showTime, 3);
        System.out.println(t1.getMovie().getMovieName());
        System.out.println(t1.getTime().toString());
        System.out.println(t1.getSeatNum());
        
        System.out.println();
        ArrayList<Ticket> tickets = getAllTickets();
        for (Ticket ticket : tickets) {
            System.out.println(ticket.getMovie().getMovieName());
            System.out.println(ticket.getTime().toString());
            System.out.println(ticket.getSeatNum());
        }
        // all methods works as intended
        // at the moment, no sync function to sync up any changes to database to allTickets or vice versa
    }
}
