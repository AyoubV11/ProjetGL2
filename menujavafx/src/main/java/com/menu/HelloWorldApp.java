package com.menu;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class HelloWorldApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Parent root = new GrilleController(new Grille("src/test/ressources/grilleTest.json"), 500, 500, (double)2/10);
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setTitle("JavaFX Slitherlink");
        primaryStage.setScene(scene);
        primaryStage.show();

        
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
