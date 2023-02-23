package allen.appointment_manager.helpers;

import allen.appointment_manager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

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

    public boolean isAnyEmpty(String...inputs) {
        for (String input : inputs) {
            if (input.isEmpty()) {
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
     * convert provided date/time to UTC
     */
    public static LocalDateTime convertToUTC(LocalDateTime localDateTime) {
        // Set the timezone of the input date to the local timezone
        ZoneId localZone = ZoneId.systemDefault();

        // Get the offset from UTC for the local timezone at the given date and time
        ZoneOffset localOffset = localZone.getRules().getOffset(localDateTime);

        // Adjust the input date and time to the equivalent UTC date and time
        LocalDateTime utcDateTime = localDateTime.atOffset(localOffset)
                .withOffsetSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();
        return utcDateTime;
    }

}
