package com.raf.javafxapp.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TherapistProfileView {

    private Stage stage;

    public TherapistProfileView(Stage stage) {
        this.stage = stage;
        initializeUI();
    }

    private void initializeUI() {
        Label titleLabel = new Label("Profil psihoterapeuta");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

//        Label nameLabel = new Label("Ime i prezime: " + therapist.getFullName());
//        Label emailLabel = new Label("Email: " + therapist.getEmail());
//        Label phoneLabel = new Label("Telefon: " + therapist.getPhone());
//        Label specializationLabel = new Label("Specijalizacija: " + therapist.getSpecialization());
//        Label bioLabel = new Label("Biografija: " + therapist.getBiography());
//        bioLabel.setWrapText(true);
//        VBox vbox = new VBox(10, titleLabel, nameLabel, emailLabel, phoneLabel, specializationLabel, bioLabel);
//        vbox.setPadding(new Insets(20));

//        Scene scene = new Scene(vbox, 400, 400);
//        stage.setScene(scene);
//        stage.setTitle("Pregled profila");
//        stage.show();
    }
}
