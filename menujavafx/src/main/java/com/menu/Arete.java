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
    public int getNbAretesVoisines(){  
        return 0;
    }


    /**
     * Cette methode permet de verifier si les cases voisines ont un nombre de  voisins inferieur a 2
     * @return boolean
     */
    public boolean estAutoriseAPoserTrait(){

        Point point1, point2;

        if((this.getOrientation() == EnumOrientation.HORIZONTAL)){
            point1 = (Point)this.getGrille().getCase(this.ligne, this.colonne-1);
            point2 = (Point)this.getGrille().getCase(this.ligne, this.colonne+1);
        }else{
            point1 = (Point)this.getGrille().getCase(this.ligne-1, this.colonne);
            point2 = (Point)this.getGrille().getCase(this.ligne+1, this.colonne);
        }
        
        return point1.getNbAretesVoisines() < 2 && point2.getNbAretesVoisines() < 2;

        
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
        
    
        if(this.grille.caseExiste(ligne, colonne) && this.estAutoriseAPoserTrait() /*Ca respe */){
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
