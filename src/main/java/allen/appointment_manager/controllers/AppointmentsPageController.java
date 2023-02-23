package allen.appointment_manager.controllers;

import allen.appointment_manager.helpers.Helpers;
import allen.appointment_manager.models.Appointment;
import allen.appointment_manager.models.DataAccessObject;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

/**
 * Manage Appointments
 */
public class AppointmentsPageController {
    Appointment appointment = null;
    Helpers myHelpers = new Helpers();
    /**
     * Filtered Part List
     */
    private FilteredList<Appointment> appointmentsList = new FilteredList<>(DataAccessObject.getAppointments(), p -> true);

    @FXML private Button addBtn;
    @FXML private TableColumn<?, ?> appointmentID;
    @FXML private Button clearBtn;
    @FXML private TableColumn<?, ?> contact;
    @FXML private ComboBox<String> contactDropdown;
    @FXML private ComboBox<Integer> customerDropdown;
    @FXML private TableColumn<?, ?> customerID;
    @FXML private TextField customerIdInput;
    @FXML private Button deleteBtn;
    @FXML private TableColumn<?, ?> description;
    @FXML private TextArea descriptionInput;
    @FXML private TableColumn<?, ?> end;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<?> endTimeDropdown;
    @FXML private TextField idInput;
    @FXML private TableColumn<?, ?> locationColumn;
    @FXML private TextField locationInput;
    @FXML private Button menuBtn;
    @FXML private Button modifyBtn;
    @FXML private RadioButton monthRadioBtn;
    @FXML private TableView<Appointment> recordsTable;
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

    public AppointmentsPageController() throws SQLException {
    }


    /**
     *setup class and listeners, fill table
     */
    @FXML public void initialize() throws SQLException {
        //set default id
        idInput.setText("Auto-Generated");

        //fill parts table on initialize
        recordsTable.setItems(appointmentsList);
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        end.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userId"));


        //listen for table row select
        recordsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedAppt) -> {
            if (selectedAppt != null) {
                appointment = selectedAppt;

                // This code will execute when a row is selected
                String apptId = String.valueOf(selectedAppt.getAppointmentId());
                idInput.setText(apptId);

                //disable buttons
                addBtn.setDisable(true);
                modifyBtn.setDisable(false);
                deleteBtn.setDisable(false);
            }
        });

        //fill dropdown menus
        try {
            //contacts
            ObservableList<String> contactsList = DataAccessObject.getContacts();
            contactDropdown.setItems(contactsList);

            //customers
            ObservableList<Integer> customerList = DataAccessObject.getCustomerIDs();
            customerDropdown.setItems(customerList);

            //if error
        } catch (SQLException e) {
            System.out.println("Error getting contacts");
        }

        //customer dropdown listener
//        customerDropdown.setOnAction(e -> filterByCustomer());
    }

    /**
     * adds a new records
     */
    @FXML void addRecord() {

    }

    /**
     * clears the selected record
     */
    @FXML void clearRecord() {
        System.out.println("clear record");
    }

    /**
     * deletes the selected record
     */
    @FXML void deleteRecord() throws SQLException {
        //attempt remove
        if (!myHelpers.showConfirm("Delete Record", "Are you sure you want to delete this record?") ) {
            return;
        }
        int delId = appointment.getAppointmentId();
        boolean noErrs = DataAccessObject.deleteFromAppointments(delId);

        //handle response
        if (noErrs) {
            clearRecord();
            appointmentsList.remove(appointment);
        } else {
            myHelpers.showAlert("DB Error", "Unable to Delete Record");
        }
    }

    /**
     * filters records table by customer
     */
    @FXML void filterByCustomer() {
        appointmentsList.setPredicate(
                appointment -> appointment.getCustomerId() == customerDropdown.getValue()
        );
    }

    /**
     * filters records table by month
     */
    @FXML
    void filterByMonth() {
        appointmentsList.setPredicate(appointment -> {
            LocalDate appointmentDate = appointment.getStartDateTime().toLocalDate();
            YearMonth currentMonth = YearMonth.now();
            LocalDate firstOfMonth = currentMonth.atDay(1);
            LocalDate lastOfMonth = currentMonth.atEndOfMonth();
            return appointmentDate.isAfter(firstOfMonth.minusDays(1)) && appointmentDate.isBefore(lastOfMonth.plusDays(1));
        });
    }


    /**
     * filters records table by month
     */
    @FXML
    void filterByWeek() {
        appointmentsList.setPredicate(appointment -> {
            LocalDate appointmentDate = appointment.getStartDateTime().toLocalDate();
            LocalDate monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate friday = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
            return appointmentDate.isAfter(monday.minusDays(1)) && appointmentDate.isBefore(friday.plusDays(1));
        });
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
     * modify the selected record
     */
    @FXML void modifyRecord(ActionEvent event) {

    }

    /**
     * show all records in the table
     */
    @FXML void showAll() {
        appointmentsList.setPredicate(null);
    }

}
