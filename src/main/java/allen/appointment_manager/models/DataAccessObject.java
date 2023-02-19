package allen.appointment_manager.models;

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
        try {
            return 0;
        } catch(Exception e) {
        }
        return -1;
    }

    //get nextCustomerId?
    //get customers?
    //add customer
    //modify customer;
    //delete customers

}
