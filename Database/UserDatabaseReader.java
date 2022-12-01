package Database;

import java.sql.*;

import Model.RegisteredUser;

public class UserDatabaseReader extends DatabaseReader{
    final private static String TABLE = "registered_users";
    
    public static RegisteredUser getUser(String email, String password) {
        if (!connect()) {
            return null;
        }
        
        String query = String.format("SELECT * FROM %s WHERE email=%s, password=%s", TABLE, email, password);
        
        RegisteredUser user = null;
        try {
            Statement fetchUser = connection.createStatement();
            ResultSet foundUser = fetchUser.executeQuery(query);

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
        
        disconnect();
        
        return user;
    }

    public static boolean userExist(String email) {
        if (!connect()) {
            return false;
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
        disconnect();
        return true;
    }

    public static boolean userExist(RegisteredUser user) {
        return userExist(user.getEmail());
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
        
        try {
            Statement insertUser = connection.createStatement();
            int rowsChanged = insertUser.executeUpdate(query);
            insertUser.close();
            // if rowsChanged is 0, then new user was not added
            // so make it throw an SQLException so it can be caught and false be returned
            if (rowsChanged == 0) {
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
        // can't remove user if he doesn't exist
        if (!userExist(email)) {
            return false;
        }
        // if can't connect, operation failed as can't remove the user
        if (!connect()) {
            return false;
        }
        
        String query = String.format("DELETE FROM %s WHERE email=%s", TABLE, email);

        try {
            Statement deleteUser = connection.createStatement();
            int rowsChanged = deleteUser.executeUpdate(query);
            deleteUser.close();

            // if rowsChanged == 0, then the user was not removed
            // so throw SQLException so it can be caught and false be returned
            if (rowsChanged == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            disconnect();
            return false;
        }
         
        disconnect();
        return true;
    }

    public static boolean removeUser(RegisteredUser user) {
        return removeUser(user.getEmail());
    }

    public static boolean updateUser(RegisteredUser updatedUser) {
        // if user doesn't exist, can't update them
        if (!userExist(updatedUser.getEmail())) {
            return false;
        }
        // operation failed if can't connect to database as can't update user
        if (!connect()) {
            return false;
        }

        // Can't update email or registration_date
        String query = String.format(
            "UPDATE %s SET password=%s, first_name=%s, last_name=%s, address=%s, card_number=%s WHERE email=%s",
            TABLE, updatedUser.getPassword(), updatedUser.getfirstName(), updatedUser.getlastName(),
            updatedUser.getAddress(), updatedUser.getCardNumber(), updatedUser.getEmail());

        try {
            Statement userUpdate = connection.createStatement();
            int rowsChanged = userUpdate.executeUpdate(query);
            userUpdate.close();

            // if rowsChanged == 0, then update did not occur
            // to return false, throw an SQLException
            if (rowsChanged == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            disconnect();
            return false;
        }

        disconnect();
        return true;
    }

}
