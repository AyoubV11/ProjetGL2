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
        Parent root = new GrilleController(new Grille("src/test/ressources/grilleTest.json"), 500, 500, 1.0/10);
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setTitle("JavaFX Slitherlink");
        primaryStage.setScene(scene);
        primaryStage.show();

        // scene.setOnMouseMoved(event -> {
        //     Node button = ((GridPane) root).getChildren().get(0);
        //     double mouseX = event.getSceneX();
        //     double mouseY = event.getSceneY();
        //     double buttonX = button.localToScene(button.getBoundsInLocal()).getCenterX();
        //     double buttonY = button.localToScene(button.getBoundsInLocal()).getCenterY();

        //     double distance = Math.sqrt(Math.pow(mouseX - buttonX, 2) + Math.pow(mouseY - buttonY, 2));

        //     if (distance < 50) {
        //         button.setStyle("-fx-background-color: lightblue;");
        //     } else {
        //         button.setStyle("-fx-background-color: red;");
        //     }
        // });
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
