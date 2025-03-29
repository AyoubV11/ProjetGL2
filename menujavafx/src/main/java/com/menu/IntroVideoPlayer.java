package com.menu;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe responsable de la lecture de la vidéo d'introduction du jeu.
 * Gère l'affichage, la lecture et les actions de fin de vidéo.
 */
public class IntroVideoPlayer {
    private static final Logger logger = LoggerFactory.getLogger(IntroVideoPlayer.class);
    
    /** Stage principal de l'application */
    private Stage primaryStage;
    
    /** Action à exécuter à la fin de la vidéo */
    private Runnable onVideoComplete;

    /**
     * Constructeur du lecteur de vidéo d'introduction.
     * 
     * @param primaryStage Le stage principal de l'application
     * @param onVideoComplete Action à exécuter lorsque la vidéo est terminée ou ignorée
     */
    public IntroVideoPlayer(Stage primaryStage, Runnable onVideoComplete) {
        logger.debug("Initialisation du lecteur de vidéo d'introduction");
        this.primaryStage = primaryStage;
        this.onVideoComplete = onVideoComplete;
    }

    /**
     * Affiche et démarre la lecture de la vidéo d'introduction.
     * Si la vidéo est introuvable, exécute directement l'action de fin.
     */
    public void showIntroVideo() {
        logger.info("Démarrage de la lecture de la vidéo d'introduction");
        
        // Récupération du chemin de la vidéo depuis les ressources
        String videoPath;
        try {
            videoPath = getClass().getResource("/tuto/test_tuto.mp4").toExternalForm();
            logger.debug("Chemin de la vidéo trouvé: {}", videoPath);
        } catch (NullPointerException e) {
            logger.error("Vidéo d'introduction introuvable dans les ressources", e);
            System.err.println("Vidéo introuvable");
            Platform.runLater(onVideoComplete);
            return;
        }

        // Création du lecteur média
        logger.debug("Création du lecteur média");
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Configuration de la vidéo
        mediaView.setFitWidth(1006);
        mediaView.setFitHeight(595);
        mediaView.setPreserveRatio(true);
        logger.debug("Configuration de la vue média: largeur={}, hauteur={}, préservation du ratio=true", 
                    1006, 595);

        // Bouton "Skip Video"
        logger.debug("Création du bouton d'ignorement de la vidéo");
        Button quitButton = ButtonFactory.createAnimatedButton("Skip Video");
        quitButton.setOnAction(e -> {
            logger.info("Vidéo d'introduction ignorée par l'utilisateur");
            mediaPlayer.stop();
            Platform.runLater(onVideoComplete);
        });

        // Layout principal
        VBox videoLayout = new VBox(0, mediaView, quitButton);
        videoLayout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(videoLayout);
        Scene videoScene = new Scene(root, 1006, 605);
        logger.debug("Scène vidéo créée avec dimensions: {}x{}", 1006, 605);

        // Changer la scène principale pour afficher la vidéo
        Platform.runLater(() -> {
            primaryStage.setScene(videoScene);
            logger.debug("Scène principale changée pour afficher la vidéo");
        });

        // Lorsque la vidéo est terminée, on affiche le menu
        mediaPlayer.setOnEndOfMedia(() -> {
            logger.info("Vidéo d'introduction terminée");
            mediaPlayer.stop();
            Platform.runLater(onVideoComplete);
        });

        // Gestion des erreurs média
        mediaPlayer.setOnError(() -> {
            logger.error("Erreur lors de la lecture de la vidéo: {}", mediaPlayer.getError());
            Platform.runLater(onVideoComplete);
        });

        // Lancer la vidéo
        logger.debug("Démarrage de la lecture");
        mediaPlayer.play();
    }
}