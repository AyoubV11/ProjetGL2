package com.menu;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation de la technique d'aide qui identifie les configurations spécifiques
 * où un chiffre 3 est adjacent à un chiffre 0.
 * Cette technique vérifie que les arêtes sont placées correctement selon une règle précise.
 */
public class Technique0Adjacent3 implements Technique {
    
    private static final Logger logger = LoggerFactory.getLogger(Technique0Adjacent3.class);
    private Grille g;
    
    /**
     * Constructeur de la technique d'aide pour un chiffre 3 adjacent à un 0.
     * 
     * @param g la grille de jeu sur laquelle appliquer la technique
     */
    public Technique0Adjacent3(Grille g) {
        this.g = g;
        logger.debug("Initialisation de la Technique0Adjacent3");
    }

    /**
     * Renvoie le message d'aide à afficher à l'utilisateur.
     * 
     * @return une chaîne de caractères expliquant la technique
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour la Technique0Adjacent3");
        return("Technique 0 adjacent 3 applicable: \n\n Un 3 ayant comme voisin un 0 doit posséder les traits correspondants à ceux de l'image.");
    }

    /**
     * Vérifie si la technique est applicable dans l'état actuel de la grille.
     * Recherche les configurations où un chiffre 3 est adjacent à un chiffre 0
     * et vérifie les arêtes correspondantes.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de la Technique0Adjacent3");
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 3) {
                // Vérification pour un 0 à gauche du 3
                if(c.getChiffreGauche().getChiffre() == 0) {
                    logger.trace("Chiffre 3 en [{},{}] avec 0 à gauche", c.getLigne(), c.getColonne());
                    if(!c.matchNbAretesVoisines() || 
                       ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() != EnumEtat.TRAIT ) {
                        logger.debug("Technique0Adjacent3 applicable: 3 en [{},{}] avec 0 à gauche", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
                // Vérification pour un 0 au-dessus du 3
                else if(c.getChiffreHaut().getChiffre() == 0) {
                    logger.trace("Chiffre 3 en [{},{}] avec 0 en haut", c.getLigne(), c.getColonne());
                    if(!c.matchNbAretesVoisines() || 
                       ((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT ) {
                        logger.debug("Technique0Adjacent3 applicable: 3 en [{},{}] avec 0 en haut", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
                // Vérification pour un 0 à droite du 3
                else if(c.getChiffreDroit().getChiffre() == 0) {
                    logger.trace("Chiffre 3 en [{},{}] avec 0 à droite", c.getLigne(), c.getColonne());
                    if(!c.matchNbAretesVoisines() || 
                       ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() != EnumEtat.TRAIT ) {
                        logger.debug("Technique0Adjacent3 applicable: 3 en [{},{}] avec 0 à droite", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
                // Vérification pour un 0 en-dessous du 3
                else if(c.getChiffreBas().getChiffre() == 0) {
                    logger.trace("Chiffre 3 en [{},{}] avec 0 en bas", c.getLigne(), c.getColonne());
                    if(!c.matchNbAretesVoisines() || 
                       ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT || 
                       ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT ) {
                        logger.debug("Technique0Adjacent3 applicable: 3 en [{},{}] avec 0 en bas", c.getLigne(), c.getColonne());
                        return true;
                    }
                }
            }
        }
        logger.debug("Technique0Adjacent3 non applicable");
        return false;
    }
}