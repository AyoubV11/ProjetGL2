package com.menu;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation de la technique d'aide qui vérifie qu'aucun trait ne doit entourer
 * ou être dirigé vers un chiffre 0.
 * Cette technique applique une règle fondamentale du jeu concernant les chiffres 0.
 */
public class TechniqueAutour0 implements Technique {
    
    private static final Logger logger = LoggerFactory.getLogger(TechniqueAutour0.class);
    private Grille g;
    
    /**
     * Constructeur de la technique d'aide pour vérifier les arêtes autour des 0.
     * 
     * @param g la grille de jeu sur laquelle appliquer la technique
     */
    public TechniqueAutour0(Grille g) {
        this.g = g;
        logger.debug("Initialisation de la TechniqueAutour0");
    }

    /**
     * Renvoie le message d'aide à afficher à l'utilisateur.
     * 
     * @return une chaîne de caractères expliquant la technique
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour la TechniqueAutour0");
        return("Technique ligne autour 0 applicable : \n\n Aucun zéro ne doit avoir de trait autour ou en direction de lui !");
    }

    /**
     * Vérifie si la technique est applicable dans l'état actuel de la grille.
     * Recherche les chiffres 0 qui ont des traits autour d'eux ou dirigés vers eux.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de la TechniqueAutour0");
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 0) {
                logger.trace("Examen du chiffre 0 en [{},{}]", c.getLigne(), c.getColonne());
                
                // Vérifie si le nombre d'arêtes voisines ne correspond pas à 0
                if(!c.matchNbAretesVoisines()) {
                    logger.debug("TechniqueAutour0 applicable: le 0 en [{},{}] a des arêtes voisines", c.getLigne(), c.getColonne());
                    return true;
                }
                // Vérifie les arêtes dirigées vers le 0 selon sa position dans la grille
                // Cas du 0 en haut de la grille (sauf coins)
                else if(c.getLigne() == 1 && c.getColonne() != 1 && c.getColonne() != g.getNbColonnes()-2) {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT) {
                        logger.debug("TechniqueAutour0 applicable: le 0 en haut [{},{}] a des traits dirigés vers lui", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
                // Cas du 0 en bas de la grille (sauf coins)
                else if(c.getLigne() == g.getNbColonnes()-2 && c.getColonne() != 1 && c.getColonne() != g.getNbColonnes()-2) {
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT) {
                        logger.debug("TechniqueAutour0 applicable: le 0 en bas [{},{}] a des traits dirigés vers lui", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
                // Cas du 0 à gauche de la grille (sauf coins)
                else if(c.getColonne() == 1 && c.getLigne() != 1 && c.getLigne() != g.getNbLignes()-2) {
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                        logger.debug("TechniqueAutour0 applicable: le 0 à gauche [{},{}] a des traits dirigés vers lui", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
                // Cas du 0 à droite de la grille (sauf coins)
                else if(c.getColonne() == g.getNbLignes()-2 && c.getLigne() != 1 && c.getLigne() != g.getNbLignes()-2) {
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                        logger.debug("TechniqueAutour0 applicable: le 0 à droite [{},{}] a des traits dirigés vers lui", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
            }
        }
        logger.debug("TechniqueAutour0 non applicable");
        return false;
    }
}