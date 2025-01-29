package com.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class HelloWorldApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Parametres root = new Parametres(primaryStage);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("JavaFX Slitherlink");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
