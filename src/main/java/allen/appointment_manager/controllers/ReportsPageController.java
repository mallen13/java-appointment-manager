package allen.appointment_manager.controllers;

import allen.appointment_manager.helpers.Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.io.IOException;

/**
 * View reports and schedule
 */
public class ReportsPageController {
    Helpers myHelpers = new Helpers();

    @FXML private Button menuBtn;
    @FXML private TableView<?> recordsTable;
    @FXML private TableView<?> report1Table;
    @FXML private TableView<?> report2Table;

    /**
     * Back to main menu
     * @param event
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
     * filter schedule by contact
     * @param event
     */
    @FXML void filterByContact(ActionEvent event) {

    }

}
