package com.menu;

/**
 * Représente une case de chiffre dans la grille du jeu Slitherlink.
 * Un chiffre indique le nombre d'arêtes qui doivent être dessinées autour de lui.
 */
public class Chiffre extends Case {
    /**
     * Valeur du chiffre. 
     * Une valeur négative indique une case vide.
     */
    protected int chiffre;   

    /**
     * Constructeur de Chiffre avec un chiffre spécifique.
     * 
     * @param ligne La ligne de la case dans la grille
     * @param colonne La colonne de la case dans la grille
     * @param grille La grille à laquelle la case appartient
     * @param chiffre La valeur du chiffre (-1 pour une case vide)
     */
    public Chiffre(int ligne, int colonne, Grille grille, int chiffre) {
        super(ligne, colonne, grille);
        this.chiffre = chiffre;
    }

    /**
     * Constructeur de Chiffre sans chiffre (case vide par défaut).
     * 
     * @param ligne La ligne de la case dans la grille
     * @param colonne La colonne de la case dans la grille
     * @param grille La grille à laquelle la case appartient
     */
    public Chiffre(int ligne, int colonne, Grille grille) {
        this(ligne, colonne, grille, -1);
    }

    /**
     * Récupère la valeur du chiffre.
     * 
     * @return La valeur du chiffre
     */
    public int getChiffre() {
        return this.chiffre;
    }

    /**
     * Définit la valeur du chiffre.
     * 
     * @param chiffre La nouvelle valeur du chiffre
     */
    public void setChiffre(int chiffre) {
        this.chiffre = chiffre;
    }

    /**
     * Vérifie si la case est vide.
     * 
     * @return true si la case est vide (chiffre invalide), false sinon
     */
    public boolean estVide() {
        return this.chiffre < 0 || this.chiffre > 3;
    }

    /**
     * Vérifie si le nombre d'arêtes voisines correspond au chiffre.
     * 
     * @return true si le nombre d'arêtes voisines est égal au chiffre, false sinon
     */
    public boolean matchNbAretesVoisines(){
        return this.getNbAretesVoisines() == this.chiffre;
    }
    
    /**
     * Convertit le chiffre en représentation textuelle.
     * 
     * @return Une représentation textuelle du chiffre
     */
    public String toString(){
        if(this.chiffre == -1) return "   ";
        else return " " + this.chiffre + " ";
    }
}