package Model;
import java.util.Date;

public class Ticket {
    private Movie movie;
    private Date time;
    private int seatNum;
    private int roomNum;
    private int ID;
    private static int counter = 0; 
    public Ticket(Movie movie,Date timeSelected,int seatNum,int roomNum) {
       this.time = timeSelected;
        this.movie = movie;
        this.seatNum = seatNum;
        this.roomNum = roomNum;
        this.ID = counter; 
        counter++;

    }
    public Ticket(Movie movie,Date timeSelected,int seatNum,int roomNum,int ID) {
        this.time = timeSelected;
         this.movie = movie;
         this.seatNum = seatNum;
         this.roomNum = roomNum;
        this.ID = ID;
        counter = ID+1;
     }
    public int getRoomNum(){
        return roomNum;
    }
    public Movie getMovie(){
        return movie;
    }
    public Date getTime(){
        return time;
    }
    public int getSeatNum(){
        return seatNum;
    }
    public int getID(){
        return ID;
    }
}
