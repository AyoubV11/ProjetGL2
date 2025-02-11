package com.menu;




import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;

public class Parametres extends BorderPane{
    public Parametres(Stage stage){
        Text message1 = new Text("Volume :");
        message1.setFont(new Font(20));

        Slider volumeSlider = new Slider(0,100,50);
        volumeSlider.setShowTickLabels(false);
        volumeSlider.setShowTickMarks(false);
        volumeSlider.setPrefWidth(200);


        HBox volumeBox = new HBox(10, message1, volumeSlider);
        volumeBox.setAlignment(Pos.CENTER_LEFT);

        Text message2 = new Text("Croix Auto :");
        message2.setFont(new Font(20));


        Slider croixAutoSwitch = new Slider(0, 1, 0);
        croixAutoSwitch.setShowTickLabels(false);
        croixAutoSwitch.setShowTickMarks(false);
        croixAutoSwitch.setPrefWidth(50); 
        croixAutoSwitch.setSnapToTicks(true); 
        croixAutoSwitch.setMajorTickUnit(1); 

       


        HBox croixAutoBox = new HBox(10, message2, croixAutoSwitch);
        croixAutoBox.setAlignment(Pos.CENTER_LEFT);



        Text message3 = new Text("Afficher temps :");
        message3.setFont(new Font(20));


        Slider afficherTempsSwitch = new Slider(0, 1, 0);
        afficherTempsSwitch.setShowTickLabels(false);
        afficherTempsSwitch.setShowTickMarks(false);
        afficherTempsSwitch.setPrefWidth(50);
        afficherTempsSwitch.setSnapToTicks(true);
        afficherTempsSwitch.setMajorTickUnit(1);

        
        HBox afficherTempsBox = new HBox(10, message3, afficherTempsSwitch);
        afficherTempsBox.setAlignment(Pos.CENTER_LEFT);




        //StackPane root = new StackPane();
        //root.getChildren().add(message);


        VBox textBox = new VBox(20);
        textBox.setPadding(new Insets(50,10,10,10));
        textBox.setAlignment(Pos.TOP_LEFT); 
        textBox.getChildren().addAll(volumeBox, croixAutoBox, afficherTempsBox);

        this.setTop(textBox);


     

         Button quitButton = new Button("Quitter");
         quitButton.setFont(new Font(20));
         quitButton.setOnAction(e ->stage.close());



        VBox buttonBox = new VBox(quitButton);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.setPadding(new Insets(10,5,20,5));

        this.setBottom(buttonBox);

        this.setStyle("-fx-background-color: lightgray;");


    }


    
}
