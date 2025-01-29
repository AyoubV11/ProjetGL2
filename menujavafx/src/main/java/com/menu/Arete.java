package com.menu;

public class Arete extends Case {
    protected EnumEtat etat;   // Etat de l'arête

    public Arete(int ligne, int colonne, Grille grille, EnumEtat etat) {
        super(ligne, colonne, grille);
        this.etat = etat;
    }

    public EnumEtat getEtat() {
        return this.etat;
    }

    public void setCroix() {
        this.etat = EnumEtat.CROIX;
    }

    public void setTrait() {
        this.etat = EnumEtat.TRAIT;
    }

    public void setVide() {
        this.etat = EnumEtat.VIDE;
    }

    public void setEtat(EnumEtat etat){
        this.etat = etat;
    }
    
    public String toString(){
        if(this.etat == EnumEtat.VIDE) return " - ";
        else if(this.etat == EnumEtat.TRAIT) return " | ";
        else return " x ";
    }
}
