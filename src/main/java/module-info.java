module com.raf.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.raf.javafxapp to javafx.fxml;
    exports com.raf.javafxapp;
}