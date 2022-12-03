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
     * Setter for allTickets. 
     * This setter also syncs database up with the provided arrayList.
     * Then sets allTickets variable.
     * This setter won't set the varible if the syncing with database fails at any point
     * @param newAllTickets
     */
    public static void setAllTickets(ArrayList<Ticket> newAllTickets) {
        // now need to sync up with database
        // syncing is clear, first remove all elements, then add all elements in array
        if (!connect()) {
            return;
        }
        String deleteQuery = String.format("DELETE FROM %s", TABLE);
        try {
            Statement delateAll = connection.createStatement();
            delateAll.executeUpdate(deleteQuery);
            delateAll.close();
        } catch (SQLException e) {
            disconnect();
            return;
        }
        // to save time with connecting, won't disconnect and use same connection
        for (Ticket ticket : newAllTickets) {
            removeTicket(ticket);
        }
        disconnect();
        allTickets = newAllTickets; 
        // do this last as keep allTickets same as database, so if database sync failed, don't sync this up
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
        ArrayList<Integer> ticket_IDs = new ArrayList<>();

        try {
            Statement selectAll = connection.createStatement();
            ResultSet allResult = selectAll.executeQuery(query);
            // now fill up our arrays
            while (allResult.next()) {
                ticket_IDs.add(allResult.getInt("ID"));
            }
            selectAll.close();
        } catch (Exception e) {
            disconnect();
            return null;
        }
        disconnect();

        int n = ticket_IDs.size();
        ArrayList<Ticket> tickets = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            tickets.add(getTicket(ticket_IDs.get(i))); 
        }
        return tickets;
    }
    
    /**
     * Adds a new ticket to the database.
     * @param ID the ticket ID
     * @param movieName name of the movie
     * @param showTime the show time of the movie
     * @param seatNum the seat number the ticket reserves
     * @return true if ticket was added to the database. false if the operation failed.
     */
    public static boolean addTicket(int ID, String movieName, Date showTime, int seatNum) {
        // if fail to connect, failed to add new ticket
        if (!connect()) {
            return false;
        }

        String query = String.format("INSERT INTO %s (movie_name, show_time, seat_num, ID) VALUES (?, ?, ?, ?)", 
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
            insertTicket.setInt(4, ID);
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
        return addTicket(ticket.getID(), ticket.getMovie().getMovieName(), ticket.getTime(), ticket.getSeatNum());
    }

    /**
     * Removes the ticket from the database
     * @param ID the ticket ID
     * @return true if the row was removed from the database. false if the operation failed.
     */
    public static boolean removeTicket(int ID) {
        // if fail to connect, failed to remove ticket
        if (!connect()) {
            return false;
        }
        Ticket t1 = getTicket(ID);  //need this as after deleting need some info to update seat
        String deleteQuery = String.format("DELETE FROM %s WHERE ID = ?", 
        TABLE);
        
        try {
            PreparedStatement deleteTicket = connection.prepareStatement(deleteQuery);
            // set values
            deleteTicket.setInt(1, ID);
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
        MovieDatabaseReader.updateSeat(t1.getMovie().getMovieName(), t1.getTime(), t1.getSeatNum(), 1);
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
        return removeTicket(ticket.getID());
    }

    /**
     * Gets a ticket from the database.
     * @param ID the ticket ID
     * @return the ticket object retrieved from the database.
     */
    public static Ticket getTicket(int ID) {
        // if fail to connect, can't return a Ticket object
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT * from %s WHERE ID = ?", 
        TABLE);
        
        String movie_name = null;
        Timestamp show_time = null;
        int seatNum = 0;
        try {
            PreparedStatement fetchTicket = connection.prepareStatement(query);
            // set values
            fetchTicket.setInt(1, ID);
            // statement ready to execute
            ResultSet foundTicket = fetchTicket.executeQuery();

            //expecting 1 Ticket/row to be found
            if (foundTicket.next()) {
                movie_name = foundTicket.getString(1);  //column 1 is movie_name
                show_time = foundTicket.getTimestamp(2); // column 2 is show_time
                seatNum = foundTicket.getInt(3);    //colum 3 is seat_num
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
        Movie movie = MovieDatabaseReader.getMovie(movie_name);

        // Now get a Date object from Timestamp
        Date showTime = Date.from(show_time.toInstant());

        // now got all info to return a Ticket object
        return new Ticket(movie, showTime, seatNum, 1, ID);
    }

    public static void main(String[] args) {
        //testing TicketDatabaseReader
        String movieName = "m1";
        Date showTime = new Date();
        showTime.setTime(roundSecondsDown(showTime.getTime()));

        MovieDatabaseReader.addMovie(movieName, showTime, showTime);
        
        System.out.println(addTicket(11, movieName, showTime, 1));

        System.out.println(addTicket(1, movieName, showTime, 3));

        //System.out.println(removeTicket(movieName, showTime, 1));
        
        Ticket t1 = getTicket(1);
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

        // testing chaining connects and disconnects without disconecting or connecting first
        System.out.println(connect());
        System.out.println(connect());
        System.out.println(disconnect());
        System.out.println(disconnect());
        // works as intended
    }
}
