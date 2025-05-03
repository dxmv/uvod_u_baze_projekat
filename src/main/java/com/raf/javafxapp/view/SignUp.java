package com.raf.javafxapp.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CentarZaObuku;
import model.Fakultet;
import model.OblastTerapije;
import model.StepenStudija;
import repository.CentarZaObukuRepository;
import repository.FakultetRepository;
import repository.OblastTerapijeRepository;
import repository.StepenStudijaRepository;
import repository.TherapistRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class SignUp extends VBox {

    // Personal information fields
    private TextField imeField = new TextField();
    private TextField prezimeField = new TextField();
    private TextField emailField = new TextField();
    private PasswordField lozinkaField = new PasswordField();
    private TextField telefonField = new TextField();
    private TextField jmbgField = new TextField();
    private DatePicker datumRodjField = new DatePicker();
    private TextField prebivalisteField = new TextField();
    private CheckBox psihologCheckBox = new CheckBox("Psiholog");
    
    // Education and certification fields
    private ComboBox<Fakultet> fakultetComboBox = new ComboBox<>();
    private ComboBox<StepenStudija> stepenStudijaComboBox = new ComboBox<>();
    private ComboBox<CentarZaObuku> centarZaObukuComboBox = new ComboBox<>();
    private ComboBox<OblastTerapije> oblastTerapijeComboBox = new ComboBox<>();
    private DatePicker datumSertifikataField = new DatePicker();


    private Label statusLabel = new Label();
    private TherapistRepository therapistRepository = new TherapistRepository();

    public SignUp(Stage stage, Scene previousScene) {
        // Set up layout
        setSpacing(10);
        setPadding(new Insets(20));

        // Create title
        Label title = new Label("Registracija psihoterapeuta");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Set prompts for all fields
        imeField.setPromptText("Ime");
        prezimeField.setPromptText("Prezime");
        emailField.setPromptText("Email");
        lozinkaField.setPromptText("Lozinka");
        telefonField.setPromptText("Broj telefona");
        jmbgField.setPromptText("JMBG");
        datumRodjField.setPromptText("Datum rođenja");
        prebivalisteField.setPromptText("Prebivalište");
        
        // Set default text values for all fields
        imeField.setText("Marko");
        prezimeField.setText("Marković");
        emailField.setText("marko@example.com");
        lozinkaField.setText("password123");
        telefonField.setText("0601234567");
        jmbgField.setText("1234567890123");
        prebivalisteField.setText("Beograd");
        psihologCheckBox.setSelected(true);
        
        // Set date picker to current date
        datumRodjField.setValue(LocalDate.of(1990, 1, 15));
        datumSertifikataField.setValue(LocalDate.of(2022, 6, 10));
        
        // Load data for combo boxes
        loadFakulteti();
        loadStepenStudija();
        loadCentriZaObuku();
        loadOblastiTerapije();
        
        // Create buttons
        Button registerButton = new Button("Registruj se");
        registerButton.setOnAction(e -> handleRegister());
        
        Button backButton = new Button("Nazad");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        // Add elements to view
        getChildren().addAll(
                title,
                // Personal information section
                new Label("Lični podaci:"),
                imeField,
                prezimeField,
                jmbgField,
                datumRodjField,
                emailField,
                telefonField,
                prebivalisteField,
                psihologCheckBox,
                lozinkaField,
                
                // Education section
                new Label("Obrazovanje:"),
                new Label("Fakultet:"),
                fakultetComboBox,
                new Label("Stepen studija:"),
                stepenStudijaComboBox,
                
                // Certification section
                new Label("Sertifikacija:"),
                new Label("Centar za obuku:"),
                centarZaObukuComboBox,
                new Label("Oblast terapije:"),
                oblastTerapijeComboBox,
                new Label("Datum sertifikata:"),
                datumSertifikataField,

                // Original fields (kept for compatibility)
                new Label("Dodatni podaci:"),

                registerButton,
                backButton,
                statusLabel
        );
    }
    
    // Empty constructor for backward compatibility
    public SignUp() {
        this(null, null);
    }
    
    private void loadFakulteti() {
        FakultetRepository repository = new FakultetRepository();
        fakultetComboBox.setItems(FXCollections.observableArrayList(repository.getAllFakulteti()));
        fakultetComboBox.setCellFactory(lv -> new ListCell<Fakultet>() {
            @Override
            protected void updateItem(Fakultet item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getIme());
            }
        });
        fakultetComboBox.setButtonCell(new ListCell<Fakultet>() {
            @Override
            protected void updateItem(Fakultet item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getIme());
            }
        });
        
        // Select first item if available
        if (!fakultetComboBox.getItems().isEmpty()) {
            fakultetComboBox.getSelectionModel().selectFirst();
        }
    }
    
    private void loadStepenStudija() {
        StepenStudijaRepository repository = new StepenStudijaRepository();
        stepenStudijaComboBox.setItems(FXCollections.observableArrayList(repository.getAllStepenStudija()));
        stepenStudijaComboBox.setCellFactory(lv -> new ListCell<StepenStudija>() {
            @Override
            protected void updateItem(StepenStudija item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getNaziv());
            }
        });
        stepenStudijaComboBox.setButtonCell(new ListCell<StepenStudija>() {
            @Override
            protected void updateItem(StepenStudija item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getNaziv());
            }
        });
        
        // Select first item if available
        if (!stepenStudijaComboBox.getItems().isEmpty()) {
            stepenStudijaComboBox.getSelectionModel().selectFirst();
        }
    }
    
    private void loadCentriZaObuku() {
        CentarZaObukuRepository repository = new CentarZaObukuRepository();
        centarZaObukuComboBox.setItems(FXCollections.observableArrayList(repository.getAllCentriZaObuku()));
        centarZaObukuComboBox.setCellFactory(lv -> new ListCell<CentarZaObuku>() {
            @Override
            protected void updateItem(CentarZaObuku item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getNaziv());
            }
        });
        centarZaObukuComboBox.setButtonCell(new ListCell<CentarZaObuku>() {
            @Override
            protected void updateItem(CentarZaObuku item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getNaziv());
            }
        });
        
        // Select first item if available
        if (!centarZaObukuComboBox.getItems().isEmpty()) {
            centarZaObukuComboBox.getSelectionModel().selectFirst();
        }
    }
    
    private void loadOblastiTerapije() {
        OblastTerapijeRepository repository = new OblastTerapijeRepository();
        oblastTerapijeComboBox.setItems(FXCollections.observableArrayList(repository.getAllOblastiTerapije()));
        oblastTerapijeComboBox.setCellFactory(lv -> new ListCell<OblastTerapije>() {
            @Override
            protected void updateItem(OblastTerapije item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getIme());
            }
        });
        oblastTerapijeComboBox.setButtonCell(new ListCell<OblastTerapije>() {
            @Override
            protected void updateItem(OblastTerapije item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getIme());
            }
        });
        
        // Select first item if available
        if (!oblastTerapijeComboBox.getItems().isEmpty()) {
            oblastTerapijeComboBox.getSelectionModel().selectFirst();
        }
    }

    private void handleRegister() {
        try {
            // Get values from form fields
            String ime = imeField.getText();
            String prezime = prezimeField.getText();
            String email = emailField.getText();
            String telefon = telefonField.getText();
            String jmbg = jmbgField.getText();
            LocalDate datumRodj = datumRodjField.getValue();
            String prebivaliste = prebivalisteField.getText();
            boolean isPsiholog = psihologCheckBox.isSelected();
            
            // Get selected values from combo boxes
            Fakultet fakultet = fakultetComboBox.getValue();
            StepenStudija stepenStudija = stepenStudijaComboBox.getValue();
            CentarZaObuku centarZaObuku = centarZaObukuComboBox.getValue();
            OblastTerapije oblastTerapije = oblastTerapijeComboBox.getValue();
            LocalDate datumSertifikata = datumSertifikataField.getValue();
            
            // Validate required fields
            if (ime.isEmpty() || prezime.isEmpty() || email.isEmpty() || 
                telefon.isEmpty() || jmbg.isEmpty() || prebivaliste.isEmpty() || 
                datumRodj == null || fakultet == null || stepenStudija == null || 
                centarZaObuku == null || oblastTerapije == null || datumSertifikata == null) {
                
                statusLabel.setText("Molimo popunite sva polja.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            
            // Register therapist using repository
            boolean success = therapistRepository.registerTherapist(
                ime, 
                prezime, 
                email, 
                telefon, 
                jmbg, 
                datumRodj, 
                prebivaliste, 
                isPsiholog,
                fakultet.getIme(),
                stepenStudija.getNaziv(),
                centarZaObuku.getNaziv(),
                oblastTerapije.getIme(),
                datumSertifikata
            );
            
            if (success) {
                statusLabel.setText("Registracija uspešna!");
                statusLabel.setStyle("-fx-text-fill: green;");
                clearForm();
            } else {
                statusLabel.setText("Greška pri registraciji. Pokušajte ponovo.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Greška: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }
    
    private void clearForm() {
        // Reset form fields after successful registration
        imeField.clear();
        prezimeField.clear();
        emailField.clear();
        lozinkaField.clear();
        telefonField.clear();
        jmbgField.clear();
        prebivalisteField.clear();
        psihologCheckBox.setSelected(false);
        datumRodjField.setValue(LocalDate.now());
        datumSertifikataField.setValue(LocalDate.now());
        
        // Reset combo boxes to first item
        if (!fakultetComboBox.getItems().isEmpty()) {
            fakultetComboBox.getSelectionModel().selectFirst();
        }
        if (!stepenStudijaComboBox.getItems().isEmpty()) {
            stepenStudijaComboBox.getSelectionModel().selectFirst();
        }
        if (!centarZaObukuComboBox.getItems().isEmpty()) {
            centarZaObukuComboBox.getSelectionModel().selectFirst();
        }
        if (!oblastTerapijeComboBox.getItems().isEmpty()) {
            oblastTerapijeComboBox.getSelectionModel().selectFirst();
        }
    }
}
