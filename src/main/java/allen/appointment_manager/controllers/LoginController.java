package allen.appointment_manager.controllers;

import allen.appointment_manager.models.Appointment;
import allen.appointment_manager.models.DataAccessObject;
import allen.appointment_manager.models.User;
import allen.appointment_manager.helpers.Helpers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Attempts sign-in
 */
public class LoginController {

    /**
     * logged in user
     */
    User user = new User();

    /**
     * new helper instance
     */
    Helpers myHelpers = new Helpers();

    /**
     * users locale
     */
    String locale = Locale.getDefault().toString();

    @FXML private Text locationLabel;
    @FXML private Button loginBtn;
    @FXML private TextField passwordInput;
    @FXML private TextField usernameInput;
    @FXML private Text loginLabel;
    @FXML private Text usernameLabel;
    @FXML private Text passwordLabel;

    /**
     * Log Login Attempts to login_activity.txt whether the attempt passed or failed
     */
    private void logLoginAttempt(User user, boolean success) {
        String fileName = "login_activity.txt";
        String message = "userId: " + user.getUserId() + ", " +
                (success ? "status: succeeded," : "status: failed") + " dateTime: " +
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(message + System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * check if appointment within next 15 mins
     */
    private void checkForAppts() throws SQLException {
        boolean noAppts = true;

        //get appointment times
        ObservableList<Appointment> appointmentsList = DataAccessObject.getAppointments();
        //loop through appointmentsList
        for (Appointment appointment: appointmentsList) {
            //if any start time is within now through the next 15 mins, return true
            Duration duration = Duration.between(LocalDateTime.now(), appointment.getStartDateTime());

            long diff = Math.abs(duration.toMinutes());
            if (diff <= 15 && diff >= 0) {
                myHelpers.showAlert(
                        "Upcoming Appointment",
                        "Upcoming Appointment: \n" + "ID: " + appointment.getAppointmentId() + "\nDATE/TIME: " + appointment.getStartDateTime()
                );
                noAppts = false;
            }
        }



        if (noAppts) {
            myHelpers.showAlert("Upcoming Appointment", "No upcoming appointments");
        }
    }

    /**
     * Initiate Sign-In Process
     * LAMBDA: used here to pass a callback into the validation helper function.
     * If the helper were passed directly into the inputs it would be executed before being called, so wrapping
     * it in the lambda allowed the changeScene helper to only be called as needed.
     */
    @FXML void login(ActionEvent event) throws IOException, SQLException {
        //variable initialization
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        String alertTitle = locale.equals("fr_FR") ? "erreur d'entrée" : "Input Error";
        String alertMessage = locale.equals("fr_FR") ? "pas d'entrée vide" : "Inputs cannot be empty.";
        String invalidTitle = locale.equals("fr_FR") ? "Invalide" : "Invalid";
        String invalidCredentials = locale.equals("fr_FR") ? "Nom d'utilisateur ou mot de passe incorrect" : "Invalid Username or Password";

        //validate inputs
        boolean emptyInputs = myHelpers.isAnyEmpty(username,password);
        if (emptyInputs) {
            myHelpers.showAlert(alertTitle, alertMessage);
            return;
        }

        //attempt login
        final int userId = DataAccessObject.authenticateUser(username,password);

        //if login fail
        if (userId == -1) {
            myHelpers.showAlert(invalidTitle,invalidCredentials);
            logLoginAttempt(user,false);
            return;
        }

        //set user
        user.setUserId(userId);
        user.setUserName(usernameInput.getText());

        //log to documents
        logLoginAttempt(user,true);

        //get appointment alert
        checkForAppts();

        //change scene
        myHelpers.changeScene(
                "mainMenu.fxml",
                600, 400,
                "Appointment Management App",
                event
        );

    }

    public static String getUserName() {
        return User.getUserName();
    }

    /**
     *get location and set language
     */
    @FXML public void initialize() {
        ZoneId zoneId = ZoneId.systemDefault();

        //set time zone
        user.setTimeZone(zoneId.toString());

        //change to french if needed
        if (locale.equals("fr_FR")) {
            loginLabel.setText("Connexion");
            usernameLabel.setText("nom d'utilisateur");
            passwordLabel.setText("mot de passe");
            locationLabel.setText("Emplacement: " + zoneId.toString());
            loginBtn.setText("Connexion");
        } else {
            locationLabel.setText("Location: " + zoneId.toString());
        }
    }
}
