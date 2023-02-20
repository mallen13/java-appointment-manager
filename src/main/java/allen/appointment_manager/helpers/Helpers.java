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
import java.util.Date;

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
     * Convert to UTC
     */
    public Date convertToUTC(Date dateTime) {
        System.out.println(dateTime);

        return dateTime;
    }

    /**
     * Convert from UTC
     */
}
