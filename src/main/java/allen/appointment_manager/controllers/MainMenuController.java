package allen.appointment_manager.controllers;

import allen.appointment_manager.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Main Menu Controller
 */
public class MainMenuController {

    Helpers myHelpers = new Helpers();
    @FXML private Button appointmentsBtn;
    @FXML private Button customersBtn;
    @FXML private Button exitBtn;
    @FXML private Button reportsBtn;

    /**
     * quits the app
     */
    @FXML void exitApp(ActionEvent event) {
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
