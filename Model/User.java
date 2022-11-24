package Model;
public class User {
    private Ticket ticket;
    private Payment payment;
    protected boolean registrationStatus;
    public User(){
        registrationStatus = false;
    }
    public Ticket getTicket(){
        return ticket;
    }
    public Payment getPayment(){
        return payment;
    }
    public boolean getRegistrationStatus() {
        return registrationStatus;
    }
}
