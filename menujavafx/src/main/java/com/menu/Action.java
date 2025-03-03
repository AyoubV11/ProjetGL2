package com.menu;

/**
 * Représente une action dans le jeu Slitherlink, avec ses coordonnées et ses états.
 * Cette classe stocke les informations relatives à une action spécifique sur la grille de jeu.
 */
public class Action {
    /**
     * Coordonnée X de l'action sur la grille.
     */
    protected int coordX;   

    /**
     * Coordonnée Y de l'action sur la grille.
     */
    protected int coordY;   

    /**
     * État actuel de l'action.
     */
    protected EnumEtat etat;   

    /**
     * État précédent de l'action.
     */
    protected EnumEtat etatPrecedent;   

    /**
     * Constructeur de la classe Action.
     * 
     * @param coordX La coordonnée X de l'action
     * @param coordY La coordonnée Y de l'action
     * @param etat L'état actuel de l'action
     * @param etatPrecedent L'état précédent de l'action
     */
    public Action(int coordX, int coordY, EnumEtat etat, EnumEtat etatPrecedent) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.etat = etat;
        this.etatPrecedent = etatPrecedent;
    }

    /**
     * Récupère la coordonnée X de l'action.
     * 
     * @return La coordonnée X
     */
    public int getCoordX() {
        return this.coordX;
    }

    /**
     * Récupère la coordonnée Y de l'action.
     * 
     * @return La coordonnée Y
     */
    public int getCoordY() {
        return this.coordY;
    }

    /**
     * Récupère l'état actuel de l'action.
     * 
     * @return L'état actuel
     */
    public EnumEtat getEtat() {
        return this.etat;
    }

    /**
     * Récupère l'état précédent de l'action.
     * 
     * @return L'état précédent
     */
    public EnumEtat getEtatPrecedent() {
        return this.etatPrecedent;
    }
}