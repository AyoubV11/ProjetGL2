package com.menu;

public class Action {
    protected int coordX;   // Coordonnée X de l'action
    protected int coordY;   // Coordonnée Y de l'action
    protected EnumEtat etat;   // Etat de l'action
    protected EnumEtat etatPrecedent;   // Etat précédent de l'action

    public Action(int coordX, int coordY, EnumEtat etat, EnumEtat etatPrecedent) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.etat = etat;
        this.etatPrecedent = etatPrecedent;
    }

    public int getCoordX() {
        return this.coordX;
    }
    public int getCoordY() {
        return this.coordY;
    }
    public EnumEtat getEtat() {
        return this.etat;
    }
    public EnumEtat getEtatPrecedent() {
        return this.etatPrecedent;
    }
}
