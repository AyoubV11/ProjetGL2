package com.menu;

public class Point extends Case{

    public Point(int ligne, int colonne, Grille grille) {
        super(ligne, colonne, grille);
    }

    
    /**
     * Methode qui verifie si un point a bien deux aretes
     * @return
     */
    public boolean matchNbAretesVoisines(){
        return this.getNbAretesVoisines() == 2 || this.getNbAretesVoisines() == 0;
    }

    public String toString(){
        return " · ";
    }
}
