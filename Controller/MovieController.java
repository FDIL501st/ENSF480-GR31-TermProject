package Controller;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import Database.MovieDatabaseReader;
import Model.Movie;
public class MovieController extends Controller{
    public MovieController(){
        super.data = new MovieDatabaseReader();
    }
    public DefaultListModel<String> getAllMovies(){
        //ArrayList<Movie> movies = data.getMovies();
        DefaultListModel<String> names = new DefaultListModel<>();
        /* 
        for(int i=0;i<movies.size();i++){
            names.addElement(movies.get(i).getMovieName());
        }
        */
        names.addElement("m1");
        names.addElement("m2");
        names.addElement("m3");
        names.addElement("m4");
        names.addElement("m5");
        names.addElement("m6");
        return names;
    }
    public DefaultListModel<String> getNewMovies(){
        //ArrayList<Movie> movies = data.getMovies();
        DefaultListModel<String> names = new DefaultListModel<>();
        /* 
        for(int i=0;i<movies.size();i++){
            if(movies.get(i).regularAnnoucement()!= null){
                names.addElement(movies.get(i).getMovieName());
            }
        }
        */
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
