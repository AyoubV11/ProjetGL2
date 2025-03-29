package com.menu;

/**
 * Classe représentant l'ajout d'un chiffre dans une grille.
 * Cette classe permet de stocker les coordonnées (ligne, colonne) 
 * et la valeur du chiffre à ajouter.
 */
class AjoutChiffre {
    /** La ligne où le chiffre sera ajouté */
    private int ligne;
    
    /** La colonne où le chiffre sera ajouté */
    private int colonne;
    
    /** La valeur du chiffre à ajouter */
    private int chiffre;

    /**
     * Retourne le numéro de ligne.
     * 
     * @return le numéro de ligne
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * Définit le numéro de ligne.
     * 
     * @param ligne le numéro de ligne à définir
     */
    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    /**
     * Retourne le numéro de colonne.
     * 
     * @return le numéro de colonne
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Définit le numéro de colonne.
     * 
     * @param colonne le numéro de colonne à définir
     */
    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    /**
     * Retourne la valeur du chiffre.
     * 
     * @return la valeur du chiffre
     */
    public int getChiffre() {
        return chiffre;
    }

    /**
     * Définit la valeur du chiffre.
     * 
     * @param chiffre la valeur du chiffre à définir
     */
    public void setChiffre(int chiffre) {
        this.chiffre = chiffre;
    }
}
