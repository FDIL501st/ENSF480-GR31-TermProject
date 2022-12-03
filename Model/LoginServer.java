package Model;
import java.util.ArrayList;

import Controller.UserController;
public class LoginServer {

    private static LoginServer instance; //only instance of login server
    private static ArrayList<RegisteredUser> users= new ArrayList<RegisteredUser>(); //list of users
    public static UserController uc = new UserController(); //controller to update database
    
    private LoginServer(){
        users = UserController.getAllUsers(); 
    }
    public static LoginServer getInstance(){ //make sure only one instance is accessed
        if(instance == null){
            instance = new LoginServer();
        }
        return instance;
    }
     /**
     * Validates the login information given as arguments
     * @param email email for registeredUser
     * @param password password for registeredUser
     * @param fName first name for registeredUser
     * @param lName last name for registeredUser
     * @param address address for registeredUser
     * @param cardNum cardNumber for registeredUser
     * @return a boolean value true if registration is successful and false
     * if email is already registered
     */
    public boolean register(String email,String password,String fName,String lName,String address,String cardNum){ 
        for(RegisteredUser u:users){ //check if email is already used
            if(u.getEmail().equals(email)){
                return false;
            }
        }
        RegisteredUser r = new RegisteredUser(email,password,fName,lName,address,cardNum); //create new registereduser
        users.add(r); //add to user list
        uc.add(r); //update database through usercontroller
        
        return true;
    }
        /**
     * Attempts to register a user into the loginserver
     * @param email email for registeredUser
     * @param password password for registeredUser
     * @return a RegisteredUser object if login information is correct 
     * and null if login information is incorrect
     */
    public RegisteredUser validate(String email,String password){ //check if login information is correct
        for(RegisteredUser u: users){ 
            if(u.getEmail().equals(email)&&(u.getPassword().equals(password))){ //return user object if login information is correct
                return u;
            }
        }
        return null; //return null if login information is incorrect
    }
}
