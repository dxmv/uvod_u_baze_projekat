package com.raf.javafxapp;


import com.raf.javafxapp.Model.Psychotherapist;
import com.raf.javafxapp.view.Login;
import com.raf.javafxapp.view.MainFrame;
import com.raf.javafxapp.view.SignUp;
import com.raf.javafxapp.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Psychotherapist therapist = new Psychotherapist(
                "Marko Marković",
                "marko@example.com",
                "+381 64 123 4567",
                "Kognitivno-bihevioralna terapija",
                "Psihoterapeut sa 10 godina iskustva u radu sa adolescentima i odraslima."
        );

        Scene scene = new Scene(new MainFrame(stage));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}