package com.menu;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Titre principal
        Text title = createTitle();

        // Boutons principaux à gauche (menu)
        VBox menuBox = createMenuBox();

        // Grilles à droite
        GridPane gridPane = createGridPane();

        // Conteneur principal
        HBox root = createRootBox(menuBox, gridPane);

        VBox mainLayout = new VBox(10, title, root);
        mainLayout.setAlignment(Pos.CENTER);

        // Redimensionner dynamiquement les éléments en fonction de la taille de la fenêtre
        mainLayout.scaleXProperty().bind(primaryStage.widthProperty().divide(800));
        mainLayout.scaleYProperty().bind(primaryStage.heightProperty().divide(600));

        // Créer la scène et l'afficher
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slither Link Menu");
        primaryStage.show();
    }

    // Méthode pour créer le titre principal
    private Text createTitle() {
        Text title = new Text("SLITHER LINK");
        title.setFont(Font.font("Arial", 40));
        title.setFill(Color.BLACK);
        return title;
    }

    // Méthode pour créer les boutons du menu principal
    private VBox createMenuBox() {
        VBox menuBox = new VBox(10);
        menuBox.setAlignment(Pos.CENTER);
        String[] menuItems = {"CLASSIQUE", "LIBRE", "TECHNIQUES", "PARAMETRES"};
        for (String item : menuItems) {
            Button button = createAnimatedButton(item);
            button.setPrefWidth(200);
            menuBox.getChildren().add(button);
        }
        menuBox.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 20; -fx-border-radius: 15; -fx-background-radius: 15;");
        return menuBox;
    }

    // Méthode pour créer la grille avec des boutons
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 12; i++) {
            Button gridButton = createImageButton("grille " + i, getClass().getResource("/skull.png").toExternalForm());
            gridPane.add(gridButton, (i - 1) % 2, (i - 1) / 2);
        }

        return gridPane;
    }

    // Méthode pour créer le conteneur principal (HBox)
    private HBox createRootBox(VBox menuBox, GridPane gridPane) {
        HBox root = new HBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #B0C4DE; -fx-padding: 20;");
        root.getChildren().addAll(menuBox, gridPane);
        return root;
    }

    // Méthode pour créer un bouton animé
    private Button createAnimatedButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 18));
        button.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 15; -fx-background-radius: 15;");

        // Animation au survol
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-border-radius: 15; -fx-background-radius: 15;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 15; -fx-background-radius: 15;"));

        // Animation au clic (agrandissement)
        button.setOnMousePressed(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });
        button.setOnMouseReleased(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });

        return button;
    }

    // Méthode pour créer un bouton avec image
    private Button createImageButton(String text, String imagePath) {
        // Chargement de l'image
        Image skullImage = new Image(imagePath);
        ImageView imageView1 = new ImageView(skullImage);
        imageView1.setFitWidth(20);
        imageView1.setFitHeight(20);
        
        ImageView imageView2 = new ImageView(skullImage);
        imageView2.setFitWidth(20);
        imageView2.setFitHeight(20);

        ImageView imageView3 = new ImageView(skullImage);
        imageView3.setFitWidth(20);
        imageView3.setFitHeight(20);

        // Conteneur pour aligner le texte et les trois images
        HBox content = new HBox(5); // Espacement de 5 entre les éléments
        content.setAlignment(Pos.CENTER);
        Text buttonText = new Text(text);
        buttonText.setFont(Font.font("Arial", 14));
        
        // Ajout des trois images et du texte dans le conteneur
        content.getChildren().addAll(buttonText, imageView1, imageView2, imageView3);

        // Bouton avec conteneur interne
        Button button = new Button();
        button.setGraphic(content);
        button.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: black;");

        // Ajouter un listener une fois que le bouton est attaché à la scène
        button.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // Lier la taille du bouton à la taille de la scène
                button.prefWidthProperty().bind(newScene.widthProperty().divide(6));
                button.prefHeightProperty().bind(newScene.heightProperty().divide(12));
            }
        });

        // Animation au survol
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: black;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black;"));

        // Animation au clic
        button.setOnMousePressed(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });
        button.setOnMouseReleased(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
