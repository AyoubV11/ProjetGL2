package com.menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChiffreView extends ImageView{
    private Chiffre chiffre;

    public ChiffreView(Chiffre chiffre, GrilleController grille){
        super();
        this.chiffre = chiffre;
        if (chiffre.getChiffre() >= 0){
            Image image = new Image("chiffre" + chiffre.getChiffre() + ".png");
            this.setImage(image);   
        }
        // mettre les dimensions de l'image en fonction de la taille de sa cellule
        this.fitWidthProperty().bind(grille.widthProperty().multiply(grille.getLargeurChiffre() / 100));
        this.fitHeightProperty().bind(grille.heightProperty().multiply(grille.getLargeurChiffre() / 100));
    }

    public Chiffre getChiffre(){
        return this.chiffre;
    }


    
}
