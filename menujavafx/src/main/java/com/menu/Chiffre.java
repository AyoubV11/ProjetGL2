package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Représente une case de chiffre dans la grille du jeu Slitherlink.
 * Un chiffre indique le nombre d'arêtes qui doivent être dessinées autour de lui.
 */
public class Chiffre extends Case {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(Chiffre.class);
    
    /**
     * Valeur du chiffre. 
     * Une valeur négative indique une case vide.
     */
    protected int chiffre;   

    /**
     * Constructeur de Chiffre avec un chiffre spécifique.
     * 
     * @param ligne La ligne de la case dans la grille
     * @param colonne La colonne de la case dans la grille
     * @param grille La grille à laquelle la case appartient
     * @param chiffre La valeur du chiffre (-1 pour une case vide)
     */
    public Chiffre(int ligne, int colonne, Grille grille, int chiffre) {
        super(ligne, colonne, grille);
        this.chiffre = chiffre;
        logger.debug("Création d'un chiffre de valeur {} à la position ({}, {})", chiffre, ligne, colonne);
    }

    /**
     * Constructeur de Chiffre sans chiffre (case vide par défaut).
     * 
     * @param ligne La ligne de la case dans la grille
     * @param colonne La colonne de la case dans la grille
     * @param grille La grille à laquelle la case appartient
     */
    public Chiffre(int ligne, int colonne, Grille grille) {
        this(ligne, colonne, grille, -1);
        logger.debug("Création d'un chiffre vide à la position ({}, {})", ligne, colonne);
    }

    /**
     * Récupère la valeur du chiffre.
     * 
     * @return La valeur du chiffre
     */
    public int getChiffre() {
        return this.chiffre;
    }

    /**
     * Définit la valeur du chiffre.
     * 
     * @param chiffre La nouvelle valeur du chiffre
     */
    public void setChiffre(int chiffre) {
        logger.debug("Modification du chiffre à la position ({}, {}): {} -> {}", 
                   this.ligne, this.colonne, this.chiffre, chiffre);
        this.chiffre = chiffre;
    }

    /**
     * Vérifie si la case est vide.
     * 
     * @return true si la case est vide (chiffre invalide), false sinon
     */
    public boolean estVide() {
        boolean vide = this.chiffre < 0 || this.chiffre > 3;
        logger.trace("Vérification si le chiffre ({}, {}) est vide: {}", this.ligne, this.colonne, vide);
        return vide;
    }

    /**
     * Vérifie si le nombre d'arêtes voisines correspond au chiffre.
     * 
     * @return true si le nombre d'arêtes voisines est égal au chiffre, false sinon
     */
    public boolean matchNbAretesVoisines(){
        int nbAretes = this.getNbAretesVoisines();
        boolean match = nbAretes == this.chiffre;
        logger.trace("Vérification si le nombre d'arêtes ({}) correspond au chiffre {} à la position ({}, {}): {}", 
                   nbAretes, this.chiffre, this.ligne, this.colonne, match);
        return match;
    }
    
    /**
     * Convertit le chiffre en représentation textuelle.
     * 
     * @return Une représentation textuelle du chiffre
     */
    public String toString(){
        if(this.chiffre == -1) return "   ";
        else return " " + this.chiffre + " ";
    }

    /**
     * Récupère le chiffre situé à gauche de ce chiffre.
     * 
     * @return Le chiffre à gauche, ou un chiffre vide si hors grille
     */
    public Chiffre getChiffreGauche() {
        logger.trace("Recherche du chiffre à gauche de ({}, {})", this.ligne, this.colonne);
        try {
            Chiffre chiffre = (Chiffre)grille.getCase(ligne, colonne-2);
            logger.trace("Chiffre à gauche trouvé: ({}, {}) avec valeur {}", 
                       chiffre.getLigne(), chiffre.getColonne(), chiffre.getChiffre());
            return chiffre;
        }
        catch (IndexOutOfBoundsException e) {
            logger.trace("Aucun chiffre à gauche (hors limites), création d'un chiffre vide");
            return new Chiffre(-1, -1, grille);
        }
    }

    /**
     * Récupère le chiffre situé au-dessus de ce chiffre.
     * 
     * @return Le chiffre au-dessus, ou un chiffre vide si hors grille
     */
    public Chiffre getChiffreHaut() {
        logger.trace("Recherche du chiffre au-dessus de ({}, {})", this.ligne, this.colonne);
        try {
            Chiffre chiffre = (Chiffre)grille.getCase(ligne-2, colonne);
            logger.trace("Chiffre au-dessus trouvé: ({}, {}) avec valeur {}", 
                       chiffre.getLigne(), chiffre.getColonne(), chiffre.getChiffre());
            return chiffre;
        }
        catch (IndexOutOfBoundsException e) {
            logger.trace("Aucun chiffre au-dessus (hors limites), création d'un chiffre vide");
            return new Chiffre(-1, -1, grille);
        }
    }

    /**
     * Récupère le chiffre situé à droite de ce chiffre.
     * 
     * @return Le chiffre à droite, ou un chiffre vide si hors grille
     */
    public Chiffre getChiffreDroit() {
        logger.trace("Recherche du chiffre à droite de ({}, {})", this.ligne, this.colonne);
        try {
            Chiffre chiffre = (Chiffre)grille.getCase(ligne, colonne+2);
            logger.trace("Chiffre à droite trouvé: ({}, {}) avec valeur {}", 
                       chiffre.getLigne(), chiffre.getColonne(), chiffre.getChiffre());
            return chiffre;
        }
        catch (IndexOutOfBoundsException e) {
            logger.trace("Aucun chiffre à droite (hors limites), création d'un chiffre vide");
            return new Chiffre(-1, -1, grille);
        }
    }

    /**
     * Récupère le chiffre situé en-dessous de ce chiffre.
     * 
     * @return Le chiffre en-dessous, ou un chiffre vide si hors grille
     */
    public Chiffre getChiffreBas() {
        logger.trace("Recherche du chiffre en-dessous de ({}, {})", this.ligne, this.colonne);
        try {
            Chiffre chiffre = (Chiffre)grille.getCase(ligne+2, colonne);
            logger.trace("Chiffre en-dessous trouvé: ({}, {}) avec valeur {}", 
                       chiffre.getLigne(), chiffre.getColonne(), chiffre.getChiffre());
            return chiffre;
        }
        catch (IndexOutOfBoundsException e) {
            logger.trace("Aucun chiffre en-dessous (hors limites), création d'un chiffre vide");
            return new Chiffre(-1, -1, grille);
        }
    }

    /**
     * Récupère le chiffre en diagonale haut-gauche de ce chiffre.
     * 
     * @return Le chiffre en diagonale haut-gauche, ou un chiffre vide si hors grille
     */
    public Chiffre getChiffreHautGauche() {
        logger.trace("Recherche du chiffre en diagonale haut-gauche de ({}, {})", this.ligne, this.colonne);
        return getChiffreHaut().getChiffreGauche();
    }

    /**
     * Récupère le chiffre en diagonale haut-droite de ce chiffre.
     * 
     * @return Le chiffre en diagonale haut-droite, ou un chiffre vide si hors grille
     */
    public Chiffre getChiffreHautDroit() {
        logger.trace("Recherche du chiffre en diagonale haut-droite de ({}, {})", this.ligne, this.colonne);
        return getChiffreHaut().getChiffreDroit();
    }

    /**
     * Récupère le chiffre en diagonale bas-gauche de ce chiffre.
     * 
     * @return Le chiffre en diagonale bas-gauche, ou un chiffre vide si hors grille
     */
    public Chiffre getChiffreBasGauche() {
        logger.trace("Recherche du chiffre en diagonale bas-gauche de ({}, {})", this.ligne, this.colonne);
        return getChiffreBas().getChiffreGauche();
    }

    /**
     * Récupère le chiffre en diagonale bas-droite de ce chiffre.
     * 
     * @return Le chiffre en diagonale bas-droite, ou un chiffre vide si hors grille
     */
    public Chiffre getChiffreBasDroit() {
        logger.trace("Recherche du chiffre en diagonale bas-droite de ({}, {})", this.ligne, this.colonne);
        return getChiffreBas().getChiffreDroit();
    }
}