package com.raf.javafxapp.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Candidate;
import model.Therapist;
import repository.CandidateRepository;
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
        
        // ============ CANDIDATES SECTION ============
        
        Label candidatesTitle = new Label("Kandidati u procesu obuke");
        candidatesTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10 0 5 0;");
        
        TableView<Candidate> candidatesTable = new TableView<>();
        
        // ID Column
        TableColumn<Candidate, Integer> candidateIdCol = new TableColumn<>("ID");
        candidateIdCol.setCellValueFactory(new PropertyValueFactory<>("kandidatId"));
        
        // Personal info columns
        TableColumn<Candidate, String> candidateImeCol = new TableColumn<>("Ime");
        candidateImeCol.setCellValueFactory(new PropertyValueFactory<>("ime"));

        TableColumn<Candidate, String> candidatePrezimeCol = new TableColumn<>("Prezime");
        candidatePrezimeCol.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        
        TableColumn<Candidate, String> candidateEmailCol = new TableColumn<>("Email");
        candidateEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Candidate, String> candidateTelefonCol = new TableColumn<>("Telefon");
        candidateTelefonCol.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        
        TableColumn<Candidate, String> candidatePrebivalisteCol = new TableColumn<>("Prebivalište");
        candidatePrebivalisteCol.setCellValueFactory(new PropertyValueFactory<>("prebivaliste"));
        
        // Birth date column with formatter
        TableColumn<Candidate, String> candidateDatumRodjCol = new TableColumn<>("Datum rođenja");
        candidateDatumRodjCol.setCellValueFactory(cellData -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return new javafx.beans.property.SimpleStringProperty(
                dateFormat.format(cellData.getValue().getDatumRodj())
            );
        });
        
        // Is Psychologist column
        TableColumn<Candidate, String> candidatePsihologCol = new TableColumn<>("Psiholog");
        candidatePsihologCol.setCellValueFactory(cellData -> {
            boolean isPsiholog = cellData.getValue().isPsiholog();
            return new javafx.beans.property.SimpleStringProperty(isPsiholog ? "Da" : "Ne");
        });
        
        // Faculty column
        TableColumn<Candidate, String> candidateFakultetCol = new TableColumn<>("Fakultet");
        candidateFakultetCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFakultet().getIme()
            )
        );
        
        // Education level column
        TableColumn<Candidate, String> candidateStepenStudijaCol = new TableColumn<>("Stepen studija");
        candidateStepenStudijaCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getStepenStudija().getNaziv()
            )
        );
        
        // Training center column
        TableColumn<Candidate, String> candidateCentarCol = new TableColumn<>("Centar za obuku");
        candidateCentarCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCentar().getNaziv()
            )
        );

        candidatesTable.getColumns().addAll(
            candidateIdCol, candidateImeCol, candidatePrezimeCol, 
            candidateEmailCol, candidateTelefonCol, candidatePrebivalisteCol, 
            candidateDatumRodjCol, candidatePsihologCol, candidateFakultetCol, 
            candidateStepenStudijaCol, candidateCentarCol
        );

        // Enable column reordering
        candidatesTable.setTableMenuButtonVisible(true);
        
        // Get candidates from repository
        CandidateRepository candidateRepository = new CandidateRepository();
        ObservableList<Candidate> candidates = FXCollections.observableArrayList(
            candidateRepository.getAllCandidates()
        );
        candidatesTable.setItems(candidates);

        Button backButton = new Button("Nazad");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        setSpacing(10);
        setPadding(new Insets(10));
        getChildren().addAll(title, table, candidatesTitle, candidatesTable, backButton);
    }
}
