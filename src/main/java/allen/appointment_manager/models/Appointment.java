package allen.appointment_manager.models;

import java.time.LocalDateTime;

/**
 * appointment object
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;
    private int userId;

    /**
     * constructor
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param contact
     * @param type
     * @param startDateTime
     * @param endDateTime
     * @param customerId
     * @param userId
     */
    public Appointment(int appointmentId, String title, String description, String location, String contact, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**
     * gets appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * sets appointment id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * gets appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets appointment id
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    /**
     * sets appointment id
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * gets appointment contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * sets appointment id
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * gets appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * sets appointment id
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * gets start date/time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * sets date / time
     * @param startDateTime
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * gets end date/time
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * sets end date/time
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * gets customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * sets customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * gets user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * sets appointment id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}

