package com.menu;

public class Chiffre extends Case {
    protected int chiffre;   // Chiffre de la case
    protected boolean estVide;   // Indique si la case est vide

    public Chiffre(int coordX, int coordY, Grille grille, int chiffre) {
        super(coordX, coordY, grille);
        this.chiffre = chiffre;
        this.estVide = false;
    }

}
