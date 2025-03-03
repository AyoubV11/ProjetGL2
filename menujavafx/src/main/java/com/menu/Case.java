package com.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant une case générique dans la grille du jeu Slitherlink.
 * Fournit des fonctionnalités de base pour les différents types de cases 
 * (comme les points, les chiffres et les arêtes) dans la grille de jeu.
 */
public abstract class Case {
    /**
     * Coordonnée de ligne de la case dans la grille.
     */
    protected int ligne;   

    /**
     * Coordonnée de colonne de la case dans la grille.
     */
    protected int colonne;   

    /**
     * Référence à la grille à laquelle appartient la case.
     */
    protected Grille grille;   

    /**
     * Constructeur de la case.
     * 
     * @param ligne La coordonnée de ligne de la case
     * @param colonne La coordonnée de colonne de la case
     * @param grille La grille à laquelle la case appartient
     */
    public Case(int ligne, int colonne, Grille grille) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.grille = grille;
    }

    /**
     * Récupère la coordonnée de ligne de la case.
     * 
     * @return La coordonnée de ligne
     */
    public int getLigne() {
        return this.ligne;
    }

    /**
     * Récupère la coordonnée de colonne de la case.
     * 
     * @return La coordonnée de colonne
     */
    public int getColonne() {
        return this.colonne;
    }
    
    /**
     * Calcule le nombre d'arêtes voisines de la case.
     * 
     * @return Le nombre d'arêtes voisines
     */
    public int getNbAretesVoisines(){
        return this.getAretesVoisines().size();
    }

    /**
     * Récupère la liste des arêtes voisines de la case.
     * 
     * @return Une liste d'arêtes voisines qui sont dans l'état TRAIT
     */
    public List<Arete> getAretesVoisines() {
        ArrayList<Arete> aretesVoisines = new ArrayList<Arete>();

        int x = this.getLigne();
        int y = this.getColonne();

        // Vérification et ajout des arêtes voisines dans l'état TRAIT
        if (this.grille.caseExiste(x, y - 1) && ((Arete) this.grille.getCase(x, y - 1)).getEtat() == EnumEtat.TRAIT)
            aretesVoisines.add((Arete) this.grille.getCase(x, y - 1));
        if (this.grille.caseExiste(x - 1, y) && ((Arete) this.grille.getCase(x - 1, y)).getEtat() == EnumEtat.TRAIT) 
            aretesVoisines.add((Arete) this.grille.getCase(x - 1, y));
        if (this.grille.caseExiste(x, y + 1) && ((Arete) this.grille.getCase(x, y + 1)).getEtat() == EnumEtat.TRAIT) 
            aretesVoisines.add((Arete) this.grille.getCase(x, y + 1));
        if (this.grille.caseExiste(x + 1, y) && ((Arete) this.grille.getCase(x + 1, y)).getEtat() == EnumEtat.TRAIT) 
            aretesVoisines.add((Arete) this.grille.getCase(x + 1, y));

        return aretesVoisines;
    }

    /**
     * Récupère la grille à laquelle la case appartient.
     * 
     * @return La grille associée à la case
     */
    public Grille getGrille(){
        return this.grille;
    }
}