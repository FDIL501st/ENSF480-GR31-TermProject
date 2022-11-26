package Database;

import java.sql.*;

import Model.RegisteredUser;

public class UserDatabaseReader extends DatabaseReader{
    final private static String TABLE = "registered_users";
    
    public static RegisteredUser getUser(String email, String password) {
        if (!connect()) {
            return null;    // TODO - Maybe throw an exception here to catch later
        }
        
        String query = String.format("SELECT * FROM %s WHERE email=%s, password=%s", TABLE, email, password);
        
        Statement fetchUser = null;
        ResultSet foundUser = null;
        RegisteredUser user = null;
        try {
            fetchUser = connection.createStatement();
            foundUser = fetchUser.executeQuery(query);

            if (foundUser.next()) {
                //make a new RegisteredUser object by getting data from row
                String fetchedEmail = foundUser.getString(1);
                String fetchedPassword = foundUser.getString(2);
                String firstName = foundUser.getString(3);
                String lastName = foundUser.getString(4);
                String address = foundUser.getString(5);
                String cardNumber = foundUser.getString(6);
                
                user = new RegisteredUser(fetchedEmail, fetchedPassword, firstName, lastName, address, cardNumber);  
            }
            foundUser.close();    
            
        } catch (SQLException e) {
            return null;    //unable to fecth user
        }
        
        while (!disconnect()) {}    //system is stuck if unable to close connection
        // TODO - change in future
        
        return user;
    }

    public static boolean userExist(String email) {
        if (!connect()) {
            return false; // TODO - Maybe throw an exception here to catch later
        }

        
        while (!disconnect()) {}    // TODO - change in future
        return true;
    }

    public static boolean addUser(RegisteredUser user) {
        if (userExist(user.getEmail())) {
            return false;   // TODO - Maybe throw an exception here to catch later
        }
        return true;
    }

    public static boolean removeUser() {
        return true;
    }
}
