package com.menu;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer {

    // Méthode pour jouer le bruit de clique
    public void bruitDeClique() {
        try {
            // Charger le fichier audio avec un chemin relatif
            String soundFile = getClass().getResource("/sound/son1.mp3").toExternalForm();
            
            // Créer un objet Media avec le chemin du fichier audio
            Media sound = new Media(soundFile);
            
            // Créer un lecteur audio (MediaPlayer)
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            
            // Jouer le son
            mediaPlayer.play();
        } catch (Exception e) {
            // Gérer les erreurs (par exemple, si le fichier est manquant ou si un problème survient)
            System.err.println("Erreur lors de la lecture du son: " + e.getMessage());
        }
    }
}
