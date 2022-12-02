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
	private Date dateLastPayed;
    private ArrayList<Payment> ticketsPaid = new ArrayList<Payment>();
    
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
    public ArrayList<Payment> getPaidTickets(){
        return ticketsPaid;
    }
    public void updatePayments(ArrayList<Payment> payments){
        ticketsPaid.clear();
        for(Payment p:payments){
            ticketsPaid.add(new Payment(p.getTicket()));
        }
    }
    public RegisteredUser(String email,String password,String fName,String lName,String address,String cardNum){
        dateLastPayed = new Date();
        this.email = email;
        this.password = password;
        firstName = fName;
        lastName = lName;
        this.address = address;
        this.cardNumber = cardNum;
        super.registrationStatus = true;
    }
    public boolean checkAnnualFee(){
        if(TimeUnit.DAYS.convert( new Date().getTime()- dateLastPayed.getTime(),TimeUnit.MILLISECONDS)>=365){
            return true;
        }
            return false;
    }
}
