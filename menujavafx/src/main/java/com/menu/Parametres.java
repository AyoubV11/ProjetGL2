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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe qui gère l'affichage et la manipulation des paramètres du jeu.
 * Permet à l'utilisateur de modifier et sauvegarder ses préférences comme
 * le volume, l'affichage du temps, et le placement automatique des croix.
 */
public class Parametres extends BorderPane {
    private static final Logger logger = LoggerFactory.getLogger(Parametres.class);
    
    /** Stage principal de l'application */
    private Stage primaryStage;
    
    /** Référence à la scène de jeu en cours */
    public SceneJeu sceneJeu;

    /**
     * Constructeur de la classe Parametres.
     * Initialise l'interface utilisateur pour la modification des paramètres du jeu.
     * 
     * @param paramStage Stage modal affichant les paramètres
     * @param mainStage Stage principal de l'application
     * @param sceneJeu Référence à la scène de jeu pour mettre à jour l'interface
     * @param libre Indique si la grille est en mode libre (true) ou classique (false)
     */
    public Parametres(Stage paramStage, Stage mainStage, SceneJeu sceneJeu, boolean libre) {
        logger.info("Initialisation de la fenêtre des paramètres, mode libre: {}", libre);
        
        // Stocker la référence au stage principal
        this.primaryStage = mainStage;
        this.sceneJeu = sceneJeu;
        
        // Titre du menu
        Text title = new Text("PARAMETRES");
        title.setFont(BalooFont.setBalooSized(24));
        logger.debug("Titre des paramètres configuré");
        
        // Récupérer les paramètres actuels
        GameSettings settings = GameSettings.getInstance();
        logger.debug("Paramètres actuels: volume={}, autoCroix={}, showTimer={}", 
                    settings.getVolume(), settings.isAutoCroix(), settings.isShowTimer());
        
        // Utilisation de la police Baloo pour la cohérence avec le reste de l'interface
        Label volumeLabel = new Label("Volume : " + settings.getVolume() + "%");
        volumeLabel.setFont(BalooFont.setBalooSized(18));
        
        // Utiliser le slider de ButtonFactory
        Slider volumeSlider = ButtonFactory.createVolumeSlider(volumeLabel);
        volumeSlider.setValue(settings.getVolume());
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int newVolume = newVal.intValue();
            logger.debug("Volume modifié: {} -> {}", oldVal.intValue(), newVolume);
            System.out.println("test");
            settings.setVolume(newVolume);
        });
        
        HBox volumeBox = new HBox(20, volumeLabel, volumeSlider);
        volumeBox.setAlignment(Pos.CENTER);
        logger.debug("Contrôle de volume configuré");
        
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
            boolean isSelected = croixAutoToggle.isSelected();
            logger.debug("Paramètre 'Croix auto' modifié: {} -> {}", settings.isAutoCroix(), isSelected);
            settings.setAutoCroix(isSelected);
            croixAutoToggle.setText(isSelected ? "ON" : "OFF");
        });
        
        HBox croixAutoBox = new HBox(20, croixAutoLabel, croixAutoToggle);
        croixAutoBox.setAlignment(Pos.CENTER);
        logger.debug("Contrôle de croix automatique configuré");
        
        // Contrôle pour l'affichage du temps
        Label afficherTempsLabel;
        afficherTempsLabel = new Label("Afficher temps :");
        afficherTempsLabel.setFont(BalooFont.setBalooSized(18));
        
        ToggleButton afficherTempsToggle;
        afficherTempsToggle = new ToggleButton(settings.isShowTimer() ? "ON" : "OFF");
        afficherTempsToggle.setFont(BalooFont.setBalooSized(18));
        afficherTempsToggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;");
        afficherTempsToggle.setSelected(settings.isShowTimer());
        
        // Animation au survol
        afficherTempsToggle.setOnMouseEntered(e -> afficherTempsToggle.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        afficherTempsToggle.setOnMouseExited(e -> afficherTempsToggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        
        // Ajouter un gestionnaire d'événements pour mettre à jour le paramètre
        afficherTempsToggle.setOnAction(e -> {
            boolean isSelected = afficherTempsToggle.isSelected();
            logger.debug("Paramètre 'Afficher temps' modifié: {} -> {}", settings.isShowTimer(), isSelected);
            settings.setShowTimer(isSelected);
            afficherTempsToggle.setText(isSelected ? "ON" : "OFF");
            
            // Mettre à jour directement l'interface si possible
            if (sceneJeu != null) {
                logger.debug("Mise à jour de la visibilité du chronomètre dans l'interface");
                // Appel de la méthode de mise à jour de l'interface
                sceneJeu.updateTimerVisibility(libre);
                
                // Force le rafraîchissement de l'interface pour appliquer les changements
                sceneJeu.requestLayout();
            }
        });
        
        HBox afficherTempsBox;
        afficherTempsBox = new HBox(20, afficherTempsLabel, afficherTempsToggle);
        afficherTempsBox.setAlignment(Pos.CENTER);
        logger.debug("Contrôle d'affichage du temps configuré");
        
        // Créer un bouton de retour animé cohérent avec le style du jeu
        Button returnButton = ButtonFactory.createAnimatedButton("RETOUR");
        returnButton.setPrefWidth(200);
        returnButton.setOnAction(e -> {
            logger.info("Fermeture de la fenêtre des paramètres et retour au jeu");
            paramStage.close(); 
            sceneJeu.runTimer();
        });
        
        // Créer un bouton pour revenir au menu principal
        Button mainMenuButton = ButtonFactory.createAnimatedButton("MENU PRINCIPAL");
        mainMenuButton.setPrefWidth(200);
        mainMenuButton.setOnAction(e -> {
            logger.info("Retour au menu principal depuis les paramètres");
            // Fermer la fenêtre des paramètres
            paramStage.close();
            
            // Revenir au menu principal
            try {
                Menu menu = new Menu();
                menu.start(primaryStage);
            } catch (Exception ex) {
                logger.error("Erreur lors du retour au menu principal: {}", ex.getMessage(), ex);
                System.err.println("Erreur lors du retour au menu principal: " + ex.getMessage());
            }
        });
        logger.debug("Boutons de navigation configurés");
        
        // Organisation des boutons avec plus d'espacement
        VBox buttonsBox = new VBox(20); // Augmentation de l'espacement vertical
        buttonsBox.setPadding(new Insets(15, 0, 15, 0)); // Ajouter du padding vertical
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(returnButton, mainMenuButton);
        
        // Création de la boîte de paramètres en fonction du mode (libre ou classique)
        VBox settingsBox;
        if (!libre) {
            logger.debug("Configuration de la boîte de paramètres pour le mode classique");
            settingsBox = BoxFactory.createStyledBox(400, 400); // Hauteur augmentée pour voir tous les éléments
            settingsBox.getChildren().addAll(
                title,
                volumeBox,
                croixAutoBox,
                afficherTempsBox,
                buttonsBox
            );
        } else {
            logger.debug("Configuration de la boîte de paramètres pour le mode libre");
            settingsBox = BoxFactory.createStyledBox(400, 400); // Hauteur augmentée pour voir tous les éléments
            settingsBox.getChildren().addAll(
                title,
                volumeBox,
                croixAutoBox,
                buttonsBox
            );
        }
        
        this.setCenter(settingsBox);
        
        // Style du fond pour la fenêtre modale avec coins arrondis
        settingsBox.setStyle("-fx-background-color: rgba(211, 211, 211, 0.95); " +
                            "-fx-background-radius: 15; " +
                            "-fx-border-radius: 15; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");
        
        // Rendre le fond du BorderPane transparent pour ne montrer que la boîte arrondie
        this.setStyle("-fx-background-color: transparent;");
        logger.info("Fenêtre des paramètres initialisée avec succès");
    }
}