package com.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class BoxFactory {

    private static final String FILE_PATH = ".nb_Etoiles.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static int listeBits[] = new int[12];
    private static Stage techStage;


    public static void sauvegarderEtoiles() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), listeBits);
            System.out.println("Etoiles sauvegardées dans " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void chargerEtoiles() {
        try {
            listeBits=objectMapper.readValue(new File(FILE_PATH), int[].class);
        } catch (IOException e) {
            initialiserListe();
        }
    }
    

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
     * @param numEtoile l'étoile obtenue
     */
    public static void majListeBits(int indice, int numEtoile){
        
        //tester les etoiles actuellement débloquées sur le niveau, et ajouter le bon score si c'est possible
        switch(listeBits[indice]){
            case 0: listeBits[indice] = 1;break;
            case 1: if(numEtoile != 1){
               listeBits[indice]+= (int)Math.pow(2,numEtoile-1);
            }
            break;
            case 3: if(numEtoile == 3){
                listeBits[indice]=7;
            }
            break;
            case 5: if(numEtoile == 2){
                listeBits[indice]=7;
            }
            break;
            default: break;
        }

        BoxFactory.sauvegarderEtoiles();
        
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
        
        // Récupérer l'instance de GameSettings
        GameSettings settings = GameSettings.getInstance();
        
        // Créer le toggle button avec l'état actuel correct
        HBox autoCrossToggle = new HBox(10);
        Label autoCrossLabel = new Label("Croix auto :");
        autoCrossLabel.setFont(BalooFont.setBalooSized(18));
        
        ToggleButton toggle = new ToggleButton(settings.isAutoCroix() ? "ON" : "OFF");
        toggle.setSelected(settings.isAutoCroix());
        toggle.setFont(BalooFont.setBalooSized(18));
        toggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;");
        
        toggle.setOnMouseEntered(e -> toggle.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        toggle.setOnMouseExited(e -> toggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        
        toggle.setOnMousePressed(e -> ButtonFactory.animateButton(toggle, 1.1));
        toggle.setOnMouseReleased(e -> ButtonFactory.animateButton(toggle, 1.0));
        
        // Mettre à jour la valeur dans GameSettings ET le texte affiché
        toggle.setOnAction(e -> {
            settings.setAutoCroix(toggle.isSelected());
            toggle.setText(toggle.isSelected() ? "ON" : "OFF");
            System.out.println("Croix auto: " + (settings.isAutoCroix() ? "Activé" : "Désactivé"));
        });
        
        autoCrossToggle.getChildren().addAll(autoCrossLabel, toggle);
        autoCrossToggle.setAlignment(Pos.CENTER);
        
        // Reste du code inchangé...
        Label volumeLabel = new Label("Volume : " + settings.getVolume() + "%");
        volumeLabel.setFont(BalooFont.setBalooSized(18));
        Slider volumeSlider = ButtonFactory.createVolumeSlider(volumeLabel);
        volumeSlider.setValue(settings.getVolume());
        
        // Mettre à jour le volume dans GameSettings
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            settings.setVolume(newVal.intValue());
        });
        
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
        jouerButton.setOnAction(e -> menu.showGame(niveau));
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

    public static VBox createVictoryBox(SceneJeu scene, int niveau, String pathCompleted, String pathUncompleted){
        VBox victoryBox = BoxFactory.createStyledBox(450, 450);
        victoryBox.setSpacing(30);

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
        
        // Texte de victoire stylisé avec la police Baloo
        Label victoryText = new Label("VICTOIRE !");
        victoryText.setFont(BalooFont.setBalooSized(48));
        
        String chronoText = scene.getTimeLabel().getText().replace("TEMPS : ", "");
        Label timeLabel = new Label("Temps : " + chronoText);
        timeLabel.setFont(BalooFont.setBalooSized(24));
        
        // Créer un bouton pour retourner au menu
        Button retourMenuButton = ButtonFactory.createAnimatedButton("RETOUR AU MENU");
        retourMenuButton.setPrefWidth(200);
        retourMenuButton.setOnAction(e -> {
            scene.getMenu().showMenu();
        });

        
        // Ajouter les éléments à la boîte de victoire
        victoryBox.getChildren().addAll(victoryText,starsBox, descriptionBox, timeLabel, retourMenuButton);

        return victoryBox;
    }

    public static void showTechnique(int i, Stage primaryStage, Technique t){
        techStage = new Stage();
        techStage.initModality(Modality.APPLICATION_MODAL);
        techStage.initOwner(primaryStage);
        
        // Centrer les paramètres sur la fenêtre principale
        techStage.setX(primaryStage.getX() + primaryStage.getWidth()/2 - 200);

        techStage.setY(primaryStage.getY() + primaryStage.getHeight()/2 - 150);
        
        // Empêcher le redimensionnement
        techStage.setResizable(false);
        HBox horizontalbox=new HBox(10);
        horizontalbox.setAlignment(Pos.CENTER);
        horizontalbox.setMaxWidth(500);
        horizontalbox.setPadding(new Insets(10));
        ImageView technique_image = new ImageView(new Image("/images_techniques/technique_image_" + i + ".png"));
        technique_image.setFitWidth(200);
        technique_image.setFitHeight(200);

        Label texteAide = new Label(t.afficherAide());
        texteAide.setWrapText(true);
        texteAide.setTextAlignment(TextAlignment.CENTER);
        texteAide.setAlignment(Pos.CENTER);
        horizontalbox.getChildren().addAll(technique_image,texteAide);

        Scene scene = new Scene(horizontalbox);

        techStage.setTitle("Technique " + i);

        techStage.setScene(scene);
        techStage.showAndWait();
    }



    public static VBox createHelpButtonBox(boolean[] listeAides, Stage primaryStage, Grille grille){
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setPrefSize(175, 300);
        box.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-border-color: black;");
        box.setAlignment(Pos.TOP_CENTER);
        int i=1;
        for(Technique t : grille.techniques) {
            final int index=i;
            if (listeAides[i-1]){
                Button bouton = ButtonFactory.createAnimatedButton("TECHNIQUE " + i);
                bouton.setOnAction(e -> {
                    showTechnique(index,primaryStage,t);
                });

                box.getChildren().add(bouton);
            } 
            i++;
        }

        return box;
    }

}
