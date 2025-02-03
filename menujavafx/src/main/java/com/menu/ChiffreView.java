package com.menu;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ChiffreView extends ImageView{
    private Chiffre chiffre;

    public ChiffreView(Chiffre chiffre, GrilleController grille){
        super();
        Image image = new Image("file:src/main/ressources/chiffre" + 
        (chiffre.getChiffre() < 0 ? "vide" : chiffre.getChiffre())
         + ".png");
        this.setImage(image);   
        //taille max imposé par les contraintes de la grille


        this.fitWidthProperty().bind(grille.widthProperty().multiply(grille.getLargeurChiffre() / 100));
        this.fitHeightProperty().bind(grille.heightProperty().multiply(grille.getLargeurChiffre() / 100));

        // Lier les propriétés de taille de l'ImageView aux propriétés de taille de la cellule

        
        // this.fitWidthProperty().bind(((GridPane)this.getParent()).layoutXProperty());
        // this.fitHeightProperty().bind(((GridPane)this.getParent()).layoutYProperty());


        this.chiffre = chiffre;
    }

    public Chiffre getChiffre(){
        return this.chiffre;
    }


    
}
