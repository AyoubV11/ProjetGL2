package com.menu;

import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;

public class AreteView extends Button{

    private Arete arete;

    public AreteView(Arete arete){
        super();
        this.arete = arete;
        this.setMinSize(0,0);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 0; -fx-background-radius: 0;");
        this.setOnMouseClicked(e -> {
            if(this.arete.getEtat() == EnumEtat.VIDE){
                this.arete.setEtat(EnumEtat.TRAIT);
                this.setStyle("-fx-background-color: #000000;");
            } else if(this.arete.getEtat() == EnumEtat.TRAIT){
                this.arete.setEtat(EnumEtat.CROIX);
                this.setStyle("-fx-background-color: #ff0000;");
            } else {
                this.arete.setEtat(EnumEtat.VIDE);
                this.setStyle("-fx-background-color: #ffffff;");
            }
        });

        Polygon diamond = new Polygon();
        double size = 10; // Taille du losange
        diamond.getPoints().addAll(
            0.0, -size,  // Haut
            size, 0.0,   // Droite
            0.0, size,   // Bas
            -size, 0.0   // Gauche
        );
        diamond.getPoints().set(0, null);
    }

    public Arete getArete(){
        return this.arete;
    }
    

}
