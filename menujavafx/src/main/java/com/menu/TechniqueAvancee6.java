package com.menu;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation d'une technique avancée qui identifie les configurations spécifiques
 * impliquant un chiffre 1 avec une contrainte dans un coin, permettant de placer un trait
 * dans le coin diagonalement opposé.
 * Cette technique utilise des règles complexes pour déduire des placements de traits valides.
 */
public class TechniqueAvancee6 implements Technique {
    
    private static final Logger logger = LoggerFactory.getLogger(TechniqueAvancee6.class);
    private Grille g;
    
    /**
     * Constructeur de la technique avancée 6.
     * 
     * @param g la grille de jeu sur laquelle appliquer la technique
     */
    public TechniqueAvancee6(Grille g) {
        this.g = g;
        logger.debug("Initialisation de la TechniqueAvancee6");
    }

    /**
     * Renvoie le message d'aide à afficher à l'utilisateur.
     * 
     * @return une chaîne de caractères expliquant la technique
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour la TechniqueAvancee6");
        return("Technique avancée 6 disponible: \n\n Si un 1 a une contrainte (trait non-posable) dans un de ses coins, alors il est possible de poser un trait dans la case diagonalement opposée à ce coin si ce trait respecte les contraintes comme illustré sur l'image.");
    }

    /**
     * Vérifie si la technique est applicable dans l'état actuel de la grille.
     * Recherche les configurations où un chiffre 1 a une contrainte dans un coin,
     * ce qui permet de placer un trait dans le coin diagonalement opposé.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de la TechniqueAvancee6");
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 1) {
                logger.trace("Examen d'un chiffre 1 en [{},{}]", c.getLigne(), c.getColonne());

                /**haut droit */
                try {
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).checkPlus()) {
                        logger.trace("Contrainte détectée en haut à droite (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        try {
                            if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).checkPlus()) {
                                logger.trace("Contrainte détectée en haut à droite (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                                try{
                                    if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).checkPlus() && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).checkPlus()) {
                                        logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte en haut à droite", c.getLigne(), c.getColonne());
                                        return true;
                                    }
                                }catch(IndexOutOfBoundsException e){
                                    logger.error("Hors limites pour la contrainte en bas à gauche (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                                }
                                
                            }
                        } catch(IndexOutOfBoundsException e) {
                            logger.trace("Hors limites pour la contrainte en haut à droite (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            try{
                                if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).checkPlus() && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).checkPlus()) {
                                    logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte en haut à droite", c.getLigne(), c.getColonne());
                                    return true;
                                }
                            }catch(IndexOutOfBoundsException f){
                                logger.error("Hors limites pour la contrainte en bas à gauche (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            }
                        }
                    }
                } catch(IndexOutOfBoundsException e) {
                    logger.trace("Hors limites pour la contrainte en haut à droite (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                    try {
                        if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).checkPlus()) {
                            try{
                                if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).checkPlus() && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).checkPlus()) {
                                    logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte en haut à droite (cas limite alternatif)", c.getLigne(), c.getColonne());
                                    return true;
                                }
                            }catch(IndexOutOfBoundsException f){
                                logger.error("Hors limites pour la contrainte en bas à gauche (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            }
                        }
                    } catch(IndexOutOfBoundsException f) {
                        logger.trace("Hors limites pour les deux contraintes en haut à droite du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        try{
                            if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).checkPlus() && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).checkPlus()) {
                                logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contraintes en haut à droite (cas limite total)", c.getLigne(), c.getColonne());
                                return true;
                            }
                        }catch(IndexOutOfBoundsException g){
                            logger.error("Hors limites pour la contrainte en bas à gauche (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        }
                        
                    }  
                }



                /*droit bas */
                try {
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).checkPlus()) {
                        logger.trace("Contrainte détectée à droite en bas (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        try {
                            if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).checkPlus()) {
                                logger.trace("Contrainte détectée à droite en bas (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                                try{
                                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).checkPlus() && ((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).checkPlus()) {
                                        logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte à droite en bas", c.getLigne(), c.getColonne());
                                        return true;
                                    }
                                }catch(IndexOutOfBoundsException e){
                                    logger.trace("Hors limites pour la contrainte à droite en bas (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                                }
                                
                            }
                        } catch(IndexOutOfBoundsException e) {
                            logger.trace("Hors limites pour la contrainte en haut à droite (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            try{
                                if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).checkPlus() && ((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).checkPlus()) {
                                    logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte en haut à droite", c.getLigne(), c.getColonne());
                                    return true;
                                }
                            }catch(IndexOutOfBoundsException f){
                                logger.trace("Hors limites pour la contrainte à droite en bas (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            }
                        }
                    }
                } catch(IndexOutOfBoundsException e) {
                    logger.trace("Hors limites pour la contrainte en haut à droite (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                    try {
                        if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).checkPlus()) {
                            try{
                                if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).checkPlus() && ((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).checkPlus()) {
                                    logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte en haut à droite (cas limite alternatif)", c.getLigne(), c.getColonne());
                                    return true;
                                }
                            }catch(IndexOutOfBoundsException f){
                                logger.error("Hors limites pour la contrainte en bas à gauche (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            }
                        }
                    } catch(IndexOutOfBoundsException f) {
                        logger.trace("Hors limites pour les deux contraintes en haut à droite du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        try{
                            if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).checkPlus() && ((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).checkPlus()) {
                                logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contraintes en haut à droite (cas limite total)", c.getLigne(), c.getColonne());
                                return true;
                            }
                        }catch(IndexOutOfBoundsException g){
                            logger.error("Hors limites pour la contrainte en bas à gauche (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        }
                        
                    }  
                }


                /*bas gauche */
                try {
                    if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).checkPlus()) {
                        logger.trace("Contrainte détectée à droite en bas (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        try {
                            if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).checkPlus()) {
                                logger.trace("Contrainte détectée à droite en bas (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                                try{
                                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).checkPlus() && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).checkPlus()) {
                                        logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte à droite en bas", c.getLigne(), c.getColonne());
                                        return true;
                                    }
                                }catch(IndexOutOfBoundsException e){
                                    logger.trace("Hors limites pour la contrainte à droite en bas (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                                }
                                
                            }
                        } catch(IndexOutOfBoundsException e) {
                            logger.trace("Hors limites pour la contrainte en haut à droite (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            try{
                                if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).checkPlus() && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).checkPlus()) {
                                    logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte en haut à droite", c.getLigne(), c.getColonne());
                                    return true;
                                }
                            }catch(IndexOutOfBoundsException f){
                                logger.trace("Hors limites pour la contrainte à droite en bas (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            }
                        }
                    }
                } catch(IndexOutOfBoundsException e) {
                    logger.trace("Hors limites pour la contrainte en haut à droite (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                    try {
                        if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).checkPlus()) {
                            try{
                                if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).checkPlus() && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).checkPlus()) {
                                    logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte en haut à droite (cas limite alternatif)", c.getLigne(), c.getColonne());
                                    return true;
                                }
                            }catch(IndexOutOfBoundsException f){
                                logger.error("Hors limites pour la contrainte en bas à gauche (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            }
                        }
                    } catch(IndexOutOfBoundsException f) {
                        logger.trace("Hors limites pour les deux contraintes en haut à droite du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        try{
                            if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).checkPlus() && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).checkPlus()) {
                                logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contraintes en haut à droite (cas limite total)", c.getLigne(), c.getColonne());
                                return true;
                            }
                        }catch(IndexOutOfBoundsException g){
                            logger.error("Hors limites pour la contrainte en bas à gauche (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        }
                        
                    }  
                }


                /*gauche haut */
                try {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).checkPlus()) {
                        logger.trace("Contrainte détectée à droite en bas (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        try {
                            if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).checkPlus()) {
                                logger.trace("Contrainte détectée à droite en bas (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                                try{
                                    if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).checkPlus() && ((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).checkPlus()) {
                                        logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte à droite en bas", c.getLigne(), c.getColonne());
                                        return true;
                                    }
                                }catch(IndexOutOfBoundsException e){
                                    logger.trace("Hors limites pour la contrainte à droite en bas (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                                }
                                
                            }
                        } catch(IndexOutOfBoundsException e) {
                            logger.trace("Hors limites pour la contrainte en haut à droite (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            try{
                                if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).checkPlus() && ((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).checkPlus()) {
                                    logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte en haut à droite", c.getLigne(), c.getColonne());
                                    return true;
                                }
                            }catch(IndexOutOfBoundsException f){
                                logger.trace("Hors limites pour la contrainte à droite en bas (2) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            }
                        }
                    }
                } catch(IndexOutOfBoundsException e) {
                    logger.trace("Hors limites pour la contrainte en haut à droite (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                    try {
                        if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).checkPlus()) {
                            try{
                                if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).checkPlus() && ((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).checkPlus()) {
                                    logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contrainte en haut à droite (cas limite alternatif)", c.getLigne(), c.getColonne());
                                    return true;
                                }
                            }catch(IndexOutOfBoundsException f){
                                logger.error("Hors limites pour la contrainte en bas à gauche (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                            }
                        }
                    } catch(IndexOutOfBoundsException f) {
                        logger.trace("Hors limites pour les deux contraintes en haut à droite du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        try{
                            if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).checkPlus() && ((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).checkPlus()) {
                                logger.debug("TechniqueAvancee6 applicable: 1 en [{},{}] avec contraintes en haut à droite (cas limite total)", c.getLigne(), c.getColonne());
                                return true;
                            }
                        }catch(IndexOutOfBoundsException g){
                            logger.error("Hors limites pour la contrainte en bas à gauche (1) du 1 en [{},{}]", c.getLigne(), c.getColonne());
                        }
                        
                    }  
                }

            }
        }

        logger.debug("TechniqueAvancee6 non applicable");
        return false;
    }
}