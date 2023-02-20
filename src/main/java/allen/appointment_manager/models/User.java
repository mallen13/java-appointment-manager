package allen.appointment_manager.models;

/**
 * Establish a user_id class
 */
public class User {
    private static int user_id;
    private static String timeZone;

    /**
     * Set user id
     * @param user_id
     */
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    /**
     * get user's id
     * @return user's id
     */
    public static int getUserId() {
        return user_id;
    }

    /**
     * set time zone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * get time zone
     */
    public String getTimeZone() {
        return this.timeZone;
    }

}

