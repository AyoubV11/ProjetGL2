package com.menu;

public class Point extends Case{

    public Point(int coordX, int coordY, Grille grille) {
        super(coordX, coordY, grille);
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
    /**
     * Methode qui verifie si un point a bien deux aretes
     * @return
     */
    public boolean VerifPoint(Point p){
        return p.getTraitVoisin() == 2;
    }

    public String toString(){
        return " · ";
    }
}
