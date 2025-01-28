package com.menu;

public class Chiffre extends Case {
    protected int chiffre;   // Chiffre de la case
    protected boolean estVide;   // Indique si la case est vide

    public Chiffre(int ligne, int colonne, Grille grille, int chiffre) {
        super(ligne, colonne, grille);
        this.chiffre = chiffre;
        this.estVide = false;
    }

    public int getChiffre() {
        return this.chiffre;
    }

    public void setChiffre(int chiffre) {
        this.chiffre = chiffre;
    }

    public String toString(){
        if(this.chiffre == -1) return "   ";
        else return " " + this.chiffre + " ";
    }

}
