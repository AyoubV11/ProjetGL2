package com.menu;

import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation de la technique de résolution qui vérifie les contraintes
 * spécifiques pour les chiffres placés dans les coins de la grille.
 * Cette technique permet d'identifier les situations où un chiffre dans un coin
 * doit obligatoirement avoir certains traits autour de lui selon sa valeur.
 */
public class TechniqueNombreCoin implements Technique {
    
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(TechniqueNombreCoin.class);
    
    /** Grille de jeu sur laquelle appliquer la technique */
    private Grille g;
    
    /**
     * Constructeur de la technique pour les chiffres dans les coins.
     * 
     * @param g La grille sur laquelle appliquer la technique
     */
    public TechniqueNombreCoin(Grille g){
        this.g = g;
        logger.debug("Initialisation de TechniqueNombreCoin");
    }

    /**
     * Fournit un message d'aide expliquant pourquoi cette technique est applicable.
     * 
     * @return Message d'aide pour l'utilisateur
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour TechniqueNombreCoin");
        return("Technique nombre dans les coins applicable: \n\n Vérifiez que les nombres de chaque coin de la grille ont des traits autour d'eux comme sur l'image.");
    }

    /**
     * Vérifie si la technique est applicable à l'état actuel de la grille.
     * Examine les quatre coins de la grille et vérifie si les contraintes
     * spécifiques à chaque valeur de chiffre sont respectées.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de TechniqueNombreCoin");
        Iterator<Chiffre> it = g.iteratorChiffres();
        
        while(it.hasNext()) {
            Chiffre c = it.next();

            // Coin supérieur gauche
            if(c.getLigne() == 1 && c.getColonne() == 1){
                logger.debug("Analyse du chiffre {} au coin supérieur gauche", c.getChiffre());
                
                if(c.getChiffre() == 0) {
                    boolean traitBas = ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitDroit = ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT;
                    if(traitBas || traitDroit) {
                        logger.info("Contrainte violée pour chiffre 0 au coin supérieur gauche - trait présent");
                        return true;
                    }
                }
                if(c.getChiffre() == 1){
                    boolean traitGauche = ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitHaut = ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    if(traitGauche || traitHaut) {
                        logger.info("Contrainte violée pour chiffre 1 au coin supérieur gauche - traits extérieurs présents");
                        return true;
                    }
                }
                if(c.getChiffre() == 2){
                    boolean traitBas = ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitDroit = ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT;
                    if(!traitBas || !traitDroit) {
                        logger.info("Contrainte violée pour chiffre 2 au coin supérieur gauche - traits manquants");
                        return true;
                    }
                }
                if(c.getChiffre() == 3){
                    boolean traitGauche = ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitHaut = ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    if(!traitGauche || !traitHaut) {
                        logger.info("Contrainte violée pour chiffre 3 au coin supérieur gauche - traits manquants");
                        return true;
                    }
                }
            }

            // Coin supérieur droit
            if(c.getLigne() == 1 && c.getColonne() == g.getNbColonnes()-2){
                logger.debug("Analyse du chiffre {} au coin supérieur droit", c.getChiffre());
                
                if(c.getChiffre() == 0) {
                    boolean traitGauche = ((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT;
                    boolean traitBas = ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                    if(traitGauche || traitBas) {
                        logger.info("Contrainte violée pour chiffre 0 au coin supérieur droit - trait présent");
                        return true;
                    }
                }
                if(c.getChiffre() == 1){
                    boolean traitDroit = ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitHaut = ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    if(traitDroit || traitHaut) {
                        logger.info("Contrainte violée pour chiffre 1 au coin supérieur droit - traits extérieurs présents");
                        return true;
                    }
                }
                if(c.getChiffre() == 2){
                    boolean traitBas = ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitGauche = ((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT;
                    if(!traitBas || !traitGauche) {
                        logger.info("Contrainte violée pour chiffre 2 au coin supérieur droit - traits manquants");
                        return true;
                    }
                }
                if(c.getChiffre() == 3){
                    boolean traitDroit = ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitHaut = ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    if(!traitDroit || !traitHaut) {
                        logger.info("Contrainte violée pour chiffre 3 au coin supérieur droit - traits manquants");
                        return true;
                    }
                }
            }

            // Coin inférieur gauche
            if(c.getLigne() == g.getNbLignes()-2 && c.getColonne() == 1){
                logger.debug("Analyse du chiffre {} au coin inférieur gauche", c.getChiffre());
                
                if(c.getChiffre() == 0) {
                    boolean traitHaut = ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitDroit = ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT;
                    if(traitHaut || traitDroit) {
                        logger.info("Contrainte violée pour chiffre 0 au coin inférieur gauche - trait présent");
                        return true;
                    }
                }
                if(c.getChiffre() == 1){
                    boolean traitGauche = ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitBas = ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    if(traitGauche || traitBas) {
                        logger.info("Contrainte violée pour chiffre 1 au coin inférieur gauche - traits extérieurs présents");
                        return true;
                    }
                }
                if(c.getChiffre() == 2){
                    boolean traitHaut = ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitDroit = ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT;
                    if(!traitHaut || !traitDroit) {
                        logger.info("Contrainte violée pour chiffre 2 au coin inférieur gauche - traits manquants");
                        return true;
                    }
                }
                if(c.getChiffre() == 3){
                    boolean traitGauche = ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitBas = ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    if(!traitGauche || !traitBas) {
                        logger.info("Contrainte violée pour chiffre 3 au coin inférieur gauche - traits manquants");
                        return true;
                    }
                }
            }

            // Coin inférieur droit
            if(c.getLigne() == g.getNbLignes()-2 && c.getColonne() == g.getNbColonnes()-2){
                logger.debug("Analyse du chiffre {} au coin inférieur droit", c.getChiffre());
                
                if(c.getChiffre() == 0) {
                    boolean traitGauche = ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT;
                    boolean traitHaut = ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                    if(traitGauche || traitHaut) {
                        logger.info("Contrainte violée pour chiffre 0 au coin inférieur droit - trait présent");
                        return true;
                    }
                }
                if(c.getChiffre() == 1){
                    boolean traitDroit = ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitBas = ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    if(traitDroit || traitBas) {
                        logger.info("Contrainte violée pour chiffre 1 au coin inférieur droit - traits extérieurs présents");
                        return true;
                    }
                }
                if(c.getChiffre() == 2){
                    boolean traitHaut = ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitGauche = ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT;
                    if(!traitHaut || !traitGauche) {
                        logger.info("Contrainte violée pour chiffre 2 au coin inférieur droit - traits manquants");
                        return true;
                    }
                }
                if(c.getChiffre() == 3){
                    boolean traitDroit = ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                    boolean traitBas = ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    if(!traitDroit || !traitBas) {
                        logger.info("Contrainte violée pour chiffre 3 au coin inférieur droit - traits manquants");
                        return true;
                    }
                }
            }
        }

        logger.debug("Aucune contrainte violée pour les chiffres dans les coins");
        return false;
    }
}