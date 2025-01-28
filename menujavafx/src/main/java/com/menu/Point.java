package com.menu;

public class Point extends Case{

    public Point(int coordX, int coordY, Grille grille) {
        super(coordX, coordY, grille);
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
