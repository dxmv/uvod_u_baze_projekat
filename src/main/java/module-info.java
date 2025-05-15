module com.raf.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;
    requires jdk.jdi;

    opens model;
    opens com.raf.javafxapp to javafx.fxml;
    exports com.raf.javafxapp;

}