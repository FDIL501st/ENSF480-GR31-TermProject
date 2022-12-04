import Interface.HomePage;
import java.text.ParseException;
import Database.TicketDatabaseReader;


public class app {
    public static void main(String[] args) throws ParseException{
    HomePage.allTickets = TicketDatabaseReader.getAllTickets();
    HomePage hp = new HomePage();
    hp.start();
    }
}
