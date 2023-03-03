package allen.appointment_manager.helpers;

import allen.appointment_manager.Main;
import allen.appointment_manager.models.Appointment;
import allen.appointment_manager.models.DataAccessObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.util.List;

/**
 * Class of various helper functions
 */
public class Helpers {

    /**
     * General Change Scene helper for re-usability
     * @params file, width, height, title,event
     */
    public void changeScene(String file,int width, int height,String title, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(file));
        //FXMLLoader fxmlLoader = new FXMLLoader();
        //fxmlLoader.setLocation(getClass().getResource(file));
        Scene scene = new Scene(fxmlLoader.load(),width,height);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    };

    /**
     * evaluate if any input string is empty
     * @param inputs
     * @return
     */
    public boolean isAnyEmpty(String...inputs) {
        for (String input : inputs) {
            if (input.isEmpty() || input == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shows window alert
     * @params title, message
     */
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
        return;
    }

    /**
     * genearic confirm helperp
     * @param title
     * @param headerText
     * @return
     */
    public static boolean showConfirm(String title, String headerText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText("This cannot be undone.");

        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(okButton, cancelButton);

        boolean result = false;
        if (alert.showAndWait().get() == okButton) {
            result = true;
        }
        return result;
    }

    /**
     * get a list of times from 8-10 EST
     * @return
     */
    public static ObservableList<LocalTime> getTimes() {
        ObservableList<LocalTime> times = FXCollections.observableArrayList();
        ZoneId userTimeZone = ZoneId.systemDefault();

        int hour = 6; //to allow for before 8am for testing
        int minute = 00;

        for (int i = 0; i < 72; i++) {

            ZonedDateTime estTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(hour, minute), ZoneId.of("America/New_York"));
            ZonedDateTime userTime = estTime.withZoneSameInstant(userTimeZone);
            times.add(userTime.toLocalTime());
            minute += 15;
            if (minute > 45) {
                minute = 00;
                hour++;
            }
        }
        return times;
    }

    /**
     * checks if start and end time between 8a and 10p EST
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isValidAppointment(LocalTime startTime, LocalTime endTime) {
        ZoneId userTimeZone = ZoneId.systemDefault();
        ZoneId estTimeZone = ZoneId.of("America/New_York");

        // Convert start time to EST
        ZonedDateTime estStartDateTime = LocalDateTime.of(LocalDate.now(userTimeZone), startTime).atZone(userTimeZone).withZoneSameInstant(estTimeZone);

        // Convert end time to EST
        ZonedDateTime estEndDateTime = LocalDateTime.of(LocalDate.now(userTimeZone), endTime).atZone(userTimeZone).withZoneSameInstant(estTimeZone);

        // Check if converted start time is after 7:59 EST and converted end time is before 10:01 PM
        LocalTime estStartTime = estStartDateTime.toLocalTime();
        LocalTime estEndTime = estEndDateTime.toLocalTime();

        return estStartTime.isAfter(LocalTime.of(7, 59)) && estEndTime.isBefore(LocalTime.of(23, 01));
    }

    /**
     * checks for any appointment overlap with others from DB
     * @param startDateTime
     * @param endDateTime
     * @return
     * @throws SQLException
     */
    public boolean doesAppointmentOverlap(LocalDateTime startDateTime, LocalDateTime endDateTime) throws SQLException {
        // Convert start time
        ZoneId userTimeZone = ZoneId.systemDefault();
        ZoneOffset utcOffset = ZoneOffset.UTC;
        ZonedDateTime utcStartDateTime = startDateTime.atZone(userTimeZone).withZoneSameInstant(utcOffset);
        ZonedDateTime utcEndDateTime = endDateTime.atZone(userTimeZone).withZoneSameInstant(utcOffset);

        // Get all appointments from DB
        List<Appointment> appointments = DataAccessObject.getAppointments();

        // Check if overlap
        for (Appointment appointment : appointments) {
            ZonedDateTime existingStartDateTime = appointment.getStartDateTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(utcOffset);
            ZonedDateTime existingEndDateTime = appointment.getEndDateTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(utcOffset);

            if (utcStartDateTime.isBefore(existingEndDateTime) && existingStartDateTime.isBefore(utcEndDateTime)) {
                return true;
            }
        }
        return false;
    }



}
