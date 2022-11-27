package Database;

import java.text.ParseException;
import java.util.Date;
import Model.Ticket;

public class TicketDatabaseReader extends DatabaseReader {
    final private static String TABLE = "tickets";

    public static boolean addTicket(String movieName, Date showTime, int seatNum) {
        return true;
    }

    public static boolean removeTicket(String movieName, Date showTime, int seatNum) {
        return true;
    }

    public static Ticket getTicket(String movieName, Date showTime, int seatNum) throws ParseException{
        return new Ticket(null, showTime.toString());
    }
}
