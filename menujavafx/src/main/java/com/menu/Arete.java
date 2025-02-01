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
     * Cette methode permet de verifier si la case est valide
     * @return boolean
     */
    public boolean estValide(int ligne, int colonne) {
        int nbLignes = grille.getNbLignes();
        int nbColonnes = grille.getNbColonnes();
    
        // Vérifier si la position est dans les limites de la grille
        return (ligne >= 0 && ligne < nbLignes && colonne >= 0 && colonne < nbColonnes);
    }
    
    /**
     * Cette methode permet de verifier si les cases voisines ont un nombre de  voisins inferieur a 2
     * @return boolean
     */
    public boolean estAutoriseAPoserArete(int ligne , int colonne){
        Grille grille =  this.getGrille();
        if(grille.getCase(ligne-1, colonne).getTraitVoisin() < 2 && grille.getCase(ligne+1, colonne).getTraitVoisin() < 2
        && grille.getCase(ligne, colonne-1).getTraitVoisin() < 2 && grille.getCase(ligne, colonne+1).getTraitVoisin() < 2){
            return true;
        }
       return false;
    }


    /**
     * Cette methode permet de verifier si l'utilisateur a le droit de poser l'arete, si la case est valide et qu'elle respecte la conditions des voisins
     * @return boolean
     */
    public boolean check(){
       
        int ligne = this.getLigne();
        int colonne = this.getColonne();     
        
        
        if(this.estValide(ligne, colonne) && this.estAutoriseAPoserArete(ligne,colonne)){
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
