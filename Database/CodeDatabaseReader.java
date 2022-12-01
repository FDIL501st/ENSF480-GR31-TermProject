package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CodeDatabaseReader extends DatabaseReader{
    final private static String TABLE = "codes";

    public static void makeNewCode(double value) {
        // if fail to connect, stop
        if (!connect()) {
            return;
        }
        String insertQuery = String.format("INSERT INTO %s (value, expiry) VALUES (?, ?)", TABLE);
        String selectQuery = String.format("SELECT * from %s WHERE value = ? AND expiry = ? ORDER by ID DESC", TABLE);

        // create sql.Date object based off of current date
        java.sql.Date expiry_date = new java.sql.Date(new Date().getTime());
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
            }
            selectCode.close();
        } catch (SQLException e) {
            disconnect();
            return;
        }
        disconnect();
        // need to return Code
        return;
    }
}
