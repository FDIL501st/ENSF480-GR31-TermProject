package Model;
import java.util.Date;
import java.util.concurrent.TimeUnit;
public class Payment {
    private Date paymentTime; //time of payment
    private Ticket ticket; 
    public Payment(Ticket t){
        paymentTime = new Date(); //set payment time to be the current time
        ticket = t;
    }
    public Date getPaymentTime(){
        return paymentTime;
    }
    public Ticket getTicket(){
        return ticket;
    }
        /**
     * Check if ticket can be cancelled
     * @return returns boolean value true if ticket is within 3 days of show time,
     * false if not
     */
    public boolean cancelStatus(){ 
       if(TimeUnit.DAYS.convert( ticket.getTime().getTime()- paymentTime.getTime(),TimeUnit.MILLISECONDS) <3){
            return false;
       }
        
        return true;
    }
}
