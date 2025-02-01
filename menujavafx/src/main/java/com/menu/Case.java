package com.menu;

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
    public int getTraitVoisin(){
        
        int x = this.getLigne();
        int y = this.getColonne();
        int nbArete = 0;
        
        // Si les case sont egale a une arete

        if(((Arete)this.getGrille().getCase(x,y-1)).getEtat() == EnumEtat.TRAIT) nbArete++;
        if(((Arete)this.getGrille().getCase(x-1,y)).getEtat() == EnumEtat.TRAIT) nbArete++;
        if(((Arete)this.getGrille().getCase(x,y+1)).getEtat() == EnumEtat.TRAIT) nbArete++;
        if(((Arete)this.getGrille().getCase(x+1,y)).getEtat() == EnumEtat.TRAIT) nbArete++;
        
        return nbArete;
    }


    public Grille getGrille(){
        return this.grille;
    }

     

}
