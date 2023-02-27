package allen.appointment_manager.models;

import allen.appointment_manager.helpers.DatabaseConnector;
import allen.appointment_manager.helpers.Helpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

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
     * returns all customer ID's
     */
    public static ObservableList<Integer> getCustomerIDs() throws SQLException {
        ObservableList<Integer> idList = FXCollections.observableArrayList();

        String query = "SELECT DISTINCT Customer_ID FROM customers";

        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Customer_ID");
            idList.add(id);
        }

        return idList;
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

            PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhoneNumber());
            ps.setInt(5, customer.getDivisionId());
            ps.setTimestamp(6, Timestamp.from(Instant.now()));
            ps.setInt(7, userId);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

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
            ps.setTimestamp(6, Timestamp.from(Instant.now())); //timestamp
            ps.setString(7, "script"); //updated by
            ps.setInt(8, customer.getId()); //customer id

            ps.executeUpdate();
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

    /**
     * gets a list of appointments
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointments() throws SQLException {

        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        String query =
                "SELECT * FROM appointments ";

        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contact = Integer.valueOf(rs.getString("Contact_ID"));
            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();  //convert
            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();  //convert
            String type = rs.getString("Type");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");

            Appointment appointment = new Appointment(id,title,description,location,contact,type,startDateTime,endDateTime,customerId,userId);
            appointmentsList.add(appointment);
        }

        return appointmentsList;
    }

    /**
     * gets a list of contacts
     * @return
     * @throws SQLException
     */
    public static ObservableList<Integer> getContacts() throws SQLException {
        ObservableList<Integer> contactList = FXCollections.observableArrayList();

        String query = "SELECT DISTINCT Contact_ID FROM contacts";

        PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int contact = rs.getInt("Contact_ID");
            contactList.add(contact);
        }

        return contactList;
    }

    /**
     * delete
     */
    public static boolean deleteFromAppointments(int id) {
        try {
            String query = "DELETE FROM appointments WHERE appointment_id = ?";
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

    /**
     * adds a new appointment
     */
    public static int addAppointment(Appointment appointment) throws SQLException {
        ResultSet rs = null;
        int returnId = -1;

        try {
            String query =
                    "INSERT INTO appointments" +
                            "(Title,Description,Location,Type,Start,End,Create_Date,Created_By,Customer_ID,User_ID,Contact_ID) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEndDateTime()));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(8, appointment.getUserId());
            ps.setInt(9, appointment.getCustomerId());
            ps.setInt(10, appointment.getUserId());
            ps.setInt(11, appointment.getContact());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                returnId = rs.getInt(1);
            }
        } catch(Exception e) {
            System.out.print("db error: " + e);
        }  finally
        {
            if (rs != null) {
                rs.close();
            }

        }
        return returnId;
    }

    /**
     * modifies an appointment
     */
    public static boolean modifyAppointment(Appointment appointment) throws SQLException {
        ResultSet rs = null;

        try {
            //Title,Description,Location,Type,Start,End,Create_Date,Created_By,Customer_ID,User_ID,Contact_ID)
            String query =
                    "UPDATE appointments SET " +
                    "title = ?, description = ?, location = ?," +
                    "type = ? , start = ?, end = ?, create_date = ?, " +
                    "created_by = ?, customer_id = ?, user_id = ?, " +
                    "contact_id = ?, last_update = ?, last_updated_by = ? " +
                    "WHERE appointment_id = ?";

            PreparedStatement ps = DatabaseConnector.getConnection().prepareStatement(query);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(appointment.getEndDateTime()));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(8, appointment.getUserId());
            ps.setInt(9, appointment.getCustomerId());
            ps.setInt(10, appointment.getUserId());
            ps.setInt(11, appointment.getContact());
            ps.setTimestamp(12, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(13, appointment.getUserId());
            ps.setInt(14, appointment.getAppointmentId());
            ps.executeUpdate();

        } catch(Exception e) {
            System.out.print("db error: " + e);
            return false;
        }  finally
        {
            if (rs != null) {
                rs.close();
            }

        }
        return true;
    }
}
