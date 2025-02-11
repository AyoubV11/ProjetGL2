package com.menu;

//import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class SceneJeu extends BorderPane {

    private Stage primaryStage;

    public SceneJeu(Stage stage){
        this.primaryStage = stage;
        setupInterface();
    }




    // Méthode pour créer des boutons ronds
private Button createRoundButton(String text) {
    Button button = new Button(text);
    button.setStyle(
        "-fx-background-color: #ffffff; " +    // Couleur de fond blanche
        "-fx-background-radius: 50%; " +       // Coins arrondis pour former un cercle
        "-fx-border-radius: 50%; " +           // Bordure arrondie
        "-fx-border-color: black; " +          // Bordure noire
        "-fx-border-width: 2px; " +            // Épaisseur de la bordure
        "-fx-font-size: 18px; " +              // Taille de la police
        "-fx-pref-width: 40px; " +             // Largeur fixe pour un bouton circulaire
        "-fx-pref-height: 40px;"           
    );
    return button;
}



private VBox createLabelOnly(String labelText) {
    Label label = new Label(labelText);
    label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

    VBox vbox = new VBox(label);
    vbox.setAlignment(Pos.CENTER);  // Centre le texte dans le VBox
    return vbox;
}






    //public void start(Stage primaryStage){
    private void setupInterface(){

        HBox topBar=new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER);

        Button restartButton = createRoundButton("↻");
        Button helpButton = createRoundButton("?");
        Button validateButton = createRoundButton("✔");
        Button lightButton = createRoundButton("💡");
        Button leftArrow = createRoundButton("←");
        Button rightArrow = createRoundButton("→");
        Button settingButton = createRoundButton("⚙");
    

        //Label timeLabel = new Label("TEMPS : 00:00:00");
        //Label bestScorLabel= new Label("MEILLEUR SCORE : 00:30:00");
        
        VBox timeGroup = createLabelOnly("TEMPS : 00:00:00");
        VBox bestScoreGroup = createLabelOnly("MEILLEUR TEMPS : 00:30:00");

        topBar.getChildren().addAll(
            restartButton, helpButton, validateButton, lightButton,
            timeGroup, bestScoreGroup,
            leftArrow, rightArrow, settingButton
        );
   


        this.setTop(topBar);
        //topBar.getChildren().addAll(restartButton, helpButton, validateButton, lightButton, timeLabel, bestScorLabel, leftArrow, rightArrow, settingButton);

        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(20));

        //Label num1=new Label("1");
        //num1.setFont(new Font("Arial",100));
        //num1.setTextFill(Color.RED);
        //num1.setTranslateX(150);
        //num1.setTranslateY(-50);

        //Label num2 = new Label("2");
        //num2.setFont(new Font("Arial",100));
        //num2.setTextFill(Color.LIGHTBLUE);
        //num2.setTranslateX(0);
        //num2.setTranslateY(20);


        //Label num3 = new Label("3");
        //num3.setFont(new Font("Arial",100));
        //num3.setTextFill(Color.GREEN);
        //num3.setTranslateX(-150);
        //num3.setTranslateY(-50);


        //aJOUTER DES CADRES TRANSPARENTS

        GridPane leftBox = new GridPane();
        leftBox.setPrefSize(300, 300);
        leftBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");


        VBox rightBox = new VBox();
        rightBox.setPrefSize(150, 300);
        rightBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");


        HBox boxes = new HBox(50,leftBox, rightBox);
        boxes.setAlignment(Pos.CENTER);


        centerPane.getChildren().addAll(boxes);



        //this.setTop(topBar);
        this.setCenter(centerPane);


         
       /*  BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(centerPane);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        */
        


    


    }
    
}
