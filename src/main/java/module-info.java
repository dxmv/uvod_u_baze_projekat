module com.raf.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;

    requires java.desktop;

    opens  com.raf.javafxapp.Model;
    opens com.raf.javafxapp to javafx.fxml;
    exports com.raf.javafxapp;
}