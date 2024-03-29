package allen.appointment_manager.controllers;

import allen.appointment_manager.helpers.Helpers;
import allen.appointment_manager.models.Customer;
import allen.appointment_manager.models.DataAccessObject;
import allen.appointment_manager.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Customers Page to view, add, modify, or delete
 */
public class CustomersPageController {
    /**
     * selected customer
     */
    Customer customer = null;

    /**
     * new helper instance
     */
    Helpers myHelpers = new Helpers();

    /**
     * list of customers to populate table
     */
    ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML private Button addBtn;
    @FXML private TextField addressInput;
    @FXML private Button deleteBtn;
    @FXML private TextField idInput;
    @FXML private Button modifyBtn;
    @FXML private TextField nameInput;
    @FXML private TextField phoneNumberInput;
    @FXML private TextField postalCodeInput;
    @FXML private ComboBox<String> countryInput;
    @FXML private TableView<Customer> recordsTable;
    @FXML private ComboBox<String> stateDropdown;
    @FXML private TextField searchInput;

    @FXML private TableColumn<Customer, String> address;
    @FXML private TableColumn<Customer, String> country;
    @FXML private TableColumn<Customer, Integer> custID;
    @FXML private TableColumn<Customer, String> custName;
    @FXML private TableColumn<Customer, String> phoneNum;
    @FXML private TableColumn<Customer, String> postalCode;
    @FXML private TableColumn<Customer, String> state;

    /**
     *setup class and listeners, fill table
     * Lambdas: used here as callbacks for initializing event listeners
     */
    @FXML public void initialize() throws SQLException {
        //get customers
        customerList = DataAccessObject.getCustomers();

        //set default customer id
        idInput.setText("Auto-Generated");

        //fill parts table on initialize
        recordsTable.setItems(customerList);
        custID.setCellValueFactory(new PropertyValueFactory<>("id"));
        custName.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        state.setCellValueFactory(new PropertyValueFactory<>("stateProvince"));

        //listen for table row select
        recordsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedCustomer) -> {
            if (selectedCustomer != null) {
                customer = selectedCustomer;
                // This code will execute when a row is selected
                String customerId = String.valueOf(selectedCustomer.getId());
                idInput.setText(customerId);
                nameInput.setText(selectedCustomer.getName());
                addressInput.setText(selectedCustomer.getAddress());
                postalCodeInput.setText(selectedCustomer.getPostalCode());
                phoneNumberInput.setText(selectedCustomer.getPhoneNumber());
                stateDropdown.setValue(selectedCustomer.getStateProvince());
                countryInput.setValue(selectedCustomer.getCountry());

                //disable buttons
                addBtn.setDisable(true);
                modifyBtn.setDisable(false);
                deleteBtn.setDisable(false);
            }
        });

        //fill dropdown menus
        try {
            ObservableList<String> divisionList = DataAccessObject.getDivisions();
            stateDropdown.setItems(divisionList);

            ObservableList<String> countryList = DataAccessObject.getCountries();
            countryInput.setItems(countryList);

        //if error
        } catch (SQLException e) {
            System.out.println("Error getting countries and states");
        }

        //listen for dropdown select
        stateDropdown.setOnAction(event -> {
            String selectedValue = stateDropdown.getValue();

            if (selectedValue == null) {
                return;
            }

            // perform some action based on the selected value
            String country = DataAccessObject.getCountryByDivision(selectedValue);
            countryInput.setValue(country);
        });

        countryInput.setOnAction(event -> {
            String selectedValue = countryInput.getValue();
            // perform some action based on the selected value
            ObservableList<String> divisionList = DataAccessObject.getDivisionsByCountry(selectedValue);

            if (!divisionList.contains(stateDropdown.getValue())) {
                stateDropdown.setItems(divisionList);
                stateDropdown.getSelectionModel().clearSelection(); // clear ComboBox value
            }

        });

    }

    /**
     * Clear current record
     */
    @FXML void clearRecord() {
        //reset customer attribute
        //clears inputs
        nameInput.clear();
        addressInput.clear();
        postalCodeInput.clear();
        phoneNumberInput.clear();
        idInput.setText("Auto-Generated");
        stateDropdown.setValue(null);
        countryInput.setValue(null);

        //disable buttons
        addBtn.setDisable(false);
        modifyBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    /**
     *adds a record
     * @param event
     */
    @FXML void addRecord(ActionEvent event) throws SQLException {
        //get inputs helper
        String customerName = nameInput.getText();
        String address = addressInput.getText();
        String postalCode = postalCodeInput.getText();
        String phoneNum = phoneNumberInput.getText();
        String country = countryInput.getSelectionModel().getSelectedItem();
        String state = stateDropdown.getSelectionModel().getSelectedItem();

        //validate inputs
        if (myHelpers.isAnyEmpty(customerName,address,postalCode,phoneNum,country,state)) {
            myHelpers.showAlert("Invalid Inputs", "Inputs cannot be empty");
            //validate state selected
        } else {
            //set customer attributes
            int divisionId = DataAccessObject.getDivisionId(state);
            int userId = User.getUserId();
            Customer customer = new Customer(
                    -1,
                    customerName,
                    address,
                    postalCode,
                    phoneNum,
                    state,
                    country,
                    divisionId
                    );

            //send data to DB
            int newCustomerId = DataAccessObject.addCustomer(customer,userId);

            //handle query response
            if (newCustomerId == -1) {
                myHelpers.showAlert("DB Error", "Unable to Complete DB Transaction.");
            } else {
                customer.setId(newCustomerId);
                customerList.add(customer);
                clearRecord();
            }

        }
    }

    /**
     * delete selected record
     */
    @FXML void deleteRecord(ActionEvent event) throws SQLException {
        //attempt remove
        if (!myHelpers.showConfirm("Delete Record", "Are you sure you want to delete this record?") ) {
            return;
        }
        int delId = customer.getId();

        //check if valid appointments
        if (DataAccessObject.customerHasAppts(customer.getId())) {
            myHelpers.showAlert("Delete Record", "Customer has appointments, unable to delete");
            return;
        }

        //query DB
        boolean noErrs = DataAccessObject.deleteFromCustomers(delId);

        //handle response
        if (noErrs) {
            clearRecord();
            customerList.remove(customer);
        } else {
            myHelpers.showAlert("DB Error", "Unable to Delete Record");
        }
    }

    /**
     * shows main menu
     */
    @FXML void loadMenu(ActionEvent event) throws IOException {
        myHelpers.changeScene(
                "mainMenu.fxml",
                600,400,
                "Appointment Management App",
                event
        );
    }

    /**
     * modify selected customer record
     */
    @FXML void modifyRecord(ActionEvent event) throws SQLException {
        //get inputs helper
        String customerName = nameInput.getText();
        String address = addressInput.getText();
        String postalCode = postalCodeInput.getText();
        String phoneNum = phoneNumberInput.getText();
        String country = countryInput.getSelectionModel().getSelectedItem();
        String state = stateDropdown.getSelectionModel().getSelectedItem();

        //validate inputs
        if (myHelpers.isAnyEmpty(customerName,address,postalCode,phoneNum,country,state)) {
            myHelpers.showAlert("Invalid Inputs", "Inputs cannot be empty");
            //validate state selected
        } else {
            //set customer attributes
            int divisionId = DataAccessObject.getDivisionId(state);
            Customer customer = new Customer(
                    this.customer.getId(),
                    customerName,
                    address,
                    postalCode,
                    phoneNum,
                    state,
                    country,
                    divisionId
            );

            //send data to DB
            boolean modifiedCustomer = DataAccessObject.modifyCustomer(customer);

            //handle query response
            if (!modifiedCustomer) {
                myHelpers.showAlert("DB Error", "Unable to Complete DB Transaction.");
            } else {
                int idx = customerList.indexOf(this.customer);
                customerList.set(idx,customer);
                clearRecord();
            }

        }
    }

    @FXML void searchPatients(KeyEvent event) {
        String searchTerm = searchInput.getText().toLowerCase();
        ObservableList<Customer> filteredList = FXCollections.observableArrayList();
        for (Customer customer : customerList) {
            if (customer.getName().toLowerCase().contains(searchTerm) ||
                    customer.getAddress().toLowerCase().contains(searchTerm) ||
                    customer.getPostalCode().toLowerCase().contains(searchTerm) ||
                    customer.getPhoneNumber().toLowerCase().contains(searchTerm) ||
                    customer.getCountry().toLowerCase().contains(searchTerm) ||
                    customer.getStateProvince().toLowerCase().contains(searchTerm)) {
                filteredList.add(customer);
            }
        }
        recordsTable.setItems(filteredList);
    }

}
