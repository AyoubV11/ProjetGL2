package com.menu;

public abstract class Case {
    protected int ligne;   // Coordonnée X de la case
    protected int colonne;   // Coordonnée Y de la case
    protected Grille grille;   // Grille à laquelle appartient la case

    public Case(int ligne, int colonne, Grille grille) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.grille = grille;
    }

    public int getLigne() {
        return this.ligne;
    }

    public int getColonne() {
        return this.colonne;
    }


    public Grille getGrille(){
        return this.grille;
    }

     

}
