package com.menu;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class AreteView extends Button{

    private Arete arete;
    
    private Image imTrait, imCroix;

    private ImageView iv;

    public AreteView(Arete arete){
        super();
        this.arete = arete;
        
        this.imTrait = new Image("file:src/main/ressources/trait" +  
        (arete.getOrientation() == EnumOrientation.VERTICAL ? "Vertical" : "Horizontal") +
        ".png");
        this.imCroix = new Image("file:src/main/ressources/croix.png");
        this.iv = new ImageView();
        this.setVide();

        iv.fitWidthProperty().bind(this.widthProperty());
        iv.fitHeightProperty().bind(this.heightProperty());
        this.setGraphic(iv);

        this.setMinSize(0,0);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setStyle("-fx-background-color: transparent;");
        this.setOnMousePressed(event -> {this.gererClic(event);});

        

        
    }

    public Arete getArete(){
        return this.arete;
    }

    public void setTrait(){
        this.arete.setEtat(EnumEtat.TRAIT);
        this.iv.setVisible(true);
        this.iv.setImage(imTrait);
    }

    public void setCroix(){
        this.arete.setEtat(EnumEtat.CROIX);
        this.iv.setVisible(true);
        this.iv.setImage(imCroix);
    }

    public void setVide(){
        this.arete.setEtat(EnumEtat.VIDE);
        this.iv.setVisible(false);
    }

    public void gererClic(MouseEvent event){
        if (this.isCurseurProche(event)){
            if (event.getButton() == MouseButton.PRIMARY)
            this.clicGauche();
        else if (event.getButton() == MouseButton.SECONDARY)
            this.clicDroit();
        }
    }

    public boolean isCurseurProche(MouseEvent event){
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();
        
        double sommetHautGaucheX = this.localToScene(this.getBoundsInLocal()).getMinX();
        double sommetBasDroiteX = this.localToScene(this.getBoundsInLocal()).getMaxX();
        double sommetHautGaucheY = this.localToScene(this.getBoundsInLocal()).getMinY();
        double sommetBasDroiteY = this.localToScene(this.getBoundsInLocal()).getMaxY();

        //calcul des autres sommets
        double sommetHautDroitX = sommetBasDroiteX;
        double sommetHautDroitY = sommetHautGaucheY;
        double sommetBasGaucheX = sommetHautGaucheX;
        double sommetBasGaucheY = sommetBasDroiteY;

        double distance1, distance2, distance = 0;
        if(this.getArete().getOrientation() == EnumOrientation.VERTICAL){
            // coté gauche
            distance1 = (sommetHautGaucheX + sommetHautGaucheY) - (mouseX + mouseY) ;
            distance2 = (sommetBasGaucheX - sommetBasGaucheY) - (mouseX - mouseY) ;
            double distanceGauche = Math.max(distance1, distance2);

            // coté droit
            distance1 = (mouseX - mouseY) - (sommetHautDroitX - sommetHautDroitY) ;
            distance2 = (mouseX + mouseY) - (sommetBasDroiteX + sommetBasDroiteY);
            double distanceDroit = Math.max(distance1, distance2);

            distance = Math.max(distanceGauche, distanceDroit);
            
        }
        else{
            // coté haut 
            distance1 = (sommetHautGaucheX + sommetHautGaucheY) - (mouseX + mouseY) ;
            distance2 = (mouseX - mouseY) - (sommetHautDroitX - sommetHautDroitY) ;
            double distanceHaut = Math.max(distance1, distance2);

            // coté bas
            distance1 = (sommetBasGaucheX - sommetBasGaucheY) - (mouseX - mouseY);
            distance2 = (mouseX + mouseY) - (sommetBasDroiteX + sommetBasDroiteY);
            double distanceBas = Math.max(distance1, distance2);

            distance = Math.max(distanceHaut, distanceBas);
        }

        
        return distance < 0;
    }

    public void clicGauche() {
        if (this.arete.getEtat() != EnumEtat.TRAIT) 
            this.setTrait();
        else 
            this.setVide();
    }

    public void clicDroit() {
        if (this.arete.getEtat() != EnumEtat.CROIX) 
            this.setCroix();
        else 
            this.setVide();
    }


    

}
