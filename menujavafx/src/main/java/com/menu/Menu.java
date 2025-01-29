package com.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Menu extends Application {

    private GridComponent gridComponent;
    
    @Override
    public void start(Stage primaryStage) {
        // Créer les composants principaux
        TitleComponent titleComponent = new TitleComponent();
        MenuBoxComponent menuBoxComponent = new MenuBoxComponent(this);
        gridComponent = new GridComponent();  // Initialiser le GridComponent

        // Agrandir le titre
        Text title = titleComponent.getTitle();

        // Charger l'image de fond
        Image backgroundImage = new Image(getClass().getResource("/slither.png").toExternalForm());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, 
                new BackgroundSize(100, 100, true, true, true, false)
        );

        // Conteneur principal avec fond d'image
        StackPane root = new StackPane();
        root.setBackground(new Background(background));

        HBox content = new HBox(40, menuBoxComponent.getMenuBox(), gridComponent.getGridPane()); // Espacement entre éléments
        content.setStyle("-fx-padding: 40;");
        content.setAlignment(javafx.geometry.Pos.CENTER);

        VBox mainLayout = new VBox(20, title, content);
        mainLayout.setAlignment(javafx.geometry.Pos.CENTER);

        root.getChildren().add(mainLayout);

        // Afficher la scène
        Scene scene = new Scene(root, 1006, 595);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slither Link");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Méthodes d'action pour chaque bouton
    public void showClassicGrid() {
        // Logique pour afficher la grille classique
        System.out.println("Affichage de la grille CLASSIQUE");
        gridComponent.showClassicGrid();  // Afficher la grille classique
    }

    public void showFreeGrid() {
        // Logique pour afficher la grille libre
        System.out.println("Affichage de la grille LIBRE");
        gridComponent.showFreeGrid();  // Afficher la grille vide
    }

    public void showTechniquesGrid() {
        // Logique pour afficher la grille techniques
        System.out.println("Affichage de la grille TECHNIQUES");
        gridComponent.showTechniquesGrid();  // Afficher une grille pour les techniques
    }

    public void showSettings() {
        // Logique pour afficher les paramètres
        System.out.println("Affichage des paramètres");
        // Modifier le layout pour afficher les paramètres
    }
}
