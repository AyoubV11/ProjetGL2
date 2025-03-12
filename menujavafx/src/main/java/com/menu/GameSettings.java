package com.menu;

/**
 * Classe singleton qui conserve les paramètres globaux du jeu.
 */
public class GameSettings {
    private static GameSettings instance;
    
    private boolean autoCroix = false; // Placement automatique des croix
    private boolean showTimer = true;  // Affichage du chronomètre
    private int volume = 50;          // Volume (0-100)
    
    // Constructeur privé pour le singleton
    private GameSettings() {
    }
    
    /**
     * Obtenir l'instance unique des paramètres
     */
    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }
    
    // Getters et setters
    public boolean isAutoCroix() {
        return autoCroix;
    }
    
    public void setAutoCroix(boolean autoCroix) {
        this.autoCroix = autoCroix;
    }
    
    public boolean isShowTimer() {
        return showTimer;
    }
    
    public void setShowTimer(boolean showTimer) {
        this.showTimer = showTimer;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public void setVolume(int volume) {
        if (volume >= 0 && volume <= 100) {
            this.volume = volume;
        }
    }
}