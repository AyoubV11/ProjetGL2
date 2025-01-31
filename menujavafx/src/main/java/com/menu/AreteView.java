package com.menu;

import javafx.scene.control.Button;

public class AreteView extends Button{

    private Arete arete;

    public AreteView(Arete arete){
        super();
        this.arete = arete;
        this.setPrefSize(100, 100);
        this.setStyle("-fx-background-color: #ffffff;");
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
    

}
