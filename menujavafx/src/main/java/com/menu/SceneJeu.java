package com.menu;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

//import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SceneJeu extends BorderPane {

    private Stage primaryStage;
    private Stage param;
    private GrilleController leftBox; 
    private StackPane centerPane; 
    private HBox boxes; 
    private Chrono chrono;
    private Label timeLabel;
    private Parametres parametres;

    public SceneJeu(Stage stage) {
        this.primaryStage = stage;
        setupInterface();
    }

    // Méthode pour créer des boutons ronds
    private Button createRoundButton(String text) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: #ffffff; " +    
            "-fx-background-radius: 50%; " +      
            "-fx-border-radius: 50%; " +           
            "-fx-border-color: black; " +         
            "-fx-border-width: 2px; " +            
            "-fx-font-size: 18px; " +             
            "-fx-pref-width: 40px; " +      
            "-fx-pref-height: 40px;"
        );
        return button;
    }

    private void setupInterface() {
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER);

        Button restartButton = createRoundButton("↻");
        Button helpButton = createRoundButton("?");
        Button validateButton = createRoundButton("✔");

        Button leftArrow = createRoundButton("←");
        
        Button rightArrow = createRoundButton("→");
        Button settingButton = createRoundButton("⚙");

        // Correction : Création unique de timeLabel
        timeLabel = new Label("TEMPS : 00:00:00"); 
        chrono = new Chrono(timeLabel); // Instancier le chrono avec le label
        chrono.start(); // Lancer le chrono dès le début

        // Correction : Utilisation directe de timeLabel dans timeGroup
        VBox timeGroup = new VBox(timeLabel);
        timeGroup.setAlignment(Pos.CENTER);
        
        VBox bestScoreGroup = createLabelOnly("MEILLEUR TEMPS : 00:30:00");

        topBar.getChildren().addAll(
            restartButton, helpButton, validateButton,
            timeGroup, bestScoreGroup,
            leftArrow, rightArrow, settingButton
        );

        this.setTop(topBar);

        centerPane = new StackPane(); 
        centerPane.setPadding(new Insets(20));

        leftBox = new GrilleController(new Grille("grilleTest.json"), 400, 0.2); // Stocke la grille dans une variable d'instance
        leftBox.setPrefSize(400, 400);
        leftBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");

        VBox rightBox = new VBox();
        rightBox.setPrefSize(150, 300);
        rightBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");

        boxes = new HBox(50, leftBox, rightBox); 
        boxes.setAlignment(Pos.CENTER);

        centerPane.getChildren().add(boxes);
        this.setCenter(centerPane);

        leftArrow.setOnAction(e -> {leftBox.getGrille().undo(); leftBox.update();});
        rightArrow.setOnAction(e -> {leftBox.getGrille().redo(); leftBox.update();});
        restartButton.setOnAction(e -> {leftBox.getGrille().clear(); leftBox.update();});
        

        validateButton.setOnAction(e -> {
            System.out.println("Validation de la grille");
            boolean resultat = leftBox.getGrille().resolue();
            if (resultat) {
                System.out.println("-Grille résolue");
                chrono.stop();
                String tempsFinal = chrono.getTemps(); 
                sauvegarderTemps(tempsFinal);
                chrono.start();
            } else {
                System.out.println("-Grille non résolue");
            }

            int erreurs = leftBox.getGrille().check();
            System.out.println("--" + erreurs + " aretes incorrectes");
            leftBox.getGrille().retablirEtatValide();
            leftBox.update();
        });


        settingButton.setOnAction(e -> {
            openSettings();
        }); 
    }

    private VBox createLabelOnly(String labelText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }


    private void openSettings() {
            Stage paramStage = new Stage();
            Parametres parametresScene = new Parametres(paramStage);
            Scene paramScene = new Scene(parametresScene, 400, 300);
            paramStage.setScene(paramScene);
            paramStage.show();
    }




    private void sauvegarderTemps(String temps) {
    TempsSauvegarde t = new TempsSauvegarde(temps);
    Gson gson = new Gson();

    try (FileWriter writer = new FileWriter("../menujavafx/src/main/resources/sauvegarde/temps.json")) {
        gson.toJson(t, writer);
        System.out.println("Temps sauvegardé !");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
