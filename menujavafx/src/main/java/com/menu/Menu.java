package com.menu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Menu extends Application {

    private GridComponent gridComponent;
    private HBox content;
    private MenuBoxComponent menuBoxComponent;
    
    @Override
    public void start(Stage primaryStage) {
        // Créer les composants principaux
        TitleComponent titleComponent = new TitleComponent();
        menuBoxComponent = new MenuBoxComponent(this);
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

        content = new HBox(40, menuBoxComponent.getMenuBox(), gridComponent.getGridPane()); // Espacement entre éléments
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
        System.out.println("Affichage des paramètres");
        gridComponent.showSettings();
    
        // Vider le contenu actuel
        content.getChildren().clear();
    
        // Créer une nouvelle VBox pour les paramètres
        VBox settingsBox = new VBox(15);
        settingsBox.setAlignment(javafx.geometry.Pos.CENTER);
        settingsBox.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 20; -fx-border-radius: 15; -fx-background-radius: 15;");
    
        // Ajouter des boutons de paramètres
        Button retourButton = ButtonFactory.createAnimatedButton("RETOUR");
        Button volumeButton = ButtonFactory.createAnimatedButton("VOLUME");
        Button themeButton = ButtonFactory.createAnimatedButton("THEME");
    
        retourButton.setPrefWidth(200);
        volumeButton.setPrefWidth(200);
        themeButton.setPrefWidth(200);
    
        // Action pour revenir au menu principal
        retourButton.setOnAction(e -> showMainMenu());
    
        settingsBox.getChildren().addAll(volumeButton, themeButton, retourButton);
    
        // Ajouter la boîte des paramètres et la grille vide pour garder la structure
        content.getChildren().addAll(settingsBox, gridComponent.getGridPane());
    }


    public void showMainMenu() {
        System.out.println("Retour au menu principal");
        content.getChildren().clear();
        content.getChildren().addAll(menuBoxComponent.getMenuBox(), gridComponent.getGridPane());
    }

}
