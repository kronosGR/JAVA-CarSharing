package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class H2 {
    private static String SQL_COMPANY= "CREATE TABLE IF NOT EXISTS COMPANY " +
            "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL)";
    private static String SQL_CAR = "CREATE TABLE IF NOT EXISTS CAR " +
            "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL, " +
            "COMPANY_ID INTEGER NOT NULL, " +
            "CONSTRAINT fk_company " +
            "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY (ID))";

    private static String SQL_CUSTOMER = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
            "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL, " +
            "RENTED_CAR_ID INTEGER DEFAULT NULL, " +
            "CONSTRAINT fk_car " +
            "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR (ID))";

    private static String URL = "jdbc:h2:./src/carsharing/db/";
    private static String DBName = "kronos";

    public static void setDBName(String[] args) {
        if (args.length != 0 && Optional.of(args[0]).get().equals("-databaseFileName")) {
            DBName = args[1];
        }
    }

    public static void initializeDB(){
        try(Connection connection =  getConnection()){
            Statement statement = connection.createStatement();
            statement.execute(SQL_COMPANY);
            statement.execute(SQL_CAR);
            statement.execute(SQL_CUSTOMER);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:h2:./src/carsharing/db/"+DBName);
            connection.setAutoCommit(true);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return  connection;
    }
}
