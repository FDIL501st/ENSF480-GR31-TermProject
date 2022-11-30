package Model;
import java.util.Date;
import java.util.concurrent.TimeUnit;
public class Payment {
    private Date paymentTime;
    private Ticket ticket;
    public Payment(Ticket t){
        paymentTime = new Date();
        ticket = t;
    }
    public Date getPaymentTime(){
        return paymentTime;
    }
    public Ticket getTicket(){
        return ticket;
    }
    public boolean cancelStatus(){
    //ticket is within 3 days of show time
       if(TimeUnit.DAYS.convert( ticket.getTime().getTime()- paymentTime.getTime(),TimeUnit.MILLISECONDS) <3){
            return false;
       }
        
        return true;
    }
}
