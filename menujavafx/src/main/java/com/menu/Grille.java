package com.menu;

import java.util.Stack;

public class Grille {
    protected int dimX;   // Dimension X de la grille
    protected int dimY;   // Dimension Y de la grille
    protected Case[][] cases;   // Cases de la grille
    protected Stack<Action> pileUndo;   // Pile des actions effectuées
    protected Stack<Action> pileRedo;   // Pile des actions annulées

    public Grille(int dimX, int dimY) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.cases = new Case[dimX][dimY];
        this.pileUndo = new Stack<Action>();
        this.pileRedo = new Stack<Action>();
    }

    public int getDimX() {
        return this.dimX;
    }

    public int getDimY() {
        return this.dimY;
    }

    public Case getCase(int coordX, int coordY) {
        return this.cases[coordX][coordY];
    }

}
