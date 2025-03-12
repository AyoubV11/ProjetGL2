package com.menu;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;

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
    private Label bestTimeLabel; // 💡 Ajout du label meilleur temps

    public SceneJeu(Stage stage) {
        this.primaryStage = stage;
        setupInterface();
    }

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

        timeLabel = new Label("TEMPS : 00:00:00"); 
        chrono = new Chrono(timeLabel);
        chrono.start(); 

        VBox timeGroup = new VBox(timeLabel);
        timeGroup.setAlignment(Pos.CENTER);

        // Création du label meilleur temps
        VBox bestScoreGroup = new VBox();
        bestScoreGroup.setAlignment(Pos.CENTER);
        if (meilleurTempsExiste("../menujavafx/src/main/resources/sauvegarde/temps.json")) {
            String meilleurTemps = chargerMeilleurTemps("../menujavafx/src/main/resources/sauvegarde/temps.json");
            bestTimeLabel = new Label("MEILLEUR TEMPS : " + meilleurTemps);
            bestTimeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            bestScoreGroup.getChildren().add(bestTimeLabel);
        }

        topBar.getChildren().addAll(
            restartButton, helpButton, validateButton,
            timeGroup,
            leftArrow, rightArrow, settingButton
        );

        if (!bestScoreGroup.getChildren().isEmpty()) {
            topBar.getChildren().add(bestScoreGroup);
        }

        this.setTop(topBar);

        centerPane = new StackPane(); 
        centerPane.setPadding(new Insets(20));

        leftBox = new GrilleController(new Grille("grilleTest.json"), 400, 0.2); 
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

                // 🔄 Mettre à jour le label du meilleur temps
                if (bestTimeLabel != null) {
                    String nouveauMeilleurTemps = chargerMeilleurTemps("../menujavafx/src/main/resources/sauvegarde/temps.json");
                    bestTimeLabel.setText("MEILLEUR TEMPS : " + nouveauMeilleurTemps);
                }
            } else {
                System.out.println("-Grille non résolue");
            }

            int erreurs = leftBox.getGrille().check();
            System.out.println("--" + erreurs + " aretes incorrectes");
            leftBox.getGrille().retablirEtatValide();
            leftBox.update();
        });

        settingButton.setOnAction(e -> openSettings()); 
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

    public static boolean meilleurTempsExiste(String cheminFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            Gson gson = new Gson();
            TempsSauvegarde t = gson.fromJson(reader, TempsSauvegarde.class);
            return t != null && t.getTemps() != null && !t.getTemps().isEmpty();
        } catch (IOException e) {
            return false;
        }
    }

    public static String chargerMeilleurTemps(String cheminFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            Gson gson = new Gson();
            TempsSauvegarde t = gson.fromJson(reader, TempsSauvegarde.class);
            return t != null && t.getTemps() != null ? t.getTemps() : "00:00:00";
        } catch (IOException e) {
            return "00:00:00";
        }
    }
}
