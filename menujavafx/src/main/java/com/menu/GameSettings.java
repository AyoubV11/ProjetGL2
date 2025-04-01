package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe singleton qui conserve les paramètres globaux du jeu.
 * Cette classe permet de gérer les paramètres communs qui s'appliquent
 * à l'ensemble de l'application.
 */
public class GameSettings {
    private static final Logger logger = LoggerFactory.getLogger(GameSettings.class);
    private static GameSettings instance;
    
    private boolean autoCroix = false; // Placement automatique des croix
    private boolean showTimer = true;  // Affichage du chronomètre
    private int volume = 50;          // Volume (0-100)
    
    /**
     * Constructeur privé pour le singleton.
     * Empêche l'instanciation directe de cette classe.
     */
    private GameSettings() {
        logger.debug("Initialisation des paramètres de jeu par défaut");
    }
    
    /**
     * Obtenir l'instance unique des paramètres.
     * 
     * @return L'instance unique de GameSettings
     */
    public static GameSettings getInstance() {
        if (instance == null) {
            logger.info("Création de l'instance GameSettings");
            instance = new GameSettings();
        }
        logger.trace("Accès à l'instance GameSettings");
        return instance;
    }
    
    /**
     * Vérifie si le placement automatique des croix est activé.
     * 
     * @return true si le placement automatique est activé, false sinon
     */
    public boolean isAutoCroix() {
        logger.trace("Consultation du paramètre autoCroix: {}", autoCroix);
        return autoCroix;
    }
    
    /**
     * Définit si le placement automatique des croix est activé.
     * 
     * @param autoCroix true pour activer le placement automatique, false sinon
     */
    public void setAutoCroix(boolean autoCroix) {
        logger.debug("Modification du paramètre autoCroix: {} -> {}", this.autoCroix, autoCroix);
        this.autoCroix = autoCroix;
    }
    
    /**
     * Vérifie si l'affichage du chronomètre est activé.
     * 
     * @return true si l'affichage du chronomètre est activé, false sinon
     */
    public boolean isShowTimer() {
        logger.trace("Consultation du paramètre showTimer: {}", showTimer);
        return showTimer;
    }
    
    /**
     * Définit si l'affichage du chronomètre est activé.
     * 
     * @param showTimer true pour activer l'affichage du chronomètre, false sinon
     */
    public void setShowTimer(boolean showTimer) {
        logger.debug("Modification du paramètre showTimer: {} -> {}", this.showTimer, showTimer);
        this.showTimer = showTimer;
    }
    
    /**
     * Obtient le niveau de volume actuel.
     * 
     * @return Le niveau de volume (entre 0 et 100)
     */
    public int getVolume() {
        logger.trace("Consultation du paramètre volume: {}", volume);
        return SoundPlayer.getVolume();
    }
    
    /**
     * Définit le niveau de volume.
     * La valeur sera ignorée si elle n'est pas dans l'intervalle [0-100].
     * 
     * @param volume Niveau de volume (doit être entre 0 et 100)
     */
    public void setVolume(int volume) {
        logger.debug("Tentative de modification du volume: {} -> {}", this.volume, volume);
        if (volume >= 0 && volume <= 100) {
            SoundPlayer.setVolume(volume);
            logger.info("Volume modifié à {}", volume);
        } else {
            logger.warn("Tentative de définir un volume invalide: {}. La valeur doit être entre 0 et 100", volume);
        }
    }

    
}