package main.java.com.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.java.com.menu.Parametres;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class Parametres extends BorderPane{
    public Parametres(Stage stage){
        Text message1 = new Text("Volume :");
        message1.setFont(new Font(20));

        Text message2 = new Text("Croix Auto :");
        message2.setFont(new Font(20));

        Text message3 = new Text("Afficher temps :");
        message3.setFont(new Font(20));

        //StackPane root = new StackPane();
        //root.getChildren().add(message);
        VBox textBox = new VBox(20);
        textBox.setPadding(new Insets(50,10,10,10));
        textBox.setAlignment(Pos.TOP_LEFT); 
        textBox.getChildren().addAll(message1, message2, message3);


        this.setTop(textBox);


         //Créer un bouton Quitter

         Button quitButton = new Button("Quitter");
         quitButton.setFont(new Font(20));
         quitButton.setOnAction(e ->stage.close());


        VBox buttonBox = new VBox(quitButton);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.setPadding(new Insets(10,5,20,5));

        this.setBottom(buttonBox);

    }
    
}
