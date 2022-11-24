package Model;
import java.util.ArrayList;
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
    public boolean register(String username,String password,String fName,String lName,String address,String cardNum){
        for(RegisteredUser u:users){
            if(u.getUsername().equals(username)){
                return false;
            }
        }
        users.add(new RegisteredUser(username,password,fName,lName,address,cardNum));
        return true;
    }
    public RegisteredUser checkDuplicate(String username,String password){
        for(RegisteredUser u: users){
            if(u.getUsername().equals(username)&&(u.getPassword().equals(password))){
                return u;
            }
        }
        return null;
    }
}
