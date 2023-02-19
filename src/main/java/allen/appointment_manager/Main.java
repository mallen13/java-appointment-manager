package allen.appointment_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main App Class
 *
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Appointment Management App");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches Class
     */
    public static void main(String[] args) {
        launch();
    }
}