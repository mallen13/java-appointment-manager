package allen.appointment_manager.models;

/**
 * creates a customer object
 */
public class Customer {
    private int custId;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String stateProvince;
    private String country;
    private int divisionID;

    public Customer(int custId, String name, String address, String postalCode, String phoneNumber, String stateProvince, String country, int divisionID) {
        this.custId = custId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.stateProvince = stateProvince;
        this.country = country;
        this.divisionID = divisionID;
    }

    /**
     * Gets the division ID of the customer.
     */
    public int getDivisionId() {
        return divisionID;
    }

    /**
     * sets the division ID of the customer.
     */
    public void setDivisionId(int id) {
        this.divisionID = id;
    }

    /**
     * Gets the ID of the customer.
     * @return the customer's ID
     */
    public int getId() {
        return this.custId;
    }

    /**
     * Sets the ID of the customer.
     * @param id the customer's ID
     */
    public void setId(int id) {
        this.custId = id;
    }

    /**
     * Gets the name of the customer.
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * @param name the customer's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the customer.
     * @return the customer's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     * @param address the customer's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the postal code of the customer.
     * @return the customer's postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the customer.
     * @param postalCode the customer's postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the phone number of the customer.
     * @return the customer's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the customer.
     * @param phoneNumber the customer's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the state or province of the customer.
     * @return the customer's state or province
     */
    public String getStateProvince() {
        return stateProvince;
    }

    /**
     * Sets the state or province of the customer.
     * @param stateProvince the customer's state or province
     */
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    /**
     * Gets the country of the customer.
     * @return the customer's country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the customer.
     * @param country the customer's country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
