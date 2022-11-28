package Model;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class Ticket {
    private Movie movie;
    private Date time;
    private String status;
    private int seatNum;
    public Ticket(Movie movie,String date,int seatNum) throws ParseException{
        status = "Active";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm",Locale.ENGLISH);
        time = formatter.parse(date);
        this.movie = movie;
        this.seatNum = seatNum;
        
    }
    public Movie getMovie(){
        return movie;
    }
    public Date getTime(){
        return time;
    }
    public String getStatus(){
        return status;
    }
    public int getSeatNum(){
        return seatNum;
    }
}
