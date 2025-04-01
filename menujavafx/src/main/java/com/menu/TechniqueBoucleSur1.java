package com.menu;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation de la technique de résolution qui vérifie 
 * si un chiffre 1 a plus d'un trait adjacent.
 * Cette technique permet de détecter les violations de règles pour les chiffres 1
 * qui ne doivent avoir qu'un seul trait connecté.
 */
public class TechniqueBoucleSur1 implements Technique {
    
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(TechniqueBoucleSur1.class);
    
    /** Grille de jeu sur laquelle appliquer la technique */
    private Grille g;
    
    /**
     * Constructeur de la technique de détection des violations pour les chiffres 1.
     * 
     * @param g La grille sur laquelle appliquer la technique
     */
    public TechniqueBoucleSur1(Grille g) {
        this.g = g;
        logger.debug("Initialisation de TechniqueBoucleSur1");
    }

    /**
     * Fournit un message d'aide expliquant pourquoi cette technique est applicable.
     * 
     * @return Message d'aide pour l'utilisateur
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour TechniqueBoucleSur1");
        return("Technique boucle 1 applicable: \n\n Non respect des règles correspondant au chiffre 1, avec la disposition de votre grille, si vous continuez votre chemin il y aura un chiffre 1 avec plus qu'un trait autour de lui.");
    }

    /**
     * Vérifie si la technique est applicable à l'état actuel de la grille.
     * Parcourt tous les chiffres 1 de la grille et vérifie si l'un d'eux
     * a déjà un trait connecté et qu'un autre trait est en train d'être placé.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de TechniqueBoucleSur1");
        Iterator<Chiffre> it = g.iteratorChiffres();
        
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 1) {
                logger.debug("Analyse du chiffre 1 à la position ({},{})", c.getLigne(), c.getColonne());
                
                try {
                    if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).checkPlus() && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                           ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                            logger.info("Violation détectée pour chiffre 1 à ({},{}) - cas 1", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    logger.debug("Position hors limites pour le cas 1");
                }
                
                try {
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).checkPlus() && ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                           ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                            logger.info("Violation détectée pour chiffre 1 à ({},{}) - cas 2", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    logger.debug("Position hors limites pour le cas 2");
                }

                try {
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).checkPlus() && ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                           ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                            logger.info("Violation détectée pour chiffre 1 à ({},{}) - cas 3", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    logger.debug("Position hors limites pour le cas 3");
                }

                try {
                    if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).checkPlus() && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                           ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                            logger.info("Violation détectée pour chiffre 1 à ({},{}) - cas 4", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    logger.debug("Position hors limites pour le cas 4");
                }

                try {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).checkPlus() && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                           ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                            logger.info("Violation détectée pour chiffre 1 à ({},{}) - cas 5", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    logger.debug("Position hors limites pour le cas 5");
                }

                try {
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).checkPlus() && ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                           ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                            logger.info("Violation détectée pour chiffre 1 à ({},{}) - cas 6", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    logger.debug("Position hors limites pour le cas 6");
                }

                try {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).checkPlus() && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                           ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                            logger.info("Violation détectée pour chiffre 1 à ({},{}) - cas 7", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    logger.debug("Position hors limites pour le cas 7");
                }

                try {
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).checkPlus() && ((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT || 
                           ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                            logger.info("Violation détectée pour chiffre 1 à ({},{}) - cas 8", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    logger.debug("Position hors limites pour le cas 8");
                }
            }
        }
        
        logger.debug("Aucune violation détectée pour les chiffres 1");
        return false;
    }
}