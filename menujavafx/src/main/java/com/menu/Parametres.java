package com.menu;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class Parametres extends BorderPane {
    
    private Stage primaryStage;
    public SceneJeu sceneJeu;

    
    public Parametres(Stage paramStage, Stage mainStage, SceneJeu sceneJeu) {
        // Stocker la référence au stage principal
        this.primaryStage = mainStage;
        this.sceneJeu = sceneJeu;
        
        // Titre du menu
        Text title = new Text("PARAMETRES");
        title.setFont(BalooFont.setBalooSized(24));
        
        // Récupérer les paramètres actuels
        GameSettings settings = GameSettings.getInstance();
        
        // Utilisation de la police Baloo pour la cohérence avec le reste de l'interface
        Label volumeLabel = new Label("Volume : " + settings.getVolume() + "%");
        volumeLabel.setFont(BalooFont.setBalooSized(18));
        
        // Utiliser le slider de ButtonFactory
        Slider volumeSlider = ButtonFactory.createVolumeSlider(volumeLabel);
        volumeSlider.setValue(settings.getVolume());
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            settings.setVolume(newVal.intValue());
        });
        
        HBox volumeBox = new HBox(20, volumeLabel, volumeSlider);
        volumeBox.setAlignment(Pos.CENTER);
        
        // Créer manuellement les boutons de bascule pour éviter les problèmes
        Label croixAutoLabel = new Label("Croix auto :");
        croixAutoLabel.setFont(BalooFont.setBalooSized(18));
        
        ToggleButton croixAutoToggle = new ToggleButton(settings.isAutoCroix() ? "ON" : "OFF");
        croixAutoToggle.setFont(BalooFont.setBalooSized(18));
        croixAutoToggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;");
        croixAutoToggle.setSelected(settings.isAutoCroix());
        
        // Animation au survol
        croixAutoToggle.setOnMouseEntered(e -> croixAutoToggle.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        croixAutoToggle.setOnMouseExited(e -> croixAutoToggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        
        croixAutoToggle.setOnAction(e -> {
            settings.setAutoCroix(croixAutoToggle.isSelected());
            croixAutoToggle.setText(croixAutoToggle.isSelected() ? "ON" : "OFF");
            System.out.println("Croix auto: " + (settings.isAutoCroix() ? "Activé" : "Désactivé"));
        });
        
        HBox croixAutoBox = new HBox(20, croixAutoLabel, croixAutoToggle);
        croixAutoBox.setAlignment(Pos.CENTER);
        
        Label afficherTempsLabel = new Label("Afficher temps :");
        afficherTempsLabel.setFont(BalooFont.setBalooSized(18));
        
        ToggleButton afficherTempsToggle = new ToggleButton(settings.isShowTimer() ? "ON" : "OFF");
        afficherTempsToggle.setFont(BalooFont.setBalooSized(18));
        afficherTempsToggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;");
        afficherTempsToggle.setSelected(settings.isShowTimer());
        
        // Animation au survol
        afficherTempsToggle.setOnMouseEntered(e -> afficherTempsToggle.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        afficherTempsToggle.setOnMouseExited(e -> afficherTempsToggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        
        // Ajouter un gestionnaire d'événements pour mettre à jour le paramètre
        afficherTempsToggle.setOnAction(e -> {
            settings.setShowTimer(afficherTempsToggle.isSelected());
            afficherTempsToggle.setText(afficherTempsToggle.isSelected() ? "ON" : "OFF");
            System.out.println("Affichage temps: " + (settings.isShowTimer() ? "Activé" : "Désactivé"));
            
            // Mettre à jour directement l'interface si possible
            if (sceneJeu != null) {
                // Appel de la méthode de mise à jour de l'interface
                sceneJeu.updateTimerVisibility();
                
                // Force le rafraîchissement de l'interface pour appliquer les changements
                sceneJeu.requestLayout();
            }
        });
        
        HBox afficherTempsBox = new HBox(20, afficherTempsLabel, afficherTempsToggle);
        afficherTempsBox.setAlignment(Pos.CENTER);
        
        // Créer un bouton de retour animé cohérent avec le style du jeu
        Button returnButton = ButtonFactory.createAnimatedButton("RETOUR");
        returnButton.setPrefWidth(200);
        returnButton.setOnAction(e -> paramStage.close());
        
        // Créer un bouton pour revenir au menu principal
        Button mainMenuButton = ButtonFactory.createAnimatedButton("MENU PRINCIPAL");
        mainMenuButton.setPrefWidth(200);
        mainMenuButton.setOnAction(e -> {
            // Fermer la fenêtre des paramètres
            paramStage.close();
            
            // Revenir au menu principal
            try {
                Menu menu = new Menu();
                menu.start(primaryStage);
            } catch (Exception ex) {
                System.err.println("Erreur lors du retour au menu principal: " + ex.getMessage());
            }
        });
        
        // Organisation des boutons avec plus d'espacement
        VBox buttonsBox = new VBox(20); // Augmentation de l'espacement vertical
        buttonsBox.setPadding(new Insets(15, 0, 15, 0)); // Ajouter du padding vertical
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(returnButton, mainMenuButton);
        
        // Organisation des éléments
        VBox settingsBox = BoxFactory.createStyledBox(400, 400); // Hauteur augmentée pour voir tous les éléments
        settingsBox.getChildren().addAll(
            title,
            volumeBox,
            croixAutoBox,
            afficherTempsBox,
            buttonsBox
        );
        
        this.setCenter(settingsBox);
        
        // Style du fond pour la fenêtre modale avec coins arrondis
        settingsBox.setStyle("-fx-background-color: rgba(211, 211, 211, 0.95); " +
                            "-fx-background-radius: 15; " +
                            "-fx-border-radius: 15; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");
        
        // Rendre le fond du BorderPane transparent pour ne montrer que la boîte arrondie
        this.setStyle("-fx-background-color: transparent;");
    }
}