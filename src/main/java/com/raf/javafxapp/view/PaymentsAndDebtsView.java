package com.raf.javafxapp.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Placanje;
import repository.PlacanjeRepository;

import java.text.SimpleDateFormat;
import java.util.List;
import javafx.scene.layout.HBox;

public class PaymentsAndDebtsView extends VBox {

    private TableView<Placanje> table;
    private Stage stage;
    private Scene previousScene;
    private PlacanjeRepository placanjeRepository;
    private TextField searchField;

    public PaymentsAndDebtsView(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.previousScene = previousScene;
        this.placanjeRepository = new PlacanjeRepository();

        setSpacing(15);
        setPadding(new Insets(20));

        Label titleLabel = new Label("Pregled uplata i dugovanja klijenata");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // HBox za pretragu
        HBox searchBox = new HBox(10);
        searchField = new TextField();
        searchField.setPromptText("Pretraga po klijentu");
        searchBox.getChildren().add(searchField);

        table = new TableView<>();

        TableColumn<Placanje, String> clientCol = new TableColumn<>("Klijent");
        clientCol.setCellValueFactory(cellData -> {
            Placanje placanje = cellData.getValue();
            if (placanje != null && placanje.getKlijent() != null) {
                return new SimpleStringProperty(
                        placanje.getKlijent().getIme() + " " + placanje.getKlijent().getPrezime()
                );
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        TableColumn<Placanje, Number> amountCol = new TableColumn<>("Iznos");
        amountCol.setCellValueFactory(cellData -> {
            Placanje placanje = cellData.getValue();
            if (placanje != null) {
                return new SimpleObjectProperty<>(placanje.getIznos());
            } else {
                return new SimpleObjectProperty<>(0); // Or handle null iznos appropriately
            }
        });

        TableColumn<Placanje, String> paymentDateCol = new TableColumn<>("Datum uplate");
        paymentDateCol.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Placanje placanje = cellData.getValue();
            if (placanje != null && placanje.getDatum() != null) {
                return new SimpleStringProperty(sdf.format(placanje.getDatum()));
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        TableColumn<Placanje, String> paymentMethodCol = new TableColumn<>("Način plaćanja");
        paymentMethodCol.setCellValueFactory(cellData -> {
            Placanje placanje = cellData.getValue();
            if (placanje != null) {
                return new SimpleStringProperty(placanje.getNacinPlacanja());
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        TableColumn<Placanje, String> sessionDateCol = new TableColumn<>("Datum seanse");
        sessionDateCol.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Placanje placanje = cellData.getValue();
            if (placanje != null && placanje.getSeansa() != null && placanje.getSeansa().getDatum() != null) {
                return new SimpleStringProperty(sdf.format(placanje.getSeansa().getDatum()));
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        TableColumn<Placanje, String> dueDateCol = new TableColumn<>("Rok za plaćanje");
        dueDateCol.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Placanje placanje = cellData.getValue();
            if (placanje != null && placanje.getDatum() != null) { // Using getDatum() as there's no getRokZaPlacanje()
                return new SimpleStringProperty(sdf.format(placanje.getDatum()));
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        TableColumn<Placanje, Long> daysOverdueCol = new TableColumn<>("Dani prekoračenja");
        daysOverdueCol.setCellValueFactory(cellData -> {
            // Assuming you calculate this in getAllPaymentsWithDelay()
            return new SimpleObjectProperty<>(0L); // Placeholder, replace with actual value if available
        });

        TableColumn<Placanje, String> debtStatusCol = new TableColumn<>("Status duga");
        debtStatusCol.setCellValueFactory(cellData -> {
            // Assuming you calculate this in getAllPaymentsWithDelay()
            return new SimpleStringProperty("N/A"); // Placeholder, replace with actual value if available
        });

        TableColumn<Placanje, String> currencyCol = new TableColumn<>("Valuta");
        currencyCol.setCellValueFactory(cellData -> {
            Placanje placanje = cellData.getValue();
            if (placanje != null && placanje.getValuta() != null) {
                return new SimpleStringProperty(placanje.getValuta().toString()); // Or placanje.getValuta().getNaziv() if Valuta has a getNaziv()
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        TableColumn<Placanje, Integer> rataCol = new TableColumn<>("Rata");
        rataCol.setCellValueFactory(cellData -> {
            Placanje placanje = cellData.getValue();
            if (placanje != null) {
                return new SimpleObjectProperty<>(placanje.getRata());
            } else {
                return new SimpleObjectProperty<>(0); // Or handle null rata appropriately
            }
        });

        table.getColumns().addAll(clientCol, amountCol, paymentDateCol, paymentMethodCol,
                sessionDateCol, dueDateCol, daysOverdueCol, debtStatusCol,
                currencyCol, rataCol);

        List<Placanje> payments = placanjeRepository.getAllPaymentsWithDelay();
        ObservableList<Placanje> data = FXCollections.observableArrayList(payments);
        table.setItems(data);

        Button backButton = new Button("Nazad");
        backButton.setOnAction(e->redirectToMainScreen());

        HBox buttonBox = new HBox(10, backButton);

        getChildren().addAll(titleLabel, searchBox, table, buttonBox);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterTable(newVal));
    }

    private void filterTable(String searchText) {
        searchText = searchText.toLowerCase();
        List<Placanje> allPayments = placanjeRepository.getAllPaymentsWithDelay();
        ObservableList<Placanje> filteredData = FXCollections.observableArrayList();

        for (Placanje placanje : allPayments) {
            boolean clientMatch = false;
            if (placanje != null && placanje.getKlijent() != null) {
                clientMatch = placanje.getKlijent().getIme().toLowerCase().contains(searchText) ||
                        placanje.getKlijent().getPrezime().toLowerCase().contains(searchText);
            }

            if (clientMatch) {
                filteredData.add(placanje);
            }
        }

        table.setItems(filteredData);
    }

    private void redirectToMainScreen() {
        MainFrame mainFrame = new MainFrame(stage);
        Scene mainScene = new Scene((Parent)mainFrame, 500, 500);
        stage.setScene(mainScene);
    }
}