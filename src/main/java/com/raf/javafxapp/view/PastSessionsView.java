package com.raf.javafxapp.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Seansa;
import model.Klijent;
import repository.SeansaRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PastSessionsView extends VBox {
    private TableView<Seansa> table;
    private Stage stage;
    private Scene previousScene;
    private SeansaRepository seansaRepository;
    private int therapistId;
    private DatePicker dateFilter;
    private ObservableList<Seansa> allPastSessions;

    public PastSessionsView(Stage stage, Scene previousScene, int therapistId) {
        this.stage = stage;
        this.previousScene = previousScene;
        this.therapistId = therapistId;
        this.seansaRepository = new SeansaRepository();

        setSpacing(15);
        setPadding(new Insets(20));

        Label titleLabel = new Label("Prošle seanse");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Filter po datumu
        HBox filterBox = new HBox(10);
        dateFilter = new DatePicker();
        dateFilter.setPromptText("Filter po datumu");
        dateFilter.setOnAction(e -> filterSessionsByDate());
        filterBox.getChildren().addAll(new Label("Datum:"), dateFilter);

        table = new TableView<>();

        TableColumn<Seansa, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getSeansaId()).asObject());

        TableColumn<Seansa, String> clientCol = new TableColumn<>("Klijent");
        clientCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().getKlijent() != null) {
                return new SimpleStringProperty(
                        cellData.getValue().getKlijent().getIme() + " " + cellData.getValue().getKlijent().getPrezime());
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        TableColumn<Seansa, String> dateCol = new TableColumn<>("Datum");
        dateCol.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            return new SimpleStringProperty(sdf.format(cellData.getValue().getDatum()));
        });

        TableColumn<Seansa, String> startTimeCol = new TableColumn<>("Vreme početka");
        startTimeCol.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return new SimpleStringProperty(sdf.format(cellData.getValue().getVremePocetka()));
        });

        TableColumn<Seansa, Integer> durationCol = new TableColumn<>("Trajanje (min)");
        durationCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getTrajanje()).asObject());

        TableColumn<Seansa, String> freeSessionCol = new TableColumn<>("Besplatna seansa");
        freeSessionCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isBesplatnaSeansa() ? "Da" : "Ne"));

        table.getColumns().addAll(idCol, clientCol, dateCol, startTimeCol, durationCol, freeSessionCol);

        List<Seansa> pastSessions = getPastSessionsWithClientInfo(therapistId);
        allPastSessions = FXCollections.observableArrayList(pastSessions);
        table.setItems(allPastSessions);

        Button backButton = new Button("Nazad");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        getChildren().addAll(titleLabel, filterBox, table, backButton);
    }

    private List<Seansa> getPastSessionsWithClientInfo(int therapistId) {
        List<Seansa> pastSessions = seansaRepository.getPastSessions(therapistId);
        //Map<Integer, Klijent> klijentMap = new HashMap<>(); // Not strictly needed if getDetailedSessionById is efficient

        for (Seansa seansa : pastSessions) {
            Seansa detailedSeansa = seansaRepository.getDetailedSessionById(seansa.getSeansaId());
            if (detailedSeansa != null) {
                seansa.setKlijent(detailedSeansa.getKlijent());
            }
        }
        return pastSessions;
    }


    private void filterSessionsByDate() {
        LocalDate selectedDate = dateFilter.getValue();
        if (selectedDate == null) {
            table.setItems(allPastSessions);
            return;
        }

        ObservableList<Seansa> filteredSessions = FXCollections.observableArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Seansa seansa : allPastSessions) {
            if (seansa.getDatum() != null) {
                LocalDate sessionDate = LocalDate.parse(sdf.format(seansa.getDatum()));
                if (sessionDate.equals(selectedDate)) {
                    filteredSessions.add(seansa);
                }
            }
        }
        table.setItems(filteredSessions);
    }
} 