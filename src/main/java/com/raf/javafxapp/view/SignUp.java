package com.raf.javafxapp.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SignUp extends AnchorPane {

    private TextField imeField = new TextField();
    private TextField prezimeField = new TextField();
    private TextField emailField = new TextField();
    private PasswordField lozinkaField = new PasswordField();
    private TextField telefonField = new TextField();
    private TextField specijalizacijaField = new TextField();
    private TextField iskustvoField = new TextField();
    private Label statusLabel = new Label();

    public SignUp() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));

        imeField.setPromptText("Ime");
        prezimeField.setPromptText("Prezime");
        emailField.setPromptText("Email");
        lozinkaField.setPromptText("Lozinka");
        telefonField.setPromptText("Broj telefona");
        specijalizacijaField.setPromptText("Specijalizacija");
        iskustvoField.setPromptText("Radno iskustvo (u godinama)");

        Button registerButton = new Button("Registruj se");
        registerButton.setOnAction(e -> handleRegister());

        form.getChildren().addAll(
                new Text("Registracija psihoterapeuta"),
                imeField,
                prezimeField,
                emailField,
                lozinkaField,
                telefonField,
                specijalizacijaField,
                iskustvoField,
                registerButton,
                statusLabel
        );

        this.getChildren().add(form);
    }

    private void handleRegister() {
        String dbUrl = "jdbc:mysql://localhost:3306/savetovaliste"; // promeni naziv baze ako treba
        String dbUser = "root"; // korisnik
        String dbPassword = "lozinka"; // lozinka baze

        String sql = "INSERT INTO Psihoterapeut (ime, prezime, email, lozinka, broj_telefona, specijalizacija, radno_iskustvo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, imeField.getText());
            stmt.setString(2, prezimeField.getText());
            stmt.setString(3, emailField.getText());
            stmt.setString(4, lozinkaField.getText());
            stmt.setString(5, telefonField.getText());
            stmt.setString(6, specijalizacijaField.getText());
            stmt.setInt(7, Integer.parseInt(iskustvoField.getText()));

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                statusLabel.setText("Registracija uspešna!");
            } else {
                statusLabel.setText("Greška pri registraciji.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Greška: " + e.getMessage());
        }
    }
}
