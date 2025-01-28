package com.menu;

public abstract class Case {
    protected int coordX;   // Coordonnée X de la case
    protected int coordY;   // Coordonnée Y de la case
    protected Grille grille;   // Grille à laquelle appartient la case

    public Case(int coordX, int coordY, Grille grille) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.grille = grille;
    }

    public int getCoordX() {
        return this.coordX;
    }

    public int getCoordY() {
        return this.coordY;
    }


    public Grille getGrille(){
        return this.grille;
    }

     /**
     * Methode qui renvoie le nombre d'aretes autour du point
     * @return int
     */
    public int getTraitVoisin(){
        
        int x = this.getCoordX();
        int y = this.getCoordY();
        int nbArete = 0;
        
        // Si les case sont egale a une arete

        if(((Arete)this.getGrille().getCase(x,y-1)).getEtat() == EnumEtat.TRAIT) nbArete++;
        if(((Arete)this.getGrille().getCase(x-1,y)).getEtat() == EnumEtat.TRAIT) nbArete++;
        if(((Arete)this.getGrille().getCase(x,y+1)).getEtat() == EnumEtat.TRAIT) nbArete++;
        if(((Arete)this.getGrille().getCase(x+1,y)).getEtat() == EnumEtat.TRAIT) nbArete++;
        
        return nbArete;
    }

}
