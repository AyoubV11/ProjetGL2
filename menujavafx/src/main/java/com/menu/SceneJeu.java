package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Représente la scène principale du jeu.
 * Cette classe gère l'interface utilisateur du jeu, y compris la grille,
 * les contrôles, le chronomètre et les écrans de victoire.
 */
public class SceneJeu extends BorderPane {

    private static final Logger logger = LoggerFactory.getLogger(SceneJeu.class);
    
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
    private VBox tatonnementBox;

    /**
     * Constructeur de la scène de jeu.
     * 
     * @param stage la fenêtre principale de l'application
     * @param menu le menu principal
     * @param niveau le niveau de jeu à charger
     * @param libre indique si le mode de jeu est libre
     */
    public SceneJeu(Stage stage, Menu menu, int niveau, boolean libre) {
        this.menu = menu;
        this.primaryStage = stage;
        this.currentlevel = niveau;
        logger.info("Initialisation de la scène de jeu pour le niveau {}, mode libre: {}", niveau, libre);
        setupInterface(libre);
    }

    /**
     * Retourne la fenêtre principale.
     * 
     * @return la fenêtre principale
     */
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    /**
     * Définit une nouvelle boîte de droite et met à jour l'interface.
     * 
     * @param newBox la nouvelle boîte à afficher à droite
     */
    public void setRightBox(VBox newBox) {
        logger.debug("Mise à jour de la boîte de droite");
        this.boxes.getChildren().clear();
        this.rightBox = newBox;
        this.boxes.getChildren().addAll(this.tatonnementBox, this.leftBox, this.rightBox);
    }

    /**
     * Retourne la boîte de droite actuelle.
     * 
     * @return la boîte de droite
     */
    public VBox getRightBox() {
        return this.rightBox;
    }

    /**
     * Met à jour l'affichage du temps écoulé.
     * 
     * @param seconds le temps en secondes
     */
    public void setTemps(int seconds) {
        logger.trace("Mise à jour du temps affiché: {} secondes", seconds);
        timeLabel.setText("TEMPS : " + formatTime(seconds));
    }

    /**
     * Met à jour l'affichage du meilleur temps.
     * 
     * @param seconds le meilleur temps en secondes, ou -1 s'il n'y a pas de meilleur temps
     */
    public void setMeilleurTemps(int seconds) {
        if (seconds == -1) {
            logger.debug("Aucun meilleur temps défini");
            bestTimeLabel.setText("MEILLEUR TEMPS : AUCUN");
        } else {
            logger.debug("Mise à jour du meilleur temps: {} secondes", seconds);
            bestTimeLabel.setText("MEILLEUR TEMPS : " + formatTime(seconds));
        }
    }

    /**
     * Convertit un nombre de secondes en format heures:minutes:secondes.
     * 
     * @param totalSeconds le temps total en secondes
     * @return une chaîne au format HH:MM:SS
     */
    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    /**
     * Configure l'interface utilisateur du jeu.
     * 
     * @param libre indique si le mode de jeu est libre
     */
    private void setupInterface(boolean libre) {
        logger.debug("Configuration de l'interface du jeu, mode libre: {}", libre);
        
        topBar = new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER);

        // Utilisation de ButtonFactory avec des symboles textuels alternatifs
        Button restartButton = ButtonFactory.createAnimatedButton("Reset");  // R pour Restart/Recommencer
        Button helpButton = ButtonFactory.createAnimatedButton("Aide");
        Button validateButton = ButtonFactory.createAnimatedButton("Valider");  // V pour Valider
        Button leftArrow = ButtonFactory.createAnimatedButton("<-");      // < au lieu de ←
        Button rightArrow = ButtonFactory.createAnimatedButton("->");     // > au lieu de →
        Button settingButton = ButtonFactory.createAnimatedButton("Paramètres");  
        
        
        timeLabel = new Label("TEMPS : 00:00:00");
        timeLabel.setFont(BalooFont.setBalooSized(16));

        // Créer le groupe pour le chronomètre
        VBox timeGroup = new VBox(timeLabel);
        timeGroup.setAlignment(Pos.CENTER);
        
        // Définir la visibilité APRÈS avoir ajouté le label à son parent
        boolean showTimer = GameSettings.getInstance().isShowTimer();
        logger.debug("Affichage du chronomètre: {}", showTimer);
        timeLabel.setVisible(showTimer);
        timeGroup.setVisible(showTimer);

        // Création du label meilleur temps
        bestTimeLabel = new Label("MEILLEUR TEMPS : AUCUN");
        bestTimeLabel.setFont(BalooFont.setBalooSized(16));
        VBox bestScoreGroup = new VBox(bestTimeLabel);
        bestScoreGroup.setAlignment(Pos.CENTER);
    
        if (!libre) {
            topBar.getChildren().addAll(
                restartButton, helpButton, validateButton,
                timeGroup, bestScoreGroup,
                leftArrow, rightArrow, settingButton
            );
        } else {
            topBar.getChildren().addAll(
                restartButton, helpButton, validateButton,
                leftArrow, rightArrow, settingButton
            );
        }

        this.setTop(topBar);

        centerPane = new StackPane(); 
        centerPane.setPadding(new Insets(20));

        // Initialiser la grille avec la taille par défaut - en utilisant le constructeur qui prend un int
        logger.info("Chargement de la grille: grilleClassic{}.json", currentlevel);
        grille = new Grille("grilleClassic" + currentlevel + ".json", this, libre);
        grille.setSceneJeu(this);
        leftBox = new GrilleController(grille, gridSize, 0.2, this);
        leftBox.setPrefSize(gridSize, gridSize);
        leftBox.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-border-color: black;");

        rightBox = BoxFactory.createHelpButtonBox(grille.getListeAides(), primaryStage, grille);

        tatonnementBox = BoxFactory.createLeftBox(primaryStage, grille, this, validateButton);
        
        // Ajout de boutons dans le panneau de droite
        boxes = new HBox(50, tatonnementBox, leftBox, rightBox); 
        boxes.setAlignment(Pos.CENTER);

        centerPane.getChildren().add(boxes);
        this.setCenter(centerPane);

        leftArrow.setOnAction(e -> {
            logger.debug("Action: Annuler");
            grille.undo(); 
            leftBox.update();
        });
        
        rightArrow.setOnAction(e -> {
            logger.debug("Action: Refaire");
            grille.redo(); 
            leftBox.update();
        });
        
        restartButton.setOnAction(e -> {
            logger.debug("Action: Réinitialiser");
            grille.clear(libre); 
            leftBox.update();
        });

        validateButton.setOnAction(e -> {
            logger.debug("Action: Valider");
            grille.retablirEtatValide();
            leftBox.update();
        });

        settingButton.setOnAction(e -> {
            logger.debug("Action: Ouvrir les paramètres");
            openSettings(libre);
        }); 

        helpButton.setOnAction(e -> {
            logger.debug("Action: Demander une aide");
            grille.aide(libre);
        });

        if (!libre) {
            logger.debug("Démarrage du chronomètre (mode standard)");
            runTimer();
        }
    }

    /**
     * Retourne le contrôleur de la grille.
     * 
     * @return le contrôleur de la grille
     */
    public GrilleController getleftBox() {
        return this.leftBox;
    }

    /**
     * Ouvre le menu des paramètres.
     * 
     * @param libre indique si le mode de jeu est libre
     */
    private void openSettings(boolean libre) {
        logger.debug("Ouverture du menu des paramètres");
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
            Parametres parametresScene = new Parametres(paramStage, primaryStage, this, libre);
            
            Scene paramScene = new Scene(parametresScene, 400, 400);
            paramScene.setFill(Color.TRANSPARENT);
            paramStage.setScene(paramScene);
            
            logger.debug("Création d'une nouvelle fenêtre de paramètres");
        } else {
            // Mettre à jour la référence à la scène de jeu si la fenêtre existe déjà
            ((Parametres)paramStage.getScene().getRoot()).sceneJeu = this;
            logger.debug("Réutilisation de la fenêtre de paramètres existante");
        }

        paramStage.setOnHidden(e -> {
            this.setEffect(null); // Enlever l'effet de flou quand on ferme la fenêtre
            logger.debug("Fermeture du menu des paramètres");
        });
        
        // Afficher le menu des paramètres
        paramStage.show();
    }
        
    /**
     * Met à jour la visibilité du chronomètre en fonction des paramètres.
     * 
     * @param libre indique si le mode de jeu est libre
     */
    public void updateTimerVisibility(boolean libre) {
        boolean showTimer = GameSettings.getInstance().isShowTimer();
        logger.debug("Mise à jour de la visibilité du chronomètre: {}", showTimer);
        
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
            VBox bestScoreGroup = new VBox(bestTimeLabel);
            bestScoreGroup.setAlignment(Pos.CENTER);
            bestScoreGroup.setVisible(showTimer);
            bestScoreGroup.setManaged(showTimer); // Également géré par le paramètre du timer
            
            // Réajouter les éléments à la barre du haut
            if (!libre) {
                topBar.getChildren().addAll(
                    restartButton, helpButton, validateButton,
                    timeGroup, bestScoreGroup,
                    leftArrow, rightArrow, settingButton
                );
            } else {
                topBar.getChildren().addAll(
                    restartButton, helpButton, validateButton,
                    leftArrow, rightArrow, settingButton
                );
            }
            
            // Remettre les actions sur les boutons
            leftArrow.setOnAction(e -> {
                logger.debug("Action: Annuler");
                grille.undo(); 
                leftBox.update();
            });
            
            rightArrow.setOnAction(e -> {
                logger.debug("Action: Refaire");
                grille.redo(); 
                leftBox.update();
            });
            
            restartButton.setOnAction(e -> {
                logger.debug("Action: Réinitialiser");
                grille.clear(libre); 
                leftBox.update();
            });

            validateButton.setOnAction(e -> {
                logger.debug("Action: Valider");
                grille.retablirEtatValide();
                leftBox.update();
            });

            settingButton.setOnAction(e -> {
                logger.debug("Action: Ouvrir les paramètres");
                openSettings(libre);
            }); 

            helpButton.setOnAction(e -> {
                logger.debug("Action: Demander une aide");
                grille.aide(libre);
            });

            if (!libre) {
                logger.debug("Redémarrage du chronomètre (mode standard)");
                runTimer();
            }
        }
        
        // Force le rafraîchissement de la disposition!
        this.requestLayout();
    }

    /**
     * Affiche l'écran de fin pour le mode libre.
     */
    public void finishScreen() {
        logger.info("Affichage de l'écran de fin (mode libre)");
        // Créer une boîte stylisée pour l'affichage de la victoire
        VBox finishBox = BoxFactory.createFinishBox(this, "Partie terminée !!!");
        
        grille.initialiserAides();
        grille.sauvegarderAidesLibre();

        // Centrer la boîte de victoire
        StackPane.setAlignment(finishBox, Pos.CENTER);
        
        // Ajouter la boîte de victoire au centre
        this.centerPane.getChildren().add(finishBox);

        grille.clear(true);
    }

    /**
     * Affiche l'écran de victoire et met à jour les scores.
     */
    public void victoryScreen() {
        logger.info("Affichage de l'écran de victoire pour le niveau {}", currentlevel);
        // Arrêt du chronomètre
        grille.stopTimer();
        grille.updateMeilleurTemps();

        BoxFactory.majListeBits(this.currentlevel - 1, 1);

        if (grille.nbAides() <= 2) {
            logger.debug("Objectif 'Max 2 aides' débloqué");
            BoxFactory.majListeBits(this.currentlevel - 1, 2);
        }

        // Reset Aides utilisées
        grille.initialiserAides();
        grille.sauvegarderAides();

        int time = this.grille.tempsSauvegarde.getTemps();
        if (time <= 180) {
            logger.debug("Objectif 'Temps <= 3 minutes' débloqué");
            BoxFactory.majListeBits(this.currentlevel - 1, 3);
        }
        
        // Créer une boîte stylisée pour l'affichage de la victoire
        VBox victoryBox = BoxFactory.createVictoryBox(this, this.currentlevel, "/star_completed.png", "/star_uncompleted.png");
        
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

        // Remise à zéro de la grille
        grille.clear(false);
        
        // Déverrouiller le niveau suivant si nécessaire
        ButtonFactory.unlockLevel(currentlevel + 1);
        logger.info("Niveau {} déverrouillé", currentlevel + 1);

        grille.resetTimer();
        grille.sauvegarderTemps();
    }

    /**
     * Démarre le chronomètre.
     */
    public void runTimer() {
        logger.debug("Démarrage du chronomètre");
        grille.runTimer();
    }

    /**
     * Arrête le chronomètre.
     */
    public void stopTimer() {
        logger.debug("Arrêt du chronomètre");
        grille.stopTimer();
    }

    /**
     * Retourne le label affichant le temps.
     * 
     * @return le label de temps
     */
    public Label getTimeLabel() {
        return this.timeLabel;
    }

    /**
     * Retourne le menu principal.
     * 
     * @return le menu principal
     */
    public Menu getMenu() {
        return this.menu;
    }
}