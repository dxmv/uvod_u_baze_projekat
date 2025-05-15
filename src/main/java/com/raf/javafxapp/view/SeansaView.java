package com.raf.javafxapp.view;

import com.raf.javafxapp.SessionManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.BeleskeSeanse;
import model.ObjavaPodataka;
import model.Seansa;
import model.SeansaTest;
import repository.SeansaRepository;

import java.text.SimpleDateFormat;
import java.util.List;

public class SeansaView extends VBox {
    
    private Stage stage;
    private ComboBox<Integer> seansaComboBox;
    private GridPane detailsPane;
    private SeansaRepository seansaRepository;
    
    public SeansaView(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.seansaRepository = new SeansaRepository();
        
        // Configure VBox
        setSpacing(15);
        setPadding(new Insets(20));
        
        // Create title label
        Label titleLabel = new Label("Pregled seansi");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Create seansa selection dropdown
        Label dropdownLabel = new Label("Izaberi seansu:");
        seansaComboBox = new ComboBox<>();
        loadSeansaIds();
        
        // Create button to print selected ID and show details
        Button showDetailsButton = new Button("Prikaži detalje");
        showDetailsButton.setOnAction(e -> {
            Integer selectedId = seansaComboBox.getValue();
            if (selectedId != null) {
                System.out.println("Izabrani ID seanse: " + selectedId);
                displaySeansaDetails(selectedId);
            } else {
                System.out.println("Nijedna seansa nije izabrana");
            }
        });
        
        // Create horizontal box for dropdown and button
        HBox selectionBox = new HBox(10);
        selectionBox.getChildren().addAll(dropdownLabel, seansaComboBox, showDetailsButton);
        
        // Create details pane
        detailsPane = new GridPane();
        detailsPane.setHgap(10);
        detailsPane.setVgap(10);
        detailsPane.setPadding(new Insets(10));
        
        // Create back button
        Button backButton = new Button("Nazad");
        backButton.setOnAction(e -> stage.setScene(previousScene));
        
        // Add components to the layout
        getChildren().addAll(titleLabel, selectionBox, detailsPane, backButton);
    }
    
    private void loadSeansaIds() {
        // For demonstration, we'll load all sessions from a common therapist ID
        // In a real application, you would get the therapist ID from the logged-in user
        SeansaRepository seansaRepository = new SeansaRepository();
        // Using therapist ID 1 for demonstration
        seansaComboBox.getItems().addAll(seansaRepository.getAllSessions(SessionManager.getInstance().getLoggedInKandidatId()));
        
        // Select first item if available
        if (!seansaComboBox.getItems().isEmpty()) {
            seansaComboBox.setValue(seansaComboBox.getItems().get(0));
        }
    }
    
    private void displaySeansaDetails(int seansaId) {
        // Clear previous details
        detailsPane.getChildren().clear();
        
        // Get detailed information about the seansa
        Seansa seansa = seansaRepository.getDetailedSessionById(seansaId);
        
        if (seansa == null) {
            Label errorLabel = new Label("Greška prilikom učitavanja detalja seanse");
            detailsPane.add(errorLabel, 0, 0);
            return;
        }
        
        // Format dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        // Add basic Seansa info
        int row = 0;
        
        detailsPane.add(new Label("Osnovne informacije:"), 0, row++);
        detailsPane.add(new Label("ID:"), 0, row);
        detailsPane.add(new Label(String.valueOf(seansa.getSeansaId())), 1, row++);
        
        detailsPane.add(new Label("Datum:"), 0, row);
        detailsPane.add(new Label(dateFormat.format(seansa.getDatum())), 1, row++);
        
        detailsPane.add(new Label("Vreme početka:"), 0, row);
        detailsPane.add(new Label(timeFormat.format(seansa.getVremePocetka())), 1, row++);
        
        detailsPane.add(new Label("Trajanje (min):"), 0, row);
        detailsPane.add(new Label(String.valueOf(seansa.getTrajanje())), 1, row++);
        
        detailsPane.add(new Label("Besplatna seansa:"), 0, row);
        detailsPane.add(new Label(seansa.isBesplatnaSeansa() ? "Da" : "Ne"), 1, row++);
        
        // Add cena info
        detailsPane.add(new Label("Cena po satu:"), 0, row);
        detailsPane.add(new Label(seansa.getCenaPoSatu().getCena().toString()), 1, row++);
        
        // Add Klijent info
        row++;
        detailsPane.add(new Label("Informacije o klijentu:"), 0, row++);
        
        detailsPane.add(new Label("Ime i prezime:"), 0, row);
        detailsPane.add(new Label(seansa.getKlijent().getIme() + " " + seansa.getKlijent().getPrezime()), 1, row++);
        
        detailsPane.add(new Label("Email:"), 0, row);
        detailsPane.add(new Label(seansa.getKlijent().getEmail()), 1, row++);
        
        detailsPane.add(new Label("Telefon:"), 0, row);
        detailsPane.add(new Label(seansa.getKlijent().getTelefon()), 1, row++);
        
        // Add BeleskeSeanse info
        row++;
        detailsPane.add(new Label("Beleške:"), 0, row++);
        
        List<BeleskeSeanse> beleske = seansa.getBeleske();
        if (beleske != null && !beleske.isEmpty()) {
            for (BeleskeSeanse beleska : beleske) {
                TextArea textArea = new TextArea(beleska.getText());
                textArea.setEditable(false);
                textArea.setPrefRowCount(3);
                textArea.setPrefColumnCount(30);
                detailsPane.add(textArea, 0, row++, 2, 1);
            }
        } else {
            detailsPane.add(new Label("Nema beleški"), 0, row++);
        }
        
        // Add SeansaTest info
        row++;
        detailsPane.add(new Label("Testovi:"), 0, row++);
        
        List<SeansaTest> testovi = seansa.getTestovi();
        if (testovi != null && !testovi.isEmpty()) {
            for (SeansaTest test : testovi) {
                detailsPane.add(new Label(test.getPsihoTest().getNaziv() + ":"), 0, row);
                detailsPane.add(new Label("Rezultat: " + test.getRezultat()), 1, row++);
            }
        } else {
            detailsPane.add(new Label("Nema testova"), 0, row++);
        }
        
        // Add ObjavaPodataka info
        row++;
        detailsPane.add(new Label("Objave podataka:"), 0, row++);
        
        List<ObjavaPodataka> objave = seansa.getObjave();
        if (objave != null && !objave.isEmpty()) {
            for (ObjavaPodataka objava : objave) {
                detailsPane.add(new Label("Datum objave: " + dateFormat.format(objava.getDatumObjave())), 0, row);
                detailsPane.add(new Label("Primalac: " + objava.getPrimalac().getNaziv()), 1, row++);
                detailsPane.add(new Label("Razlog: " + objava.getRazlog()), 0, row++, 2, 1);
            }
        } else {
            detailsPane.add(new Label("Nema objava podataka"), 0, row++);
        }
    }
} 