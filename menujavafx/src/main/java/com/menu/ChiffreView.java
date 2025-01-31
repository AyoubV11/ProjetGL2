package com.menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ChiffreView extends ImageView{
    private Chiffre chiffre;

    public ChiffreView(Chiffre chiffre){
        super();
        Image image = new Image("file:src/main/ressources/chiffre" + 
        (chiffre.getChiffre() < 0 ? "vide" : chiffre.getChiffre())
         + ".png");
        this.setImage(image);   
        //taille max imposé par les contraintes de la grille
        this.setFitWidth(10);
        this.setPreserveRatio(true);
        // Lier les propriétés de taille de l'ImageView aux propriétés de taille de la cellule

        
        // this.fitWidthProperty().bind(((GridPane)this.getParent()).layoutXProperty());
        // this.fitHeightProperty().bind(((GridPane)this.getParent()).layoutYProperty());
        // ca ne ma

        this.chiffre = chiffre;
    }

    public Chiffre getChiffre(){
        return this.chiffre;
    }


    
}
