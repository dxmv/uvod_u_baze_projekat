package com.raf.javafxapp.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Klijent;
import repository.KlijentRepository;

public class PregledPrijavaView extends VBox {

    private TableView<Klijent> table;
    private Stage stage;
    private Scene previousScene;
    private KlijentRepository klijentRepository;
    private TextField searchField;
    private DatePicker dateFilter;
    private ObservableList<Klijent> allKlijenti;

    public PregledPrijavaView(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.previousScene = previousScene;
        this.klijentRepository = new KlijentRepository();

        setSpacing(15);
        setPadding(new Insets(20));

        Label titleLabel = new Label("Pregled prijava novih klijenata");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // HBox za filtere i pretragu
        HBox filterBox = new HBox(10);
        searchField = new TextField();
        searchField.setPromptText("Pretraga po imenu/prezimenu/opisu");

        dateFilter = new DatePicker();
        dateFilter.setPromptText("Filtriraj po datumu prijave");

        filterBox.getChildren().addAll(searchField, dateFilter);

        table = new TableView<>();
        TableColumn<Klijent, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("klijentId"));

        TableColumn<Klijent, String> imeCol = new TableColumn<>("Ime");
        imeCol.setCellValueFactory(new PropertyValueFactory<>("ime"));

        TableColumn<Klijent, String> prezimeCol = new TableColumn<>("Prezime");
        prezimeCol.setCellValueFactory(new PropertyValueFactory<>("prezime"));

        TableColumn<Klijent, String> datumCol = new TableColumn<>("Datum prijave");
        datumCol.setCellValueFactory(new PropertyValueFactory<>("datumPrijave"));

        TableColumn<Klijent, String> opisCol = new TableColumn<>("Opis problema");
        opisCol.setCellValueFactory(new PropertyValueFactory<>("opisProblema"));

        table.getColumns().addAll(idCol, imeCol, prezimeCol, datumCol, opisCol);

        allKlijenti = FXCollections.observableArrayList(klijentRepository.getAllPrijave());
        table.setItems(allKlijenti);

        Button backButton = new Button("Nazad");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        getChildren().addAll(titleLabel, filterBox, table, backButton);

        // Listener za pretragu
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterTable());

        // Listener za filter po datumu
        dateFilter.valueProperty().addListener((obs, oldVal, newVal) -> filterTable());
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();
        java.time.LocalDate selectedDate = dateFilter.getValue();

        ObservableList<Klijent> filteredList = FXCollections.observableArrayList();

        for (Klijent klijent : allKlijenti) {
            boolean searchMatch = searchText.isEmpty() ||
                    klijent.getIme().toLowerCase().contains(searchText) ||
                    klijent.getPrezime().toLowerCase().contains(searchText) ||
                    (klijent.getOpisProblema() != null && klijent.getOpisProblema().toLowerCase().contains(searchText));

            boolean dateMatch = selectedDate == null ||
                    (klijent.getDatumPrijave() != null &&
                            new java.sql.Date(klijent.getDatumPrijave().getTime()).toLocalDate().equals(selectedDate));

            if (searchMatch && dateMatch) {
                filteredList.add(klijent);
            }
        }
        table.setItems(filteredList);
    }
}