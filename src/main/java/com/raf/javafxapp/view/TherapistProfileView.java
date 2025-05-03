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
import repository.CandidateRepository;
import model.Therapist;
import model.Candidate;

public class TherapistProfileView extends VBox {
    
    private int therapistId;
    private boolean isCertified;
    private Label nameLabel = new Label();
    private Label emailLabel = new Label();
    private Label jmbgLabel = new Label();
    private Label phoneLabel = new Label();
    private Label educationLabel = new Label();
    private Label certificationLabel = new Label();
    private TherapistRepository therapistRepository = new TherapistRepository();
    private CandidateRepository candidateRepository = new CandidateRepository();
    
    public TherapistProfileView(Stage stage, Scene previousScene) {
        // Set up layout
        setSpacing(10);
        setPadding(new Insets(20));
        
        // Get current user ID and certification status from session
        therapistId = SessionManager.getInstance().getLoggedInKandidatId();
        isCertified = SessionManager.getInstance().isTherapistCertified();
        
        // Create title
        Label title = new Label(isCertified ? "Profil terapeuta" : "Profil kandidata");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Create placeholder labels
        nameLabel.setText("Ime i prezime: ");
        emailLabel.setText("Email: ");
        jmbgLabel.setText("JMBG: ");
        phoneLabel.setText("Telefon: ");
        educationLabel.setText("Obrazovanje: ");
        certificationLabel.setText("Sertifikacija: ");
        
        // Get user data if available
        if (therapistId > 0) {
            loadUserData();
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
    
    private void loadUserData() {
        if (isCertified) {
            loadTherapistData();
        } else {
            loadCandidateData();
        }
    }
    
    private void loadTherapistData() {
        Therapist therapist = therapistRepository.getTherapistById(therapistId);
        
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
            displayUserNotFoundError();
        }
    }
    
    private void loadCandidateData() {
        Candidate candidate = candidateRepository.getCandidateById(therapistId);
        
        if (candidate != null) {
            // Populate with real data
            nameLabel.setText("Ime i prezime: " + candidate.getIme() + " " + candidate.getPrezime());
            emailLabel.setText("Email: " + candidate.getEmail());
            jmbgLabel.setText("JMBG: " + candidate.getJmbg());
            phoneLabel.setText("Telefon: " + candidate.getTelefon());
            
            String education = "Obrazovanje: " + 
                               candidate.getFakultet().getIme() + ", " + 
                               candidate.getStepenStudija().getNaziv();
            educationLabel.setText(education);
            
            // For candidates, no certification info
            certificationLabel.setText("Status: Kandidat (U procesu sertifikacije)");
        } else {
            // No data found, show error
            displayUserNotFoundError();
        }
    }
    
    private void displayUserNotFoundError() {
        nameLabel.setText("Greška: Podaci za korisnika sa ID " + therapistId + " nisu pronađeni");
        emailLabel.setText("");
        jmbgLabel.setText("");
        phoneLabel.setText("");
        educationLabel.setText("");
        certificationLabel.setText("");
    }
}
