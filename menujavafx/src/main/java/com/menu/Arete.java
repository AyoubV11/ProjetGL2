package com.menu;

import java.util.ArrayList;
import java.util.List;

public class Arete extends Case {
    protected EnumEtat etat;   // Etat de l'arête
    protected boolean estUneAreteDeLaGrilleResolue;

    public Arete(int ligne, int colonne, Grille grille, EnumEtat etat) {
        super(ligne, colonne, grille);
        this.etat = etat;
        this.estUneAreteDeLaGrilleResolue = false;
    }

    public EnumEtat getEtat() {
        return this.etat;
    }
    

    public void setCroix() {
        this.pushAction(EnumEtat.CROIX);
        this.setEtat(EnumEtat.CROIX);
    }

    public void pushAction(EnumEtat nouvelleEtat){
        Action action = new Action(this.ligne, this.colonne, nouvelleEtat, etat);
        this.grille.getPileUndo().push(action);
        this.grille.getPileRedo().clear();
        this.grille.sauvegarderProgress();
    }

    public void setTrait() {
        this.pushAction(EnumEtat.TRAIT);
        this.setEtat(EnumEtat.TRAIT);
    }

    public void setVide() {
        this.pushAction(EnumEtat.VIDE);
        this.setEtat(EnumEtat.VIDE);
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
        boolean posePossibleSelonPoints = getPointsVoisins().stream().allMatch(point -> point.getNbAretesVoisines() < 2); 
        boolean posePossibleSelonChiffres = getChiffresVoisins().stream().noneMatch(Chiffre::matchNbAretesVoisines); // on puet poser temps que les chiffres voisins n'ont pas le bon nombre d'arretes

        return posePossibleSelonPoints && posePossibleSelonChiffres;
    }

    public List<Chiffre> getChiffresVoisins() {
        ArrayList<Chiffre> chiffreVoisins = new ArrayList<Chiffre>();

        int x = this.getLigne();
        int y = this.getColonne();

        if(this.getOrientation() == EnumOrientation.HORIZONTAL){
            if (this.grille.caseExiste(x - 1, y))
                chiffreVoisins.add((Chiffre) this.grille.getCase(x - 1, y));
            if (this.grille.caseExiste(x + 1, y))
            chiffreVoisins.add((Chiffre) this.grille.getCase(x + 1, y));
        }
        else if(this.getOrientation() == EnumOrientation.VERTICAL){
            if (this.grille.caseExiste(x, y - 1))
                chiffreVoisins.add((Chiffre) this.grille.getCase(x, y - 1));
            if (this.grille.caseExiste(x, y + 1))
            chiffreVoisins.add((Chiffre) this.grille.getCase(x, y + 1));
        }

        return chiffreVoisins;
    }

    public List<Point> getPointsVoisins() {
        ArrayList<Point> pointsVoisins = new ArrayList<Point>();

        int x = this.getLigne();
        int y = this.getColonne();

        if(this.getOrientation() == EnumOrientation.HORIZONTAL){
            if (this.grille.caseExiste(x, y - 1))
                pointsVoisins.add((Point) this.grille.getCase(x, y - 1));
            if (this.grille.caseExiste(x, y + 1))
                pointsVoisins.add((Point) this.grille.getCase(x, y + 1));
        }
        else if(this.getOrientation() == EnumOrientation.VERTICAL){
            if (this.grille.caseExiste(x - 1, y))
                pointsVoisins.add((Point) this.grille.getCase(x - 1, y));
            if (this.grille.caseExiste(x + 1, y))
                pointsVoisins.add((Point) this.grille.getCase(x + 1, y));
        }

        return pointsVoisins;
    }

    @Override
    public List<Arete> getAretesVoisines() {
        return getPointsVoisins().stream()
            .map(Point::getAretesVoisines)
            .flatMap(List::stream).toList();
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

    public void devientUneAreteDeLaGrilleResolue(){
        this.estUneAreteDeLaGrilleResolue = true;
    }

    public boolean estUneAreteDeLaGrilleResolue(){
        return this.estUneAreteDeLaGrilleResolue;
    }
}
