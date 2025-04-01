package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe représentant les informations de temps pour une partie sauvegardée.
 * Permet de stocker le temps courant de la partie ainsi que le meilleur temps réalisé.
 */
public class TempsSauvegarde {
    
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(TempsSauvegarde.class);
    
    /** Temps courant de la partie */
    private int temps;
    
    /** Meilleur temps réalisé */
    private int meilleurTemps;

    /**
     * Constructeur avec paramètres pour initialiser les temps.
     * 
     * @param temps Le temps courant de la partie
     * @param meilleurTemps Le meilleur temps réalisé
     */
    public TempsSauvegarde(int temps, int meilleurTemps){
        this.temps = temps;
        this.meilleurTemps = meilleurTemps;
        logger.debug("Création d'un TempsSauvegarde avec temps={}, meilleurTemps={}", temps, meilleurTemps);
    }

    /**
     * Constructeur par défaut.
     * Initialise les temps à leurs valeurs par défaut (0).
     */
    public TempsSauvegarde(){
        logger.debug("Création d'un TempsSauvegarde avec valeurs par défaut");
    }

    /**
     * Récupère le temps courant de la partie.
     * 
     * @return Le temps courant
     */
    public int getTemps() {
        logger.trace("Récupération du temps: {}", temps);
        return temps;
    }

    /**
     * Récupère le meilleur temps réalisé.
     * 
     * @return Le meilleur temps
     */
    public int getMeilleurTemps() {
        logger.trace("Récupération du meilleur temps: {}", meilleurTemps);
        return meilleurTemps;
    }

    /**
     * Incrémente le temps courant de la partie d'une unité.
     */
    public void incrementerTemps(){
        temps++;
        logger.debug("Incrémentation du temps à {}", temps);
    }

    /**
     * Définit le temps courant de la partie.
     * 
     * @param temps Le nouveau temps courant
     */
    public void setTemps(int temps) {
        logger.debug("Modification du temps: {} -> {}", this.temps, temps);
        this.temps = temps;
    }

    /**
     * Définit le meilleur temps réalisé.
     * 
     * @param meilleurTemps Le nouveau meilleur temps
     */
    public void setMeilleurTemps(int meilleurTemps) {
        logger.debug("Modification du meilleur temps: {} -> {}", this.meilleurTemps, meilleurTemps);
        this.meilleurTemps = meilleurTemps;
    }
}