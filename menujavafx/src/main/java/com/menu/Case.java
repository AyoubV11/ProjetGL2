package com.menu;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe abstraite représentant une case générique dans la grille du jeu Slitherlink.
 * Fournit des fonctionnalités de base pour les différents types de cases 
 * (comme les points, les chiffres et les arêtes) dans la grille de jeu.
 */
public abstract class Case {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(Case.class);
    
    /**
     * Coordonnée de ligne de la case dans la grille.
     */
    protected int ligne;   

    /**
     * Coordonnée de colonne de la case dans la grille.
     */
    protected int colonne;   

    /**
     * Référence à la grille à laquelle appartient la case.
     */
    protected Grille grille;   

    /**
     * Constructeur de la case.
     * 
     * @param ligne La coordonnée de ligne de la case
     * @param colonne La coordonnée de colonne de la case
     * @param grille La grille à laquelle la case appartient
     */
    public Case(int ligne, int colonne, Grille grille) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.grille = grille;
        logger.debug("Création d'une case à la position ({}, {})", ligne, colonne);
    }

    /**
     * Récupère la coordonnée de ligne de la case.
     * 
     * @return La coordonnée de ligne
     */
    public int getLigne() {
        return this.ligne;
    }

    /**
     * Récupère la coordonnée de colonne de la case.
     * 
     * @return La coordonnée de colonne
     */
    public int getColonne() {
        return this.colonne;
    }
    
    /**
     * Calcule le nombre d'arêtes voisines de la case.
     * 
     * @return Le nombre d'arêtes voisines
     */
    public int getNbAretesVoisines(){
        int nbAretes = this.getAretesVoisines().size();
        logger.trace("Nombre d'arêtes voisines pour la case ({}, {}): {}", this.ligne, this.colonne, nbAretes);
        return nbAretes;
    }

    /**
     * Récupère la liste des arêtes voisines de la case.
     * 
     * @return Une liste d'arêtes voisines qui sont dans l'état TRAIT
     */
    public List<Arete> getAretesVoisines() {
        logger.trace("Recherche des arêtes voisines pour la case ({}, {})", this.ligne, this.colonne);
        ArrayList<Arete> aretesVoisines = new ArrayList<Arete>();

        int x = this.getLigne();
        int y = this.getColonne();

        // Utilisation de try-catch pour éviter les ClassCastException
        try {
            if (this.grille.caseExiste(x, y - 1)) {
                Arete arete = (Arete) this.grille.getCase(x, y - 1);
                if (arete.getEtat() == EnumEtat.TRAIT) {
                    aretesVoisines.add(arete);
                    logger.trace("Arête voisine à gauche ({}, {}) trouvée", x, y - 1);
                }
            }
        } catch (ClassCastException e) {
            logger.trace("Case à gauche ({}, {}) n'est pas une arête", x, y - 1);
        }

        // Utilisation de try-catch pour éviter les ClassCastException
        try {
            if (this.grille.caseExiste(x - 1, y)) {
                Arete arete = (Arete) this.grille.getCase(x - 1, y);
                if (arete.getEtat() == EnumEtat.TRAIT) {
                    aretesVoisines.add(arete);
                    logger.trace("Arête voisine en haut ({}, {}) trouvée", x - 1, y);
                }
            }
        } catch (ClassCastException e) {
            logger.trace("Case en haut ({}, {}) n'est pas une arête", x - 1, y);
        }

        // Utilisation de try-catch pour éviter les ClassCastException
        try {
            if (this.grille.caseExiste(x, y + 1)) {
                Arete arete = (Arete) this.grille.getCase(x, y + 1);
                if (arete.getEtat() == EnumEtat.TRAIT) {
                    aretesVoisines.add(arete);
                    logger.trace("Arête voisine à droite ({}, {}) trouvée", x, y + 1);
                }
            }
        } catch (ClassCastException e) {
            logger.trace("Case à droite ({}, {}) n'est pas une arête", x, y + 1);
        }

        // Utilisation de try-catch pour éviter les ClassCastException
        try {
            if (this.grille.caseExiste(x + 1, y)) {
                Arete arete = (Arete) this.grille.getCase(x + 1, y);
                if (arete.getEtat() == EnumEtat.TRAIT) {
                    aretesVoisines.add(arete);
                    logger.trace("Arête voisine en bas ({}, {}) trouvée", x + 1, y);
                }
            }
        } catch (ClassCastException e) {
            logger.trace("Case en bas ({}, {}) n'est pas une arête", x + 1, y);
        }

        logger.trace("Total de {} arêtes voisines trouvées pour la case ({}, {})", 
                  aretesVoisines.size(), this.ligne, this.colonne);
        return aretesVoisines;
    }

    /**
     * Récupère la grille à laquelle la case appartient.
     * 
     * @return La grille associée à la case
     */
    public Grille getGrille(){
        return this.grille;
    }
}