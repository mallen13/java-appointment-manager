package allen.appointment_manager.controllers;

import allen.appointment_manager.Helpers;
import allen.appointment_manager.models.Customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.IOException;

/**
 * Customers Page to view, add, modify, or delete
 */
public class CustomersPageController {
    Customer customer = new Customer();
    Helpers myHelpers = new Helpers();
    int nextId;

    @FXML private Button addBtn;
    @FXML private TextField addressInput;
    @FXML private Button clearBtn;
    @FXML private ComboBox<?> countryDropdown;
    @FXML private Button deleteBtn;
    @FXML private TextField idInput;
    @FXML private Button menuBtn;
    @FXML private Button modifyBtn;
    @FXML private TextField nameInput;
    @FXML private TextField phoneNumberInput;
    @FXML private TextField postalCodeInput;
    @FXML private TableView<?> recordsTable;
    @FXML private ComboBox<?> stateDropdown;

    /**
     *get location and set language
     */
    @FXML public void initialize() {
        //get customers
        //set next available id
    }

    /**
     *adds a record
     * @param event
     */
    @FXML void addRecord(ActionEvent event) {
        //get inputs helper
        String customerName = nameInput.getText();
        String address = addressInput.getText();
        String postalCode = postalCodeInput.getText();
        String phoneNum = phoneNumberInput.getText();
       // String country = ???;

        //validate inputs
        if (myHelpers.isAnyEmpty(customerName,address,postalCode,phoneNum)) {
            myHelpers.showAlert("Invalid Inputs", "Inputs cannot be empty");
        } else {
            //set customer attributes
            //send customer to DB
                //reset form or alert an error

            //reset form helpers
            nameInput.clear();
            addressInput.clear();
            postalCodeInput.clear();
            phoneNumberInput.clear();
            //countryDropdown
            //state dropdown
            idInput.setText(Integer.toString(nextId));
        }
    }

    /**
     * Clear current record
     */
    @FXML void clearRecord(ActionEvent event) {
        //reset customer attribute
        //clears inputs
    }

    /**
     * delete selected customer record
     */
    @FXML void deleteRecord(ActionEvent event) {
        //post to DB
            //clear data or alert an error
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
     * delete selected customer record
     */
    @FXML void modifyRecord(ActionEvent event) {
        //validate inputs
        //set customer attributes

        //send customer to DB
        //clear data or alert an error
    }

    /**
     * populates the form with the selected customer
     */
    @FXML void onSelectCustomer() {
        //get selected customer
        //populate data with selected info
    }

}
