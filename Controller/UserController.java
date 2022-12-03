package Controller;

import java.util.ArrayList;

import Database.UserDatabaseReader;
import Model.RegisteredUser;

public class UserController extends Controller{
    public static ArrayList<RegisteredUser> getAllUsers(){
        return UserDatabaseReader.getAllUsers(); //get all users from database
        //return new ArrayList<RegisteredUser>();
    }
    @Override
    public void add(Object o) {
    
        RegisteredUser ru = (RegisteredUser)o;
        UserDatabaseReader.addUser(ru);
        
        
        
    }
    @Override
    public void remove(Object o) {
        
        RegisteredUser ru = (RegisteredUser)o;
        UserDatabaseReader.removeUser(ru);
        
    }
}
