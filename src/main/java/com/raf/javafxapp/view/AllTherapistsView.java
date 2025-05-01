package com.raf.javafxapp.view;

import com.raf.javafxapp.Model.Psychotherapist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;

public class AllTherapistsView extends VBox {

    public AllTherapistsView(Connection conn, Stage stage, Scene previousScene) {
        Label title = new Label("Svi psihoterapeuti savetovališta");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Psychotherapist> table = new TableView<>();

        TableColumn<Psychotherapist, String> nameCol = new TableColumn<>("Ime i prezime");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<Psychotherapist, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Psychotherapist, String> phoneCol = new TableColumn<>("Telefon");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Psychotherapist, String> specCol = new TableColumn<>("Specijalizacija");
        specCol.setCellValueFactory(new PropertyValueFactory<>("specialization"));

        TableColumn<Psychotherapist, String> bioCol = new TableColumn<>("Biografija");
        bioCol.setCellValueFactory(new PropertyValueFactory<>("biography"));

        // Formatiranje za prelazak u novi red ako ima \n
        bioCol.setCellFactory(column -> new TableCell<>() {
            private final TextArea textArea = new TextArea();

            {
                textArea.setWrapText(true);
                textArea.setEditable(false);
                textArea.setPrefHeight(100);
                setGraphic(textArea);
            }

            @Override
            protected void updateItem(String bio, boolean empty) {
                super.updateItem(bio, empty);
                if (empty || bio == null) {
                    setText(null);
                    textArea.setText(null);
                } else {
                    textArea.setText(bio);
                }
            }
        });

        table.getColumns().addAll(nameCol, emailCol, phoneCol, specCol, bioCol);

        ObservableList<Psychotherapist> data = FXCollections.observableArrayList(
                new Psychotherapist(
                        "Dr Ana Petrović",
                        "ana.petrovic@example.com",
                        "0612345678",
                        "Kognitivno-bihejvioralna terapija",
                        "Dr Ana Petrović je licencirani psihoterapeut sa preko 10 godina iskustva u radu sa adolescentima.\nZavršila je edukaciju u oblasti KBT-a i učestvovala na brojnim stručnim seminarima."
                ),
                new Psychotherapist(
                        "Milan Jovanović",
                        "milan.jovanovic@example.com",
                        "0623456789",
                        "Porodična terapija",
                        "Milan Jovanović se bavi porodičnom terapijom i ima veliko iskustvo u radu sa bračnim parovima.\nAutor je više stručnih članaka na temu porodične dinamike."
                ),
                new Psychotherapist(
                        "Jelena Marković",
                        "jelena.markovic@example.com",
                        "0634567890",
                        "Psihodinamska terapija",
                        "Jelena Marković koristi psihodinamski pristup u radu sa klijentima koji pate od anksioznosti i depresije.\nČlan je Udruženja psihoterapeuta Srbije."
                )
        );

//        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM psihoterapeut");
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                String fullName = rs.getString("ime") + " " + rs.getString("prezime");
//                String email = rs.getString("email");
//                String phone = rs.getString("telefon");
//                String specialization = rs.getString("specijalizacija");
//                String biography = rs.getString("biografija");
//
//                // Ako želiš da biografija ide u novi red svaki put posle npr. 60 karaktera:
//                biography = wrapText(biography, 60);
//
//                Psychotherapist pt = new Psychotherapist(fullName, email, phone, specialization, biography);
//                data.add(pt);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        table.setItems(data);

        Button backButton = new Button("Nazad");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        setSpacing(10);
        setPadding(new Insets(10));
        getChildren().addAll(title, table, backButton);
    }

    private String wrapText(String text, int wrapLength) {
        StringBuilder sb = new StringBuilder(text);
        int i = wrapLength;
        while (i < sb.length()) {
            int spaceIndex = sb.lastIndexOf(" ", i);
            if (spaceIndex != -1) {
                sb.replace(spaceIndex, spaceIndex + 1, "\n");
                i = spaceIndex + wrapLength + 1;
            } else {
                break;
            }
        }
        return sb.toString();
    }
}
