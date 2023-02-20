package allen.appointment_manager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class AppointmentsPageController {

    @FXML private Button addBtn;
    @FXML private TableColumn<?, ?> appointmentID;
    @FXML private Button clearBtn;
    @FXML private TableColumn<?, ?> contact;
    @FXML private ComboBox<?> contactDropdown;
    @FXML private ComboBox<?> customerDropdown;
    @FXML private TableColumn<?, ?> customerID;
    @FXML private TextField customerIdInput;
    @FXML private Button deleteBtn;
    @FXML private TableColumn<?, ?> description;
    @FXML private TextArea descriptionInput;
    @FXML private TableColumn<?, ?> end;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<?> endTimeDropdown;
    @FXML private TextField idInput;
    @FXML private TableColumn<?, ?> location;
    @FXML private TextField locationInput;
    @FXML private Button menuBtn;
    @FXML private Button modifyBtn;
    @FXML private RadioButton monthRadioBtn;
    @FXML private TableView<?> recordsTable;
    @FXML private Button showAllBtn;
    @FXML private TableColumn<?, ?> start;
    @FXML private DatePicker startDatePicker;
    @FXML private ComboBox<?> startTimeDropdown;
    @FXML private ToggleGroup tableFilter;
    @FXML private TableColumn<?, ?> title;
    @FXML private TextField titleInput;
    @FXML private TableColumn<?, ?> type;
    @FXML private TextField typeInput;
    @FXML private TableColumn<?, ?> userID;
    @FXML private TextField userIdInput;
    @FXML private RadioButton weekRadioBtn;


    @FXML void addRecord(ActionEvent event) {

    }

    @FXML void clearRecord(ActionEvent event) {

    }

    @FXML void deleteRecord(ActionEvent event) {

    }

    @FXML void filterByCustomer(ActionEvent event) {

    }

    @FXML void filterByMonth(ActionEvent event) {

    }

    @FXML void filterByWeek(ActionEvent event) {

    }

    @FXML void loadMenu(ActionEvent event) {

    }

    @FXML
    void modifyRecord(ActionEvent event) {

    }

    @FXML void showAll(ActionEvent event) {

    }

}
