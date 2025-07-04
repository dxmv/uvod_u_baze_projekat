package com.raf.javafxapp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Parent;
import com.raf.javafxapp.SessionManager;
import com.raf.javafxapp.view.SeansaView;

public class MainFrame extends VBox {
    private Stage stage;

    public MainFrame(Stage stage) {
        this.stage = stage;
        
        // Configure VBox
        setSpacing(15);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
        
        // Create welcome text
        Text welcomeText = new Text("Dobrodošli u savetovalište 'Novi početak'");
        welcomeText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        
        // Create description text
        Text descriptionText = new Text(
            "Aplikacija za upravljanje terapeutima, klijentima i sesijama savetovališta"
        );
        descriptionText.setStyle("-fx-font-size: 14px;");
        descriptionText.setTextAlignment(TextAlignment.CENTER);
        
        // Create separator
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        
        // Check if user is logged in
        boolean isLoggedIn = SessionManager.getInstance().isLoggedIn();
        
        // Create buttons with consistent styling
        getChildren().addAll(
            welcomeText,
            descriptionText,
            separator
        );
        
        // Show different buttons based on login status
        if (isLoggedIn) {
            // User is logged in, show Profile button
            Button btnProfile = createStyledButton("Profil");
            Button btnLogout = createStyledButton("Odjava");
            Button btnTherapists = createStyledButton("Psihoterapeuti");
            Button btnSeansa = createStyledButton("Pregled seanse");
            Button btnPregledPrijava = createStyledButton("Novi klijenti");
            Button btnFutureSessions = createStyledButton("Buduce seanse");
            Button btnPastSessions = createStyledButton("Prosle seanse");
            Button btnExit = createStyledButton("Izlaz");
            Button btnPayments=createStyledButton("Pregled placanja");
            Button btnScheduleSeansa = createStyledButton("Zakaži novu seansu");

            btnPayments.setOnAction(e->openPaymentView());
            btnProfile.setOnAction(e -> openTherapistProfileView());
            btnLogout.setOnAction(e -> handleLogout());
            btnTherapists.setOnAction(e -> openTherapistsView());
            btnSeansa.setOnAction(e -> openSeansaView());
            btnFutureSessions.setOnAction(e->openNoveSeanseView());
            btnPastSessions.setOnAction(e -> openPastSessionsView());
            btnPregledPrijava.setOnAction(e->openPregledPrijavaView());
            btnScheduleSeansa.setOnAction(e -> openScheduleSeansaView());
            btnExit.setOnAction(e -> System.exit(0));
            
            getChildren().addAll(
                btnProfile,
                btnLogout,
                btnTherapists,
                btnSeansa,
                btnFutureSessions,
                btnPastSessions,
                btnPregledPrijava,
                btnScheduleSeansa,
                btnPayments,
                btnExit
            );
        } else {
            // User is not logged in, show Login and SignUp buttons
            Button btnLogin = createStyledButton("Prijava");
            Button btnSignUp = createStyledButton("Registracija");
            Button btnTherapists = createStyledButton("Psihoterapeuti");
            Button btnExit = createStyledButton("Izlaz");
            
            btnLogin.setOnAction(e -> openLoginView());
            btnSignUp.setOnAction(e -> openSignUpView());
            btnTherapists.setOnAction(e -> openTherapistsView());
            btnExit.setOnAction(e -> System.exit(0));
            
            getChildren().addAll(
                btnLogin,
                btnSignUp,
                btnTherapists,
                btnExit
            );
        }
    }
    
    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(200);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-font-size: 14px;");
        return btn;
    }
    
    private void openLoginView() {
        Scene currentScene = stage.getScene();
        LoginView loginView = new LoginView(stage, currentScene);
        Scene newScene = new Scene((Parent)loginView, 500, 300);
        stage.setScene(newScene);
    }
    
    private void openSignUpView() {
        Scene currentScene = stage.getScene();
        SignUp signUpView = new SignUp(stage, currentScene);
        
        // Using ScrollPane for the large form
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(signUpView);
        scrollPane.setFitToWidth(true);
        
        Scene newScene = new Scene((Parent)scrollPane, 500, 600);
        stage.setScene(newScene);
    }

    private void openTherapistsView() {
        Scene currentScene = stage.getScene();
        AllTherapistsView view = new AllTherapistsView(stage, currentScene);
        Scene newScene = new Scene((Parent)view, 800, 400);
        stage.setScene(newScene);
    }
    private  void openPaymentView(){
        PaymentsAndDebtsView view = new PaymentsAndDebtsView(stage, stage.getScene());
        stage.setScene(new Scene(view, 900, 600));
    }
    private void openTherapistProfileView() {
        Scene currentScene = stage.getScene();
        TherapistProfileView view = new TherapistProfileView(stage, currentScene);
        Scene newScene = new Scene((Parent)view, 500, 500);
        stage.setScene(newScene);
    }
    private void openNoveSeanseView() {
        int therapistId = SessionManager.getInstance().getLoggedInKandidatId();
        FutureSessionsView view = new FutureSessionsView(stage, stage.getScene(), therapistId);
        stage.setScene(new Scene(view, 900, 600));
    }
    private void openPastSessionsView() {
        int therapistId = SessionManager.getInstance().getLoggedInKandidatId();
        PastSessionsView view = new PastSessionsView(stage, stage.getScene(), therapistId);
        stage.setScene(new Scene(view, 900, 600));
    }
    private void openSeansaView() {
        Scene currentScene = stage.getScene();
        SeansaView view = new SeansaView(stage, currentScene);
        Scene newScene = new Scene((Parent)view, 800, 1000);
        stage.setScene(newScene);
    }
    private void openPregledPrijavaView() {
        Scene currentScene = stage.getScene();
        PregledPrijavaView view = new PregledPrijavaView(stage, currentScene);
        Scene newScene = new Scene(view, 900, 600);
        stage.setScene(newScene);
    }
    
    private void openScheduleSeansaView() {
        Scene currentScene = stage.getScene();
        ScheduleSeansaView view = new ScheduleSeansaView(stage, currentScene);
        Scene newScene = new Scene(view, 600, 750); 
        stage.setScene(newScene);
    }
    
    private void handleLogout() {
        // Clear the session
        SessionManager.getInstance().clearSession();
        
        // Refresh the main frame to show login/signup buttons
        Scene currentScene = stage.getScene();
        MainFrame refreshedView = new MainFrame(stage);
        Scene newScene = new Scene((Parent)refreshedView, currentScene.getWidth(), currentScene.getHeight());
        stage.setScene(newScene);
    }
}
