
package com.menu;

public class Coordonnee {
    private int ligne, colonne;

    public Coordonnee(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public Coordonnee() {}
    
    public int getLigne() {
        return this.ligne;
    }
    public int getColonne() {
        return this.colonne;
    }

    public boolean equals(Coordonnee coordonnee) {
        return this.ligne == coordonnee.getLigne() && this.colonne == coordonnee.getColonne();
    }


}