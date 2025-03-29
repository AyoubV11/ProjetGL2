package com.menu;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation de la technique de résolution qui vérifie 
 * les contraintes spécifiques pour deux chiffres 3 adjacents.
 * Cette technique permet d'identifier les situations où deux chiffres 3
 * côte à côte nécessitent une disposition particulière des traits.
 */
public class TechniqueDeux3Adjacent implements Technique {
    
    private static final Logger logger = LoggerFactory.getLogger(TechniqueDeux3Adjacent.class);
    private Grille g;
    
    /**
     * Constructeur de la technique pour deux chiffres 3 adjacents.
     * 
     * @param g La grille sur laquelle appliquer la technique
     */
    public TechniqueDeux3Adjacent(Grille g) {
        this.g = g;
        logger.debug("Initialisation de TechniqueDeux3Adjacent");
    }

    /**
     * Fournit un message d'aide expliquant pourquoi cette technique est applicable.
     * 
     * @return Message d'aide pour l'utilisateur
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour TechniqueDeux3Adjacent");
        return("Technique deux 3 adjacent applicable: \n\n Si deux 3 sont à côté, il faut placer des traits comme sur l'image.");
    }

    /**
     * Vérifie si la technique est applicable à l'état actuel de la grille.
     * Parcourt tous les chiffres 3 de la grille et vérifie s'ils sont adjacents
     * à un autre chiffre 3 et si les contraintes sont respectées.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de TechniqueDeux3Adjacent");
        Iterator<Chiffre> it = g.iteratorChiffres();
        
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 3) {
                logger.debug("Analyse du chiffre 3 à la position ({},{})", c.getLigne(), c.getColonne());
                
                // Vérification du chiffre au-dessus
                try {
                    if(c.getChiffreHaut().getChiffre() == 3) {
                        logger.debug("Chiffre 3 adjacent en haut détecté à ({},{})", c.getLigne()-2, c.getColonne());
                        
                        boolean traitCentre = ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                        boolean traitBas = ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                        boolean traitHaut = ((Arete)g.getCase(c.getLigne()-3, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                        
                        if(!traitCentre || !traitBas || !traitHaut) {
                            logger.info("Technique applicable pour deux 3 verticaux à ({},{}) - traits manquants", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (NullPointerException e) {
                    logger.debug("Pas de chiffre en haut ou hors limites");
                }
                
                // Vérification du chiffre à droite
                try {
                    if(c.getChiffreDroit().getChiffre() == 3) {
                        logger.debug("Chiffre 3 adjacent à droite détecté à ({},{})", c.getLigne(), c.getColonne()+2);
                        
                        boolean traitGauche = ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                        boolean traitCentre = ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                        boolean traitDroit = ((Arete)g.getCase(c.getLigne(), c.getColonne()+3)).getEtat() == EnumEtat.TRAIT;
                        
                        if(!traitGauche || !traitCentre || !traitDroit) {
                            logger.info("Technique applicable pour deux 3 horizontaux à ({},{}) - traits manquants", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (NullPointerException e) {
                    logger.debug("Pas de chiffre à droite ou hors limites");
                }
                
                // Vérification du chiffre en bas
                try {
                    if(c.getChiffreBas().getChiffre() == 3) {
                        logger.debug("Chiffre 3 adjacent en bas détecté à ({},{})", c.getLigne()+2, c.getColonne());
                        
                        boolean traitHaut = ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                        boolean traitCentre = ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                        boolean traitBas = ((Arete)g.getCase(c.getLigne()+3, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                        
                        if(!traitHaut || !traitCentre || !traitBas) {
                            logger.info("Technique applicable pour deux 3 verticaux à ({},{}) - traits manquants", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (NullPointerException e) {
                    logger.debug("Pas de chiffre en bas ou hors limites");
                }
                
                // Vérification du chiffre à gauche
                try {
                    if(c.getChiffreGauche().getChiffre() == 3) {
                        logger.debug("Chiffre 3 adjacent à gauche détecté à ({},{})", c.getLigne(), c.getColonne()-2);
                        
                        boolean traitGauche = ((Arete)g.getCase(c.getLigne(), c.getColonne()-3)).getEtat() == EnumEtat.TRAIT;
                        boolean traitCentre = ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                        boolean traitDroit = ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                        
                        if(!traitGauche || !traitCentre || !traitDroit) {
                            logger.info("Technique applicable pour deux 3 horizontaux à ({},{}) - traits manquants", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (NullPointerException e) {
                    logger.debug("Pas de chiffre à gauche ou hors limites");
                }
            }
        }
        
        logger.debug("Aucune configuration de deux 3 adjacents détectée");
        return false;
    }
}