package com.raf.javafxapp.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.raf.javafxapp.SessionManager;
import repository.TherapistRepository;
import model.Therapist;

public class TherapistProfileView extends VBox {
    
    private int therapistId;
    private Label nameLabel = new Label();
    private Label emailLabel = new Label();
    private Label jmbgLabel = new Label();
    private Label phoneLabel = new Label();
    private Label educationLabel = new Label();
    private Label certificationLabel = new Label();
    private TherapistRepository repository = new TherapistRepository();
    
    public TherapistProfileView(Stage stage, Scene previousScene) {
        // Set up layout
        setSpacing(10);
        setPadding(new Insets(20));
        
        // Get current therapist ID from session
        therapistId = SessionManager.getInstance().getLoggedInKandidatId();
        
        // Create title
        Label title = new Label("Profil terapeuta");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Create placeholder labels
        nameLabel.setText("Ime i prezime: ");
        emailLabel.setText("Email: ");
        jmbgLabel.setText("JMBG: ");
        phoneLabel.setText("Telefon: ");
        educationLabel.setText("Obrazovanje: ");
        certificationLabel.setText("Sertifikacija: ");
        
        // Get therapist data if available
        if (therapistId > 0) {
            loadTherapistData();
        } else {
            nameLabel.setText("Greška: Korisnik nije prijavljen");
        }
        
        // Create separator
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        
        // Create back button
        Button backButton = new Button("Nazad");
        backButton.setOnAction(e -> stage.setScene(previousScene));
        
        // Add elements to view
        getChildren().addAll(
            title,
            nameLabel,
            emailLabel,
            jmbgLabel,
            phoneLabel,
            separator,
            educationLabel,
            certificationLabel,
            new Separator(),
            backButton
        );
    }
    
    private void loadTherapistData() {
        // In a real implementation, this would fetch data from the repository
        // For now, we'll just display the ID and placeholder information
        
        Therapist therapist = repository.getTherapistById(therapistId);
        
        if (therapist != null) {
            // Populate with real data
            nameLabel.setText("Ime i prezime: " + therapist.getIme() + " " + therapist.getPrezime());
            emailLabel.setText("Email: " + therapist.getEmail());
            jmbgLabel.setText("JMBG: " + therapist.getJmbg());
            phoneLabel.setText("Telefon: " + therapist.getTelefon());
            
            String education = "Obrazovanje: " + 
                               therapist.getFakultet().getIme() + ", " + 
                               therapist.getStepenStudija().getNaziv();
            educationLabel.setText(education);
            
            String certification = "Sertifikacija: " + 
                                  therapist.getSertifikat().getOblast().getIme() + ", " +
                                  "datum: " + therapist.getSertifikat().getDatumSert();
            certificationLabel.setText(certification);
        } else {
            // No data found, show error
            nameLabel.setText("Greška: Podaci za korisnika sa ID " + therapistId + " nisu pronađeni");
            emailLabel.setText("");
            jmbgLabel.setText("");
            phoneLabel.setText("");
            educationLabel.setText("");
            certificationLabel.setText("");
        }
    }
}
