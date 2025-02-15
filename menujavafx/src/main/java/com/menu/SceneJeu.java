package com.menu;

//import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SceneJeu extends BorderPane {

    private Stage primaryStage;
    private GrilleController leftBox; 
    private StackPane centerPane; 
    private HBox boxes; 

    public SceneJeu(Stage stage){
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

    private VBox createLabelOnly(String labelText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
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

        VBox timeGroup = createLabelOnly("TEMPS : 00:00:00");
        VBox bestScoreGroup = createLabelOnly("MEILLEUR TEMPS : 00:30:00");

        topBar.getChildren().addAll(
            restartButton, helpButton, validateButton,
            timeGroup, bestScoreGroup,
            leftArrow, rightArrow, settingButton
        );

        this.setTop(topBar);

        centerPane = new StackPane(); 
        centerPane.setPadding(new Insets(20));

        leftBox = new GrilleController(new Grille("grilleTest.json"), 300, 0.2); // Stocke la grille dans une variable d'instance
        leftBox.setPrefSize(300, 300);
        leftBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");

        VBox rightBox = new VBox();
        rightBox.setPrefSize(150, 300);
        rightBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");

        boxes = new HBox(50, leftBox, rightBox); 
        boxes.setAlignment(Pos.CENTER);

        centerPane.getChildren().add(boxes);
        this.setCenter(centerPane);

        validateButton.setOnAction(e -> {
            System.out.println("Validation de la grille");
            boolean resultat = leftBox.getGrille().check();
            if (resultat) {
                System.out.println("Grille correcte");
            } else {
                System.out.println("Grille incorrecte");
            }
        });

        restartButton.setOnAction(e -> resetGrille()); 
    }

  
    private void resetGrille() {
        System.out.println("Restart de la grille");

        
        centerPane.getChildren().remove(boxes);

        // Creation nouvelle grille
        leftBox = new GrilleController(new Grille("grilleTest.json"), 300, 0.2);
        leftBox.setPrefSize(300, 300);
        leftBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");

        VBox rightBox = new VBox();
        rightBox.setPrefSize(150, 300);
        rightBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");

        boxes = new HBox(50, leftBox, rightBox); 
        boxes.setAlignment(Pos.CENTER);

        centerPane.getChildren().add(boxes);
    }
}
