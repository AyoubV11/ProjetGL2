package com.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une arête dans la grille du jeu Slitherlink.
 * Une arête peut être dans différents états (vide, trait, croix) et a des propriétés 
 * spécifiques liées à sa position et à ses contraintes de placement.
 */
public class Arete extends Case {
    protected EnumEtat etat;   // Etat de l'arête
    protected boolean estUneAreteDeLaGrilleResolue;
    protected boolean libre;
    protected SoundPlayer sound = new SoundPlayer();

    /**
     * Constructeur de l'arête avec tous les paramètres.
     * 
     * @param ligne La ligne de l'arête dans la grille
     * @param colonne La colonne de l'arête dans la grille
     * @param grille La grille à laquelle l'arête appartient
     * @param etat L'état initial de l'arête
     */
    public Arete(int ligne, int colonne, Grille grille, EnumEtat etat,boolean libre) {
        super(ligne, colonne, grille);
        this.etat = etat;
        this.estUneAreteDeLaGrilleResolue = false;
        this.libre=libre;
    }

    /**
     * Récupère l'état actuel de l'arête.
     * 
     * @return L'état de l'arête
     */
    public EnumEtat getEtat() {
        return this.etat;
    }

    /**
     * Récupère si on est dans le mode libre.
     * 
     * @return Fais partis d'une grille libre ou non
     */
    public boolean getLibre() {
        return this.libre;
    }
    

    /**
     * Définit l'état de l'arête comme une croix.
     */
    public void setCroix() {
        this.pushAction(EnumEtat.CROIX);
        this.setEtat(EnumEtat.CROIX);
    }

    public void pushAction(EnumEtat nouvelleEtat){
        Action action = new Action(this.ligne, this.colonne, nouvelleEtat, etat);
        this.grille.getPileUndo().push(action);
        this.grille.getPileRedo().clear();
        if(!libre){
            this.grille.sauvegarderProgression();
        }
        else{
            this.grille.sauvegarderProgressionLibre();
        }
        if(this.grille.enModeTatonnement()){
            this.grille.getPileUndoTatonnement().push(action);
            this.grille.getPileRedoTatonnement().clear();
        }else{
            this.grille.getPileUndo().push(action);
            this.grille.getPileRedo().clear();
            
        }
        this.grille.sauvegarderProgression();
    }

    /**
     * Tente de définir l'arête comme un trait, en vérifiant les contraintes.
     * 
     * @return true si le trait peut être posé, false sinon
     */
    public boolean setTrait(boolean croix) {
    EnumEtat nouvelEtat;

    if (!croix || this.check()) {
        nouvelEtat = EnumEtat.TRAIT;
    } else {
        nouvelEtat = EnumEtat.CROIX;
    }

    this.pushAction(nouvelEtat);
    this.etat = nouvelEtat;

    // Jouer le son uniquement si on place un trait
    if (nouvelEtat == EnumEtat.TRAIT) {
        sound.bruitDeClique();
    }

    return nouvelEtat == EnumEtat.TRAIT;
}


    public List<Arete> getAretesConnectees() {
        ArrayList<Arete> aretesConnectees = new ArrayList<Arete>();
        
        // Obtenir les points voisins de cette arête
        List<Point> points = this.getPointsVoisins();
        
        // Pour chaque point voisin
        for (Point p : points) {
            
            for (Case voisin : p.getAretesVoisines()) {
                if (voisin instanceof Arete && ((Arete)voisin).getEtat() == EnumEtat.TRAIT 
                    && voisin != this) {  // Ne pas s'inclure soi-même
                    aretesConnectees.add((Arete)voisin);
                }
            }
        }
        
        return aretesConnectees;
    }

    /**
     * Définit l'état de l'arête comme vide.
     */
    public void setVide() {
        this.pushAction(EnumEtat.VIDE);
        this.setEtat(EnumEtat.VIDE);
    }

    /**
     * Définit explicitement l'état de l'arête.
     * 
     * @param etat Le nouvel état de l'arête
     */
    public void setEtat(EnumEtat etat){
        this.etat = etat;
    }

    @Override
    public int getNbAretesVoisines(){  
        return 0;
    }


    /**
     * Vérifie si l'ajout d'un trait est autorisé selon les contraintes du jeu.
     * 
     * @return true si l'ajout d'un trait est autorisé, false sinon
     */
    public boolean estAutoriseAPoserTrait(){
        // Vérifie les contraintes des points et des chiffres voisins
        boolean posePossibleSelonPoints = getPointsVoisins().stream().allMatch(point -> point.getNbAretesVoisines() < 2); 
        boolean posePossibleSelonChiffres = getChiffresVoisins().stream().noneMatch(Chiffre::matchNbAretesVoisines);

        return posePossibleSelonPoints && posePossibleSelonChiffres;
    }

    /**
     * Récupère les chiffres voisins de l'arête.
     * 
     * @return Une liste des chiffres adjacents à l'arête
     */
    public List<Chiffre> getChiffresVoisins() {
        ArrayList<Chiffre> chiffreVoisins = new ArrayList<Chiffre>();

        int x = this.getLigne();
        int y = this.getColonne();

        // Logique de récupération des chiffres voisins selon l'orientation de l'arête
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

    /**
     * Récupère les points voisins de l'arête.
     * 
     * @return Une liste des points adjacents à l'arête
     */
    public List<Point> getPointsVoisins() {
        ArrayList<Point> pointsVoisins = new ArrayList<Point>();

        int x = this.getLigne();
        int y = this.getColonne();

        // Logique de récupération des points voisins selon l'orientation de l'arête
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

    /**
     * Récupère l'orientation de l'arête (horizontale ou verticale).
     * 
     * @return L'orientation de l'arête
     */
    public EnumOrientation getOrientation(){
        if(this.getLigne() % 2 == 0){
            return EnumOrientation.HORIZONTAL;
        } else {
            return EnumOrientation.VERTICAL;
        }
    }

    /**
     * Vérifie si le placement d'un trait est valide selon les règles du jeu.
     * 
     * @return true si le placement est valide, false sinon
     */
    public boolean check(){
        int ligne = this.getLigne();
        int colonne = this.getColonne();     
    
        if(this.grille.caseExiste(ligne, colonne) && this.estAutoriseAPoserTrait()){
            return true;
        }
        
        return false;
    }
    
    /**
     * Convertit l'arête en représentation textuelle.
     * 
     * @return Une représentation textuelle de l'état de l'arête
     */
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
