package com.menu;

public class Arete extends Case {
    protected EnumEtat etat;   // Etat de l'arête

    public Arete(int coordX, int coordY, Grille grille, EnumEtat etat) {
        super(coordX, coordY, grille);
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

    public boolean check(){
       
        int ligne = this.getCoordX();
        int colonne = this.getCoordY();
        
        System.out.println("ligne : "  + ligne);
        System.out.println("Colonne : " +colonne);
        // Si ses points ont deux voisins alors c'est impossible de poser l'arete
        
        // CONDITONS PAS BONNE
        // Comprendre logique coordonnees
        
        /*if(this.getGrille().getCase(ligne-1, colonne).getTraitVoisin() <2 && this.getGrille().getCase(ligne+1, colonne).getTraitVoisin() <2){

            return true;
        }*/
        //throw new IllegalStateException("Error : interdiction de poser l'arete");
        
       // System.out.println("Etat :" + this.getEtat());
       // System.out.println("ligne : "  +this.getCoordX());
       // System.out.println("Colonne : " +this.getCoordY());
       return false;
   

        
    }
    
    public String toString(){
        if(this.etat == EnumEtat.VIDE) return " - ";
        else if(this.etat == EnumEtat.TRAIT) return " | ";
        else return " x ";
    }
}
