package com.raf.javafxapp.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CentarZaObuku;
import model.Fakultet;
import model.OblastTerapije;
import model.StepenStudija;
import repository.CentarZaObukuRepository;
import repository.FakultetRepository;
import repository.OblastTerapijeRepository;
import repository.StepenStudijaRepository;

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
        
        // Set prompts for original fields

        // Set date picker to current date
        datumRodjField.setValue(LocalDate.now());
        datumSertifikataField.setValue(LocalDate.now());
        
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
