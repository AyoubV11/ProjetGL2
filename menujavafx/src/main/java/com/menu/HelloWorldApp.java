package com.menu;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class HelloWorldApp extends Application {

    @Override
    public void start(Stage primaryStage) {    
        GridPane grille = new GrilleController(
            new Grille("grilleTest.json"), 
            600, 1-0.95
            );
        Parent root = new StackPane(grille);
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setTitle("JavaFX Slitherlink");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}
