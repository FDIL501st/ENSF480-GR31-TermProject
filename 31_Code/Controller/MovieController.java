package Controller;

import java.util.ArrayList;



import Database.MovieDatabaseReader;
import Model.Movie;

public class MovieController extends Controller{
    

    public static ArrayList<Movie> getAllMovies() {
        return MovieDatabaseReader.getAllMovies();

    }
    @Override
    public void add(Object o) {
        Movie m = (Movie) o;
        for(int i=0;i<m.getAvailableTimes().size();i++){
            MovieDatabaseReader.addMovie(m.getMovieName(),m.getAvailableTimes().get(i),m.getReleaseDate());
        }
        
    }
    @Override
    public void remove(Object o) {
        Movie m = (Movie) o;
        for(int i=0;i<m.getAvailableTimes().size();i++){
            MovieDatabaseReader.removeMovie(m.getMovieName(),m.getAvailableTimes().get(i));
        }
    }
}
