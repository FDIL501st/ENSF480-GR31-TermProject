package Controller;
import java.util.ArrayList;
import java.util.Date;


import Database.MovieDatabaseReader;

public class TicketController extends Controller{
    
    public  static ArrayList<Integer> getSeats(String movieName, Date showTime) {
        /* 
      return MovieDatabaseReader.getSeats(movieName,time);
      */
      //test data
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
        
      
    }
    
}
