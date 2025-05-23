package com.raf.javafxapp.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.ObjavaPodatakaRepository;
import repository.PrimalacRepository; // Assuming you have a PrimalacRepository

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import model.Primalac;


public class AddObjavaPodatakaView extends VBox {

    private Stage stage;
    private int seansaId;
    private ObjavaPodatakaRepository objavaPodatakaRepository;
    private PrimalacRepository primalacRepository; // For fetching Primalac options

    private DatePicker datumPicker;
    private ComboBox<String> primalacComboBox;
    private TextArea razlogTextArea;

    public AddObjavaPodatakaView(Stage stage, int seansaId) {
        this.stage = stage;
        this.seansaId = seansaId;
        this.objavaPodatakaRepository = new ObjavaPodatakaRepository();
        this.primalacRepository = new PrimalacRepository(); // Initialize PrimalacRepository

        setSpacing(10);
        setPadding(new Insets(20));

        Label titleLabel = new Label("Dodaj Novu Objavu Podataka");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Datum Objave
        Label datumLabel = new Label("Datum Objave:");
        datumPicker = new DatePicker(LocalDate.now());
        grid.add(datumLabel, 0, 0);
        grid.add(datumPicker, 1, 0);

        // Primalac
        Label primalacLabel = new Label("Primalac:");
        primalacComboBox = new ComboBox<>();
        loadPrimalacOptions(); // Load options for Primalac
        grid.add(primalacLabel, 0, 1);
        grid.add(primalacComboBox, 1, 1);
        
        // Razlog
        Label razlogLabel = new Label("Razlog:");
        razlogTextArea = new TextArea();
        razlogTextArea.setPromptText("Unesite razlog objave...");
        razlogTextArea.setPrefRowCount(5);
        grid.add(razlogLabel, 0, 2);
        grid.add(razlogTextArea, 1, 2);

        // Save Button
        Button saveButton = new Button("Sačuvaj");
        saveButton.setOnAction(e -> saveObjavaPodataka());

        // Cancel Button
        Button cancelButton = new Button("Otkaži");
        cancelButton.setOnAction(e -> ((Stage) getScene().getWindow()).close());

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        
        getChildren().addAll(titleLabel, grid, buttonBox);
    }

    private void loadPrimalacOptions() {
        // Fetch Primalac names from repository
        List<Primalac> primaoci = primalacRepository.getAllPrimaoci(); // You need to implement this method
        if (primaoci != null) {
            for (Primalac p : primaoci) {
                primalacComboBox.getItems().add(p.getNaziv());
            }
        }
        if (!primalacComboBox.getItems().isEmpty()) {
            primalacComboBox.setValue(primalacComboBox.getItems().get(0));
        }
    }

    private void saveObjavaPodataka() {
        LocalDate localDate = datumPicker.getValue();
        String primalacNaziv = primalacComboBox.getValue();
        String razlog = razlogTextArea.getText();

        if (localDate == null || primalacNaziv == null || primalacNaziv.trim().isEmpty() || razlog == null || razlog.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sva polja moraju biti popunjena!");
            alert.showAndWait();
            return;
        }

        Date datum = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        boolean success = objavaPodatakaRepository.save(seansaId, datum, primalacNaziv, razlog);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Objava podataka uspešno sačuvana!");
            alert.showAndWait();
            ((Stage) getScene().getWindow()).close(); // Close the window on success
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Greška prilikom čuvanja objave podataka.");
            alert.showAndWait();
        }
    }
} 