package allen.appointment_manager.controllers;

import allen.appointment_manager.helpers.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.io.IOException;

/**
 * Manage Appointments Page
 */
public class AppointmentsPageController {

    Helpers myHelpers = new Helpers();
    @FXML private Button addBtn;
    @FXML private Button clearBtn;
    @FXML private ComboBox<?> contactDropdown;
    @FXML private ComboBox<?> customerDropdown;
    @FXML private TextField customerIdInput;
    @FXML private Button deleteBtn;
    @FXML private TextArea descriptionInput;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<?> endTimeDropdown;
    @FXML private TextField idInput;
    @FXML private TextField locationInput;

    @FXML private Button menuBtn;
    @FXML private Button modifyBtn;
    @FXML private RadioButton monthRadioBtn;
    @FXML private TableView<?> recordsTable;
    @FXML private Button showAllBtn;
    @FXML private DatePicker startDatePicker;
    @FXML private ComboBox<?> startTimeDropdown;
    @FXML private ToggleGroup tableFilter;
    @FXML private TextField titleInput;
    @FXML private TextField typeInput;
    @FXML private TextField userIdInput;
    @FXML private RadioButton weekRadioBtn;

    /**
     * Add a new appointment
     * @param event
     */
    @FXML void addRecord(ActionEvent event) {

    }

    /**
     * clear selected record
     * @param event
     */
    @FXML void clearRecord(ActionEvent event) {

    }

    /**
     * delete appointment record
     * @param event
     */
    @FXML void deleteRecord(ActionEvent event) {

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
     * change selected appointment record
     * @param event
     */
    @FXML void modifyRecord(ActionEvent event) {

    }

    /**
     * show all appointments
     * @param event
     */
    @FXML void showAll(ActionEvent event) {

    }

    /**
     * FIlter appointments by month
     * @param event
     */
    @FXML void filterByMonth(ActionEvent event) {
        System.out.println("filter by month");
    }

    /**Filter appointments by week
    @FXML
    **/
    @FXML void filterByWeek(ActionEvent event) {

    }

    /**Filter appointments by customer
     @FXML
     **/
    @FXML void filterByCustomer(ActionEvent event) {

    }

}
