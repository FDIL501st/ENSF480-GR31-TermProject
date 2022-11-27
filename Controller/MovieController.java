package Controller;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;

import Database.MovieDatabaseReader;
import Model.Movie;
public class MovieController extends Controller{
    

    public void getAllShowTimes(String movieName,DefaultListModel<String> times){
       
        /* 
        ArrayList<Date> showTimes = MovieDatabaseReader.getAllShowTimes(movieName);
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
    public DefaultListModel<String> getAllMovies(){
        //ArrayList<Movie> movies = MovieDatabaseReader.getAllMovies();
        DefaultListModel<String> names = new DefaultListModel<>();
        /* 
        for(int i=0;i<movies.size();i++){
            names.addElement(movies.get(i).getMovieName());
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
    public DefaultListModel<String> getNewMovies(){
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
    public void addRUAnnoucement(DefaultListModel<String> names){
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
