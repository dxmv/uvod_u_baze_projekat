package com.raf.javafxapp.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainFrame extends BorderPane {
    Stage stage;

    public MainFrame(Stage stage) {
        this.stage=stage;
        initializeGUI();
    }

    private void initializeGUI() {
        // Top: meni bar
        MenuBar menuBar = createMenuBar();
        this.setTop(menuBar);

        // Left: navigacija
        VBox navigationPanel = createNavigationPanel();
        this.setLeft(navigationPanel);

        // Center: početni prikaz
        this.setCenter(new Text("Dobrodošli u savetovalište 'Novi početak'"));
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu menuFajl = new Menu("Fajl");
        MenuItem itemIzlaz = new MenuItem("Izlaz");
        itemIzlaz.setOnAction(e -> System.exit(0));
        menuFajl.getItems().add(itemIzlaz);

        Menu menuPomoc = new Menu("Pomoć");
        MenuItem itemOApp = new MenuItem("O aplikaciji");
        itemOApp.setOnAction(e -> showAbout());
        menuPomoc.getItems().add(itemOApp);

        menuBar.getMenus().addAll(menuFajl, menuPomoc);
        return menuBar;
    }

    private VBox createNavigationPanel() {
        VBox nav = new VBox(10);
        nav.setPadding(new Insets(10));
        nav.setStyle("-fx-background-color: #f0f0f0;");

        Button btnTerapeuti = new Button("Terapeuti");
        Button btnKlijenti = new Button("Klijenti");
        Button btnSeanse = new Button("Seanse");
        Button btnUplate = new Button("Uplate");


        // Placeholder akcije
        btnTerapeuti.setOnAction(e -> {
            Scene currentScene = stage.getScene(); // uzmi trenutnu scenu
            AllTherapistsView view = new AllTherapistsView(null,stage, currentScene); // prosledi kao previousScene
            Scene newScene = new Scene(view, 800, 400); // veće dimenzije da tabela stane lepo
            stage.setScene(newScene);
        });
        btnKlijenti.setOnAction(e -> this.setCenter(new Label("Prikaz klijenata")));
        btnSeanse.setOnAction(e -> this.setCenter(new Label("Prikaz seansi")));
        btnUplate.setOnAction(e -> this.setCenter(new Label("Prikaz uplata")));

        nav.getChildren().addAll(btnTerapeuti, btnKlijenti, btnSeanse, btnUplate);
        return nav;
    }

    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O aplikaciji");
        alert.setHeaderText("Savetovalište 'Novi početak'");
        alert.setContentText("Aplikacija za upravljanje radom savetovališta.");
        alert.showAndWait();
    }
}
