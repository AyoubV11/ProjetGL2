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


        if(estValide(x,y-1) && ((Arete)this.getGrille().getCase(x,y-1)).getEtat() == EnumEtat.TRAIT) nbArete++;
        if(estValide(x-1,y) &&((Arete)this.getGrille().getCase(x-1,y)).getEtat() == EnumEtat.TRAIT) nbArete++;
        if(estValide(x,y+1) &&((Arete)this.getGrille().getCase(x,y+1)).getEtat() == EnumEtat.TRAIT) nbArete++;
        if(estValide(x+1,y) &&((Arete)this.getGrille().getCase(x+1,y)).getEtat() == EnumEtat.TRAIT) nbArete++;
        
        return nbArete;
    }

    /**
     * Cette methode permet de verifier si la case est valide
     * @return boolean
     */
    public boolean estValide(int ligne, int colonne) {
        int nbLignes = grille.getNbLignes();
        int nbColonnes = grille.getNbColonnes();
        System.out.println("nb ligne" + nbLignes);
        System.out.println("nb Colonne" + nbColonnes);
        System.out.println("ligne" + ligne);
        System.out.println("colonne" + colonne);
    
        // Vérifier si la position est dans les limites de la grille
        return (ligne >= 0 && ligne < nbLignes && colonne >= 0 && colonne < nbColonnes);
    }


    public Grille getGrille(){
        return this.grille;
    }

     

}
