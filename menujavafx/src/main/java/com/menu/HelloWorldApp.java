package com.menu;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class HelloWorldApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Parent root = new GrilleController(new Grille("src/test/ressources/grilleTest.json"), 500);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JavaFX Slitherlink");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
