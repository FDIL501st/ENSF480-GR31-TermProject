package Controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.DefaultListModel;

import Database.MovieDatabaseReader;

public class TicketController extends Controller{
    public static void getAllShowTimes(String movieName,DefaultListModel<String> times){
       
        /* 
        ArrayList<Date> showTimes = MovieDatabaseReader.getAllShowTimes(movieName);
        times.clear();
        for(int i=0;i<showTimes.size();i++){
            times.addElement(showTimes.get(i).toString());
        }
        */
        //test data
        times.clear();
        times.addElement("26-11-2022 12:50");
        times.addElement("26-11-2022 1:50");
        times.addElement("26-11-2022 2:50");
        times.addElement("26-11-2022 3:50");
        times.addElement("26-11-2022 4:50");
   

    }
    
    public  static ArrayList<Integer> getSeats(String movieName, String showTime) throws ParseException{
        /* 
        Date time;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm",Locale.ENGLISH);
        time = formatter.parse(showTime);
      return MovieDatabaseReader.getSeats(movieName,time);
      */
      //test data
      ArrayList<Integer> seats = new ArrayList<Integer>(100);
      for(int i=0;i<100;i++){
        if(i%2 ==0){
            seats.add(i);

        }
      }
      return seats;
        
      
    }
    
}
