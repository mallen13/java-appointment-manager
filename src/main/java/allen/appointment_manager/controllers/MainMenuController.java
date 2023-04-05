package allen.appointment_manager.controllers;

import allen.appointment_manager.helpers.DatabaseConnector;
import allen.appointment_manager.helpers.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Main Menu Controller
 */
public class MainMenuController {

    /**
     * new helper instance
     */
    Helpers myHelpers = new Helpers();

    @FXML private Button appointmentsBtn;
    @FXML private Button customersBtn;
    @FXML private Button exitBtn;
    @FXML private Button reportsBtn;
    @FXML private Label userName;


    @FXML public void initialize() {
        String userNameVal;
        userNameVal = LoginController.getUserName();
        userName.setText(userNameVal);
    }
    /**
     * quits the app
     */
    @FXML void exitApp(ActionEvent event) {
        DatabaseConnector.closeConnection();
        System.exit(0);
    }

    /**
     * Proceeds to appointments page
     */
    @FXML void showAppointmentsPage(ActionEvent event) throws IOException {
        myHelpers.changeScene(
                "appointmentsPage.fxml",
                1063,739,
                "Appointment Management App",
                event
        );
    }

    /**
     * Proceeds to customers page
     */
    @FXML void showCustomersPage(ActionEvent event) throws IOException {
        myHelpers.changeScene(
                "customersPage.fxml",
                1063,739,
                "Appointment Management App",
                event
        );
    }

    /**
     * Proceeds to reports page
     */
    @FXML void showReportsPage(ActionEvent event) throws IOException {
        myHelpers.changeScene(
                "reportsPage.fxml",
                1063,739,
                "Appointment Management App",
                event
        );
    }

}
