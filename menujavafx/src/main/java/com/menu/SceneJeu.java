package com.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneJeu extends BorderPane {

    private Stage primaryStage;
    private Stage paramStage;
    private GrilleController leftBox; 
    private StackPane centerPane; 
    private HBox boxes; 
    private Chrono chrono;
    private Label timeLabel;
    private int gridSize = 400; // Taille par défaut de la grille (en int, pas en double)

    public SceneJeu(Stage stage) {
        this.primaryStage = stage;
        setupInterface();
    }

    private void setupInterface() {
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER);

        // Utilisation de ButtonFactory avec des symboles textuels alternatifs
        Button restartButton = ButtonFactory.createAnimatedButton("R");  // R pour Restart/Recommencer
        Button helpButton = ButtonFactory.createAnimatedButton("?");
        Button validateButton = ButtonFactory.createAnimatedButton("V");  // V pour Valider
        Button leftArrow = ButtonFactory.createAnimatedButton("<");      // < au lieu de ←
        Button rightArrow = ButtonFactory.createAnimatedButton(">");     // > au lieu de →
        Button settingButton = ButtonFactory.createAnimatedButton("P");  // P pour Paramètres
        
        // Ajuster la taille des boutons pour qu'ils soient plus compacts
        restartButton.setPrefSize(40, 40);
        helpButton.setPrefSize(40, 40);
        validateButton.setPrefSize(40, 40);
        leftArrow.setPrefSize(40, 40);
        rightArrow.setPrefSize(40, 40);
        settingButton.setPrefSize(40, 40);

        timeLabel = new Label("TEMPS : 00:00:00");
        timeLabel.setFont(BalooFont.setBalooSized(16));
        
        // Créer le groupe pour le chronomètre
        VBox timeGroup = new VBox(timeLabel);
        timeGroup.setAlignment(Pos.CENTER);
        
        // Initialiser et démarrer le chronomètre
        chrono = new Chrono(timeLabel);
        
        // Définir la visibilité APRÈS avoir ajouté le label à son parent
        boolean showTimer = GameSettings.getInstance().isShowTimer();
        timeLabel.setVisible(showTimer);
        timeGroup.setVisible(showTimer);
        
        chrono.start();
        
        VBox bestScoreGroup = createLabelOnly("MEILLEUR TEMPS : 00:30:00");

        topBar.getChildren().addAll(
            restartButton, helpButton, validateButton,
            timeGroup, bestScoreGroup,
            leftArrow, rightArrow, settingButton
        );

        this.setTop(topBar);

        centerPane = new StackPane(); 
        centerPane.setPadding(new Insets(20));

        // Initialiser la grille avec la taille par défaut - en utilisant le constructeur qui prend un int
        leftBox = new GrilleController(new Grille("grilleTest.json"), gridSize, 0.2);
        leftBox.setPrefSize(gridSize, gridSize);
        leftBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");

        VBox rightBox = new VBox(10);
        rightBox.setPadding(new Insets(10));
        rightBox.setPrefSize(150, 300);
        rightBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");
        rightBox.setAlignment(Pos.TOP_CENTER);
        
        // Ajout de boutons dans le panneau de droite
        Button aideButton = ButtonFactory.createAnimatedButton("AIDE");
        aideButton.setPrefWidth(120);
        
        rightBox.getChildren().addAll(aideButton);

        boxes = new HBox(50, leftBox, rightBox); 
        boxes.setAlignment(Pos.CENTER);

        centerPane.getChildren().add(boxes);
        this.setCenter(centerPane);

        validateButton.setOnAction(e -> {
            System.out.println("Validation de la grille");
            boolean resultat = leftBox.getGrille().check();
            if (resultat) {
                System.out.println("Grille correcte");
            } else {
                System.out.println("Grille incorrecte");
            }
        });

        restartButton.setOnAction(e -> {
            resetGrille();
        }); 

        settingButton.setOnAction(e -> {
            openSettings();
        }); 
    }

    private VBox createLabelOnly(String labelText) {
        Label label = new Label(labelText);
        label.setFont(BalooFont.setBalooSized(16));

        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private void openSettings() {
        // Arrêter le chronomètre pendant l'affichage des paramètres
        chrono.stop();
        
        // Appliquer un effet de flou sur le contenu du jeu
        this.setEffect(new javafx.scene.effect.GaussianBlur(10));
        
        // Créer un stage modal pour les paramètres
        if (paramStage == null) {
            paramStage = new Stage();
            paramStage.initModality(Modality.APPLICATION_MODAL); // Empêche l'interaction avec la fenêtre principale
            paramStage.initOwner(primaryStage);                  // Définit la fenêtre principale comme propriétaire
            paramStage.initStyle(StageStyle.TRANSPARENT);        // Fenêtre transparente pour les bords arrondis
            
            // Centrer les paramètres sur la fenêtre principale
            paramStage.setX(primaryStage.getX() + primaryStage.getWidth()/2 - 200);
            paramStage.setY(primaryStage.getY() + primaryStage.getHeight()/2 - 150);
            
            // Empêcher le redimensionnement
            paramStage.setResizable(false);
            
            // Créer le contenu du menu des paramètres avec référence à la fenêtre principale
            Parametres parametresScene = new Parametres(paramStage, primaryStage);
            
            // Ajouter un gestionnaire pour reprendre le chronomètre quand le menu est fermé
            paramStage.setOnHidden(e -> {
                chrono.start();
                this.setEffect(null); // Enlever l'effet de flou quand on ferme la fenêtre
            });
            
            Scene paramScene = new Scene(parametresScene, 400, 400); // Hauteur augmentée pour voir tous les éléments
            paramScene.setFill(Color.TRANSPARENT); // Nécessaire pour les bords arrondis
            paramStage.setScene(paramScene);
        } else {
            // Si la fenêtre existe déjà, s'assurer que l'effet de flou est supprimé à la fermeture
            paramStage.setOnHidden(e -> {
                chrono.start();
                this.setEffect(null);
            });
        }
        
        // Afficher le menu des paramètres
        paramStage.show();
    }
        
    /**
     * Met à jour la visibilité du chronomètre en fonction des paramètres
     */
    public void updateTimerVisibility() {
        boolean showTimer = GameSettings.getInstance().isShowTimer();
        
        // Récupérer directement les éléments depuis la barre du haut
        if (this.getTop() instanceof HBox) {
            HBox topBar = (HBox) this.getTop();
            
            // Trouver et mettre à jour la visibilité des deux éléments de temps
            for (int i = 0; i < topBar.getChildren().size(); i++) {
                javafx.scene.Node node = topBar.getChildren().get(i);
                
                // Vérifier si c'est un VBox (les deux éléments de temps sont dans des VBox)
                if (node instanceof VBox) {
                    VBox box = (VBox) node;
                    
                    // Vérifier le contenu pour déterminer si c'est un élément de temps
                    for (javafx.scene.Node child : box.getChildren()) {
                        if (child instanceof Label) {
                            Label label = (Label) child;
                            String text = label.getText();
                            
                            // Si c'est le temps courant ou le meilleur temps, mettre à jour sa visibilité
                            if (text.contains("TEMPS") || text.contains("MEILLEUR TEMPS")) {
                                box.setVisible(showTimer);
                                box.setManaged(showTimer); // Important pour que l'espace soit libéré
                                break;
                            }
                        }
                    }
                }
            }
        }
    
        // Mettre à jour également la visibilité du label (par sécurité)
        timeLabel.setVisible(showTimer);
        if (timeLabel.getParent() != null) {
            timeLabel.getParent().setVisible(showTimer);
            timeLabel.getParent().setManaged(showTimer);
        }
        
        // Forcer la mise à jour de la mise en page
        this.requestLayout();
    }


    private void resetGrille() {
        System.out.println("Restart de la grille");

        // Stocker la taille actuelle de la grille avant de la supprimer
        // Utilisation de cast en int pour être compatible avec le constructeur
        int currentWidth = (int) leftBox.getWidth();
        
        // Si la dimension actuelle est valide, l'utiliser
        if (currentWidth > 0) {
            gridSize = currentWidth;
        }
        
        centerPane.getChildren().remove(boxes);

        // Création nouvelle grille en conservant la même taille
        leftBox = new GrilleController(new Grille("grilleTest.json"), gridSize, 0.2);
        leftBox.setPrefSize(gridSize, gridSize);
        leftBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");

        VBox rightBox = new VBox(10);
        rightBox.setPadding(new Insets(10));
        rightBox.setPrefSize(150, 300);
        rightBox.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: black;");
        rightBox.setAlignment(Pos.TOP_CENTER);
        
        // Recréer les boutons dans le panneau de droite
        Button aideButton = ButtonFactory.createAnimatedButton("AIDE");
        aideButton.setPrefWidth(120);
        
        rightBox.getChildren().addAll(aideButton);

        boxes = new HBox(50, leftBox, rightBox); 
        boxes.setAlignment(Pos.CENTER);

        centerPane.getChildren().add(boxes);
        
        // Ne pas réinitialiser le chronomètre
        // chrono.reset(); - Cette ligne est commentée pour conserver le temps
    }
}