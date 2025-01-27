package com.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Menu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Créer les composants principaux
        TitleComponent titleComponent = new TitleComponent();
        MenuBoxComponent menuBoxComponent = new MenuBoxComponent();
        GridComponent gridComponent = new GridComponent();

        // Conteneur principal
        HBox root = new HBox(20, menuBoxComponent.getMenuBox(), gridComponent.getGridPane());
        root.setStyle("-fx-background-color: #B0C4DE; -fx-padding: 20;");
        root.setAlignment(javafx.geometry.Pos.CENTER);

        VBox mainLayout = new VBox(10, titleComponent.getTitle(), root);
        mainLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // Redimensionner dynamiquement les éléments en fonction de la taille de la fenêtre
        mainLayout.scaleXProperty().bind(primaryStage.widthProperty().divide(800));
        mainLayout.scaleYProperty().bind(primaryStage.heightProperty().divide(600));

        // Afficher la scène
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slither Link Menu");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
