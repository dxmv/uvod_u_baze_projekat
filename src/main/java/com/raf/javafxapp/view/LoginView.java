package com.raf.javafxapp.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import repository.TherapistRepository;
import com.raf.javafxapp.SessionManager;

public class LoginView extends VBox {
    
    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label statusLabel = new Label();
    private TherapistRepository therapistRepository = new TherapistRepository();
    private Stage stage;
    
    public LoginView(Stage stage, Scene previousScene) {
        this.stage = stage;
        
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
        
        // Vrlo jednostavna validacija
        if (email.isEmpty()) {
            statusLabel.setText("Molimo unesite email i lozinku.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        int kandidatId = therapistRepository.getTherapistIdByEmail(email);

        if (kandidatId == 0) {
            statusLabel.setText("Neispravan email ili lozinka.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        else {
            // Store the kandidatId in the session
            SessionManager.getInstance().setLoggedInKandidatId(kandidatId);
            
            statusLabel.setText("Uspešna prijava! ID: " + kandidatId);
            statusLabel.setStyle("-fx-text-fill: green;");
            
            // Show temporary success message, then redirect after a short delay
            redirectToMainScreen();
        }
    }
    
    private void redirectToMainScreen() {
        // Create and display the main frame with updated buttons
        MainFrame mainFrame = new MainFrame(stage);
        Scene mainScene = new Scene((Parent)mainFrame, 500, 500);
        stage.setScene(mainScene);
    }
} 