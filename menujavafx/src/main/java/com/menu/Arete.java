package com.menu;

public class Arete extends Case {
    protected EnumEtat etat;   // Etat de l'arête

    public Arete(int ligne, int colonne, Grille grille, EnumEtat etat) {
        super(ligne, colonne, grille);
        this.etat = etat;
    }

    public EnumEtat getEtat() {
        return this.etat;
    }

    public void setCroix() {
        this.etat = EnumEtat.CROIX;
    }

    public void setTrait() {
        if(check()) this.etat = EnumEtat.TRAIT;
    }

    public void setVide() {
        this.etat = EnumEtat.VIDE;
    }

    public void setEtat(EnumEtat etat){
        this.etat = etat;
    }


    /**
     * Cette methode permet de verifier si l'utilisateur a le droit de poser l'arete
     * @return boolean
     */

     
    public boolean check(){
       
        int ligne = this.getLigne();
        int colonne = this.getColonne();
        
        System.out.println("ligne : "  + ligne);
        System.out.println("Colonne : " +colonne);
        
        if(this.getGrille().getCase(ligne-1, colonne).getTraitVoisin() < 2 && this.getGrille().getCase(ligne+1, colonne).getTraitVoisin() < 2){
            return true;
        }
       return false;
   

        
    }
    
    public String toString(){
        if(this.etat == EnumEtat.VIDE) return " - ";
        else if(this.etat == EnumEtat.TRAIT) return " | ";
        else return " x ";
    }
}
