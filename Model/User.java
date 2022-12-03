package Model;

import java.util.ArrayList;

public class User {
    private ArrayList<Payment> ticketsPaid = new ArrayList<Payment>(); //list of tickets user has paid for
    protected boolean registrationStatus;
    public User(){
        registrationStatus = false;
    }
            /**
     * update the list of paid tickets for the user
     * @param payments ArrayList of payments the user has made
     */
    public void updatePayments(ArrayList<Payment> payments){
        ticketsPaid.clear();
        for(Payment p:payments){
            ticketsPaid.add(new Payment(p.getTicket()));
        }
    }
    public ArrayList<Payment> getPaidTickets(){
        return ticketsPaid;
    }
    public boolean getRegistrationStatus() {
        return registrationStatus;
    }
}
