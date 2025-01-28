package com.menu;

public class Point extends Case{

    public Point(int coordX, int coordY, Grille grille) {
        super(coordX, coordY, grille);
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
