package allen.appointment_manager.models;

import allen.appointment_manager.helpers.DatabaseConnector;
import allen.appointment_manager.helpers.Helpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


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

    /**
     * gets a list of countries to populate customer dropdown menu
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getCountries() throws SQLException {
        ObservableList<String> countryList = FXCollections.observableArrayList();

        String query = "SELECT DISTINCT Country FROM countries";

        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String countryName = rs.getString("Country");
            countryList.add(countryName);
        }

        return countryList;
    }


    /**
     * get list of state/ provinces
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getDivisions() throws SQLException {
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        String query = "SELECT DISTINCT Division FROM first_level_divisions";

        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String divisionName = rs.getString("Division");
            divisionList.add(divisionName);
        }

        return divisionList;
    }


    /**
     * get country based on division name
     */
    public static String getCountryByDivision(String divisionName) {
        String country = null;

        String query = "SELECT COUNTRY " +
                "FROM countries " +
                "JOIN first_level_divisions " +
                "ON countries.COUNTRY_ID = first_level_divisions.COUNTRY_ID " +
                "WHERE first_level_divisions.DIVISION = ?";

        try (PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query)) {
            ps.setString(1, divisionName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                country = rs.getString("COUNTRY");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return country;
    }

    /**
     * return divison id
     */
    public static int getDivisionId(String state) {
        int id = -1;

        String query =
                "SELECT division_id FROM first_level_divisions" +
                " WHERE Division = ?";

        try (PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query)) {
            ps.setString(1, state);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = Integer.parseInt(rs.getString("division_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * returns a list of customers
     * @return customers obersvable list
     * @throws SQLException
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        String query = "" +
                "SELECT c.*, d.Division, country.Country FROM customers c" +
                " JOIN first_level_divisions d ON c.division_id = d.division_id" +
                " JOIN countries country ON country.country_id = d.country_id";

        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String stateProvince = rs.getString("Division");
            String country = rs.getString("Country");
            int dID = rs.getInt("Division_ID");

            Customer customer = new Customer(id,name,address,postalCode,phone,stateProvince,country,dID);
            customerList.add(customer);
        }

        return customerList;
    }

    /**
     * adds a new customer
     */
    public static int addCustomer(Customer customer, int userId) throws SQLException {
        ResultSet rs = null;
        int returnId = -1;

        try {
            String query =
                    "INSERT INTO customers " +
                    "(Customer_Name,Address,Postal_Code,Phone,Division_ID,Create_Date,Created_By) " +
                    "VALUES (?,?,?,?,?,?,?)";

            //Helpers myHelpers = new Helpers();
            //myHelpers.convertToUTC(new Timestamp(System.currentTimeMillis()));

            PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhoneNumber());
            ps.setInt(5, customer.getDivisionId());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setInt(7, userId);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            System.out.println(rs);

            if (rs.next()) {
                returnId = rs.getInt(1);
            }
        } catch(Exception e) {
            System.out.println("db error: " + e);
        }  finally
         {
            if (rs != null) {
                rs.close();
            }

        }
        return returnId;
    }

    /**
     * modifies an existing customer record
     * @param customer
     * @return
     * @throws SQLException
     */
    public static boolean modifyCustomer(Customer customer) throws SQLException {
        ResultSet rs = null;

        try {
            String query =
                    "UPDATE CUSTOMERS SET " +
                    "Customer_Name = ?, " +
                    "Address = ?, " +
                    "Postal_Code = ?, " +
                    "Phone = ?, " +
                    "Division_ID = ?, " +
                    "Last_Update = ?, " +
                    "Last_Updated_By = ? " +
                    "WHERE customer_id = ?";

            PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getName()); //name
            ps.setString(2, customer.getAddress()); //address
            ps.setString(3, customer.getPostalCode()); //postal code
            ps.setString(4, customer.getPhoneNumber()); //phone number
            ps.setInt(5, customer.getDivisionId()); //division id
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis())); //timestamp
            ps.setString(7, "script"); //updated by
            ps.setInt(8, customer.getId()); //customer id

            ps.executeUpdate();
            rs = ps.getResultSet();
            System.out.println(rs);

            return true;
        } catch(Exception e) {
            System.out.println("db error: " + e);
            return false;
        }
    }

    //delete customers
    public static boolean deleteFromCustomers(int id) {
        try {
            String query = "DELETE FROM customers WHERE customer_id = ?";
            PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query);
            ps.setString(1, String.valueOf(id));
            int rs = ps.executeUpdate();
            if (rs == 1) { return true; }
            else {return false;}

        } catch (Exception e) {
            System.out.println("Err deleting: " + e);
            return false;
        }
    }
}
