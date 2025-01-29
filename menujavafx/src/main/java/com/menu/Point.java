package com.menu;

public class Point extends Case{

    public Point(int ligne, int colonne, Grille grille) {
        super(ligne, colonne, grille);
    }


    /**
     * Methode qui renvoie le nombre d'aretes autour du point
     * @return int
     */
    public int getAreteNeighbors(){
        /* 
         * FAIRE
        */
        return 0;
    }
    public String toString(){
        return " · ";
    }
}
