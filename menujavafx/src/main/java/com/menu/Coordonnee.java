package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe représentant une coordonnée dans la grille du jeu.
 * Permet de stocker et manipuler les positions (ligne, colonne) des éléments du jeu.
 */
public class Coordonnee {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(Coordonnee.class);
    
    /** La ligne de la coordonnée */
    private int ligne;
    
    /** La colonne de la coordonnée */
    private int colonne;

    /**
     * Constructeur avec paramètres.
     * Initialise une coordonnée avec des valeurs spécifiées de ligne et colonne.
     *
     * @param ligne La valeur de la ligne
     * @param colonne La valeur de la colonne
     */
    public Coordonnee(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
        logger.debug("Création d'une coordonnée à la position ({}, {})", ligne, colonne);
    }

    /**
     * Constructeur par défaut.
     * Initialise une coordonnée sans valeurs spécifiques.
     */
    public Coordonnee() {
        logger.debug("Création d'une coordonnée vide");
    }
    
    /**
     * Retourne la valeur de la ligne.
     * 
     * @return La valeur de la ligne
     */
    public int getLigne() {
        return this.ligne;
    }
    
    /**
     * Retourne la valeur de la colonne.
     * 
     * @return La valeur de la colonne
     */
    public int getColonne() {
        return this.colonne;
    }

    /**
     * Compare cette coordonnée avec une autre pour déterminer si elles sont égales.
     * Deux coordonnées sont égales si elles ont les mêmes valeurs de ligne et de colonne.
     * 
     * @param coordonnee La coordonnée à comparer avec celle-ci
     * @return true si les coordonnées sont égales, false sinon
     */
    public boolean equals(Coordonnee coordonnee) {
        boolean result = this.ligne == coordonnee.getLigne() && this.colonne == coordonnee.getColonne();
        logger.trace("Comparaison des coordonnées ({}, {}) et ({}, {}): {}", 
                   this.ligne, this.colonne, coordonnee.getLigne(), coordonnee.getColonne(), result);
        return result;
    }

    /**
     * Surcharge de la méthode equals de Object pour permettre une comparaison correcte.
     * 
     * @param obj L'objet à comparer avec cette coordonnée
     * @return true si les objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return equals((Coordonnee) obj);
    }

    /**
     * Génère un hashCode basé sur les valeurs de ligne et colonne.
     * Nécessaire lorsque equals est surchargé pour maintenir le contrat equals/hashCode.
     * 
     * @return Le hashcode de cet objet
     */
    @Override
    public int hashCode() {
        return 31 * ligne + colonne;
    }

    /**
     * Retourne une représentation textuelle de la coordonnée.
     * 
     * @return Une chaîne représentant la coordonnée au format "(ligne, colonne)"
     */
    @Override
    public String toString() {
        return "(" + ligne + ", " + colonne + ")";
    }
}