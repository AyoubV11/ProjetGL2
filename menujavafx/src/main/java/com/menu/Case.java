package com.menu;

public abstract class Case {
    protected int coordX;   // Coordonnée X de la case
    protected int coordY;   // Coordonnée Y de la case
    protected Grille grille;   // Grille à laquelle appartient la case

    public Case(int coordX, int coordY, Grille grille) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.grille = grille;
    }

    public int getCoordX() {
        return this.coordX;
    }

    public int getCoordY() {
        return this.coordY;
    }


    public Grille getGrille(){
        return this.grille;
    }

     

}
