package com.raf.javafxapp.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Therapist;
import repository.TherapistRepository;

import java.text.SimpleDateFormat;

public class AllTherapistsView extends VBox {

    public AllTherapistsView(Stage stage, Scene previousScene) {
        Label title = new Label("Svi psihoterapeuti savetovališta");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Therapist> table = new TableView<>();
        
        // ID Column
        TableColumn<Therapist, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("kandidatId"));
        
        // Personal info columns
        TableColumn<Therapist, String> imeCol = new TableColumn<>("Ime");
        imeCol.setCellValueFactory(new PropertyValueFactory<>("ime"));

        TableColumn<Therapist, String> prezimeCol = new TableColumn<>("Prezime");
        prezimeCol.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        
        TableColumn<Therapist, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Therapist, String> telefonCol = new TableColumn<>("Telefon");
        telefonCol.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        
        TableColumn<Therapist, String> prebivalisteCol = new TableColumn<>("Prebivalište");
        prebivalisteCol.setCellValueFactory(new PropertyValueFactory<>("prebivaliste"));
        
        // Birth date column with formatter
        TableColumn<Therapist, String> datumRodjCol = new TableColumn<>("Datum rođenja");
        datumRodjCol.setCellValueFactory(cellData -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return new javafx.beans.property.SimpleStringProperty(
                dateFormat.format(cellData.getValue().getDatumRodj())
            );
        });
        
        // Is Psychologist column
        TableColumn<Therapist, String> psihologCol = new TableColumn<>("Psiholog");
        psihologCol.setCellValueFactory(cellData -> {
            boolean isPsiholog = cellData.getValue().isPsiholog();
            return new javafx.beans.property.SimpleStringProperty(isPsiholog ? "Da" : "Ne");
        });
        
        // Faculty column
        TableColumn<Therapist, String> fakultetCol = new TableColumn<>("Fakultet");
        fakultetCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFakultet().getIme()
            )
        );
        
        // Education level column
        TableColumn<Therapist, String> stepenStudijaCol = new TableColumn<>("Stepen studija");
        stepenStudijaCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getStepenStudija().getNaziv()
            )
        );
        
        // Center column
        TableColumn<Therapist, String> centarCol = new TableColumn<>("Centar za obuku");
        centarCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCentar().getNaziv()
            )
        );
        
        // Certificate date column
        TableColumn<Therapist, String> datumSertCol = new TableColumn<>("Datum sertifikata");
        datumSertCol.setCellValueFactory(cellData -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return new javafx.beans.property.SimpleStringProperty(
                dateFormat.format(cellData.getValue().getSertifikat().getDatumSert())
            );
        });
        
        // Therapy area column
        TableColumn<Therapist, String> oblastCol = new TableColumn<>("Oblast terapije");
        oblastCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getSertifikat().getOblast().getIme()
            )
        );

        table.getColumns().addAll(
            idCol, imeCol, prezimeCol, emailCol, telefonCol, prebivalisteCol, 
            datumRodjCol, psihologCol, fakultetCol, stepenStudijaCol,
            centarCol, datumSertCol, oblastCol
        );

        // Enable column reordering
        table.setTableMenuButtonVisible(true);
        
        // Get therapists from repository
        TherapistRepository therapistRepository = new TherapistRepository();
        ObservableList<Therapist> therapists = FXCollections.observableArrayList(
            therapistRepository.getAllTherapists()
        );
        table.setItems(therapists);

        Button backButton = new Button("Nazad");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        setSpacing(10);
        setPadding(new Insets(10));
        getChildren().addAll(title, table, backButton);
    }
}
