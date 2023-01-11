package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        // set dbname
        H2.setDBName(args);
        // initialize db
        H2.initializeDB();

        // List the menu
        Menu.showMenu();




    }
}