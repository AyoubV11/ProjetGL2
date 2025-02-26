package com.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoxFactory {

    private static int listeBits[] = new int[12];


    /**
     * Charge les étoiles obtenues dans chaque niveau
     * Si c'est la première fois que le joueur lance le jeu,
     * tout est initialisé avec des zéros.
     */
    public static void initialiserListe(){
        int i;
        for (i=0;i<12;i++){
            listeBits[i]=0;
        }
    }

    
    /** 
     * Met à jour le nombre d'étoiles d'un niveau
     * @param indice le i-ème niveau
     * @param nbEtoile l'étoile obtenue
     */
    public static void majListeBits(int indice, int nbEtoile){
        
        //tester les etoiles actuellement débloquées sur le niveau, et ajouter le bon score si c'est possible
        switch(listeBits[indice]){
            case 0: 
        }

        if(listeBits[indice] + Math.pow(2,nbEtoile) <=7){
            listeBits[indice] += Math.pow(2,nbEtoile);
        }
        
    }
    
    /** 
     * Associe la Box du menu contenant les boutons "Classique", "Libre", "Techniques" et "Paramètres" avec le menu principal,
     * et définit ses dimensions ainsi que son alignement.
     * @param menuBoxComponent la box contenant les 4 boutons.
     * @return VBox la Box créée.
     */
    public static VBox setupMenuBox(MenuBoxComponent menuBoxComponent ) {
        VBox menu = menuBoxComponent.getMenuBox();
        menu.setPrefSize(220, 260);
        menu.setMinSize(220, 260);
        menu.setMaxSize(220, 260);
        menu.setAlignment(Pos.CENTER);
        return menu;
    }

    
    /** 
     * Crée la box du menu des paramètres du jeu.
     * @param menu le contenant principal du jeu, qui contient les autres Box.
     * @return VBox la Box créée.
     */
    public static VBox createSettingsBox(Menu menu) {
        VBox settingsBox = createStyledBox(220, 260);
        
        HBox autoCrossToggle = ButtonFactory.createToggleButton("Croix auto : ", "OFF", "ON");
        Label volumeLabel = new Label("Volume : 50%");
        volumeLabel.setFont(BalooFont.setBalooSized(18));
        Slider volumeSlider = ButtonFactory.createVolumeSlider(volumeLabel);
        
        Button retourButton = ButtonFactory.createAnimatedButton("RETOUR");
        retourButton.setPrefWidth(200);
        retourButton.setOnAction(e -> menu.showMainMenu());
        
        settingsBox.getChildren().addAll(autoCrossToggle, volumeLabel, volumeSlider, retourButton);
        return settingsBox;
    }

    
    /** 
     * Crée une VBox de taille fixe selon les valeurs rentrées en paramètres.
     * @param width la largeur voulue.
     * @param height la hauteur voulue.
     * @return VBox la Box créée.
     */
    public static VBox createStyledBox(int width, int height) {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 20; -fx-border-radius: 15; -fx-background-radius: 15;");
        box.setPrefSize(width, height);
        box.setMinWidth(width);
        box.setMaxWidth(width);
        box.setMinHeight(height);
        box.setMaxHeight(height);
        return box;
    }

    
    /** 
     * Crée l'écran qui s'affiche avant de lancer un niveau.
     * Cet écran montre quelles étoiles ont été obtenues pour ce niveau, et comment les obtenir.
     * @param menu le contenant principal du jeu, qui contient les autres Box.
     * @param pathCompleted le chemin vers l'image de l'étoile d'un défi complété.
     * @param pathUncompleted le chemin vers l'image de l'étoile d'un défi non-complété.
     * @param niveau le numéro du niveau.
     * @return VBox la Box créée.
     */
    public static VBox createLevelBox(Menu menu,String pathCompleted, String pathUncompleted, int niveau) {
        VBox levelBox = createStyledBox(440, 270);
        HBox starsBox = new HBox(30);
        switch(listeBits[niveau-1]){
            case 0: starsBox.getChildren().addAll(createStar(pathUncompleted),createStar(pathUncompleted),createStar(pathUncompleted));
                    break;
            case 1: starsBox.getChildren().addAll(createStar(pathCompleted),createStar(pathUncompleted),createStar(pathUncompleted));
                    break;
            case 3: starsBox.getChildren().addAll(createStar(pathCompleted),createStar(pathCompleted),createStar(pathUncompleted));
                    break;
            case 5: starsBox.getChildren().addAll(createStar(pathCompleted),createStar(pathUncompleted),createStar(pathCompleted));
                    break;
            case 7: starsBox.getChildren().addAll(createStar(pathCompleted),createStar(pathCompleted),createStar(pathCompleted));
                    break;
        }
        starsBox.setAlignment(Pos.CENTER);

        HBox descriptionBox = new HBox(30,createDescription("Finir le niveau"),createDescription("Moins de 2 aides"),createDescription("Moins de 3min"));
        descriptionBox.setAlignment(Pos.CENTER);
        
        Button retourButton = ButtonFactory.createAnimatedButton("RETOUR");
        retourButton.setOnAction(e -> menu.showClassicMenu());
        retourButton.setPrefWidth(200);
        Button jouerButton = ButtonFactory.createAnimatedButton("JOUER");
        jouerButton.setOnAction(e -> menu.showSettings());
        jouerButton.setPrefWidth(200);
        
        HBox buttonBox = new HBox(30, retourButton, jouerButton);
        buttonBox.setAlignment(Pos.BOTTOM_LEFT);
        buttonBox.setPadding(new Insets(10,10,10,10));
        
        levelBox.getChildren().addAll(starsBox,descriptionBox, buttonBox);
        return levelBox;
    }

     
     /** 
      * Crée la description du défi permettant d'obtenir une étoile.
      * @param description le texte de la description.
      * @return Label contenant le texte de description stylisé comme nous le voulons. 
      */
     private static Label createDescription(String description){
        Label starDescription = new Label(description);
        starDescription.setFont(BalooFont.setBalooSized(16));
        starDescription.setAlignment(Pos.CENTER);
        return starDescription;
    }

    
    /** 
     * Crée l'image d'une étoile, d'une taille prédéfinie.
     * @param imagePath le chemin vers l'image de l'étoile
     * @return ImageView l'image de l'étoile avec les bonnes dimensions.
     */
    private static ImageView createStar(String imagePath) {
        ImageView star = new ImageView(new Image(imagePath));
        star.setFitWidth(100);
        star.setFitHeight(100);
        return star;
    }

}
