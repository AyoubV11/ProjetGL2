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

    private static MediaPlayer sonClique = new MediaPlayer(new Media(SoundPlayer.class.getResource(SON_CLIQUE).toString()));
    private static MediaPlayer musicBackground = new MediaPlayer(new Media(SoundPlayer.class.getResource(MUSIC_BACKGROUND).toString()));


    /**
     * Constructeur privé pour empêcher l'instanciation directe de la classe.
     */
    private SoundPlayer() {
        // Ne rien faire
    }

    /**
     * Joue le son de clic lorsqu'un utilisateur interagit avec un élément de l'interface.
     * Utilise JavaFX Media pour charger et jouer un fichier MP3.
     * En cas d'erreur lors du chargement ou de la lecture du son, un message d'erreur est enregistré.
     */
    public static void bruitDeClique() {
        sonClique.play();
        //rejouer le son
        sonClique.seek(sonClique.getStartTime());
    }

    public static void lanceMusic() {
        musicBackground.setCycleCount(MediaPlayer.INDEFINITE);
        musicBackground.play();
    }




    public static void ajusteSon(Slider slider) {
        double volume = slider.getValue() / 100.0;
        sonClique.setVolume(volume);
        musicBackground.setVolume(volume);

    }
}
