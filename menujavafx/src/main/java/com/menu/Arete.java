package com.menu;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Représente une arête dans la grille du jeu Slitherlink.
 * Une arête peut être dans différents états (vide, trait, croix) et a des propriétés 
 * spécifiques liées à sa position et à ses contraintes de placement.
 */
public class Arete extends Case {
    private static final Logger logger = LoggerFactory.getLogger(Arete.class);
    
    /** État actuel de l'arête (VIDE, TRAIT, CROIX) */
    protected EnumEtat etat;
    
    /** Indique si cette arête fait partie de la solution de la grille */
    protected boolean estUneAreteDeLaGrilleResolue;
    
    /** Indique si l'arête est en mode libre (sans contraintes) */
    protected boolean libre;

    /**
     * Constructeur de l'arête avec tous les paramètres.
     * 
     * @param ligne La ligne de l'arête dans la grille
     * @param colonne La colonne de l'arête dans la grille
     * @param grille La grille à laquelle l'arête appartient
     * @param etat L'état initial de l'arête
     * @param libre Indique si l'arête est en mode libre
     */
    public Arete(int ligne, int colonne, Grille grille, EnumEtat etat, boolean libre) {
        super(ligne, colonne, grille);
        this.etat = etat;
        this.estUneAreteDeLaGrilleResolue = false;
        this.libre = libre;
        logger.debug("Création d'une arête à la position ({}, {}) avec l'état {} et mode libre={}", 
                   ligne, colonne, etat, libre);
    }

    /**
     * Récupère l'état actuel de l'arête.
     * 
     * @return L'état de l'arête (VIDE, TRAIT ou CROIX)
     */
    public EnumEtat getEtat() {
        return this.etat;
    }

    /**
     * Récupère si on est dans le mode libre.
     * 
     * @return Vrai si l'arête fait partie d'une grille en mode libre, faux sinon
     */
    public boolean getLibre() {
        return this.libre;
    }
    
    /**
     * Définit l'état de l'arête comme une croix.
     * Enregistre l'action dans l'historique pour permettre l'annulation.
     */
    public void setCroix() {
        logger.debug("Définition d'une croix sur l'arête ({}, {})", this.ligne, this.colonne);
        this.pushAction(EnumEtat.CROIX);
        this.setEtat(EnumEtat.CROIX);
    }


    /**
     * Enregistre une action dans l'historique des actions pour permettre l'annulation.
     * Selon le mode de jeu actuel (normal ou tâtonnement), l'action est enregistrée
     * dans la pile appropriée. La progression du jeu est également sauvegardée.
     * 
     * @param nouvelleEtat Le nouvel état à appliquer à l'arête
     */
    public void pushAction(EnumEtat nouvelleEtat){
        logger.debug("Ajout d'une action dans l'historique pour l'arête ({}, {}): {} -> {}", 
                   this.ligne, this.colonne, this.etat, nouvelleEtat);
        Action action = new Action(this.ligne, this.colonne, nouvelleEtat, etat);
        if(this.grille.enModeTatonnement()){
            logger.trace("Mode tâtonnement: ajout à la pile undo tâtonnement et vidage de la pile redo tâtonnement");
            this.grille.getPileUndoTatonnement().push(action);
            this.grille.getPileRedoTatonnement().clear();
        }else{
            logger.trace("Mode normal: ajout à la pile undo et vidage de la pile redo");
            this.grille.getPileUndo().push(action);
            this.grille.getPileRedo().clear();
        }
        if(!libre) {
            logger.trace("Sauvegarde de la progression");
            this.grille.sauvegarderProgression();
        } else {
            logger.trace("Sauvegarde de la progression libre");
            this.grille.sauvegarderProgressionLibre();
        }
    }

    /**
     * Tente de définir l'arête comme un trait, en vérifiant les contraintes.
     * Si le placement n'est pas autorisé et que le paramètre croix est vrai,
     * une croix sera placée à la place du trait.
     * 
     * @param croix Indique si une croix doit être placée en cas d'échec de placement du trait
     * @return true si le trait a été posé avec succès, false si une croix a été placée
     */
    public boolean setTrait(boolean croix) {
        logger.debug("Tentative de définir un trait sur l'arête ({}, {}), avec croix={}", 
                   this.ligne, this.colonne, croix);
        EnumEtat nouvelEtat;

        if (!croix || this.check()) {
            nouvelEtat = EnumEtat.TRAIT;
            logger.debug("Placement d'un trait autorisé");
        } else {
            nouvelEtat = EnumEtat.CROIX;
            logger.debug("Placement d'un trait non autorisé, remplacement par une croix");
        }

        this.pushAction(nouvelEtat);
        this.etat = nouvelEtat;
        
        return nouvelEtat == EnumEtat.TRAIT;
    }

    /**
     * Récupère toutes les arêtes connectées à cette arête qui sont dans l'état TRAIT.
     * Une arête est considérée comme connectée si elle partage un point commun
     * avec cette arête et qu'elle a l'état TRAIT.
     * 
     * @return Une liste contenant toutes les arêtes connectées à l'état TRAIT
     */
    public List<Arete> getAretesConnectees() {
        logger.trace("Recherche des arêtes connectées à l'arête ({}, {})", this.ligne, this.colonne);
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
        
        logger.trace("Nombre d'arêtes connectées trouvées: {}", aretesConnectees.size());
        return aretesConnectees;
    }

    /**
     * Définit l'état de l'arête comme vide.
     * Enregistre l'action dans l'historique pour permettre l'annulation.
     */
    public void setVide() {
        logger.debug("Définition de l'état VIDE sur l'arête ({}, {})", this.ligne, this.colonne);
        this.pushAction(EnumEtat.VIDE);
        this.setEtat(EnumEtat.VIDE);
    }

    /**
     * Définit explicitement l'état de l'arête sans enregistrer l'action dans l'historique.
     * Cette méthode est utilisée par les autres méthodes de modification d'état qui
     * gèrent elles-mêmes l'enregistrement de l'action.
     * 
     * @param etat Le nouvel état de l'arête
     */
    public void setEtat(EnumEtat etat){
        logger.trace("Changement d'état de l'arête ({}, {}): {} -> {}", 
                   this.ligne, this.colonne, this.etat, etat);
        this.etat = etat;
    }

    /**
     * Implémentation de la méthode abstraite de la classe Case.
     * Pour une arête, le nombre d'arêtes voisines est toujours 0.
     * 
     * @return Toujours 0 pour une arête
     */
    @Override
    public int getNbAretesVoisines(){  
        return 0;
    }

    /**
     * Vérifie si l'ajout d'un trait est autorisé selon les contraintes du jeu.
     * Les contraintes vérifiées sont :
     * - Aucun point adjacent ne doit avoir plus de 2 arêtes connectées
     * - Les chiffres voisins ne doivent pas avoir leur contrainte déjà satisfaite
     * 
     * @return true si l'ajout d'un trait est autorisé, false sinon
     */
    public boolean estAutoriseAPoserTrait(){
        logger.trace("Vérification des autorisations pour poser un trait sur l'arête ({}, {})", 
                   this.ligne, this.colonne);
        // Vérifie les contraintes des points et des chiffres voisins
        boolean posePossibleSelonPoints = getPointsVoisins().stream().allMatch(point -> point.getNbAretesVoisines() < 2); 
        boolean posePossibleSelonChiffres = getChiffresVoisins().stream().noneMatch(Chiffre::matchNbAretesVoisines);

        logger.trace("Pose possible selon points: {}, pose possible selon chiffres: {}", 
                   posePossibleSelonPoints, posePossibleSelonChiffres);
        return posePossibleSelonPoints && posePossibleSelonChiffres;
    }

    /**
     * Récupère les chiffres voisins de l'arête.
     * Les chiffres voisins dépendent de l'orientation de l'arête :
     * - Pour une arête horizontale, les chiffres sont situés au-dessus et en-dessous
     * - Pour une arête verticale, les chiffres sont situés à gauche et à droite
     * 
     * @return Une liste des chiffres adjacents à l'arête
     */
    public List<Chiffre> getChiffresVoisins() {
        logger.trace("Recherche des chiffres voisins de l'arête ({}, {})", this.ligne, this.colonne);
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

        logger.trace("Nombre de chiffres voisins trouvés: {}", chiffreVoisins.size());
        return chiffreVoisins;
    }

    /**
     * Récupère les points voisins de l'arête.
     * Les points voisins dépendent de l'orientation de l'arête :
     * - Pour une arête horizontale, les points sont situés à gauche et à droite
     * - Pour une arête verticale, les points sont situés au-dessus et en-dessous
     * 
     * @return Une liste des points adjacents à l'arête
     */
    public List<Point> getPointsVoisins() {
        logger.trace("Recherche des points voisins de l'arête ({}, {})", this.ligne, this.colonne);
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

        logger.trace("Nombre de points voisins trouvés: {}", pointsVoisins.size());
        return pointsVoisins;
    }

    /**
     * Détermine l'orientation de l'arête (horizontale ou verticale) en fonction
     * de sa position dans la grille. Les arêtes aux lignes paires sont horizontales,
     * celles aux lignes impaires sont verticales.
     * 
     * @return L'orientation de l'arête (HORIZONTAL ou VERTICAL)
     */
    public EnumOrientation getOrientation(){
        EnumOrientation orientation = (this.getLigne() % 2 == 0) ? 
                                     EnumOrientation.HORIZONTAL : 
                                     EnumOrientation.VERTICAL;
        logger.trace("Orientation de l'arête ({}, {}): {}", this.ligne, this.colonne, orientation);
        return orientation;
    }

    /**
     * Vérifie si le placement d'un trait est valide selon les règles du jeu.
     * Cette méthode vérifie que la case existe et qu'il est autorisé de poser un trait.
     * 
     * @return true si le placement est valide, false sinon
     */
    public boolean check(){
        logger.trace("Vérification de la validité du placement d'un trait sur l'arête ({}, {})", 
                   this.ligne, this.colonne);
        int ligne = this.getLigne();
        int colonne = this.getColonne();     
    
        boolean resultat = this.grille.caseExiste(ligne, colonne) && this.estAutoriseAPoserTrait();
        logger.trace("Résultat de la vérification: {}", resultat);
        return resultat;
    }

    /**
     * Vérifie également si l'arete n'est pas un trait
     * @return true si valide, false sinon
     */
    public boolean checkPlus() throws ArrayIndexOutOfBoundsException {
        return (!this.check()) && this.getEtat() != EnumEtat.TRAIT;
    }
    
    /**
     * Convertit l'arête en représentation textuelle.
     * La représentation dépend de l'état de l'arête :
     * - " - " pour une arête vide
     * - " | " pour une arête avec un trait
     * - " x " pour une arête avec une croix
     * 
     * @return Une représentation textuelle de l'état de l'arête
     */
    public String toString(){
        if(this.etat == EnumEtat.VIDE) return " - ";
        else if(this.etat == EnumEtat.TRAIT) return " | ";
        else return " x ";
    }

    /**
     * Marque cette arête comme faisant partie de la solution de la grille.
     * Cette méthode est utilisée pour indiquer que l'arête fait partie du chemin
     * de solution correct du puzzle.
     */
    public void devientUneAreteDeLaGrilleResolue(){
        logger.debug("L'arête ({}, {}) devient une arête de la grille résolue", this.ligne, this.colonne);
        this.estUneAreteDeLaGrilleResolue = true;
    }

    /**
     * Vérifie si cette arête fait partie de la solution de la grille.
     * 
     * @return true si l'arête fait partie de la solution, false sinon
     */
    public boolean estUneAreteDeLaGrilleResolue(){
        return this.estUneAreteDeLaGrilleResolue;
    }
}