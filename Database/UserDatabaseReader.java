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
            disconnect();
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

        String query = String.format("SELECT * FROM %s WHERE email=%s", TABLE, email);

        Statement fetchUser = null;

        try {

            fetchUser = connection.createStatement();
            if (!fetchUser.execute(query)) {
                throw new SQLException();   //to return false
            }
            fetchUser.close();   
        } catch (SQLException e) {
            disconnect();
            return false;
        }
        while (!disconnect()) {}    // TODO - change in future
        return true;
    }

    public static boolean addUser(RegisteredUser user) {
        // No need to add an already existing user
        if (userExist(user.getEmail())) {
            return false;   
        }
        // attempt to connect to database
        if (!connect()) {
            return false;
        }

        String query = String.format("INSERT INTO %s VALUES (%s, %s, %s, %s, %s, %s)",
        TABLE, user.getEmail(), user.getPassword(), user.getfirstName(), 
        user.getlastName(), user.getAddress(), user.getCardNumber());

        Statement insertUser = null;
        
        try {
            insertUser = connection.createStatement();
            int count = insertUser.executeUpdate(query);
            insertUser.close();
            // if count is 0, then new user was not added
            // so make it throw an SQLException so it can be caught and false be returned
            if (count == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            disconnect();
            return false;
        }

        disconnect();
        return true;
    }

    public static boolean removeUser(String email) {
        return true;
    }

    public static boolean updateUser(RegisteredUser updatedUser) {
        return true;
    }
}
