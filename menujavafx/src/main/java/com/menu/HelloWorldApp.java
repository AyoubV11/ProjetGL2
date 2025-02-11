package com.menu;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


// public class HelloWorldApp extends Application {

//     @Override
//     public void start(Stage primaryStage) {
//         Parent root = new Parametres(primaryStage);
//         Scene scene = new Scene(root, 500, 550);
//         primaryStage.setTitle("JavaFX Slitherlink");
//         primaryStage.setScene(scene);
//         primaryStage.show();
//     }

//     public static void main(String[] args) {
//         launch(args);
//     }
// }




public class HelloWorldApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Parent gameInterface = new SceneJeu(primaryStage);
        Scene scene = new Scene(gameInterface, 600, 400);

        primaryStage.setTitle("Jeu JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}
