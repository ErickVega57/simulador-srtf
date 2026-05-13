module uady.so.simuladorsrtf {
    requires javafx.controls;
    requires javafx.fxml;


    opens uady.so.simuladorsrtf to javafx.fxml;
    exports uady.so.simuladorsrtf;
    exports uady.so.simuladorsrtf.controller;
    opens uady.so.simuladorsrtf.controller to javafx.fxml;
    opens uady.so.simuladorsrtf.model.clases to javafx.base;
}