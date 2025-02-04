package com.menu;

import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;

public class AreteView extends Button{

    private Arete arete;
    private boolean curseurProche; //

    public AreteView(Arete arete){
        super();
        this.curseurProche = false;
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

        
    }

    public Arete getArete(){
        return this.arete;
    }

    public boolean getCurseurProche(){
        return this.curseurProche;
    }

    public void setCurseurProche(boolean curseurProche){
        this.curseurProche = curseurProche;
    }
    

}
