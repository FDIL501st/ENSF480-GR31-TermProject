package Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Iterator;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Model.Movie;

public class MovieDatabaseReader extends DatabaseReader{
    private static String TABLE = "theatre1";
    final private static int NUM_OF_SEATS = 100;

    /**
     * Sets the table accessed by this class. Is to be set when wanting to access movies from a specific theatre.
     * Table names are the names of the theatre.
     * @param theatreName name of theatre whose database/table we need to access
     */
    public static void setTable(String theatreName) {
        TABLE = theatreName;
    }

    /**
     * Gets all show times of a movie in a theatre.
     * @param movieName the name of the movie whose show times are wanted
     * @return 
     */
    public static ArrayList<Date> getAllShowTimes(String movieName) {
        // return nothing if failed to connect
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT show_time FROM %s WHERE movie_name = ?",
        TABLE);

        ArrayList<Date> showTimes = new ArrayList<>();
        try {
            PreparedStatement fetchAllTimes = connection.prepareStatement(query);
            // insert movie_name
            fetchAllTimes.setString(1, movieName);
            ResultSet allTimes = fetchAllTimes.executeQuery();

            while (allTimes.next()) {
                Date showTime = allTimes.getTimestamp("show_time");
                showTimes.add(showTime);
            }

            fetchAllTimes.close();

        } catch (SQLException e) {
            disconnect();
            return null;
        }

        disconnect();
        return showTimes;
    }

    /**
     * Gets all 50 seats of a movie for one of its show time.
     * @param movieName name of movie 
     * @param showTime the show time of the movie
     * @return An array of integers, which index 0 is the room number, 
     * and the rest are 1 or 0 representing if a seat is available or not
     */
    public static ArrayList<Integer> getSeats(String movieName, Date showTime) {
        
        // return nothing if failed to connect
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT * FROM %s WHERE movie_name=%s, show_time=%t", 
        TABLE, movieName, showTime);

        ArrayList<Integer> seats = new ArrayList<>(NUM_OF_SEATS);
        try {
            Statement fetchSeats = connection.createStatement();
            ResultSet allSeats = fetchSeats.executeQuery(query);

            // only expecting one row to be returned, so use only first row
            if (allSeats.next()) {
                // loop through all the seats of the row
                for (int i = 1; i <=NUM_OF_SEATS; i++) {
                    String seatNum = "seat" + Integer.toString(i);
                    Integer seatStatus = allSeats.getInt(seatNum);  //call colum by name
                    seats.add(seatStatus);
                }
            }

            fetchSeats.close();

        } catch (SQLException e) {
            disconnect();
            return null;
        }

        disconnect();
        return seats;
    }

    /**
     * Gets a movie from the database
     * @param movieName name of movie 
     * @return Reference to movie object
     */
    public static Movie getMovie(String movieName) {
        //if fail to connect, got no movie object to return
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT release_date FROM %s WHERE movie_name = ?", 
        TABLE);

        Date releaseDate = null;
        try {
            PreparedStatement fetchMovie = connection.prepareStatement(query);
            //insert movie_name
            fetchMovie.setString(1, movieName);
            ResultSet movies = fetchMovie.executeQuery();

            // all release dates for the movie should be the same, so only need to read 1
            if (movies.next()) {
                releaseDate = movies.getTimestamp("release_date");
            }

            fetchMovie.close(); //also closes movies
        } catch (SQLException e) {
            disconnect();
            return null;
        }

        //at this point have movie_name and release_date, now need all showTimes of the movie to make Movie object
        // first need to convert to String
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String release_date = null;
        if (releaseDate != null) {
            release_date = formatter.format(releaseDate);
        }
        disconnect();

        // now can go get all showTimes
        ArrayList<Date> allShowTimes = getAllShowTimes(movieName);

        //now need to convert to String all the showTimes
        Iterator<Date> showTimeIterator = allShowTimes.iterator();
        ArrayList<String> allShowTimesString = new ArrayList<>();
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        while (showTimeIterator.hasNext()) {
            allShowTimesString.add(formatter.format(showTimeIterator.next()));
        }

        //Now got all parameters ready to make Movie object to return
        try {
            return new Movie(movieName, release_date, allShowTimesString);
        } catch (ParseException e) {
            // failed to create Movie object, so can't return one
            return null;
        }
        
    }

    /**
     * gets all the movies stored in the database
     * @return an ArrayList filled with Movie objects for all the movies
     */
    public static ArrayList<Movie> getAllMovies() {
        //if fail to connect, can't return anything
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT movie_name FROM %s", TABLE);

        ArrayList<Movie> allMovies = new ArrayList<>();
        ArrayList<String> movieNames = new ArrayList<>();
        
        try {
            Statement fetchAllMovies = connection.createStatement();
            ResultSet movies = fetchAllMovies.executeQuery(query);

            while (movies.next()) {
                String movie_name = movies.getString("movie_name");   
                if (movieNames.contains(movie_name)) {
                    continue;   //no need to get more info as already done so
                }
                movieNames.add(movie_name);
                
            }
            fetchAllMovies.close(); //also closes movies 
        } catch (SQLException e) {
            disconnect();
            return null;
        }
        disconnect();

        //Now go call getMovie on each unique movie in our table
        Iterator<String> movieNamesIterator = movieNames.iterator();
        while (movieNamesIterator.hasNext()) {
            allMovies.add(getMovie(movieNamesIterator.next()));
        }
        
        return allMovies;
    }

    public static boolean updateSeat(String movieName, Date showTime, int seatNum, int seatStatus) {
        //if fail to connect, unable to update seat
        if (!connect()) {
            return false;
        }

        String query = String.format(
            "UPDATE %s SET seat%d=%d WHERE movie_name=%s, show_time=%s",
            TABLE, seatNum, seatStatus, movieName, showTime);

        try {
            Statement seatUpdate = connection.createStatement();
            int rowsChanged = seatUpdate.executeUpdate(query);
            seatUpdate.close();
            //if rowsChanged == 0, seat was not updated
            // so throw a SQLException so it can be thrown so we can return false
            if (rowsChanged == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            disconnect();
            return false;
        }
        disconnect();
        return true;
    }

    public static boolean removeMovie(String movieName, Date showTime) {
        // if can't connect, failed to remove movie
        if (!connect()) {
            return false;
        }

        String query = String.format("DELETE FROM %s WHERE movie_name= ? AND show_time= ?", 
        TABLE);
        // create TimeStamp object from Date object to later set
        Timestamp showDate = new Timestamp(showTime.getTime());
        try {
            PreparedStatement deleteMovie = connection.prepareStatement(query);
            //set values
            deleteMovie.setString(1, movieName);
            deleteMovie.setTimestamp(2, showDate);
            // statment ready to execute
            int rowsChanged = deleteMovie.executeUpdate();
            deleteMovie.close();
            // if rowsChanged is 0, then did not delete movie
            // throw SQLException is it can be caught and false be returned
            if (rowsChanged == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            disconnect();
            return false;
        }

        disconnect();
        return true;
    }

    public static boolean addMovie(String movieName, Date showTime, Date releaseDate) {
        // failed to add movie if can't connect
        if (!connect()) {
            return false;
        }
        
        String query = String.format("INSERT INTO %s (movie_name, show_time, release_date) VALUES (?, ?, ?)",
        TABLE);
        // need to create from Date objects to TimeStamp objects
        Timestamp showDate = new Timestamp(showTime.getTime());
        Timestamp relDate = new Timestamp(releaseDate.getTime());
        try {
            PreparedStatement insertMovie = connection.prepareStatement(query);
            // set values
            insertMovie.setString(1, movieName);
            insertMovie.setTimestamp(2, showDate);
            insertMovie.setTimestamp(3, relDate);
            // statement complete to execute  
            int rowsChanged = insertMovie.executeUpdate();
            insertMovie.close();
            // if rowsChanged == 0, new movie was not added
            //  so throw SQLException so can return false
            if (rowsChanged == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            disconnect();
            return false;
        }
        disconnect();
        return true;
    }

    public static void main(String[] args) {
        /* Testing this class */
        Date show = new Date();
        System.out.println(show.toString());
        Date release = new Date();
        System.out.println(release.toString());
        String movieName = "Movie1";

        System.out.println(MovieDatabaseReader.addMovie(movieName, show, release));

        Movie movie1 = MovieDatabaseReader.getMovie(movieName);
        if (movie1 != null)
            System.out.println(movie1.getMovieName());
            System.out.println(movie1.releaseDate().toString());

            Iterator<Date> iterator = movie1.getAvailableTimes().iterator();
            while (iterator.hasNext()) 
                System.out.println(iterator.next().toString());
        
        System.out.println(MovieDatabaseReader.removeMovie(movieName, show));
    }
}
