package com.raf.javafxapp.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView extends VBox {
    
    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label statusLabel = new Label();
    
    public LoginView(Stage stage, Scene previousScene) {
        // Set up layout
        setSpacing(10);
        setPadding(new Insets(20));
        
        // Create title
        Label title = new Label("Prijava korisnika");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Create form fields
        emailField.setPromptText("Email");
        passwordField.setPromptText("Lozinka");
        
        // Create buttons
        Button loginButton = new Button("Prijavi se");
        loginButton.setOnAction(e -> handleLogin());
        
        Button backButton = new Button("Nazad");
        backButton.setOnAction(e -> stage.setScene(previousScene));
        
        // Add elements to view
        getChildren().addAll(
            title,
            new Label("Email:"),
            emailField,
            new Label("Lozinka:"),
            passwordField,
            loginButton,
            backButton,
            statusLabel
        );
    }
    
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        
        // Vrlo jednostavna validacija
        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Molimo unesite email i lozinku.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        
        // Ovde bi trebalo implementirati pravu autentikaciju
        // Za sada samo prikazujemo poruku o uspešnoj prijavi
        statusLabel.setText("Uspešna prijava!");
        statusLabel.setStyle("-fx-text-fill: green;");
        
        // Nakon uspešne prijave možda biste hteli da pređete na drugi ekran
        // ili da prikažete poruku dobrodošlice
    }
} 