package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Représente un point dans la grille du jeu.
 * Un point est une case spéciale qui doit avoir exactement 0 ou 2 arêtes voisines
 * pour être valide.
 */
public class Point extends Case {
    
    private static final Logger logger = LoggerFactory.getLogger(Point.class);
    
    /**
     * Constructeur de la classe Point.
     * 
     * @param ligne la position en ligne du point dans la grille
     * @param colonne la position en colonne du point dans la grille
     * @param grille la grille à laquelle appartient ce point
     */
    public Point(int ligne, int colonne, Grille grille) {
        super(ligne, colonne, grille);
        logger.debug("Point créé à la position [{},{}]", ligne, colonne);
    }

    /**
     * Vérifie si ce point respecte la règle du jeu, c'est-à-dire
     * s'il a exactement 0 ou 2 arêtes voisines.
     * 
     * @return true si le point a 0 ou 2 arêtes voisines, false sinon
     */
    public boolean matchNbAretesVoisines() {
        int nbAretes = this.getNbAretesVoisines();
        boolean result = nbAretes == 2 || nbAretes == 0;
        logger.debug("Vérification nombre d'arêtes pour le point [{},{}]: {} arêtes, résultat: {}", 
                     this.getLigne(), this.getColonne(), nbAretes, result);
        return result;
    }

    /**
     * Représentation textuelle d'un point.
     * 
     * @return une chaîne de caractères représentant un point
     */
    @Override
    public String toString() {
        return " · ";
    }
}