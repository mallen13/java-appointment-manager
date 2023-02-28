package allen.appointment_manager.controllers;

import allen.appointment_manager.helpers.Helpers;
import allen.appointment_manager.models.Appointment;
import allen.appointment_manager.models.DataAccessObject;
import allen.appointment_manager.models.User;
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
import java.time.*;
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
    ObservableList<Appointment> appointmentObservableList = DataAccessObject.getAppointments();
    private FilteredList<Appointment> appointmentsList = new FilteredList<>(appointmentObservableList, p -> true);

    @FXML private Button addBtn;
    @FXML private TableColumn<?, ?> appointmentID;
    @FXML private Button clearBtn;
    @FXML private TableColumn<?, ?> contact;
    @FXML private ComboBox<String> contactDropdown;
    @FXML private ComboBox<Integer> customerDropdown;
    @FXML private ComboBox<Integer> customerDropdown2;
    @FXML private TableColumn<?, ?> customerID;
    @FXML private Button deleteBtn;
    @FXML private TableColumn<?, ?> description;
    @FXML private TextArea descriptionInput;
    @FXML private TableColumn<?, ?> end;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<LocalTime> endTimeDropdown;
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
    @FXML private ComboBox<LocalTime> startTimeDropdown;
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
     * LAMBDA: used when adding a required callback argument for the add button event handler. Easier than creating a full method in the namespace.
     */
    @FXML public void initialize() throws SQLException {
        //set default id
        idInput.setText("Auto-Generated");
        userIdInput.setText(String.valueOf(User.getUserId()));

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
        end.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userId"));


        //listen for table row select
        recordsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedAppt) -> {
            if (selectedAppt != null) {
                appointment = selectedAppt;

                // This code will execute when a row is selected
                String apptId = String.valueOf(selectedAppt.getAppointmentId());
                String title = String.valueOf(selectedAppt.getTitle());
                String description = String.valueOf(selectedAppt.getDescription());
                String location = String.valueOf(selectedAppt.getLocation());
                String contact = String.valueOf(selectedAppt.getContact());
                String type = String.valueOf(selectedAppt.getType());
                LocalDateTime startDateTime = selectedAppt.getStartDateTime();
                LocalDateTime endDateTime = selectedAppt.getEndDateTime();
                String custID= String.valueOf(selectedAppt.getCustomerId());
                String userID = String.valueOf(selectedAppt.getUserId());

                idInput.setText(apptId);
                titleInput.setText(title);
                descriptionInput.setText(description);
                locationInput.setText(location);
                contactDropdown.getSelectionModel().select(contact);
                typeInput.setText(type);
                startDatePicker.setValue(startDateTime.toLocalDate());
                startTimeDropdown.setValue(startDateTime.toLocalTime());
                endDatePicker.setValue(endDateTime.toLocalDate());
                endTimeDropdown.setValue(endDateTime.toLocalTime());
                customerDropdown2.getSelectionModel().select(Integer.valueOf(custID));
                userIdInput.setText(userID);

                //disable buttons
                addBtn.setDisable(true);
                modifyBtn.setDisable(false);
                deleteBtn.setDisable(false);
            }

        });

        //fill dropdown menus
        try {
            //contacts
            ObservableList<String> contactsList = DataAccessObject.getContactNames();
            contactDropdown.setItems(contactsList);

            //customers id's
            ObservableList<Integer> customerList = DataAccessObject.getCustomerIDs();
            customerDropdown.setItems(customerList);
            customerDropdown2.setItems(customerList);

            //if error
        } catch (SQLException e) {
            System.out.println("Error getting contacts");
        }

        //fill time dropdowns
        ObservableList<LocalTime> timesList = Helpers.getTimes();
        startTimeDropdown.setItems(timesList);
        endTimeDropdown.setItems(timesList);

        //event listener lambda
        addBtn.setOnAction(event -> {
            try {
                this.addRecord();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * adds a new records
     */
    @FXML void addRecord() throws SQLException {
        //get inputs
        String title = titleInput.getText();
        String description = descriptionInput.getText();
        String location = locationInput.getText();
        String type = typeInput.getText();
        LocalDate startDate  = startDatePicker.getValue();
        LocalTime startTime = startTimeDropdown.getValue();
        LocalDate endDate  = endDatePicker.getValue();
        LocalTime endTime = endTimeDropdown.getValue();
        int custId;
        int userId;

        String contact = String.valueOf(contactDropdown.getValue());
        try {
            custId = customerDropdown2.getValue();
            userId = Integer.parseInt(userIdInput.getText());
        } catch (Exception err) {
            myHelpers.showAlert("Invalid Inputs", "Inputs cannot be empty");
            return;
        }

        //validate not empty
        if (myHelpers.isAnyEmpty( title, description, location, contact, type)) {
            myHelpers.showAlert("Invalid Inputs", "Inputs cannot be empty.");
            return;
        }

        if (startDate == null || startTime == null || endDate == null || endTime == null) {
            myHelpers.showAlert("Invalid Inputs", "Dates & times cannot be empty.");
            return;
        }

        //validate start and end date
        if (!endDate.equals(startDate)) {
            myHelpers.showAlert("Invalid Inputs", "Start date must equal end date.");
            return;
        }

        //validate end after start
        if (!(endTime.isAfter(startTime))) {
            myHelpers.showAlert("Time Error", "End Time must be after start time.");
            return;
        }

        //validate hours
        if (!myHelpers.isValidAppointment(startTime,endTime)) {
            myHelpers.showAlert("DateTime Error", "Must be between 8a-10p EST.");
            return;
        }

        //validate day selected and  not saturday/ sunday
        if (
                startDate.getDayOfWeek() == DayOfWeek.SATURDAY
                        || startDate.getDayOfWeek() == DayOfWeek.SUNDAY
                || endDate.getDayOfWeek() == DayOfWeek.SATURDAY
                || endDate.getDayOfWeek() == DayOfWeek.SUNDAY
        ) {
            myHelpers.showAlert("Time Error", "Must be between Monday and Friday");
            return;
        }

        //combine
        LocalDateTime startDateTime = startDate.atTime(startTime);
        LocalDateTime endDateTime = endDate.atTime(endTime);

        //validate no overlap
        if (myHelpers.doesAppointmentOverlap(startDateTime,endDateTime)) {
            myHelpers.showAlert("Date Error", "Appointment overlaps with another. Please verify date/time.");
            return;
        }

        //create object
        Appointment appointment = new Appointment(
                -1,
                title,
                description,
                location,
                contact,
                type,
                startDateTime,
                endDateTime,
                custId,
                userId
                );

        //post to DB
        int newAppointmentId = DataAccessObject.addAppointment(appointment);
        System.out.println("id: " + newAppointmentId);

        //if error posting
        if (newAppointmentId == -1) {
            myHelpers.showAlert("DB Error", "Unable to Complete DB Transaction.");
            return;
        }
        appointment.setAppointmentId(newAppointmentId);
        appointmentObservableList.add(appointment);
        clearRecord();
    }

    /**
     * clears the selected record
     */
    @FXML void clearRecord() {
        //clears inputs
        idInput.setText("Auto-Generated");
        userIdInput.setText(String.valueOf(User.getUserId()));
        titleInput.clear();
        descriptionInput.clear();
        locationInput.clear();
        contactDropdown.setValue(null);
        typeInput.clear();
        startDatePicker.getEditor().clear();
        startTimeDropdown.setValue(null);
        endDatePicker.getEditor().clear();
        endTimeDropdown.setValue(null);
        customerDropdown2.setValue(null);

        //disable buttons
        addBtn.setDisable(false);
        modifyBtn.setDisable(true);
        deleteBtn.setDisable(true);
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
            recordsTable.getSelectionModel().clearSelection();
            clearRecord();
            appointmentsList.getSource().remove(appointment);
        } else {
            myHelpers.showAlert("DB Error", "Unable to Delete Record");
        }
    }

    /**
     * filters records table by customer
     * LAMBDA: allows for a quick and easy way to to pass a callback as an argument when filtering appointments
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
    @FXML void modifyRecord() throws SQLException {
        //get inputs
        String title = titleInput.getText();
        String description = descriptionInput.getText();
        String location = locationInput.getText();
        String type = typeInput.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = startTimeDropdown.getValue();
        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = endTimeDropdown.getValue();
        int custId;
        int userId = User.getUserId();

        String contact = String.valueOf(contactDropdown.getValue());
        try {
            custId = customerDropdown2.getValue();
        } catch (Exception err) {
            myHelpers.showAlert("Invalid Inputs", "Inputs cannot be empty");
            return;
        }

        //validate not empty
        if (myHelpers.isAnyEmpty(title, description, location, contact, type)) {
            myHelpers.showAlert("Invalid Inputs", "Inputs cannot be empty.");
            return;
        }

        if (startDate == null || startTime == null || endDate == null || endTime == null) {
            myHelpers.showAlert("Invalid Inputs", "Dates & times cannot be empty.");
            return;
        }

        //validate start and end date
        if (!endDate.equals(startDate)) {
            myHelpers.showAlert("Invalid Inputs", "Start date must equal end date.");
            return;
        }

        //validate end after start
        if (!(endTime.isAfter(startTime))) {
            myHelpers.showAlert("Time Error", "End Time must be after start time.");
            return;
        }

        //validate hours
        if (!myHelpers.isValidAppointment(startTime, endTime)) {
            myHelpers.showAlert("DateTime Error", "Must be between 8a-10p EST.");
            return;
        }

        //validate day selected and not saturday/ sunday
        if (
                startDate.getDayOfWeek() == DayOfWeek.SATURDAY
                        || startDate.getDayOfWeek() == DayOfWeek.SUNDAY
                        || endDate.getDayOfWeek() == DayOfWeek.SATURDAY
                        || endDate.getDayOfWeek() == DayOfWeek.SUNDAY
        ) {
            myHelpers.showAlert("Time Error", "Must be between Monday and Friday");
            return;
        }

        //combine
        LocalDateTime startDateTime = startDate.atTime(startTime);
        LocalDateTime endDateTime = endDate.atTime(endTime);

        //validate no overlap, if input modified
        if (!appointment.getStartDateTime().equals(startDateTime) && !appointment.getEndDateTime().equals(endDateTime)) {
            if (myHelpers.doesAppointmentOverlap(startDateTime, endDateTime)) {
                myHelpers.showAlert("Date Error", "Appointment overlaps with another. Please verify date/time.");
                return;
            }
        }

        //create object
        Appointment appointment = new Appointment(
                this.appointment.getAppointmentId(),
                title,
                description,
                location,
                contact,
                type,
                startDateTime,
                endDateTime,
                custId,
                userId
        );

        //post to DB
        //if error posting
        if (!DataAccessObject.modifyAppointment(appointment)) {
            myHelpers.showAlert("DB Error", "Unable to Complete DB Transaction.");
            return;
        }

        int idx = appointmentObservableList.indexOf(this.appointment);
        appointmentObservableList.set(idx,appointment);
        clearRecord();
    }

    /**
     * show all records in the table
     */
    @FXML void showAll() {
        appointmentsList.setPredicate(null);
    }

}
