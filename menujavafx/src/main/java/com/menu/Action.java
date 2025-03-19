package com.menu;

/**
 * Représente une action dans le jeu Slitherlink, avec ses coordonnées et ses états.
 * Cette classe stocke les informations relatives à une action spécifique sur la grille de jeu.
 */
public class Action {
    protected int ligne;   // Coordonnée X de l'action
    protected int colonne;   // Coordonnée Y de l'action
    protected EnumEtat etat;   // Etat de l'action
    protected EnumEtat etatPrecedent;   // Etat précédent de l'action

    public Action() {}

    public Action(int ligne, int colonne, EnumEtat etat, EnumEtat etatPrecedent) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.etat = etat;
        this.etatPrecedent = etatPrecedent;
    }

    public int getLigne() {
        return this.ligne;
    }

    public int getColonne() {
        return this.colonne;
    }

    public EnumEtat getEtat() {
        return this.etat;
    }

    public EnumEtat getEtatPrecedent() {
        return this.etatPrecedent;
    }
}