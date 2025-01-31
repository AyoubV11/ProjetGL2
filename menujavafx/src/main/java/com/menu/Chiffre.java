package com.menu;

public class Chiffre extends Case {
    protected int chiffre;   // Chiffre de la case
    protected boolean estVide;   // Indique si la case est vide

    public Chiffre(int ligne, int colonne, Grille grille, int chiffre) {
        super(ligne, colonne, grille);
        this.chiffre = chiffre;
        this.estVide = false;
    }

    public int getChiffre() {
        return this.chiffre;
    }

    public void setChiffre(int chiffre) {
        this.chiffre = chiffre;
    }


    /**
     * Methode qui verifie si le nombre d'aretes est bien egal au chiffre
     * @return
     */
    public boolean VerifChiffre(){
        return this.chiffre==this.getTraitVoisin();
    }

    /**
     * Methode qui renvoie vrai si le nombre d'arete est egal au chiffre sinon faux
     * @return boolean
     */
    public boolean ChiffreEgaleVoisins(){
        
        if( this.getChiffre() == this.getTraitVoisin()){
            return true;
        }
        return false;
    }


    /**
     * Methode qui renvoie le nombre d'aretes autour du point
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

    public String toString(){
        if(this.chiffre == -1) return "   ";
        else return " " + this.chiffre + " ";
    }

}
