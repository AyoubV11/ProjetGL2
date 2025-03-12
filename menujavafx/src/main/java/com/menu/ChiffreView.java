package com.menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Représente la vue graphique d'un chiffre dans l'interface utilisateur du jeu Slitherlink.
 * Étend ImageView pour afficher le chiffre avec une image.
 */
public class ChiffreView extends ImageView {
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
        
        // Charge l'image du chiffre si ce n'est pas une case vide
        if (!chiffre.estVide()){
            Image image = new Image("chiffre" + chiffre.getChiffre() + ".png");
            this.setImage(image);   
        }
        
        // Ajuste la taille de l'image en fonction de la taille de la cellule de la grille
        this.fitWidthProperty().bind(grille.widthProperty().multiply(grille.getLargeurChiffre() / 100));
        this.fitHeightProperty().bind(grille.heightProperty().multiply(grille.getLargeurChiffre() / 100));
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