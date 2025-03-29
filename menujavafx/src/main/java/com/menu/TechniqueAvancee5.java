package com.menu;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation d'une technique avancée qui identifie les configurations spécifiques
 * impliquant un chiffre 1 avec un chiffre 3 en diagonale et des traits dans certaines positions.
 * Cette technique permet d'identifier des contraintes sur le placement des traits autour du chiffre 1.
 */
public class TechniqueAvancee5 implements Technique {
    
    private static final Logger logger = LoggerFactory.getLogger(TechniqueAvancee5.class);
    private Grille g;
    
    /**
     * Constructeur de la technique avancée 5.
     * 
     * @param g la grille de jeu sur laquelle appliquer la technique
     */
    public TechniqueAvancee5(Grille g) {
        this.g = g;
        logger.debug("Initialisation de la TechniqueAvancee5");
    }

    /**
     * Renvoie le message d'aide à afficher à l'utilisateur.
     * 
     * @return une chaîne de caractères expliquant la technique
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour la TechniqueAvancee5");
        return("Technique avancée 5 disponible: \n\n Si un 1 a un 3 dans une de ses diagonales, avec des traits dans le coin opposé au 1 (voir image), alors ce 1 ne peut pas avoir de traits dans le coin opposé au 3 (voir image).");
    }

    /**
     * Vérifie si la technique est applicable dans l'état actuel de la grille.
     * Recherche les configurations où un chiffre 1 a un chiffre 3 en diagonale
     * avec des traits spécifiques, ce qui impose des contraintes sur le placement
     * des traits autour du chiffre 1.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de la TechniqueAvancee5");
        Iterator<Chiffre> it = g.iteratorChiffres();
        boolean d = false;
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 1) {
                logger.trace("Examen d'un chiffre 1 en [{},{}]", c.getLigne(), c.getColonne());

                /**haut gauche */
                try {
                    if((c.getChiffreHautGauche().getChiffre() == 3 && 
                        ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-3)).getEtat() == EnumEtat.TRAIT && 
                        ((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT) && 
                       (((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                        ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT)) {
                        
                        logger.debug("TechniqueAvancee5 applicable: 1 en [{},{}] avec 3 en haut à gauche et traits en conflit", 
                                    c.getLigne(), c.getColonne());
                        return true;
                    }
                } catch(IndexOutOfBoundsException e) {
                    logger.trace("Exception lors de la vérification en haut à gauche du 1 en [{},{}]: {}", 
                                c.getLigne(), c.getColonne(), e.getMessage());
                    d = false;
                }

                /**haut droit */
                try {
                    if((c.getChiffreHautDroit().getChiffre() == 3 && 
                        ((Arete)g.getCase(c.getLigne()-3, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT && 
                        ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).getEtat() == EnumEtat.TRAIT) && 
                       (((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                        ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT)) {
                        
                        logger.debug("TechniqueAvancee5 applicable: 1 en [{},{}] avec 3 en haut à droite et traits en conflit", 
                                    c.getLigne(), c.getColonne());
                        return true;
                    }
                } catch(IndexOutOfBoundsException e) {
                    logger.trace("Exception lors de la vérification en haut à droite du 1 en [{},{}]: {}", 
                                c.getLigne(), c.getColonne(), e.getMessage());
                    d = false;
                }

                /**bas droit */
                try {
                    if((c.getChiffreBasDroit().getChiffre() == 3 && 
                        ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+3)).getEtat() == EnumEtat.TRAIT && 
                        ((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT) && 
                       (((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT || 
                        ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT)) {
                        
                        logger.debug("TechniqueAvancee5 applicable: 1 en [{},{}] avec 3 en bas à droite et traits en conflit", 
                                    c.getLigne(), c.getColonne());
                        return true;
                    }
                } catch(IndexOutOfBoundsException e) {
                    logger.trace("Exception lors de la vérification en bas à droite du 1 en [{},{}]: {}", 
                                c.getLigne(), c.getColonne(), e.getMessage());
                    d = false;
                }

                /**bas gauche */
                try {
                    if((c.getChiffreBasGauche().getChiffre() == 3 && 
                        ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).getEtat() == EnumEtat.TRAIT && 
                        ((Arete)g.getCase(c.getLigne()+3, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT) && 
                       (((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                        ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT)) {
                        
                        logger.debug("TechniqueAvancee5 applicable: 1 en [{},{}] avec 3 en bas à gauche et traits en conflit", 
                                    c.getLigne(), c.getColonne());
                        return true;
                    }
                } catch(IndexOutOfBoundsException e) {
                    logger.trace("Exception lors de la vérification en bas à gauche du 1 en [{},{}]: {}", 
                                c.getLigne(), c.getColonne(), e.getMessage());
                    d = false;
                }
            }
        }
        
        logger.debug("TechniqueAvancee5 non applicable");
        return d;
    }
}