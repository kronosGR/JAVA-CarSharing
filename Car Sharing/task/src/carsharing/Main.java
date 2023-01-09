package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String DBName = "kronos";
        String SQL_COMPANY = "CREATE TABLE IF NOT EXISTS COMPANY " +
                "(ID INTEGER , " +
                "NAME VARCHAR(255) UNIQUE NOT NULL)";

        if (args.length != 0 && Optional.of(args[0]).get().equals("-databaseFileName")) {
            DBName = args[1];
        }

        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:h2:./src/carsharing/db/"+DBName);
            connection.setAutoCommit(true);

            Statement st = connection.createStatement();
            st.execute(SQL_COMPANY);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}