package allen.appointment_manager.controllers;

import allen.appointment_manager.helpers.Helpers;
import allen.appointment_manager.models.Appointment;
import allen.appointment_manager.models.DataAccessObject;
import allen.appointment_manager.models.ReportOne;
import allen.appointment_manager.models.ReportTwo;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * displays auto-generated reports
 */
public class ReportsPageController {

    /**
     * new helper instance
     */
    Helpers myHelpers = new Helpers();

    /**
     * list of appointments
     */
    ObservableList<Appointment> appointmentObservableList = DataAccessObject.getAppointments();

    /**
     * gets report data
     */
    ObservableList<ReportOne> reportOneList= DataAccessObject.getReportOne();

    /**
     * gets report data
     */
    ObservableList<ReportTwo> reportTwoList = DataAccessObject.getReportTwo();

    /**
     * gets list of appointments to filter
     */
    private FilteredList<Appointment> appointmentsList = new FilteredList<>(appointmentObservableList, p -> true);

    @FXML private Button clearBtn;
    @FXML private TableColumn<?, ?> apptID;
    @FXML private TableColumn<?, ?> contact1;
    @FXML private ComboBox<String> contactFilterDropdown;
    @FXML private TableColumn<?, ?> custID1;
    @FXML private TableColumn<?, ?> description1;
    @FXML private TableColumn<?, ?> end1;
    @FXML private TableColumn<?, ?> location1;
    @FXML private Button menuBtn;
    @FXML private TableColumn<?, ?> r1Count;
    @FXML private TableColumn<?, ?> r1Month;
    @FXML private TableColumn<?, ?> r1Type;
    @FXML private TableColumn<?, ?> r2Count;
    @FXML private TableColumn<?, ?> r2CustID;
    @FXML private TableView<Appointment> recordsTable2;
    @FXML private TableView<ReportOne> report1Table;
    @FXML private TableView<ReportTwo> report2Table;
    @FXML private TableColumn<?, ?> start1;
    @FXML private TableColumn<?, ?> title1;
    @FXML private TableColumn<?, ?> type1;
    @FXML private TableColumn<?, ?> userID1;

    /**
     * setup the page
     */
    @FXML public void initialize() {
        //fill parts table on initialize
        recordsTable2.setItems(appointmentsList);
        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title1.setCellValueFactory(new PropertyValueFactory<>("title"));
        description1.setCellValueFactory(new PropertyValueFactory<>("description"));
        location1.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact1.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type1.setCellValueFactory(new PropertyValueFactory<>("type"));
        start1.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        end1.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        custID1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userID1.setCellValueFactory(new PropertyValueFactory<>("userId"));

        //fill 1st report
        report1Table.setItems(reportOneList);
        r1Month.setCellValueFactory(new PropertyValueFactory<>("month"));
        r1Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        r1Count.setCellValueFactory(new PropertyValueFactory<>("count"));

        //fill 2nd report
        report2Table.setItems(reportTwoList);
        r2CustID.setCellValueFactory(new PropertyValueFactory<>("customer"));
        r2Count.setCellValueFactory(new PropertyValueFactory<>("count"));

        //fill dropdown menus
        try {
            //contacts
            ObservableList<String> contactsList = DataAccessObject.getContactNames();
            contactFilterDropdown.setItems(contactsList);

            //if error
        } catch (SQLException e) {
            System.out.println("Error getting contacts");
        }
    }

    /**
     * constructor
     * @throws SQLException
     */
    public ReportsPageController() throws SQLException {
    }

    /**
     * filters table
     */
    @FXML void filterByContact() {
        appointmentsList.setPredicate(
                appointment -> appointment.getContact().equals(contactFilterDropdown.getValue())
        );
    }

    /**
     * clears table filter
     */
    @FXML void clearFilter() { appointmentsList.setPredicate(null); }

    /**
     * Back to main menu
     */
    @FXML void loadMenu(ActionEvent event) throws IOException {
        myHelpers.changeScene(
                "mainMenu.fxml",
                600,400,
                "Appointment Management App",
                event
        );
    }

}
