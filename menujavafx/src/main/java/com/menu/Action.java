package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Représente une action dans le jeu Slitherlink, avec ses coordonnées et ses états.
 * Cette classe stocke les informations relatives à une action spécifique sur la grille de jeu.
 */
public class Action {
    private static final Logger logger = LoggerFactory.getLogger(Action.class);
    
    // Coordonnées de l'action
    protected int ligne; 
    // Coordonnée Y de l'action
    protected int colonne; 
    // Etat de l'action  
    protected EnumEtat etat;  
    // Etat précédent de l'action
    protected EnumEtat etatPrecedent;   

    /**
     * Constructeur par défaut.
     * Initialise une action sans valeurs spécifiques.
     */
    public Action() {
        logger.debug("Création d'une action vide");
    }

    /**
     * Constructeur avec paramètres.
     * Initialise une action avec des coordonnées et des états spécifiés.
     *
     * @param ligne         La coordonnée de ligne de l'action
     * @param colonne       La coordonnée de colonne de l'action
     * @param etat          L'état actuel de l'action
     * @param etatPrecedent L'état précédent de l'action
     */
    public Action(int ligne, int colonne, EnumEtat etat, EnumEtat etatPrecedent) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.etat = etat;
        this.etatPrecedent = etatPrecedent;
        logger.debug("Création d'une action à la position ({}, {}) avec l'état {} et l'état précédent {}", 
                    ligne, colonne, etat, etatPrecedent);
    }

    /**
     * Retourne la coordonnée de ligne de l'action.
     *
     * @return La valeur de la ligne
     */
    public int getLigne() {
        return this.ligne;
    }

    /**
     * Retourne la coordonnée de colonne de l'action.
     *
     * @return La valeur de la colonne
     */
    public int getColonne() {
        return this.colonne;
    }

    /**
     * Retourne l'état actuel de l'action.
     *
     * @return L'état actuel
     */
    public EnumEtat getEtat() {
        return this.etat;
    }

    /**
     * Retourne l'état précédent de l'action.
     *
     * @return L'état précédent
     */
    public EnumEtat getEtatPrecedent() {
        return this.etatPrecedent;
    }
}