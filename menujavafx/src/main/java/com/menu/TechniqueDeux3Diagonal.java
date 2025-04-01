package com.menu;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation de la technique de résolution qui vérifie 
 * les contraintes spécifiques pour deux chiffres 3 en diagonale.
 * Cette technique permet d'identifier les situations où deux chiffres 3
 * en diagonale nécessitent une disposition particulière des traits.
 */
public class TechniqueDeux3Diagonal implements Technique {
    
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(TechniqueDeux3Diagonal.class);
    
    /** Grille de jeu sur laquelle appliquer la technique */
    private Grille g;
    
    /**
     * Constructeur de la technique pour deux chiffres 3 en diagonale.
     * 
     * @param g La grille sur laquelle appliquer la technique
     */
    public TechniqueDeux3Diagonal(Grille g) {
        this.g = g;
        logger.debug("Initialisation de TechniqueDeux3Diagonal");
    }

    /**
     * Fournit un message d'aide expliquant pourquoi cette technique est applicable.
     * 
     * @return Message d'aide pour l'utilisateur
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour TechniqueDeux3Diagonal");
        return("Technique deux 3 diagonal applicable: \n\n Si deux 3 sont en diagonale, on doit placer des traits comme sur l'image.");
    }

    /**
     * Vérifie si la technique est applicable à l'état actuel de la grille.
     * Parcourt tous les chiffres 3 de la grille et vérifie s'ils sont en diagonale
     * par rapport à un autre chiffre 3 et si les contraintes sont respectées.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de TechniqueDeux3Diagonal");
        Iterator<Chiffre> it = g.iteratorChiffres();
        
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 3) {
                logger.debug("Analyse du chiffre 3 à la position ({},{})", c.getLigne(), c.getColonne());
                
                // Vérification du chiffre en haut à gauche
                try {
                    if(c.getChiffreHautGauche().getChiffre() == 3) {
                        logger.debug("Chiffre 3 diagonal en haut à gauche détecté à ({},{})", c.getLigne()-2, c.getColonne()-2);
                        
                        boolean traitDroit = ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                        boolean traitBas = ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                        boolean traitHautGaucheVert = ((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT;
                        boolean traitHautGaucheHoriz = ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-3)).getEtat() == EnumEtat.TRAIT;
                        
                        if(!traitDroit || !traitBas || !traitHautGaucheVert || !traitHautGaucheHoriz) {
                            logger.info("Technique applicable pour deux 3 en diagonale (haut-gauche) à ({},{})", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (NullPointerException e) {
                    logger.debug("Pas de chiffre en haut à gauche ou hors limites");
                }
                
                // Vérification du chiffre en haut à droite
                try {
                    if(c.getChiffreHautDroit().getChiffre() == 3) {
                        logger.debug("Chiffre 3 diagonal en haut à droite détecté à ({},{})", c.getLigne()-2, c.getColonne()+2);
                        
                        boolean traitGauche = ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                        boolean traitBas = ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                        boolean traitHautDroitVert = ((Arete)g.getCase(c.getLigne()-3, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT;
                        boolean traitHautDroitHoriz = ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).getEtat() == EnumEtat.TRAIT;
                        
                        if(!traitGauche || !traitBas || !traitHautDroitVert || !traitHautDroitHoriz) {
                            logger.info("Technique applicable pour deux 3 en diagonale (haut-droite) à ({},{})", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (NullPointerException e) {
                    logger.debug("Pas de chiffre en haut à droite ou hors limites");
                }
                
                // Vérification du chiffre en bas à droite
                try {
                    if(c.getChiffreBasDroit().getChiffre() == 3) {
                        logger.debug("Chiffre 3 diagonal en bas à droite détecté à ({},{})", c.getLigne()+2, c.getColonne()+2);
                        
                        boolean traitHaut = ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                        boolean traitGauche = ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                        boolean traitBasDroitVert = ((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT;
                        boolean traitBasDroitHoriz = ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+3)).getEtat() == EnumEtat.TRAIT;
                        
                        if(!traitHaut || !traitGauche || !traitBasDroitVert || !traitBasDroitHoriz) {
                            logger.info("Technique applicable pour deux 3 en diagonale (bas-droite) à ({},{})", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (NullPointerException e) {
                    logger.debug("Pas de chiffre en bas à droite ou hors limites");
                }
                
                // Vérification du chiffre en bas à gauche
                try {
                    if(c.getChiffreBasGauche().getChiffre() == 3) {
                        logger.debug("Chiffre 3 diagonal en bas à gauche détecté à ({},{})", c.getLigne()+2, c.getColonne()-2);
                        
                        boolean traitHaut = ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                        boolean traitDroit = ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                        boolean traitBasGaucheVert = ((Arete)g.getCase(c.getLigne()+3, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT;
                        boolean traitBasGaucheHoriz = ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).getEtat() == EnumEtat.TRAIT;
                        
                        if(!traitHaut || !traitDroit || !traitBasGaucheVert || !traitBasGaucheHoriz) {
                            logger.info("Technique applicable pour deux 3 en diagonale (bas-gauche) à ({},{})", c.getLigne(), c.getColonne());
                            return true;
                        }
                    }
                } catch (NullPointerException e) {
                    logger.debug("Pas de chiffre en bas à gauche ou hors limites");
                }
            }
        }
        
        logger.debug("Aucune configuration de deux 3 en diagonale détectée");
        return false;
    }
}