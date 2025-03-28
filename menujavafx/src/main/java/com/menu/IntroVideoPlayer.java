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

public class IntroVideoPlayer {
    private Stage primaryStage;
    private Runnable onVideoComplete;

    public IntroVideoPlayer(Stage primaryStage, Runnable onVideoComplete) {
        this.primaryStage = primaryStage;
        this.onVideoComplete = onVideoComplete;
    }

    public void showIntroVideo() {
        // Get video path from resources
        String videoPath;
        try {
            videoPath = getClass().getResource("/tuto/test_tuto.mp4").toExternalForm();
        } catch (NullPointerException e) {
            System.err.println("Vidéo introuvable");
            Platform.runLater(onVideoComplete);
            return;
        }

        // Create media player
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Configuration de la vidéo
        mediaView.setFitWidth(1006);
        mediaView.setFitHeight(595);
        mediaView.setPreserveRatio(true);

        // Bouton "Skip Video"
        Button quitButton = ButtonFactory.createAnimatedButton("Skip Video");
        quitButton.setOnAction(e -> {
            mediaPlayer.stop();
            Platform.runLater(onVideoComplete);
        });

        // Layout principal
        VBox videoLayout = new VBox(0, mediaView, quitButton);
        videoLayout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(videoLayout);
        Scene videoScene = new Scene(root, 1006, 605);

        // Changer la scène principale pour afficher la vidéo
        Platform.runLater(() -> primaryStage.setScene(videoScene));

        // Lorsque la vidéo est terminée, on affiche le menu
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
            Platform.runLater(onVideoComplete);
        });

        // Lancer la vidéo
        mediaPlayer.play();
    }
}
