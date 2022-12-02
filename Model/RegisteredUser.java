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
    private Date registrationDate;
    private int yearsRegistered;//keeps track of which year the annual payment is for
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
        this.registrationDate = new Date();
        this.yearsRegistered = 1;
        this.email = email;
        this.password = password;
        firstName = fName;
        lastName = lName;
        this.address = address;
        this.cardNumber = cardNum;
        super.registrationStatus = true;
    }
    public RegisteredUser(String email,String password,String fName,String lName,String address,String cardNum,Date registrationDate){
        this.registrationDate = registrationDate;
        this.yearsRegistered = 1;
        this.dateLastPayed = new Date();
        while(TimeUnit.DAYS.convert(new Date().getTime()-registrationDate.getTime(),TimeUnit.MILLISECONDS)>yearsRegistered*365){
            yearsRegistered++;
        }
        this.email = email;
        this.password = password;
        firstName = fName;
        lastName = lName;
        this.address = address;
        this.cardNumber = cardNum;
        super.registrationStatus = true;
    }
    public Date getRegistrationDate(){
        return registrationDate;
    }
    /**
     * Check if movie is ready for RegisteredUser announcement
     * @return boolean value true if annual payment has never been completed or payment for the year has not been completed
     * false if payment has already been completed for the year
     */
    public boolean checkAnnualFee(){ 
        if(dateLastPayed == null){
            return true;
        }
        if(TimeUnit.DAYS.convert( dateLastPayed.getTime()- registrationDate.getTime(),TimeUnit.MILLISECONDS)>=yearsRegistered*365){
            yearsRegistered++;
            return true;
        }
        
            return false;
    }
}
