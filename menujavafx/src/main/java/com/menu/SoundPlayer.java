package com.menu;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    private static final String FILE_PATH_VOLUME = ".volume.json";

    private static MediaPlayer sonClique;
    private static MediaPlayer musicBackground;
    private static MediaPlayer victorySound;
    private static MediaPlayer curseurBouton;
    
    /** Mapper JSON pour sérialiser/désérialiser les données des niveaux */
    private static final ObjectMapper objectMapper = new ObjectMapper();


    private static double volume;


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
        chargerVolume();
        
    }  

    /**
     * Joue le son de clic lorsqu'un utilisateur interagit avec un élément de l'interface.
    */
    public static void bruitDeClique() {
        sonClique.play();
        sonClique.seek(sonClique.getStartTime());
    }

    /**
     * Joue la musique de fond en boucle.
     */
    public static void lanceMusic() {
        musicBackground.setCycleCount(MediaPlayer.INDEFINITE);
        musicBackground.play();
    }

    /**
     * Joue le son de victoire lorsqu'un joueur termine un niveau.
     */
    public static void victorySound() {
        victorySound.play();
        victorySound.seek(victorySound.getStartTime());

    }

    /**
     * Joue le son de curseur touchant un bouton lorsqu'un utilisateur navigue dans le menu.
     */
    public static void curseurBouton() {
        curseurBouton.play();
        curseurBouton.seek(curseurBouton.getStartTime());
    }

    /**
     * Sauvegarde le volume actuel dans un fichier JSON.
     */
    public static void sauvegarderVolume(){
        try {
            objectMapper.writeValue(new File(FILE_PATH_VOLUME), volume);
            logger.info("Volume sauvegardé");
        } catch (IOException e) {
            logger.error("Erreur lors de la sauvegarde du volume", e);
            e.printStackTrace();
        }
    }

    /**
     * Charge le volume actuel depuis un fichier JSON.
    */
    public static void chargerVolume(){
        try {
            File file = new File(FILE_PATH_VOLUME);
            if (file.exists()) {
                volume = objectMapper.readValue(file, int.class);
                setVolume((int)volume);
                logger.info("Volume chargé");
            } else {
                volume = 50;
                logger.info("Volume initialisé à 0.5");
            }
        } catch (IOException e) {
            volume = 50;
            logger.warn("Erreur lors du chargement du volume, initialisation à 0.5", e);
        }
    }

    /**
     * Définit le volume des effets sonores.
     * @param volume
    */
    public static void setVolume(int volume) {
        SoundPlayer.volume = volume;
        musicBackground.setVolume(volume/100.0);
        sonClique.setVolume(volume/100.0);
        victorySound.setVolume(volume/100.0);
        curseurBouton.setVolume(volume/100.0);
        sauvegarderVolume();
    }

    /**
     * Obtient le volume actuel.
     * @return Le volume actuel
    */
    public static int getVolume() {
        return (int)volume;
    }
}
