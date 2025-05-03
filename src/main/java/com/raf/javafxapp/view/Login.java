package com.raf.javafxapp.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends AnchorPane {

    private TextField emailField = new TextField();
    private PasswordField lozinkaField = new PasswordField();
    private Label statusLabel = new Label();

    public Login(Stage primaryStage) {
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));

        emailField.setPromptText("Email");
        lozinkaField.setPromptText("Lozinka");

        Button loginButton = new Button("Prijavi se");
        loginButton.setOnAction(e -> handleLogin(primaryStage));

        form.getChildren().addAll(
                new Text("Prijava psihoterapeuta"),
                emailField,
                lozinkaField,
                loginButton,
                statusLabel
        );

        this.getChildren().add(form);
    }

    private void handleLogin(Stage primaryStage) {
        String dbUrl = "jdbc:mysql://localhost:3306/savetovaliste"; // prilagodi ako treba
        String dbUser = "root";
        String dbPassword = "lozinka";

        String sql = "SELECT * FROM Psihoterapeut WHERE email = ? AND lozinka = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emailField.getText());
            stmt.setString(2, lozinkaField.getText());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                statusLabel.setText("Uspešna prijava!");



            } else {
                statusLabel.setText("Pogrešan email ili lozinka.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Greška: " + e.getMessage());
        }
    }
}
