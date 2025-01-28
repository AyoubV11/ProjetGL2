package com.menu;

import java.util.Stack;

public class Grille {
    protected int ligne;   // Nombre de lignes de la grille
    protected int colonne;   // Nombre de colonnes de la grille
    protected Case[][] cases;   // Cases de la grille
    protected Stack<Action> pileUndo;   // Pile des actions effectuées
    protected Stack<Action> pileRedo;   // Pile des actions annulées

    public Grille(int ligne, int colonne) {
        this.ligne = ligne * 2 + 1;
        this.colonne = colonne * 2 + 1;
        this.cases = new Case[this.ligne][this.colonne];

        for(int i = 0; i < this.ligne; i++){
            for(int j = 0; j < this.colonne; j++){
                if(i%2 == 0 && j%2 == 0) this.cases[i][j] = new Point(i,j,this);
                else if(i%2 == 0 || (i%2 == 1 && j%2 == 0)) this.cases[i][j] = new Arete(i,j,this,EnumEtat.VIDE);
                else this.cases[i][j] = new Chiffre(i,j,this,-1);
            }
        }

        this.pileUndo = new Stack<Action>();
        this.pileRedo = new Stack<Action>();
    }

    public int getligne() {
        return this.ligne;
    }

    public int getcolonne() {
        return this.colonne;
    }

    public Case getCase(int ligne, int colonne) {
        return this.cases[ligne][colonne];
    }

    public void setChiffre(int ligne, int colonne, int chiffre){
        ((Chiffre) this.getCase(ligne*2+1, colonne*2+1)).setChiffre(chiffre);
    }

    public String toString(){
        String chaine = "";
        for(Case[] x : this.cases){
            for(Case y : x){
                chaine += y;
            }
            chaine += "\n";
        }
        return chaine;
    }
}
