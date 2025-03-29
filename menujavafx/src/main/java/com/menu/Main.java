package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Point d'entrée principal de l'application Slither Link.
 * Cette classe sert de délégation vers le menu principal de l'application.
 */
public class Main {
    
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
    /**
     * Méthode principale qui démarre l'application.
     * Délègue le démarrage à la classe Menu.
     * 
     * @param args Arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        logger.info("Démarrage de l'application Slither Link");
        try {
            logger.debug("Délégation vers Menu.main()");
            Menu.main(args);
        } catch (Exception e) {
            logger.error("Erreur critique lors du démarrage de l'application", e);
        }
    }
}