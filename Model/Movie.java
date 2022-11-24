package Model;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class Movie {
    private String movieName;
    private String movieGenre;
    private Date releaseDate;
    private ArrayList<Date> availableTimes;
    public Movie(String name,String genre,String releaseDate,ArrayList<String> times)  throws ParseException{
        movieName = name;
        movieGenre = genre;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
        this.releaseDate =formatter.parse(releaseDate);
        formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm",Locale.ENGLISH);
        for(int i=0;i<times.size();i++){
            availableTimes.add(formatter.parse(times.get(i)));
        }
    }
    public String getMovieName(){
        return movieName;
    }
    public String getMovieGenre(){
        return movieGenre;
    }
}
