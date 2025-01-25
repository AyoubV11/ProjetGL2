package com.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorldApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label helloLabel = new Label("Hello, World!");
        StackPane root = new StackPane(helloLabel);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("JavaFX HelloWorld");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
