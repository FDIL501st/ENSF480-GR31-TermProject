package Controller;

import Model.LoginServer;
import Model.User;

public class UserController extends Controller{
    public static boolean register(String email,String password,String fName,String lName,String address,String cardNum){
        LoginServer lg = LoginServer.getInstance();
        if(lg.register(email, password, fName, lName, address,cardNum)){
            return true;
        }
        return false;
    }
    public static User validate(String email,String password){
        LoginServer lg = LoginServer.getInstance();
        if(lg.validate(email,password)!= null){
            return lg.validate(email,password);
        }
        return null;
    }
}
