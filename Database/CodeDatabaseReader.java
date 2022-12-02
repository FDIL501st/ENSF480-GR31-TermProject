package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;

public class CodeDatabaseReader extends DatabaseReader{
    final private static String TABLE = "codes";

    public static void makeNewCode(double value) {
        // if fail to connect, stop
        if (!connect()) {
            return;
        }
        String insertQuery = String.format("INSERT INTO %s (value, expiry) VALUES (?, ?)", TABLE);
        String selectQuery = String.format("SELECT * from %s WHERE value = ? AND expiry = ? ORDER by ID DESC", TABLE);
        int codeNum = 0;   //stores code number

        // create sql.Date object based off of current date + 1 year
        Calendar cal = Calendar.getInstance();
        // add 1 year as expires in 1 year
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) +1);
        Date expiry_date = new Date(cal.getTime().getTime());
        
        try {
            PreparedStatement insertCode = connection.prepareStatement(insertQuery);
            // set values
            insertCode.setDouble(1, value);
            insertCode.setDate(2, expiry_date);
            // statement ready to execute
            int rowsChanged = insertCode.executeUpdate();
            insertCode.close();
            // if rowsChanged == 0, new Code not made
            if (rowsChanged == 0) {
                throw new SQLException("New code not made.");
            }

            // Now want to fetch the new code just made
            PreparedStatement selectCode = connection.prepareStatement(selectQuery);
            // set values
            selectCode.setDouble(1, value);
            selectCode.setDate(2, expiry_date);
            // statement ready to execute
            ResultSet codes = selectCode.executeQuery();
            if (codes.next()) {
                // only need first result as that is the latest code (one with highest ID)
                codeNum = codes.getInt("ID");
            }
            selectCode.close();
        } catch (SQLException e) {
            disconnect();
            return;
        }
        disconnect();
        // TODO - need to return code object
        return;
    }

    public static double getCodeValue(int codeNum) {
        // if fail to connect, can't return an actual value so end up returning 0
        if (!connect()) {
            return 0;
        }
        
        String query = String.format("SELECT value FROM %s WHERE ID = ?", TABLE);
        double codeValue = 0;
        try {
            PreparedStatement fetchCodeValue = connection.prepareStatement(query);
            // set ID
            fetchCodeValue.setInt(1, codeNum);
            // statment ready to execut
            ResultSet resultCodeValue = fetchCodeValue.executeQuery();
            // expecting only 1 result
            if (resultCodeValue.next()) {
                codeValue = resultCodeValue.getDouble("value"); //get the value
            }
            fetchCodeValue.close();
        } catch (SQLException e) {
            disconnect();
            return 0;
        }
        disconnect();
        return codeValue;
    }
}
