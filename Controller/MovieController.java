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
        //return MovieDatabaseReader.getAllMovies();
        ArrayList<Movie> names = new ArrayList<Movie>();
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
        names.add(new Movie("m5","06-01-2023", times2));
        names.add(new Movie("m6","06-01-2023", times2));
        return names;
    }
    @Override
    public void add(Object o) {
        Movie m = (Movie) o;
        MovieDatabaseReader.addMovie(m.getMovieName(),new Date(),m.getReleaseDate());
        
    }
    @Override
    public void remove(Object o) {
        Movie m = (Movie) o;
        MovieDatabaseReader.removeMovie(m.getMovieName(),new Date());
    }
}
