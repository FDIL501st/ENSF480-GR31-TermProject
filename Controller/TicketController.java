package Controller;
import java.util.ArrayList;
import java.util.Date;


import Database.MovieDatabaseReader;
import Database.TicketDatabaseReader;
import Database.CodeDatabaseReader;
import Model.Ticket;

public class TicketController extends Controller{
    
    public  static ArrayList<Integer> getSeats(String movieName, Date showTime) {
      return MovieDatabaseReader.getSeats(movieName,showTime);
      
      //test data
      /* 
      ArrayList<Integer> seats = new ArrayList<Integer>(100);
        seats.add(10);
      for(int i=1;i<100;i++){
        if(i%2 ==0){
            seats.add(1);

        }
        else{
            seats.add(0);
        }
      }
      return seats;
        */
      
    }
    public static int makeNewCode(double value){
      return CodeDatabaseReader.makeNewCode(value);
    }
    public static double getCodeValue(int code){
      return CodeDatabaseReader.getCodeValue(code);
    }
    @Override
    public void add(Object o) {
      Ticket t = (Ticket)o;
      TicketDatabaseReader.addTicket(t);
      MovieDatabaseReader.updateSeat(t.getMovie().getMovieName(),t.getTime(),t.getSeatNum(),0); //update the seat
      
    }

    @Override
    public void remove(Object o) {
      Ticket t=(Ticket)o;
      TicketDatabaseReader.removeTicket(t);
      MovieDatabaseReader.updateSeat(t.getMovie().getMovieName(),t.getTime(),t.getSeatNum(),1);
    }
    
}
