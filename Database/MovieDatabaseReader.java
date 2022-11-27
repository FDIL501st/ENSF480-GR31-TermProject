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
    final private static int NUM_OF_SEATS = 30;

    public static void setTable(String theatreName) {
        TABLE = theatreName;
    }

    public static ArrayList<Date> getAllShowTimes(String movieName) {
        // return nothing if failed to connect
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT show_time FROM %s WHERE movie_name=%s",
        TABLE, movieName);

        ArrayList<Date> showTimes = new ArrayList<>();
        try {
            Statement fetchAllTimes = connection.createStatement();
            ResultSet allTimes = fetchAllTimes.executeQuery(query);

            while (allTimes.next()) {
                Date showTime = allTimes.getDate("show_time");
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

    public static ArrayList<Integer> getSeats(String movieName, Date showTime) {
        
        // return nothing if failed to connect
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT * FROM %s WHERE movie_name=%s, time=%t", 
        TABLE, movieName, showTime);

        ArrayList<Integer> seats = new ArrayList<>();
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
            allSeats.close();

        } catch (SQLException e) {
            disconnect();
            return null;
        }

        disconnect();
        return seats;
    }

    public static Movie getMovie(String movieName) throws ParseException{
        //if fail to connect, got no movie object to return
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT release_date FROM %s WHERE movie_name=%s", 
        TABLE, movieName);

        Date releaseDate = null;
        try {
            Statement fetchMovie = connection.createStatement();
            ResultSet movies = fetchMovie.executeQuery(query);

            // all release dates for the movie should be the same, so only need to read 1
            if (movies.next()) {
                releaseDate = movies.getDate("release_date");
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
        formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.ENGLISH);
        while (showTimeIterator.hasNext()) {
            allShowTimesString.add(formatter.format(showTimeIterator.next()));
        }

        //Now got all parameters ready to make Movie object to return
        return new Movie(movieName, release_date, allShowTimesString);
    }

    public static ArrayList<Movie> getAllMovies() throws ParseException{
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

        String query = String.format("DELETE FROM %s WHERE movie_name=%s, time=%s", 
        TABLE, movieName, showTime);

        try {
            Statement deleteMovie = connection.createStatement();
            int rowsChanged = deleteMovie.executeUpdate(query);
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

    public static boolean addMovie(String movieName, Date showTime, Date releaseDate, ArrayList<Integer> seats) {
        // failed to add movie if can't connect
        if (!connect()) {
            return false;
        }
        
        String query = String.format("INSERT INTO %s VALUES (%s, %t, %t",
        TABLE, movieName, showTime, releaseDate);
        //to the query, need to add rest of the seats
        Iterator<Integer> seatsIterator = seats.iterator();
        int i = 0;  //secondary loop tracker so don't exceed number of seats stored
        while (seatsIterator.hasNext() && i < NUM_OF_SEATS) {
            int seatStatus = seatsIterator.next();
            query += String.format(", %d", seatStatus);
            i++;
        }
        query += ")";   //close off parenthesis for sql syntax
        //query has been made
        
        try {
            Statement movieAdd = connection.createStatement();
            int rowsChanged = movieAdd.executeUpdate(query);
            movieAdd.close();
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
}
