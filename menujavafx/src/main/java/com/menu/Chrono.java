package com.menu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe représentant un chronomètre pour suivre le temps de jeu.
 * Ce chronomètre affiche le temps écoulé dans un format heures:minutes:secondes
 * et permet de démarrer, arrêter et réinitialiser le temps.
 */
public class Chrono {
    private static final Logger logger = LoggerFactory.getLogger(Chrono.class);
    
    /** Nombre de secondes écoulées depuis le démarrage du chronomètre */
    private int seconds;
    
    /** Timeline JavaFX utilisée pour l'incrémentation du temps */
    private Timeline timeline;
    
    /** Label dans lequel le temps est affiché */
    private Label timeLabel;

    /**
     * Constructeur du chronomètre.
     * Initialise le chronomètre avec un label spécifié et configure la timeline.
     * 
     * @param label Le label dans lequel le temps sera affiché
     */
    public Chrono(Label label) {
        this.timeLabel = label;
        this.seconds = 0;
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
            timeLabel.setText("TEMPS : " + formatTime(seconds));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        logger.debug("Chronomètre initialisé");
    }

    /**
     * Démarre le chronomètre.
     * Lance le décompte du temps et sa mise à jour dans le label.
     */
    public void start() {
        logger.info("Démarrage du chronomètre");
        timeline.play();
    }

    /**
     * Arrête le chronomètre.
     * Suspend le décompte du temps sans réinitialiser la valeur.
     */
    public void stop() {
        logger.info("Arrêt du chronomètre à {}", formatTime(seconds));
        timeline.stop();
    }

    /**
     * Réinitialise le chronomètre.
     * Remet le compteur à zéro et redémarre le chronomètre.
     */
    public void reset() {
        logger.info("Réinitialisation du chronomètre");
        timeline.stop();
        seconds = 0;
        timeLabel.setText("TEMPS : 00:00:00");
        timeline.play();
    }

    /**
     * Récupère le temps actuel sous forme de texte formaté.
     * 
     * @return Le temps actuel sous forme de chaîne de caractères
     */
    public String getTemps() {
        logger.trace("Récupération du temps: {}", timeLabel.getText());
        return timeLabel.getText(); 
    }
    
    /**
     * Formate le nombre de secondes en une chaîne "heures:minutes:secondes".
     * 
     * @param totalSeconds Le nombre total de secondes à formater
     * @return Une chaîne formatée au format hh:mm:ss
     */
    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, secs);
        logger.trace("Formatage du temps {} secondes en {}", totalSeconds, formattedTime);
        return formattedTime;
    }
}