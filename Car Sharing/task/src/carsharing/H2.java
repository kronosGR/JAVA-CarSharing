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
