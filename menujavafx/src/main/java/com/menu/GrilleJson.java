package com.menu;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe pour représenter les données d'une grille importées depuis un fichier JSON.
 * Contient les dimensions de la grille, les chiffres à ajouter et les arêtes de la solution.
 */
class GrilleJson {
    private static final Logger logger = LoggerFactory.getLogger(GrilleJson.class);
    
    /** Nombre de lignes logiques de la grille */
    private int ligne;
    
    /** Nombre de colonnes logiques de la grille */
    private int colonne;
    
    /** Liste des chiffres à ajouter dans la grille */
    private List<AjoutChiffre> ajoutChiffres;
    
    /** Liste des coordonnées des arêtes qui constituent la solution de la grille */
    private List<Coordonnee> aretesGrilleResolue;

    /**
     * Récupère le nombre de lignes logiques de la grille.
     * 
     * @return Nombre de lignes
     */
    public int getLigne() {
        logger.trace("Récupération du nombre de lignes: {}", ligne);
        return ligne;
    }

    /**
     * Définit le nombre de lignes logiques de la grille.
     * 
     * @param ligne Nombre de lignes à définir
     */
    public void setLigne(int ligne) {
        logger.debug("Définition du nombre de lignes: {}", ligne);
        this.ligne = ligne;
    }

    /**
     * Récupère le nombre de colonnes logiques de la grille.
     * 
     * @return Nombre de colonnes
     */
    public int getColonne() {
        logger.trace("Récupération du nombre de colonnes: {}", colonne);
        return colonne;
    }

    /**
     * Définit le nombre de colonnes logiques de la grille.
     * 
     * @param colonne Nombre de colonnes à définir
     */
    public void setColonne(int colonne) {
        logger.debug("Définition du nombre de colonnes: {}", colonne);
        this.colonne = colonne;
    }

    /**
     * Récupère la liste des chiffres à ajouter dans la grille.
     * 
     * @return Liste des chiffres à ajouter
     */
    public List<AjoutChiffre> getAjoutChiffres() {
        if (ajoutChiffres != null) {
            logger.trace("Récupération de la liste des chiffres à ajouter (taille: {})", ajoutChiffres.size());
        } else {
            logger.warn("Récupération d'une liste de chiffres null");
        }
        return ajoutChiffres;
    }

    /**
     * Définit la liste des chiffres à ajouter dans la grille.
     * 
     * @param ajoutChiffres Liste des chiffres à ajouter
     */
    public void setAjoutChiffre(List<AjoutChiffre> ajoutChiffres) {
        if (ajoutChiffres != null) {
            logger.debug("Définition de la liste des chiffres à ajouter (taille: {})", ajoutChiffres.size());
        } else {
            logger.warn("Définition d'une liste de chiffres null");
        }
        this.ajoutChiffres = ajoutChiffres;
    }
    
    /**
     * Récupère la liste des coordonnées des arêtes qui constituent la solution de la grille.
     * 
     * @return Liste des coordonnées des arêtes de la solution
     */
    public List<Coordonnee> getAretesGrilleResolue() {
        if (aretesGrilleResolue != null) {
            logger.trace("Récupération de la liste des arêtes de la solution (taille: {})", aretesGrilleResolue.size());
        } else {
            logger.warn("Récupération d'une liste d'arêtes de solution null");
        }
        return aretesGrilleResolue;
    }

    /**
     * Définit la liste des coordonnées des arêtes qui constituent la solution de la grille.
     * 
     * @param aretesGrilleResolue Liste des coordonnées des arêtes de la solution
     */
    public void setAretesGrilleResolue(List<Coordonnee> aretesGrilleResolue) {
        if (aretesGrilleResolue != null) {
            logger.debug("Définition de la liste des arêtes de la solution (taille: {})", aretesGrilleResolue.size());
        } else {
            logger.warn("Définition d'une liste d'arêtes de solution null");
        }
        this.aretesGrilleResolue = aretesGrilleResolue;
    }
}