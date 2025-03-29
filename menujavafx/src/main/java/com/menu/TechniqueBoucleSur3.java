package com.menu;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implémentation de la technique de résolution qui vérifie 
 * les configurations spécifiques pour les chiffres 3.
 * Cette technique permet d'identifier les situations où deux traits
 * autour d'un chiffre 3 doivent être obligatoirement placés d'une certaine façon.
 */
public class TechniqueBoucleSur3 implements Technique {
    
    private static final Logger logger = LoggerFactory.getLogger(TechniqueBoucleSur3.class);
    private Grille g;
    
    /**
     * Constructeur de la technique pour les chiffres 3.
     * 
     * @param g La grille sur laquelle appliquer la technique
     */
    public TechniqueBoucleSur3(Grille g) {
        this.g = g;
        logger.debug("Initialisation de TechniqueBoucleSur3");
    }

    /**
     * Fournit un message d'aide expliquant pourquoi cette technique est applicable.
     * 
     * @return Message d'aide pour l'utilisateur
     */
    public String afficherAide() {
        logger.debug("Affichage de l'aide pour TechniqueBoucleSur3");
        return "Technique boucle 3 applicable: \n\n Dans la disposition actuelle de la grille, il y a un 3 dont deux des traits autour de lui doivent être obligatoirement placés comme sur l'image.";
    }

    /**
     * Vérifie si la technique est applicable à l'état actuel de la grille.
     * Parcourt tous les chiffres 3 de la grille et vérifie s'ils respectent
     * les conditions pour appliquer cette technique.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable() {
        logger.debug("Vérification de l'applicabilité de TechniqueBoucleSur3");
        Iterator<Chiffre> it = g.iteratorChiffres();
        while (it.hasNext()) {
            Chiffre c = it.next();
            if (c.getChiffre() == 3) {
                logger.debug("Analyse du chiffre 3 à la position ({},{})", c.getLigne(), c.getColonne());
                if (checkBoucleSur3(c)) {
                    logger.info("Configuration spécifique détectée pour le chiffre 3 à ({},{})", c.getLigne(), c.getColonne());
                    return true;
                }
            }
        }
        logger.debug("Aucune configuration applicable trouvée pour les chiffres 3");
        return false;
    }

    /**
     * Vérifie si un chiffre 3 spécifique présente une configuration
     * où certains traits doivent être placés d'une certaine manière.
     * 
     * @param c Le chiffre à vérifier
     * @return true si la configuration spécifique est détectée, false sinon
     */
    private boolean checkBoucleSur3(Chiffre c) {
        // Tableau de directions pour vérifier les arêtes autour du chiffre
        // Chaque groupe de 4 représente une configuration à tester
        int[][] directions = {
            {-2, -1}, {-1, -2}, {1, 0}, {0, 1},
            {-1, 2}, {-2, 1}, {1, 0}, {0, -1},
            {2, 1}, {1, 2}, {-1, 0}, {0, -1},
            {1, -2}, {2, -1}, {-1, 0}, {0, 1},
            {-1, -2}, {-2, -1}, {1, 0}, {0, 1},
            {-2, 1}, {-1, 2}, {1, 0}, {0, -1},
            {1, 2}, {2, 1}, {-1, 0}, {0, -1},
            {2, -1}, {1, -2}, {-1, 0}, {0, 1}
        };

        logger.debug("Vérification des configurations pour le chiffre 3 à ({},{})", c.getLigne(), c.getColonne());
        for (int i = 0; i < directions.length; i += 4) {
            try {
                if (isTrait(c, directions[i]) &&
                    (isTrait(c, directions[i + 1]) || !isTrait(c, directions[i + 2]) || !isTrait(c, directions[i + 3]))) {
                    logger.debug("Configuration détectée à l'indice {} pour le chiffre 3 à ({},{})", i/4, c.getLigne(), c.getColonne());
                    return true;
                }
            } catch (IndexOutOfBoundsException e) {
                logger.debug("Position hors limites pour la configuration {} du chiffre 3 à ({},{})", i/4, c.getLigne(), c.getColonne());
            }
        }
        return false;
    }

    /**
     * Vérifie si une arête à une position relative donnée est un trait.
     * 
     * @param c Le chiffre de référence
     * @param d Tableau de deux entiers représentant le décalage en ligne et colonne
     * @return true si l'arête à cette position est un trait, false sinon
     * @throws IndexOutOfBoundsException si la position est en dehors de la grille
     */
    private boolean isTrait(Chiffre c, int[] d) {
        int ligne = c.getLigne() + d[0];
        int colonne = c.getColonne() + d[1];
        boolean result = ((Arete) g.getCase(ligne, colonne)).getEtat() == EnumEtat.TRAIT;
        logger.trace("Vérification trait à ({},{}) pour le chiffre 3 à ({},{}): {}", 
                  ligne, colonne, c.getLigne(), c.getColonne(), result);
        return result;
    }
}