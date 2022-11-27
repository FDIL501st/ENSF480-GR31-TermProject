package Model;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
public class Movie {
    private String movieName;
    private String movieGenre;
    private Date releaseDate;
    private ArrayList<Date> availableTimes;
    public Movie(String name,String releaseDate,ArrayList<String> times)  throws ParseException{
        movieName = name;
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
    public String regularAnnoucement(){
        Date d = new Date();
        //check if releaseDate is within a month
        if(TimeUnit.DAYS.convert(releaseDate.getTime() - d.getTime(),TimeUnit.MILLISECONDS)>0 && TimeUnit.DAYS.convert(releaseDate.getTime() - d.getTime(),TimeUnit.MILLISECONDS)<=30){
            return movieName + " " + releaseDate.toString();
        }
        return null;
    }
    public String RUAnnoucement(){
        Date d = new Date();
        if(TimeUnit.DAYS.convert(releaseDate.getTime() - d.getTime(),TimeUnit.MILLISECONDS)>30 && TimeUnit.DAYS.convert(releaseDate.getTime() - d.getTime(),TimeUnit.MILLISECONDS)<=37){
            return movieName + " " + releaseDate.toString();
        }
        return null;
    }
}
