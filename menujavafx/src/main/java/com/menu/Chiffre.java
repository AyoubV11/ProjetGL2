package com.menu;

public class Chiffre extends Case {
    protected int chiffre;   // Chiffre de la case

    public Chiffre(int ligne, int colonne, Grille grille, int chiffre) {
        super(ligne, colonne, grille);
        this.chiffre = chiffre;
    }

    public Chiffre(int ligne, int colonne, Grille grille) {
        this(ligne, colonne, grille, -1);
    }

    public int getChiffre() {
        return this.chiffre;
    }

    public void setChiffre(int chiffre) {
        this.chiffre = chiffre;
    }

    public boolean estVide() {
        return this.chiffre < 0 || this.chiffre > 3;
    }


    /**
     * Methode qui verifie si le nombre d'aretes est bien egal au chiffre
     * @return
     */
    public boolean matchNbAretesVoisines(){
        return this.chiffre==this.getNbAretesVoisines();
    }
    
    public String toString(){
        if(this.chiffre == -1) return "   ";
        else return " " + this.chiffre + " ";
    }

    

}
