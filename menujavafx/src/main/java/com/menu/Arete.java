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
        if(check()){
            this.etat = EnumEtat.TRAIT;
        }else{

            this.etat = EnumEtat.CROIX;
       }
    }

    public void setVide() {
        this.etat = EnumEtat.VIDE;
    }

    public void setEtat(EnumEtat etat){
        this.etat = etat;
    }


    @Override
    public int getTraitVoisin(){
      
        return 0;
    }


    /**
     * Cette methode permet de verifier si les cases voisines ont un nombre de  voisins inferieur a 2
     * @return boolean
     */
    public boolean estAutoriseAPoserArete(int ligne , int colonne){
        Grille grille =  this.getGrille();
        
        if((this.getOrientation() == EnumOrientation.HORIZONTAL) && (((Point)grille.getCase(ligne, colonne-1)).getTraitVoisin() < 2 && ((Point)grille.getCase(ligne, colonne+1)).getTraitVoisin() < 2))
        {
            return true;
        }
        else if((this.getOrientation() == EnumOrientation.VERTICAL) && (((Point)grille.getCase(ligne-1, colonne)).getTraitVoisin() < 2 && ((Point)grille.getCase(ligne+1, colonne)).getTraitVoisin() < 2))
        {
            return true;
        }

        return false;
        
    }

    public EnumOrientation getOrientation(){
        if(this.getLigne() % 2 == 0){
            return EnumOrientation.HORIZONTAL;
        } else {
            return EnumOrientation.VERTICAL;
        }
    }


    public boolean check(){
       
        int ligne = this.getLigne();
        int colonne = this.getColonne();     
        
    
        if(this.estValide(ligne, colonne) && this.estAutoriseAPoserArete(ligne,colonne) /*Ca respe */){
            return true;
        }

        // Cas si le nb d'arrete autour du chiffre est superieur au chiffre
        
        return false;
    }
    
    public String toString(){
        if(this.etat == EnumEtat.VIDE) return " - ";
        else if(this.etat == EnumEtat.TRAIT) return " | ";
        else return " x ";
    }
}
