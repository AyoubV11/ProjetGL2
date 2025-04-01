package com.menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Représente la vue graphique d'un chiffre dans l'interface utilisateur du jeu Slitherlink.
 * Étend ImageView pour afficher le chiffre avec une image.
 */
public class ChiffreView extends ImageView {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(ChiffreView.class);
    
    /**
     * Le modèle de chiffre associé à cette vue.
     */
    private Chiffre chiffre;

    /**
     * Constructeur de ChiffreView.
     * Initialise la vue du chiffre avec son image et ses dimensions.
     * 
     * @param chiffre Le chiffre du modèle à associer à cette vue
     * @param grille Le contrôleur de grille utilisé pour dimensionner l'image
     */
    public ChiffreView(Chiffre chiffre, GrilleController grille){
        super();
        this.chiffre = chiffre;
        
        logger.debug("Création d'une vue pour le chiffre à la position ({}, {}), valeur: {}", 
                   chiffre.getLigne(), chiffre.getColonne(), chiffre.getChiffre());
        
        // Charge l'image du chiffre si ce n'est pas une case vide
        if (!chiffre.estVide()){
            String imagePath = "chiffre" + chiffre.getChiffre() + ".png";
            logger.trace("Chargement de l'image: {}", imagePath);
            Image image = new Image(imagePath);
            this.setImage(image);   
        } else {
            logger.trace("Chiffre vide, aucune image chargée");
        }
        
        // Ajuste la taille de l'image en fonction de la taille de la cellule de la grille
        double largeurRelative = grille.getLargeurChiffre() / 100.0;
        logger.trace("Dimensionnement de l'image avec une largeur relative de {}", largeurRelative);
        this.fitWidthProperty().bind(grille.widthProperty().multiply(largeurRelative));
        this.fitHeightProperty().bind(grille.heightProperty().multiply(largeurRelative));
    }

    /**
     * Récupère le chiffre du modèle associé à cette vue.
     * 
     * @return Le chiffre du modèle
     */
    public Chiffre getChiffre(){
        return this.chiffre;
    }
}