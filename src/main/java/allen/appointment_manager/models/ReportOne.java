package allen.appointment_manager.models;

public class ReportOne {
    private int month;
    private String type;
    private int count;

    public ReportOne(int month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    public String getMonth() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[month - 1];
    }

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }
}

