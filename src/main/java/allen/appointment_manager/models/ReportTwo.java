package allen.appointment_manager.models;

/**
 * second report (appointment count by customer)
 */
public class ReportTwo {
    private int customer_id;
    private int count;

    /**
     * constructor
     * @param customer_id
     * @param count
     */
    public ReportTwo(int customer_id, int count) {
        this.customer_id = customer_id;
        this.count = count;
    }

    /**
     * gets customer_id attribute
     * @return
     */
    public int getCustomer() {
        return customer_id;
    }

    /**
     * gets customer count attribute
     * @return
     */
    public int getCount() {
        return count;
    }
}
