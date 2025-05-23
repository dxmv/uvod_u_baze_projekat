package com.raf.javafxapp.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Klijent;
import model.Seansa;
import repository.KlijentRepository;
import repository.SeansaRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleSeansaView extends VBox {

    private Stage stage;
    private Scene previousScene;

    private KlijentRepository klijentRepository;
    private SeansaRepository seansaRepository;

    private ComboBox<KlijentStringWrapper> klijentComboBox;
    private RadioButton selectExistingClientRadio;
    private RadioButton createNewClientRadio;
    private ToggleGroup clientSelectionToggleGroup;

    // Klijent fields
    private TextField imeField;
    private TextField prezimeField;
    private DatePicker datumRodjenjaPicker;
    private ComboBox<String> polComboBox;
    private TextField emailField;
    private TextField telefonField;
    private DatePicker datumPrijavePicker;
    private CheckBox ranijePosetioCheckBox;
    private TextArea opisProblemaArea;
    private GridPane newClientFormGrid;

    // Seansa fields
    private DatePicker seansaDatumPicker;
    private TextField seansaVremePocetkaField; // Format HH:MM
    private TextField seansaTrajanjeField; // In minutes
    private CheckBox besplatnaSeansaCheckBox;


    public ScheduleSeansaView(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.previousScene = previousScene;
        this.klijentRepository = new KlijentRepository();
        this.seansaRepository = new SeansaRepository();

        initUI();
        loadKlijenti();
    }

    private void initUI() {
        setSpacing(15);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Zakazivanje Nove Seanse");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Client Selection Mode
        selectExistingClientRadio = new RadioButton("Izaberi postojećeg klijenta");
        createNewClientRadio = new RadioButton("Kreiraj novog klijenta");
        clientSelectionToggleGroup = new ToggleGroup();
        selectExistingClientRadio.setToggleGroup(clientSelectionToggleGroup);
        createNewClientRadio.setToggleGroup(clientSelectionToggleGroup);
        selectExistingClientRadio.setSelected(true); // Default selection

        HBox radioBox = new HBox(20, selectExistingClientRadio, createNewClientRadio);
        radioBox.setAlignment(Pos.CENTER);

        // Existing Client ComboBox
        klijentComboBox = new ComboBox<>();
        klijentComboBox.setPromptText("Izaberite klijenta");
        klijentComboBox.setPrefWidth(300);

        // New Client Form (initially hidden/disabled)
        initNewClientForm();
        setNewClientFormDisabled(true);

        // Seansa Details Form
        GridPane seansaFormGrid = new GridPane();
        seansaFormGrid.setHgap(10);
        seansaFormGrid.setVgap(10);
        seansaFormGrid.setAlignment(Pos.CENTER);

        seansaDatumPicker = new DatePicker(LocalDate.now());
        seansaVremePocetkaField = new TextField();
        seansaVremePocetkaField.setPromptText("HH:MM (npr. 10:30)");
        seansaTrajanjeField = new TextField();
        seansaTrajanjeField.setPromptText("Trajanje u minutima");
        besplatnaSeansaCheckBox = new CheckBox("Besplatna uvodna seansa");

        seansaFormGrid.add(new Label("Datum seanse:"), 0, 0);
        seansaFormGrid.add(seansaDatumPicker, 1, 0);
        seansaFormGrid.add(new Label("Vreme početka:"), 0, 1);
        seansaFormGrid.add(seansaVremePocetkaField, 1, 1);
        seansaFormGrid.add(new Label("Trajanje (min):"), 0, 2);
        seansaFormGrid.add(seansaTrajanjeField, 1, 2);
        seansaFormGrid.add(besplatnaSeansaCheckBox, 1, 3);

        // Action Buttons
        Button scheduleButton = new Button("Zakaži Seansu");
        scheduleButton.setOnAction(e -> handleScheduleSeansa());

        Button backButton = new Button("Nazad");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        HBox actionButtonsBox = new HBox(20, backButton, scheduleButton);
        actionButtonsBox.setAlignment(Pos.CENTER_RIGHT);


        // Event listener for radio buttons
        clientSelectionToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == selectExistingClientRadio) {
                klijentComboBox.setDisable(false);
                setNewClientFormDisabled(true);
            } else if (newValue == createNewClientRadio) {
                klijentComboBox.setDisable(true);
                klijentComboBox.getSelectionModel().clearSelection();
                setNewClientFormDisabled(false);
            }
        });

        getChildren().addAll(
                titleLabel,
                radioBox,
                klijentComboBox,
                newClientFormGrid, // Add the new client form here
                new Separator(javafx.geometry.Orientation.HORIZONTAL),
                new Label("Detalji Seanse:"),
                seansaFormGrid,
                actionButtonsBox
        );
    }

    private void initNewClientForm() {
        newClientFormGrid = new GridPane();
        newClientFormGrid.setHgap(10);
        newClientFormGrid.setVgap(10);
        newClientFormGrid.setAlignment(Pos.CENTER);
        newClientFormGrid.setPadding(new Insets(10));

        imeField = new TextField();
        prezimeField = new TextField();
        datumRodjenjaPicker = new DatePicker();
        polComboBox = new ComboBox<>(FXCollections.observableArrayList("M", "F", "O")); // Male, Female, Other
        emailField = new TextField();
        telefonField = new TextField();
        datumPrijavePicker = new DatePicker(LocalDate.now());
        ranijePosetioCheckBox = new CheckBox("Ranije posećivao/la");
        opisProblemaArea = new TextArea();
        opisProblemaArea.setPromptText("Kratak opis problema...");
        opisProblemaArea.setPrefRowCount(3);

        newClientFormGrid.add(new Label("Ime:"), 0, 0); newClientFormGrid.add(imeField, 1, 0);
        newClientFormGrid.add(new Label("Prezime:"), 0, 1); newClientFormGrid.add(prezimeField, 1, 1);
        newClientFormGrid.add(new Label("Datum rođenja:"), 0, 2); newClientFormGrid.add(datumRodjenjaPicker, 1, 2);
        newClientFormGrid.add(new Label("Pol:"), 0, 3); newClientFormGrid.add(polComboBox, 1, 3);
        newClientFormGrid.add(new Label("Email:"), 0, 4); newClientFormGrid.add(emailField, 1, 4);
        newClientFormGrid.add(new Label("Telefon:"), 0, 5); newClientFormGrid.add(telefonField, 1, 5);
        newClientFormGrid.add(new Label("Datum prijave:"), 0, 6); newClientFormGrid.add(datumPrijavePicker, 1, 6);
        newClientFormGrid.add(ranijePosetioCheckBox, 1, 7);
        newClientFormGrid.add(new Label("Opis problema:"), 0, 8); newClientFormGrid.add(opisProblemaArea, 1, 8);
    }

    private void setNewClientFormDisabled(boolean disabled) {
        imeField.setDisable(disabled);
        prezimeField.setDisable(disabled);
        datumRodjenjaPicker.setDisable(disabled);
        polComboBox.setDisable(disabled);
        emailField.setDisable(disabled);
        telefonField.setDisable(disabled);
        datumPrijavePicker.setDisable(disabled);
        ranijePosetioCheckBox.setDisable(disabled);
        opisProblemaArea.setDisable(disabled);
        newClientFormGrid.setVisible(!disabled); // Hide if disabled
    }


    private void loadKlijenti() {
        List<Klijent> klijenti = klijentRepository.getAll();
        ObservableList<KlijentStringWrapper> klijentWrappers = FXCollections.observableArrayList(
                klijenti.stream().map(KlijentStringWrapper::new).collect(Collectors.toList())
        );
        klijentComboBox.setItems(klijentWrappers);
    }

    private void handleScheduleSeansa() {
        // Validate Seansa inputs
        LocalDate seansaDatum = seansaDatumPicker.getValue();
        String vremePocetkaStr = seansaVremePocetkaField.getText();
        String trajanjeStr = seansaTrajanjeField.getText();

        if (seansaDatum == null || vremePocetkaStr.isEmpty() || trajanjeStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Greška", "Molimo popunite sva polja za seansu.");
            return;
        }

        Time vremePocetka;
        try {
            LocalTime lt = LocalTime.parse(vremePocetkaStr); // Expects HH:mm
            vremePocetka = Time.valueOf(lt);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Greška", "Format vremena početka nije ispravan (HH:MM).");
            return;
        }

        int trajanje;
        try {
            trajanje = Integer.parseInt(trajanjeStr);
            if (trajanje <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Greška", "Trajanje mora biti pozitivan broj minuta.");
            return;
        }

        Seansa seansa = new Seansa();
        seansa.setDatum(java.sql.Date.valueOf(seansaDatum));
        seansa.setVremePocetka(vremePocetka);
        seansa.setTrajanje(trajanje);
        seansa.setBesplatnaSeansa(besplatnaSeansaCheckBox.isSelected());
        // KandidatId and CenaId will be handled by the stored procedure via SeansaRepository

        boolean success;

        if (selectExistingClientRadio.isSelected()) {
            KlijentStringWrapper selectedKlijentWrapper = klijentComboBox.getSelectionModel().getSelectedItem();
            if (selectedKlijentWrapper == null) {
                showAlert(Alert.AlertType.ERROR, "Greška", "Molimo izaberite postojećeg klijenta.");
                return;
            }
            int klijentId = selectedKlijentWrapper.getKlijent().getKlijentId();
            success = seansaRepository.insertSeansa(seansa, klijentId);
        } else { // Create new client
            // Validate Klijent inputs
            String ime = imeField.getText();
            String prezime = prezimeField.getText();
            LocalDate datumRodjenja = datumRodjenjaPicker.getValue();
            String pol = polComboBox.getSelectionModel().getSelectedItem();
            String email = emailField.getText();
            String telefon = telefonField.getText();
            LocalDate datumPrijave = datumPrijavePicker.getValue();
            // opisProblema can be empty

            if (ime.isEmpty() || prezime.isEmpty() || datumRodjenja == null || pol == null || email.isEmpty() || telefon.isEmpty() || datumPrijave == null) {
                showAlert(Alert.AlertType.ERROR, "Greška", "Molimo popunite sva obavezna polja za novog klijenta.");
                return;
            }
             if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$") && !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\\.[a-zA-Z]{2,6}$")) {
                 // Basic email validation, consider a more robust regex
                  showAlert(Alert.AlertType.ERROR, "Neispravan Format Email-a", "Molimo unesite validnu email adresu.");
                return;
             }


            Klijent klijent = new Klijent();
            klijent.setIme(ime);
            klijent.setPrezime(prezime);
            klijent.setDatumRodj(java.sql.Date.valueOf(datumRodjenja));
            klijent.setPol(pol);
            klijent.setEmail(email);
            klijent.setTelefon(telefon);
            klijent.setDatumPrijave(java.sql.Date.valueOf(datumPrijave));
            klijent.setRanijePosetio(ranijePosetioCheckBox.isSelected());
            klijent.setOpisProblema(opisProblemaArea.getText());

            success = seansaRepository.insertKlijentAndSeansa(klijent, seansa);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Uspeh", "Seansa je uspešno zakazana.");
            // Optionally, clear fields or navigate back
            clearFormFields();
            loadKlijenti(); // Refresh client list if new one was added
        } else {
            showAlert(Alert.AlertType.ERROR, "Greška", "Zakazivanje seanse nije uspelo. Proverite konzolu za detalje.");
        }
    }
    
    private void clearFormFields() {
        // Clear Klijent fields
        imeField.clear();
        prezimeField.clear();
        datumRodjenjaPicker.setValue(null);
        polComboBox.getSelectionModel().clearSelection();
        emailField.clear();
        telefonField.clear();
        datumPrijavePicker.setValue(LocalDate.now());
        ranijePosetioCheckBox.setSelected(false);
        opisProblemaArea.clear();

        // Clear Seansa fields
        seansaDatumPicker.setValue(LocalDate.now());
        seansaVremePocetkaField.clear();
        seansaTrajanjeField.clear();
        besplatnaSeansaCheckBox.setSelected(false);

        // Reset client selection
        klijentComboBox.getSelectionModel().clearSelection();
        selectExistingClientRadio.setSelected(true); // Default back to existing client
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Wrapper class to display Klijent's full name in ComboBox
    private static class KlijentStringWrapper {
        private Klijent klijent;

        public KlijentStringWrapper(Klijent klijent) {
            this.klijent = klijent;
        }

        public Klijent getKlijent() {
            return klijent;
        }

        @Override
        public String toString() {
            return klijent.getIme() + " " + klijent.getPrezime() + " (ID: " + klijent.getKlijentId() + ")";
        }
    }
} 