package allen.appointment_manager.helpers;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * abstract class to create database connection
 */
public abstract class DatabaseConnector {

    private static final String url = "jdbc:mysql://localhost/client_schedule?connectionTimeZone=SERVER";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    public static Connection connection;

    /**
     * responsible for opening DB connection
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch(Exception e) {
            System.out.println("db error: " + e);
        }
    }

    /**
     * responsible for closing db connection
     */
    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch(Exception e) {
            System.out.println("Error closing db: ");
        }

    }

    public static Connection getConnection() {
        return connection;
    }

}
