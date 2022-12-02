package Model;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
public class RegisteredUser extends User{
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String cardNumber;
	private Date dateLastPayed; //date of last annual payment
    private ArrayList<Payment> ticketsPaid = new ArrayList<Payment>(); //list of tickets user has paid for
    
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getfirstName(){
        return firstName;
    }
    public String getlastName(){
        return lastName;
    }
    public String getAddress(){
        return address;
    }
    public String getCardNumber(){
        return cardNumber;
    }
	public Date getDateLastPayed(){
		return dateLastPayed;
	}
    public void setDateLastPayed(Date d){
        dateLastPayed = d;
    }
    public ArrayList<Payment> getPaidTickets(){
        return ticketsPaid;
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
    public RegisteredUser(String email,String password,String fName,String lName,String address,String cardNum){
        this.email = email;
        this.password = password;
        firstName = fName;
        lastName = lName;
        this.address = address;
        this.cardNumber = cardNum;
        super.registrationStatus = true;
    }
    public RegisteredUser(String email,String password,String fName,String lName,String address,String cardNum, Date registrationDate){
        dateLastPayed = registrationDate;
        this.email = email;
        this.password = password;
        firstName = fName;
        lastName = lName;
        this.address = address;
        this.cardNumber = cardNum;
        super.registrationStatus = true;
    }
    /**
     * Check if movie is ready for RegisteredUser announcement
     * @return boolean value true if annual payment has never been completed or payment has not been completed in 365 days
     * false if payment has already been completed for the year
     */
    public boolean checkAnnualFee(){ 
        if(dateLastPayed == null){
            return true;
        }
        if(TimeUnit.DAYS.convert( new Date().getTime()- dateLastPayed.getTime(),TimeUnit.MILLISECONDS)>=365){
            return true;
        }
            return false;
    }
}
