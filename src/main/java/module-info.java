module com.raf.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;

    opens model;
    opens com.raf.javafxapp to javafx.fxml;
    exports com.raf.javafxapp;

}