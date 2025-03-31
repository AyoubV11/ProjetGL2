package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Classe responsable de la gestion des effets sonores dans l'application.
 * Permet de jouer différents sons pour améliorer l'expérience utilisateur.
 */
public class SoundPlayer {
    
    private static SoundPlayer instance = null;
    private static final Logger logger = LoggerFactory.getLogger(SoundPlayer.class);
    private MediaPlayer mediaPlayer;

    /**
     * Constructeur privé pour empêcher l'instanciation directe de la classe.
     */
    private SoundPlayer() {
        // Ne rien faire
    }

    /**
     * Méthode statique pour obtenir l'instance unique de la classe SoundPlayer.
     * Cette méthode suit le patron de conception Singleton.
     * 
     * @return L'instance unique de la classe SoundPlayer
     */
    public static SoundPlayer getInstance() {
        if (instance == null) {
            instance = new SoundPlayer();
        }
        return instance;
    }

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

            // permettre les son de se superposer
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(mediaPlayer.getStartTime()));

            logger.debug("Lecture du son de clic réussie");
        } catch (Exception e) {
            // Gérer les erreurs (par exemple, si le fichier est manquant ou si un problème survient)
            logger.error("Erreur lors de la lecture du son: {}", e.getMessage(), e);
        }
    }

    public void lanceMusic() {
        try {
            logger.debug("Tentative de lecture de la musique");
            
            String soundFile = getClass().getResource("/music/slitherlink.wav").toExternalForm();
            Media sound = new Media(soundFile);
            
            mediaPlayer = new MediaPlayer(sound); // Utilisation de l'attribut global
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 
            mediaPlayer.play();
            logger.debug("Lecture de la musique réussie");
        } catch (Exception e) {
            logger.error("Erreur lors de la lecture de la musique: {}", e.getMessage(), e);
        }
    }




    public void ajusteSon(Slider slider) {
    if (slider == null) {
        logger.warn("Slider nul, impossible d'ajuster le volume.");
        return;
    }

    // Vérifier que le MediaPlayer est bien initialisé
    if (mediaPlayer != null) {
        // Calculer la valeur du volume en fonction du slider
        double volume = slider.getValue() / 100.0;
        System.out.println("Lenny:"+volume);        
        // Ajuster le volume du MediaPlayer
        mediaPlayer.setVolume(volume);

        // Log du volume ajusté pour le débogage
        logger.debug("Volume ajusté à : {}", mediaPlayer.getVolume());
    } else {
        System.out.println("Echec");  
        logger.warn("Impossible d'ajuster le volume : aucun MediaPlayer actif.");
    }
}

 
}