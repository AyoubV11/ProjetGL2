package com.menu;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation d'une technique avancée qui identifie des configurations spécifiques
 * impliquant un chiffre 3 avec deux traits dans un coin, adjacent à un chiffre 1
 * et à proximité d'un autre chiffre 3.
 * Cette technique permet de déterminer les traits à placer dans certaines configurations complexes.
 */
public class TechniqueAvancee2 implements Technique {
    
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(TechniqueAvancee2.class);
    
    /** Grille de jeu sur laquelle appliquer la technique */
    private Grille g;
    
    /**
     * Constructeur de la technique avancée 2.
     * 
     * @param g la grille de jeu sur laquelle appliquer la technique
     */
    public TechniqueAvancee2(Grille g) {
        this.g = g;
        logger.debug("Initialisation de la TechniqueAvancee2");
    }
    
    /**
     * Renvoie le message d'aide à afficher à l'utilisateur.
     * 
     * @return une chaîne de caractères expliquant la technique
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour la TechniqueAvancee2");
        return "Technique avancée 2 disponible: \n\n Si un 3 avec deux traits dans un de ses coins est à coté d'un 1, et à 2 cases d'un autre 3 perpendiculairement, alors on sait qu'un trait est posable sur l'autre 3 comme sur l'image.";
    }
    
    /**
     * Vérifie si la technique est applicable dans l'état actuel de la grille.
     * Recherche les configurations où un chiffre 3 a deux traits dans un coin,
     * est adjacent à un chiffre 1 et à proximité d'un autre chiffre 3.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de la TechniqueAvancee2");
        Iterator<Chiffre> it = g.iteratorChiffres();
        boolean d = false;
        while (it.hasNext()) {
            Chiffre c = it.next();
            if (c.getChiffre() == 3) {
                logger.trace("Examen d'un chiffre 3 en [{},{}]", c.getLigne(), c.getColonne());
                if (c.getNbAretesVoisines() == 2) {
                    logger.trace("Chiffre 3 en [{},{}] avec 2 arêtes voisines", c.getLigne(), c.getColonne());
                    
                    /*voisins haut et droit */
                    if (((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT && 
                        ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT && 
                        ((Arete)g.getCase(c.getLigne(), c.getColonne()-5)).getEtat() != EnumEtat.TRAIT){
                        
                        logger.trace("3 en [{},{}] avec traits en haut et à droite", c.getLigne(), c.getColonne());
                        /**vérifier si ça ne déborde pas */
                        if (c.getChiffreBas().getChiffre() == 1 && c.getChiffreGauche().getChiffreGauche().getChiffre() == 3) {
                            logger.debug("Configuration potentielle: 3 en [{},{}] avec 1 en bas et 3 à gauche-gauche", 
                                         c.getLigne(), c.getColonne());
                            
                            // Vérification des arêtes requises
                            try {
                                if (((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()+1, c.getColonne()+2);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()+1, c.getColonne()+2);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()-2, c.getColonne()-1);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()-2, c.getColonne()-1);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()-2, c.getColonne()-3)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()-2, c.getColonne()-3);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()-2, c.getColonne()-3);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()-2, c.getColonne()-5)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()-2, c.getColonne()-5);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()-2, c.getColonne()-5);
                                d = false;
                            }
                            
                            logger.debug("TechniqueAvancee2 applicable: configuration haut-droit en [{},{}]", c.getLigne(), c.getColonne());
                            return true;
                        }
                        return false;
                    }

                    /*voisins droit et bas */
                    else if (((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT && 
                             ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT &&
                             ((Arete)g.getCase(c.getLigne(), c.getColonne()-5)).getEtat() != EnumEtat.TRAIT) {
                        
                        logger.trace("3 en [{},{}] avec traits à droite et en bas", c.getLigne(), c.getColonne());
                        /**vérifier si ça ne déborde pas */
                        if (c.getChiffreGauche().getChiffre() == 1 && c.getChiffreHaut().getChiffreHaut().getChiffre() == 3) {
                            logger.debug("Configuration potentielle: 3 en [{},{}] avec 1 à gauche et 3 en haut-haut", 
                                         c.getLigne(), c.getColonne());
                            
                            // Vérification des arêtes requises
                            try {
                                if (((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).checkPlus()) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()+2, c.getColonne()-1);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()+2, c.getColonne()-1);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()-1, c.getColonne()+2);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()-1, c.getColonne()+2);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()-3, c.getColonne()+2)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()-3, c.getColonne()+2);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()-3, c.getColonne()+2);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()-5, c.getColonne()+2)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()-5, c.getColonne()+2);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()-5, c.getColonne()+2);
                                d = false;
                            }
                            
                            logger.debug("TechniqueAvancee2 applicable: configuration droit-bas en [{},{}]", c.getLigne(), c.getColonne());
                            return true;
                        }
                        return false;
                    }
                    
                    /*voisins bas et gauche */
                    else if (((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT && 
                             ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT &&
                             ((Arete)g.getCase(c.getLigne(), c.getColonne()+5)).getEtat() != EnumEtat.TRAIT) {
                        
                        logger.trace("3 en [{},{}] avec traits en bas et à gauche", c.getLigne(), c.getColonne());
                        /**vérifier si ça ne déborde pas */
                        if (c.getChiffreHaut().getChiffre() == 1 && c.getChiffreDroit().getChiffreDroit().getChiffre() == 3) {
                            logger.debug("Configuration potentielle: 3 en [{},{}] avec 1 en haut et 3 à droite-droite", 
                                         c.getLigne(), c.getColonne());
                            
                            // Vérification des arêtes requises
                            try {
                                if (((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()-1, c.getColonne()-2);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()-1, c.getColonne()-2);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()+2, c.getColonne()+1);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()+2, c.getColonne()+1);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()+2, c.getColonne()+3)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()+2, c.getColonne()+3);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()+2, c.getColonne()+3);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()+2, c.getColonne()+5)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()+2, c.getColonne()+5);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()+2, c.getColonne()+5);
                                d = false;
                            }
                            
                            logger.debug("TechniqueAvancee2 applicable: configuration bas-gauche en [{},{}]", c.getLigne(), c.getColonne());
                            return true;
                        }
                        return false;
                    }
                    
                    /*voisins gauche et haut */
                    else if (((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT && 
                             ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT &&
                             ((Arete)g.getCase(c.getLigne()+5, c.getColonne())).getEtat() != EnumEtat.TRAIT) {
                        
                        logger.trace("3 en [{},{}] avec traits à gauche et en haut", c.getLigne(), c.getColonne());
                        /**vérifier si ça ne déborde pas */
                        if (c.getChiffreDroit().getChiffre() == 1 && c.getChiffreBas().getChiffreBas().getChiffre() == 3) {
                            logger.debug("Configuration potentielle: 3 en [{},{}] avec 1 à droite et 3 en bas-bas", 
                                         c.getLigne(), c.getColonne());
                            
                            // Vérification des arêtes requises
                            try {
                                if (((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()-2, c.getColonne()+1);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()-2, c.getColonne()+1);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()+1, c.getColonne()-2);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()+1, c.getColonne()-2);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()+3, c.getColonne()-2)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()+3, c.getColonne()-2);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()+3, c.getColonne()-2);
                                d = false;
                            }
                            
                            try {
                                if (((Arete)g.getCase(c.getLigne()+5, c.getColonne()-2)).checkPlus() ) {
                                    logger.trace("Arête non valide en [{},{}]", c.getLigne()+5, c.getColonne()-2);
                                    return false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                logger.trace("Arête hors limites: [{},{}]", c.getLigne()+5, c.getColonne()-2);
                                d = false;
                            }
                            
                            logger.debug("TechniqueAvancee2 applicable: configuration gauche-haut en [{},{}]", c.getLigne(), c.getColonne());
                            return true;
                        }
                        return d;
                    }
                }
            }
        }
        logger.debug("TechniqueAvancee2 non applicable");
        return d;
    }
}