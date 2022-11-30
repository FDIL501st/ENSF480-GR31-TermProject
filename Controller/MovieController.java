package Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.DefaultListModel;

import Database.MovieDatabaseReader;
import Model.Movie;
public class MovieController extends Controller{
    

    public static ArrayList<Movie> getAllMovies() throws ParseException {
        //ArrayList<Movie> movies = MovieDatabaseReader.getAllMovies();
        ArrayList<Movie> names = new ArrayList<Movie>();
        /* 
        for(int i=0;i<movies.size();i++){
            names.addElement(movies.get(i).getMovieName());
        }
        */
        //test data
        ArrayList<String> times = new ArrayList<>();
     
            times.add("01-12-2022 12:50");
            times.add("01-12-2022 1:50");
            times.add("01-12-2022 2:50");
            times.add("01-12-2022 3:50");
            times.add("01-12-2022 4:50");
        
    ArrayList<String> times1 = new ArrayList<>();
  
            times1.add("02-12-2022 12:50");
            times1.add("02-12-2022 1:50");
            times1.add("27-11-2022 2:50");
            times1.add("02-12-2022 3:50");
            times1.add("02-12-2022 4:50");
            ArrayList<String> times2= new ArrayList<>();
  
            times2.add("14-01-2023 12:50");
            times2.add("14-01-2023 1:50");
            times2.add("14-01-2023 2:50");
            times2.add("14-01-2023 3:50");
            times2.add("14-01-2023 4:50");
        
        

        names.add(new Movie("m1","02-12-2022", times));
        names.add(new Movie("m2","02-12-2022", times1));
        names.add(new Movie("m3","02-12-2022", times));
        names.add(new Movie("m4","02-12-2022", times1));
        names.add(new Movie("m5","10-01-2023", times2));
        names.add(new Movie("m6","10-01-2023", times2));
        return names;
    }
    public static DefaultListModel<String> getNewMovies(){
       // ArrayList<Movie> movies = MovieDatabaseReader.getAllMovie();
        DefaultListModel<String> names = new DefaultListModel<>();
        /* 
        for(int i=0;i<movies.size();i++){
            if(movies.get(i).regularAnnoucement()!= null){
                names.addElement(movies.get(i).getMovieName());
            }
        }
        */
        //test data
        names.addElement("m1");
        names.addElement("m2");
        names.addElement("m3");
        names.addElement("m4");
        names.addElement("m5");
        names.addElement("m6");
        return names;
    }
    public static void addRUAnnouncement(DefaultListModel<String> names){
        //ArrayList<Movie> movies = data.getMovies();
        //test data
        names.addElement("m7 (RU)");
        names.addElement("m8 (RU)");
        names.addElement("m9 (RU)");
        names.addElement("m10 (RU)");
          /* 
        for(int i=0;i<movies.size();i++){
            if(movies.get(i).RUAnnoucement()!= null){
                names.addElement(movies.get(i).getMovieName() + " (RU)");
            }
        }
        */
    }
}
