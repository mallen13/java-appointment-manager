module allen.appointment_manager {
    requires javafx.controls;
    requires javafx.fxml;


    opens allen.appointment_manager to javafx.fxml;
    exports allen.appointment_manager;
    exports allen.appointment_manager.controllers;
    opens allen.appointment_manager.controllers to javafx.fxml;
}