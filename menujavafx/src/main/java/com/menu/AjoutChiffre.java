package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe représentant l'ajout d'un chiffre dans une grille.
 * Cette classe permet de stocker les coordonnées (ligne, colonne) 
 * et la valeur du chiffre à ajouter.
 */
class AjoutChiffre {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(AjoutChiffre.class);
    
    /** La ligne où le chiffre sera ajouté */
    private int ligne;
    
    /** La colonne où le chiffre sera ajouté */
    private int colonne;
    
    /** La valeur du chiffre à ajouter */
    private int chiffre;
    
    /**
     * Constructeur par défaut.
     */
    public AjoutChiffre() {
        logger.debug("Création d'un nouvel AjoutChiffre vide");
    }
    
    /**
     * Constructeur avec paramètres.
     * 
     * @param ligne le numéro de ligne
     * @param colonne le numéro de colonne
     * @param chiffre la valeur du chiffre
     */
    public AjoutChiffre(int ligne, int colonne, int chiffre) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.chiffre = chiffre;
        logger.debug("Création d'un AjoutChiffre à la position ({}, {}) avec la valeur {}", ligne, colonne, chiffre);
    }

    /**
     * Retourne le numéro de ligne.
     * 
     * @return le numéro de ligne
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * Définit le numéro de ligne.
     * 
     * @param ligne le numéro de ligne à définir
     */
    public void setLigne(int ligne) {
        logger.trace("Mise à jour de la ligne: {} -> {}", this.ligne, ligne);
        this.ligne = ligne;
    }

    /**
     * Retourne le numéro de colonne.
     * 
     * @return le numéro de colonne
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Définit le numéro de colonne.
     * 
     * @param colonne le numéro de colonne à définir
     */
    public void setColonne(int colonne) {
        logger.trace("Mise à jour de la colonne: {} -> {}", this.colonne, colonne);
        this.colonne = colonne;
    }

    /**
     * Retourne la valeur du chiffre.
     * 
     * @return la valeur du chiffre
     */
    public int getChiffre() {
        return chiffre;
    }

    /**
     * Définit la valeur du chiffre.
     * 
     * @param chiffre la valeur du chiffre à définir
     */
    public void setChiffre(int chiffre) {
        logger.trace("Mise à jour du chiffre: {} -> {}", this.chiffre, chiffre);
        this.chiffre = chiffre;
    }
}