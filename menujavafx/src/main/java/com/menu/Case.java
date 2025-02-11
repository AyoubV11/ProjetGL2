package com.menu;

import java.util.ArrayList;
import java.util.List;

public abstract class Case {
    protected int ligne;   // Coordonnée X de la case
    protected int colonne;   // Coordonnée Y de la case
    protected Grille grille;   // Grille à laquelle appartient la case

    public Case(int ligne, int colonne, Grille grille) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.grille = grille;
    }

    public int getLigne() {
        return this.ligne;
    }

    public int getColonne() {
        return this.colonne;
    }
    
    /**
     * Methode qui renvoie le nombre d'aretes autour de la case
     * @return int
     */
    public int getNbAretesVoisines(){
        return this.getAretesVoisines().size();
    }

    public List<Arete> getAretesVoisines() {
        ArrayList<Arete> aretesVoisines = new ArrayList<Arete>();

        int x = this.getLigne();
        int y = this.getColonne();

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


    public Grille getGrille(){
        return this.grille;
    }

     

}
