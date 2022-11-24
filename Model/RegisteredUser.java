package Model;
public class RegisteredUser extends User{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String cardNumber;
	private double annualFee;
    
    public String getUsername(){
        return username;
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
    public RegisteredUser(String username,String password,String fName,String lName,String address,String cardNum){
        this.username = username;
        this.password = password;
        firstName = fName;
        lastName = lName;
        this.address = address;
        this.cardNumber = cardNum;
        super.registrationStatus = true;
    }
}
