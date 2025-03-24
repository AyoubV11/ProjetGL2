package com.menu;

import javafx.scene.control.Button;

public abstract class Technique {
    protected Grille g;


    public Technique(Grille g){
        this.g=g;
    }

    public void afficherAide(){
        Button aideButton = ButtonFactory.createAnimatedButton("AIDE");
        aideButton.setPrefWidth(120);
        
        this.g.sceneJeu.getRightBox().getChildren().addAll(aideButton);
    }
    
    public boolean applicable(){
        return true;
    }
}