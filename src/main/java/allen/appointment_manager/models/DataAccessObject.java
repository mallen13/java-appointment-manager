package allen.appointment_manager.models;

import allen.appointment_manager.helpers.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * model that contains method for working with the back end database
 */
public class DataAccessObject {
    /**
     * handles user logins
     * @param username
     * @param password
     * @return user id or -1 if bad auth
     */
    public static int authenticateUser(String username, String password) {
        try
        {
            String query = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password +"'";

            PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();

            //if username/ password matches, return ID
            if (rs.getString("User_Name").equals(username) && rs.getString("Password").equals(password))
            {
                return rs.getInt("User_ID");
            }
        }
        catch (Exception e)
        {
            System.out.println("db err: " + e);
        }
        return -1;
    }

    //get nextCustomerId?

    /**
     * returns a list of customers
     * @return customers obersvable list
     * @throws SQLException
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        String query = "" +
                "SELECT c.* FROM customers c";

        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            Customer customer = new Customer(id,name,address,postalCode,phone,"Michigan","United States");
            customerList.add(customer);
        }

        return customerList;
    }

    //add customer
    //modify customer;
    //delete customers

}
