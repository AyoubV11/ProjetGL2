package com.menu;

public class Point extends Case{

    public Point(int ligne, int colonne, Grille grille) {
        super(ligne, colonne, grille);
    }

    
    /**
     * Methode qui verifie si un point a bien deux aretes
     * @return
     */
    public boolean VerifPoint(Point p){
        return p.getTraitVoisin() == 2;
    }

    public String toString(){
        return " · ";
    }
}
