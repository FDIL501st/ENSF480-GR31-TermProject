package Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;



import Database.MovieDatabaseReader;
import Model.Movie;
public class MovieController extends Controller{
    

    public static ArrayList<Movie> getAllMovies() throws ParseException {
        return MovieDatabaseReader.getAllMovies();

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
