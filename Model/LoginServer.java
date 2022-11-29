package Model;
import java.util.ArrayList;
import java.util.Date;
public class LoginServer {

    private static LoginServer instance;
    private ArrayList<RegisteredUser> users;
    private LoginServer(){
        users = new ArrayList<RegisteredUser>();
    }
    public static LoginServer getInstance(){
        if(instance == null){
            instance = new LoginServer();
        }
        return instance;
    }
    public boolean register(String email,String password,String fName,String lName,String address,String cardNum){
        for(RegisteredUser u:users){
            if(u.getEmail().equals(email)){
                return false;
            }
        }
        users.add(new RegisteredUser(email,password,fName,lName,address,cardNum));
        return true;
    }
    public RegisteredUser checkDuplicate(String email,String password){
        for(RegisteredUser u: users){
            if(u.getEmail().equals(email)&&(u.getPassword().equals(password))){
                return u;
            }
        }
        return null;
    }
}
