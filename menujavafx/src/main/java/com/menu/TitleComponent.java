package com.menu;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Composant qui gère le titre du jeu Slither Link.
 * Permet de créer un élément Text formaté pour l'affichage du titre.
 */
public class TitleComponent {

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(TitleComponent.class);

    /**
     * Fonction qui crée le titre du jeu.
     * Charge la police personnalisée et configure le texte.
     * 
     * @return Text le titre formaté pour l'affichage
     */
    public Text getTitle() {
        logger.debug("Création du titre du jeu");
        try {
            Font baloo = Font.loadFont(getClass().getResourceAsStream("/Baloo2-Medium.ttf"), 50);
            logger.debug("Police 'Baloo2-Medium' chargée avec succès, taille 50");
            
            Text title = new Text("SLITHER LINK");
            title.setFont(baloo);
            title.setFill(Color.BLACK);
            
            logger.debug("Titre créé avec succès");
            return title;
        } catch (Exception e) {
            logger.error("Erreur lors du chargement de la police ou de la création du titre", e);
            // Création d'un titre de secours en cas d'erreur
            Text fallbackTitle = new Text("SLITHER LINK");
            fallbackTitle.setFill(Color.BLACK);
            return fallbackTitle;
        }
    }
}