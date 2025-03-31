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

    private static final Logger logger = LoggerFactory.getLogger(SoundPlayer.class);
    private static final String SON_CLIQUE = "/sound/sonclique.wav";
    private static final String MUSIC_BACKGROUND = "/sound/slitherlink.wav";
    private static final String VICTORY_SOUND = "/sound/victory.wav";
    private static final String CURSEUR_BOUTON_SOUND = "/sound/curseurBouton.wav";


    private static MediaPlayer sonClique;
    private static MediaPlayer musicBackground;
    private static MediaPlayer victorySound;
    private static MediaPlayer curseurBouton;


    /**
     * Constructeur privé pour empêcher l'instanciation directe de la classe.
     */
    private SoundPlayer() {
        // Ne rien faire
    }

    public static void init(){
        try {
            logger.debug("Chargement des fichiers audio");

            
            sonClique = new MediaPlayer(new Media(SoundPlayer.class.getResource(SON_CLIQUE).toString()));
            logger.debug("sonClique.wav chargé");

            musicBackground = new MediaPlayer(new Media(SoundPlayer.class.getResource(MUSIC_BACKGROUND).toString()));
            logger.debug("slitherlink.wav chargé");
            
            victorySound = new MediaPlayer(new Media(SoundPlayer.class.getResource(VICTORY_SOUND).toString()));
            logger.debug("victory.wav chargé");

            curseurBouton = new MediaPlayer(new Media(SoundPlayer.class.getResource(CURSEUR_BOUTON_SOUND).toString()));
            logger.debug("curseurBouton.wav chargé");

        } catch (Exception e) {
            logger.error("Erreur lors du chargement des fichiers audio", e);
        }
        
    }  

    /**
     * Joue le son de clic lorsqu'un utilisateur interagit avec un élément de l'interface.
     * Utilise JavaFX Media pour charger et jouer un fichier MP3.
     * En cas d'erreur lors du chargement ou de la lecture du son, un message d'erreur est enregistré.
     */
    public static void bruitDeClique() {
        sonClique.play();
        sonClique.seek(sonClique.getStartTime());
    }

    public static void lanceMusic() {
        musicBackground.setCycleCount(MediaPlayer.INDEFINITE);
        musicBackground.play();
    }

    public static void victorySound() {
        victorySound.play();
        victorySound.seek(victorySound.getStartTime());

    }

    public static void ajusteSon(Slider slider) {
        double volume = slider.getValue() / 100.0;
        musicBackground.setVolume(volume);

    }

    public static void curseurBouton() {
        curseurBouton.play();
        curseurBouton.seek(curseurBouton.getStartTime());
    }

}
