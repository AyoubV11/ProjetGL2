package com.menu;

public class Point extends Case{

    public Point(int coordX, int coordY, Grille grille) {
        super(coordX, coordY, grille);
    }

    public String toString(){
        return " · ";
    }
}
