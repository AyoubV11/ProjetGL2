package com.menu;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation de la technique d'aide qui identifie les configurations spécifiques
 * où un chiffre 3 a un chiffre 0 dans l'une de ses diagonales.
 * Cette technique vérifie que les arêtes sont placées correctement selon une règle précise.
 */
public class Technique0Diagonal3 implements Technique {
    
    private static final Logger logger = LoggerFactory.getLogger(Technique0Diagonal3.class);
    private Grille g;
    
    /**
     * Constructeur de la technique d'aide pour un chiffre 3 avec un 0 en diagonale.
     * 
     * @param g la grille de jeu sur laquelle appliquer la technique
     */
    public Technique0Diagonal3(Grille g) {
        this.g = g;
        logger.debug("Initialisation de la Technique0Diagonal3");
    }

    /**
     * Renvoie le message d'aide à afficher à l'utilisateur.
     * 
     * @return une chaîne de caractères expliquant la technique
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour la Technique0Diagonal3");
        return("Technique 0 diagonal 3 applicable: \n\n Un 3 ayant un 0 dans sa diagonale doit posséder les traits correspondants à ceux de l'image.");
    }

    /**
     * Vérifie si la technique est applicable dans l'état actuel de la grille.
     * Recherche les configurations où un chiffre 3 a un chiffre 0 en diagonale
     * et vérifie les arêtes correspondantes.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de la Technique0Diagonal3");
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 3) {
                // Vérification pour un 0 en haut à gauche du 3
                if(c.getChiffreHautGauche().getChiffre() == 0) {
                    logger.trace("Chiffre 3 en [{},{}] avec 0 en haut à gauche", c.getLigne(), c.getColonne());
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT) {
                        logger.debug("Technique0Diagonal3 applicable: 3 en [{},{}] avec 0 en haut à gauche", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
                // Vérification pour un 0 en haut à droite du 3
                else if(c.getChiffreHautDroit().getChiffre() == 0) {
                    logger.trace("Chiffre 3 en [{},{}] avec 0 en haut à droite", c.getLigne(), c.getColonne());
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT) {
                        logger.debug("Technique0Diagonal3 applicable: 3 en [{},{}] avec 0 en haut à droite", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
                // Vérification pour un 0 en bas à droite du 3
                else if(c.getChiffreBasDroit().getChiffre() == 0) {
                    logger.trace("Chiffre 3 en [{},{}] avec 0 en bas à droite", c.getLigne(), c.getColonne());
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT) {
                        logger.debug("Technique0Diagonal3 applicable: 3 en [{},{}] avec 0 en bas à droite", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
                // Vérification pour un 0 en bas à gauche du 3
                else if(c.getChiffreBasGauche().getChiffre() == 0) {
                    logger.trace("Chiffre 3 en [{},{}] avec 0 en bas à gauche", c.getLigne(), c.getColonne());
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT) {
                        logger.debug("Technique0Diagonal3 applicable: 3 en [{},{}] avec 0 en bas à gauche", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
            }
        }
        logger.debug("Technique0Diagonal3 non applicable");
        return false;
    }
}