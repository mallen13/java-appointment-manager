package allen.appointment_manager.models;

/**
 * report one object
 */
public class ReportOne {
    private int month;
    private String type;
    private int count;

    /**
     * constructor
     * @param month
     * @param type
     * @param count
     */
    public ReportOne(int month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     * returns the report month
     * @return
     */
    public String getMonth() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[month - 1];
    }

    /**
     * returns report type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * returns report count
     * @return
     */
    public int getCount() {
        return count;
    }
}

