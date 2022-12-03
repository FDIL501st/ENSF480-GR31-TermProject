package Database;

import java.sql.*;
import java.util.ArrayList;
import Model.RegisteredUser;

public class UserDatabaseReader extends DatabaseReader{
    final private static String TABLE = "registered_users";
    private static ArrayList<RegisteredUser> allUsers = fetchAllUsers();

    /**
     * Getter for allUsers.
     * @return the arrayList of all registered users
     */
    public static ArrayList<RegisteredUser> getAllUsers() {
        return allUsers;
    }

    /**
     * Fetchs all registered users in the database.
     * @return an arrayList of all registered users in the database
     */
    private static ArrayList<RegisteredUser> fetchAllUsers() {
        // if can't connect, return null
        if (!connect()) {
            return null;
        }

        String query = String.format("SELECT * FROM %s", TABLE);
        ArrayList<RegisteredUser> users = new ArrayList<>();

        try {
            Statement selectAllUsers = connection.createStatement();
            ResultSet allUsersResult = selectAllUsers.executeQuery(query);
            while (allUsersResult.next()) {
                //make a new RegisteredUser object by getting data from row
                String fetchedEmail = allUsersResult.getString(1);
                String fetchedPassword = allUsersResult.getString(2);
                String firstName = allUsersResult.getString(3);
                String lastName = allUsersResult.getString(4);
                String address = allUsersResult.getString(5);
                String cardNumber = allUsersResult.getString(6);
                java.util.Date registrationDate = allUsersResult.getDate(7);
                
                users.add(new RegisteredUser(fetchedEmail, fetchedPassword, firstName, lastName, address, cardNumber, registrationDate));  
            }
            selectAllUsers.close();
        } catch (SQLException e) {
            disconnect();
            return null;
        }

        disconnect();
        return users;
    }

    /**
     * Gets a single registered user from the database.
     * @param email the email of the user
     * @param password the password of the user
     * @return a RegisteredUser object from the database
     */
    public static RegisteredUser getUser(String email, String password) {
        if (!connect()) {
            return null;
        }
        
        String query = String.format("SELECT * FROM %s WHERE email= ? AND password= ?", TABLE);
        
        RegisteredUser user = null;
        try {
            PreparedStatement fetchUser = connection.prepareStatement(query);
            // set values 
            fetchUser.setString(1, email);
            fetchUser.setString(2, password);
            // statement ready to execute
            ResultSet foundUser = fetchUser.executeQuery();

            if (foundUser.next()) {
                //make a new RegisteredUser object by getting data from row
                String fetchedEmail = foundUser.getString(1);
                String fetchedPassword = foundUser.getString(2);
                String firstName = foundUser.getString(3);
                String lastName = foundUser.getString(4);
                String address = foundUser.getString(5);
                String cardNumber = foundUser.getString(6);
                java.util.Date registrationDate = foundUser.getDate(7);
                
                user = new RegisteredUser(fetchedEmail, fetchedPassword, firstName, lastName, address, cardNumber, registrationDate);  
            }
            foundUser.close();    
            
        } catch (SQLException e) {
            disconnect();
            return null;    //unable to fecth user
        }
        
        disconnect();
        
        return user;
    }

    /**
     * Adds a new registered user to the database.
     * @param user the new registered user to add
     * @return true if the user was added to the database. false if the operation failed
     */
    public static boolean addUser(RegisteredUser user) {
        // attempt to connect to database
        if (!connect()) {
            return false;
        }

        String query = String.format(
            "INSERT INTO %s (email, password, first_name, last_name, address, card_number, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?)",
            TABLE);
        // get java.sql.Date object from java.util.Date object
        Date registration_date = new Date(user.getRegistrationDate().getTime());
        try {
            PreparedStatement insertUser = connection.prepareStatement(query);
            // set values
            insertUser.setString(1, user.getEmail());
            insertUser.setString(2, user.getPassword());
            insertUser.setString(3, user.getfirstName());
            insertUser.setString(4, user.getlastName());
            insertUser.setString(5, user.getAddress());
            insertUser.setString(6, user.getCardNumber());
            insertUser.setDate(7, registration_date);
            // statement ready to execute
            int rowsChanged = insertUser.executeUpdate();
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
        // before returning, need to sync up changes
        allUsers = fetchAllUsers();
        return true;
    }

    /**
     * Removes a registered user from the database.
     * @param email the email of the user 
     * @return true if the user was removed from the database. false if the operation failed
     */
    public static boolean removeUser(String email) {
        // if can't connect, operation failed as can't remove the user
        if (!connect()) {
            return false;
        }
        
        String query = String.format("DELETE FROM %s WHERE email=?", TABLE);

        try {
            PreparedStatement deleteUser = connection.prepareStatement(query);
            // set email
            deleteUser.setString(1, email);
            // statement ready to execute
            int rowsChanged = deleteUser.executeUpdate();
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

        // before returning, need to sync up changes
        allUsers = fetchAllUsers();
        return true;
    }

    /**
     * Removes a registered user from the database.
     * @param user the user to remove
     * @return true if the user was removed from the database. false if the operation failed
     */
    public static boolean removeUser(RegisteredUser user) {
        return removeUser(user.getEmail());
    }

    /**
     * Upadtes the information of a registered user.
     * @param updatedUser the updated user. Should have a matching email of a user stored in the database for the update to occur.
     * @return true if the user was updated. false if the operation failed
     */
    public static boolean updateUser(RegisteredUser updatedUser) {
        // operation failed if can't connect to database as can't update user
        if (!connect()) {
            return false;
        }

        // Can't update email or registration_date
        String query = String.format(
            "UPDATE %s SET password=?, first_name=?, last_name=?, address=?, card_number=?, registration_date=? WHERE email=?",
            TABLE);
        // get java.sql.Date object from java.util.Date object
        Date registration_date = new Date(updatedUser.getRegistrationDate().getTime());
        try {
            PreparedStatement userUpdate = connection.prepareStatement(query);
            // set values
            userUpdate.setString(7, updatedUser.getEmail());
            userUpdate.setString(1, updatedUser.getPassword());
            userUpdate.setString(2, updatedUser.getfirstName());
            userUpdate.setString(3, updatedUser.getlastName());
            userUpdate.setString(4, updatedUser.getAddress());
            userUpdate.setString(5, updatedUser.getCardNumber());
            userUpdate.setDate(6, registration_date);
            // statement ready to execute
            int rowsChanged = userUpdate.executeUpdate();
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
        // before returning, need to sync up changes
        allUsers = fetchAllUsers();
        return true;
    }

    public static void main(String[] args) {
        /* 
        String email = "fdil.fadilh1@gmail.com";
        String password = "fdil";
        String firstName = "Fadil";
        String lastName = "Husain";
        String address = "123 Someplace Dr";
        String cardNum = "1554789855244474";

        RegisteredUser registeredUser = new RegisteredUser(email, password, firstName, lastName, address, cardNum);

        System.out.println(UserDatabaseReader.addUser(registeredUser));
        System.out.println();

        RegisteredUser ru2 = UserDatabaseReader.getUser(email, password);
        System.out.println(ru2.getEmail());
        System.out.println(ru2.getPassword());
        System.out.println(ru2.getfirstName());
        System.out.println(ru2.getlastName());
        System.out.println(ru2.getAddress());
        System.out.println(ru2.getCardNumber());
        System.out.println(ru2.getDateLastPayed().toString());
        System.out.println();

        RegisteredUser ru3 = new RegisteredUser(email, password, firstName, lastName, "12385 false ave", cardNum);
        System.out.println(UserDatabaseReader.updateUser(ru3));
        System.out.println();

        //System.out.println(UserDatabaseReader.removeUser(registeredUser)); 
        */
        /* 
        ArrayList<RegisteredUser> users = getAllUsers();
        for (RegisteredUser registeredUser : users) {
        System.out.println(registeredUser.getEmail());
        System.out.println(registeredUser.getPassword());
        System.out.println(registeredUser.getfirstName());
        System.out.println(registeredUser.getlastName());
        System.out.println(registeredUser.getAddress());
        System.out.println(registeredUser.getCardNumber());
        System.out.println(registeredUser.getDateLastPayed().toString());
        System.out.println();
        }
        */
        System.out.println(getAllUsers().size());
        // all methods works as intended
    }
}
