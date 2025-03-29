package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Classe responsable de la gestion des effets sonores dans l'application.
 * Permet de jouer différents sons pour améliorer l'expérience utilisateur.
 */
public class SoundPlayer {
    
    private static final Logger logger = LoggerFactory.getLogger(SoundPlayer.class);

    /**
     * Joue le son de clic lorsqu'un utilisateur interagit avec un élément de l'interface.
     * Utilise JavaFX Media pour charger et jouer un fichier MP3.
     * En cas d'erreur lors du chargement ou de la lecture du son, un message d'erreur est enregistré.
     */
    public void bruitDeClique() {
        try {
            logger.debug("Tentative de lecture du son de clic");
            
            // Charger le fichier audio avec un chemin relatif
            String soundFile = getClass().getResource("/sound/son1.mp3").toExternalForm();
            logger.trace("Chemin du fichier son: {}", soundFile);
            
            // Créer un objet Media avec le chemin du fichier audio
            Media sound = new Media(soundFile);
            
            // Créer un lecteur audio (MediaPlayer)
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            
            // Jouer le son
            mediaPlayer.play();
            logger.debug("Lecture du son de clic réussie");
        } catch (Exception e) {
            // Gérer les erreurs (par exemple, si le fichier est manquant ou si un problème survient)
            logger.error("Erreur lors de la lecture du son: {}", e.getMessage(), e);
        }
    }
}