package com.menu;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Menu extends Application {

    private GridComponent gridComponent;
    private HBox content;
    private MenuBoxComponent menuBoxComponent;
    
    @Override
    public void start(Stage primaryStage) {
        // Créer les composants principaux
        TitleComponent titleComponent = new TitleComponent();
        menuBoxComponent = new MenuBoxComponent(this);
        gridComponent = new GridComponent(this);  // Initialiser le GridComponent

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
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/star_completed.png")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Méthodes d'action pour chaque bouton
    public void showClassicGrid() {
        // Logique pour afficher la grille classique
        gridComponent.showClassicGrid();  // Afficher la grille classique
    }

    public void showFreeGrid() {
        // Logique pour afficher la grille libre
        gridComponent.showFreeGrid();  // Afficher la grille vide
    }

    public void showTechniquesGrid() {
        // Logique pour afficher la grille techniques
        gridComponent.showTechniquesGrid();  // Afficher une grille pour les techniques
    }
    public void showClassicMenu() {
        showMainMenu();
        showClassicGrid();  
    }

    public void showSettings() {
        gridComponent.clear();
    
        // Vider le contenu actuel
        content.getChildren().clear();
    
        // Créer une nouvelle VBox pour les paramètres
        VBox settingsBox = new VBox(15);
        settingsBox.setAlignment(Pos.CENTER);
        settingsBox.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 20; -fx-border-radius: 15; -fx-background-radius: 15;");
    
        // Bouton retour
        Button retourButton = ButtonFactory.createAnimatedButton("RETOUR");
        retourButton.setPrefWidth(200);
        retourButton.setOnAction(e -> showMainMenu());
    
        // Interrupteur ON/OFF avec label "Croix auto :"
        Label autoCrossLabel = new Label("Croix auto : ");
        autoCrossLabel.setFont(BalooFont.setBalooSized(18));
        
        ToggleButton autoCrossToggle = new ToggleButton("OFF");
        autoCrossToggle.setFont(BalooFont.setBalooSized(18));
        autoCrossToggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;");
        
        autoCrossToggle.setOnMouseEntered(e -> autoCrossToggle.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        autoCrossToggle.setOnMouseExited(e -> autoCrossToggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        
        autoCrossToggle.setOnMousePressed(e -> animateButton(autoCrossToggle, 1.1));
        autoCrossToggle.setOnMouseReleased(e -> animateButton(autoCrossToggle, 1.0));
    
        autoCrossToggle.setOnAction(event -> {
            if (autoCrossToggle.isSelected()) {
                autoCrossToggle.setText("ON");
            } else {
                autoCrossToggle.setText("OFF");
            }
        });
    
        HBox autoCrossBox = new HBox(10, autoCrossLabel, autoCrossToggle);
        autoCrossBox.setAlignment(Pos.CENTER);
    
        // Slider de volume avec label
        Label volumeLabel = new Label("Volume : 50%");
        volumeLabel.setFont(BalooFont.setBalooSized(18));
    
        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setStyle("-fx-control-inner-background: #000000; -fx-background-color: #000000; -fx-border-radius: 20; -fx-background-radius: 20;" +"-fx-focus-color: transparent;" + "-fx-faint-focus-color: transparent;");
    
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            volumeLabel.setText("Volume : " + newVal.intValue() + "%");
        });
    
        VBox volumeBox = new VBox(5, volumeLabel, volumeSlider);
        volumeBox.setAlignment(Pos.CENTER);
    
        // Ajouter les éléments à la boîte des paramètres
        settingsBox.getChildren().addAll(autoCrossBox, volumeBox, retourButton);
    
        // Ajouter la boîte des paramètres et la grille vide pour garder la structure
        content.getChildren().addAll(settingsBox, gridComponent.getGridPane());
    }

    public void showLevel() {
        gridComponent.clear();
        
        // Vider le contenu actuel
        content.getChildren().clear();
        
        // Créer une nouvelle VBox pour le niveau
        VBox levelBox = new VBox(15);
        levelBox.setAlignment(Pos.CENTER);
        levelBox.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 20; -fx-border-radius: 15; -fx-background-radius: 15;");
        
        // Images des étoiles avec descriptions
        HBox starsBox = new HBox(30);
        starsBox.setAlignment(Pos.CENTER);
        
        // Image 1 : Etoile 1
        ImageView star1 = new ImageView(new Image(getClass().getResource("/star_completed.png").toExternalForm()));
        star1.setFitWidth(100);
        star1.setFitHeight(100);
        Label star1Description = new Label("Finir le niveau");
        star1Description.setFont(BalooFont.setBalooSized(16));
        star1Description.setAlignment(Pos.CENTER);
        
        // Image 2 : Etoile 2
        ImageView star2 = new ImageView(new Image(getClass().getResource("/star_completed.png").toExternalForm()));
        star2.setFitWidth(100);
        star2.setFitHeight(100);
        Label star2Description = new Label("Moins de 2 aides");
        star2Description.setFont(BalooFont.setBalooSized(16));
        star2Description.setAlignment(Pos.CENTER);
        
        // Image 3 : Etoile 3
        ImageView star3 = new ImageView(new Image(getClass().getResource("/star_uncompleted.png").toExternalForm()));
        star3.setFitWidth(100);
        star3.setFitHeight(100);
        Label star3Description = new Label("Moins de 3min");
        star3Description.setFont(BalooFont.setBalooSized(16));
        star3Description.setAlignment(Pos.CENTER);
        
        starsBox.getChildren().addAll(star1, star2, star3);
        
        // Créer une nouvelle HBox pour aligner les descriptions sous chaque étoile
        HBox descriptionsBox = new HBox(30);
        descriptionsBox.setAlignment(Pos.CENTER);
        descriptionsBox.getChildren().addAll(star1Description, star2Description, star3Description);
        
        // Ajouter les étoiles et leurs descriptions dans la boîte de niveau
        levelBox.getChildren().addAll(starsBox, descriptionsBox);
        
        // Bouton retour
        Button retourButton = ButtonFactory.createAnimatedButton("RETOUR");
        retourButton.setPrefWidth(200);
        retourButton.setOnAction(e -> showClassicMenu());
        
        // Bouton jouer
        Button jouerButton = ButtonFactory.createAnimatedButton("JOUER");
        jouerButton.setPrefWidth(200);
        jouerButton.setOnAction(e -> showSettings());
        
        // HBox pour les boutons (retour à gauche et jouer à droite)
        HBox buttonBox = new HBox(30, retourButton, jouerButton);
        buttonBox.setAlignment(Pos.BOTTOM_LEFT);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));
        
        // Ajouter les boutons au cadre du niveau
        levelBox.getChildren().add(buttonBox);
        
        // Ajouter la boîte du niveau et la grille vide pour garder la structure
        content.getChildren().addAll(levelBox, gridComponent.getGridPane());
    }
    

    public void animateButton(ToggleButton button, double scale) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        scaleTransition.setToX(scale);
        scaleTransition.setToY(scale);
        scaleTransition.play();
    }

    public void showMainMenu() {
        content.getChildren().clear();
        content.getChildren().addAll(menuBoxComponent.getMenuBox(), gridComponent.getGridPane());
    }

}
