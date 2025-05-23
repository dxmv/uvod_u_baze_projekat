module com.raf.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires static lombok;
    requires java.desktop;
    requires jdk.jdi;

    opens model;
    opens com.raf.javafxapp to javafx.fxml;
    opens com.raf.javafxapp.view to javafx.fxml, javafx.graphics;
    exports com.raf.javafxapp;
    exports com.raf.javafxapp.view;

}