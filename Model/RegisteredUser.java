package Model;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class RegisteredUser extends User{
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String cardNumber;
	private double annualFee;
	private Date dateLastPayed;
    
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
	public double getannualFee(){
		return annualFee;
	}
	public Date getDateLastPayed(){
		return dateLastPayed;
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
}
