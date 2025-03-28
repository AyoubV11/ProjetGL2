package com.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class SceneJeu extends BorderPane {

    private Menu menu;
    private Stage primaryStage;
    private Stage paramStage;
    private GrilleController leftBox; 
    private Grille grille;
    private StackPane centerPane; 
    private HBox boxes; 
    private HBox topBar;
    private Label timeLabel;
    private Label bestTimeLabel; // 💡 Ajout du label meilleur temps
    private int gridSize = 500; // Taille par défaut de la grille (en int, pas en double)
    private int currentlevel;
    private VBox rightBox;

    public SceneJeu(Stage stage, Menu menu, int niveau,boolean libre) {
        this.menu = menu;
        this.primaryStage = stage;
        this.currentlevel = niveau;
        setupInterface(libre);
    }

    public Stage getPrimaryStage(){
        return this.primaryStage;
    }

    public void setRightBox(VBox newBox){
        this.boxes.getChildren().clear();
        this.rightBox=newBox;
        this.boxes.getChildren().addAll(this.leftBox,this.rightBox);
    }

    public VBox getRightBox(){
        return this.rightBox;
    }

    public void setTemps(int seconds) {
        timeLabel.setText("TEMPS : " + formatTime(seconds));
    }

    public void setMeilleurTemps(int seconds) {
        bestTimeLabel.setText("MEILLEUR TEMPS : " + formatTime(seconds));
    }

    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
        

    private void setupInterface(boolean libre) {
        topBar = new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER);

        // Utilisation de ButtonFactory avec des symboles textuels alternatifs
        Button restartButton = ButtonFactory.createAnimatedButton("Reset");  // R pour Restart/Recommencer
        Button helpButton = ButtonFactory.createAnimatedButton("Aide");
        Button validateButton = ButtonFactory.createAnimatedButton("Valider");  // V pour Valider
        Button leftArrow = ButtonFactory.createAnimatedButton("<-");      // < au lieu de ←
        Button rightArrow = ButtonFactory.createAnimatedButton("->");     // > au lieu de →
        Button settingButton = ButtonFactory.createAnimatedButton("Paramètres");  // P pour Paramètres
        
        
        timeLabel = new Label("TEMPS : 00:00:00");
        timeLabel.setFont(BalooFont.setBalooSized(16));

        // Créer le groupe pour le chronomètre
        VBox timeGroup = new VBox(timeLabel);
        timeGroup.setAlignment(Pos.CENTER);
        
        // Définir la visibilité APRÈS avoir ajouté le label à son parent
        boolean showTimer = GameSettings.getInstance().isShowTimer();
        timeLabel.setVisible(showTimer);
        timeGroup.setVisible(showTimer);
    
        
        if(!libre){
            topBar.getChildren().addAll(
                restartButton, helpButton, validateButton,
                timeGroup,
                leftArrow, rightArrow, settingButton
            );
        }
        else{
            topBar.getChildren().addAll(
                restartButton, helpButton, validateButton,
                leftArrow, rightArrow, settingButton
            );
        }

        // Création du label meilleur temps
        VBox bestScoreGroup = new VBox();
        bestScoreGroup.setAlignment(Pos.CENTER);

        if (!bestScoreGroup.getChildren().isEmpty() && !libre) {
            topBar.getChildren().add(bestScoreGroup);
        }

        this.setTop(topBar);

        centerPane = new StackPane(); 
        centerPane.setPadding(new Insets(20));

        // Initialiser la grille avec la taille par défaut - en utilisant le constructeur qui prend un int
        grille = new Grille("grilleClassic"+currentlevel+".json", this,libre);
        grille.setSceneJeu(this);
        leftBox = new GrilleController(grille, gridSize, 0.2, this);
        leftBox.setPrefSize(gridSize, gridSize);
        leftBox.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-border-color: black;");

        rightBox = BoxFactory.createHelpButtonBox(grille.getListeAides(), primaryStage, grille);
        
        // Ajout de boutons dans le panneau de droite

        boxes = new HBox(50, leftBox, rightBox); 
        boxes.setAlignment(Pos.CENTER);

        centerPane.getChildren().add(boxes);
        this.setCenter(centerPane);

        leftArrow.setOnAction(e -> {grille.undo(); leftBox.update();});
        rightArrow.setOnAction(e -> {grille.redo(); leftBox.update();});
        restartButton.setOnAction(e -> {grille.clear(libre); leftBox.update();});

        validateButton.setOnAction(e -> {
            System.out.println("Validation de la grille");
            if (grille.resolue()) {
                System.out.println("-Grille résolue");
            } else {
                System.out.println("-Grille non résolue");
            }

            int erreurs = grille.check();
            System.out.println("--" + erreurs + " aretes incorrectes");
            grille.retablirEtatValide();
            leftBox.update();
        });

        settingButton.setOnAction(e -> {
            openSettings(libre);
        }); 

        helpButton.setOnAction(e -> {
            grille.aide(libre);
        });

        if (!libre) {
            runTimer();
        }
    }

    private VBox createLabelOnly(String labelText) {
        Label label = new Label(labelText);
        label.setFont(BalooFont.setBalooSized(16));

        VBox vBox = new VBox(label);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void openSettings(boolean libre) {
        // Arrêter le chronomètre
        grille.stopTimer();
        
        // Appliquer un effet de flou sur le contenu du jeu
        this.setEffect(new javafx.scene.effect.GaussianBlur(10));
        
        // Créer un stage modal pour les paramètres
        if (paramStage == null) {
            paramStage = new Stage();
            paramStage.initModality(Modality.APPLICATION_MODAL);
            paramStage.initOwner(primaryStage);
            paramStage.initStyle(StageStyle.TRANSPARENT);
            
            // Centrer les paramètres sur la fenêtre principale
            paramStage.setX(primaryStage.getX() + primaryStage.getWidth()/2 - 200);
            paramStage.setY(primaryStage.getY() + primaryStage.getHeight()/2 - 150);
            
            // Empêcher le redimensionnement
            paramStage.setResizable(false);
            
            // Créer le contenu du menu des paramètres avec référence à la fenêtre principale ET la scène de jeu
            Parametres parametresScene = new Parametres(paramStage, primaryStage, this,libre); // Passer 'this' pour référencer la SceneJeu actuelle
            
            
            Scene paramScene = new Scene(parametresScene, 400, 400);
            paramScene.setFill(Color.TRANSPARENT);
            paramStage.setScene(paramScene);
        } else {
            // Mettre à jour la référence à la scène de jeu si la fenêtre existe déjà
            ((Parametres)paramStage.getScene().getRoot()).sceneJeu = this;
        }

        paramStage.setOnHidden(e -> {
            this.setEffect(null); // Enlever l'effet de flou quand on ferme la fenêtre
        });
        
        // Afficher le menu des paramètres
        paramStage.show();
    }
        
    /**
     * Met à jour la visibilité du chronomètre en fonction des paramètres
     */
    public void updateTimerVisibility(boolean libre) {
        boolean showTimer = GameSettings.getInstance().isShowTimer();
        
        // Mettre à jour la visibilité du label
        timeLabel.setVisible(showTimer);

        
        
        // Récupérer la barre du haut
        HBox topBar = (HBox) this.getTop();
        if (topBar != null) {
            // Nous allons reconstruire la barre du haut entièrement
            topBar.getChildren().clear();
            
            // Recréer les boutons
            Button restartButton = ButtonFactory.createAnimatedButton("Reset");
            Button helpButton = ButtonFactory.createAnimatedButton("Aide");
            Button validateButton = ButtonFactory.createAnimatedButton("Valider");
            Button leftArrow = ButtonFactory.createAnimatedButton("<-");
            Button rightArrow = ButtonFactory.createAnimatedButton("->");
            Button settingButton = ButtonFactory.createAnimatedButton("Paramètres");
            
            // Recréer le groupe pour le chronomètre
            VBox timeGroup = new VBox(timeLabel);
            timeGroup.setAlignment(Pos.CENTER);
            timeGroup.setVisible(showTimer);
            timeGroup.setManaged(showTimer); // Important pour que l'espace soit libéré si caché
            
            // Recréer le meilleur temps
            VBox bestScoreGroup = createLabelOnly("MEILLEUR TEMPS : 00:30:00");
            bestScoreGroup.setVisible(showTimer);
            bestScoreGroup.setManaged(showTimer); // Également géré par le paramètre du timer
            
            // Réajouter les éléments à la barre du haut
            topBar.getChildren().addAll(
                restartButton, helpButton, validateButton,
                timeGroup, bestScoreGroup,
                leftArrow, rightArrow, settingButton
            );
            
            // Remettre les actions sur les boutons
            validateButton.setOnAction(e -> {
                System.out.println("Validation de la grille");
                boolean resultat = grille.resolue();
                if (resultat) {
                    System.out.println("Grille correcte");
                } else {
                    System.out.println("Grille incorrecte");
                }
            });
    
            leftArrow.setOnAction(e -> {grille.undo(); leftBox.update();});
            rightArrow.setOnAction(e -> {grille.redo(); leftBox.update();});
            restartButton.setOnAction(e -> {grille.clear(false); leftBox.update();});

            settingButton.setOnAction(e -> {
                openSettings(libre);
            });
        }
        
        // Force le rafraîchissement de la disposition!
        this.requestLayout();
    }

    public void finishScreen(){
        // Créer une boîte stylisée pour l'affichage de la victoire
        VBox finishBox = BoxFactory.createFinishBox(this,"Partie terminée !!!");
        
        grille.initialiserAides();
        grille.sauvegarderAidesLibre();

        // Centrer la boîte de victoire
        StackPane.setAlignment(finishBox, Pos.CENTER);
        
        // Ajouter la boîte de victoire au centre
        this.centerPane.getChildren().add(finishBox);

        grille.clear(true);
    }

    public void victoryScreen() {
        // Arrêt du chronomètre
        grille.stopTimer();
        grille.updateMeilleurTemps();

        grille.initialiserAides();
        grille.sauvegarderAides();
        // //attendre une demi seconde pour voir la grille résolue
        // try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}

        BoxFactory.majListeBits(this.currentlevel -1, 1);

        if(grille.nbAides()<=2){
            BoxFactory.majListeBits(this.currentlevel-1, 2);
        }
        int time = this.grille.tempsSauvegarde.getTemps();
        if(time<=180){
            BoxFactory.majListeBits(this.currentlevel-1, 3);
        }

       
        
        // Créer une boîte stylisée pour l'affichage de la victoire
        VBox victoryBox = BoxFactory.createVictoryBox(this, this.currentlevel, "/star_completed.png","/star_uncompleted.png");
        
        // Appliquer un effet de flou sur le contenu du jeu
        this.topBar.setEffect(new GaussianBlur(10));
        this.boxes.setEffect(new GaussianBlur(10));
        
        // Désactivation des éléments clicables
        this.topBar.setDisable(true);
        this.boxes.setDisable(true);
        
        // Centrer la boîte de victoire
        StackPane.setAlignment(victoryBox, Pos.CENTER);
        
        // Ajouter la boîte de victoire au centre
        this.centerPane.getChildren().add(victoryBox);

        // // Remise à zéro de la grille
        grille.clear(false);

        
        // Déverrouiller le niveau suivant si nécessaire
        ButtonFactory.unlockLevel(currentlevel + 1);
        
        // Mise à jour du score dans BoxFactory si le temps est meilleur
        // Cette partie est à implémenter selon votre logique de sauvegarde des scores


        grille.resetTimer();
        grille.sauvegarderTemps();
    }

    public void runTimer() {
        grille.runTimer();
    }

    public void stopTimer() {
        grille.stopTimer();
    }

    public Label getTimeLabel(){
        return this.timeLabel;
    }

    public Menu getMenu(){
        return this.menu;
    }
}