package allen.appointment_manager.models;

public class ReportTwo {
    private int customer_id;
    private int count;

    public ReportTwo(int customer_id, int count) {
        this.customer_id = customer_id;
        this.count = count;
    }

    public int getCustomer() {
        return customer_id;
    }

    public int getCount() {
        return count;
    }
}
