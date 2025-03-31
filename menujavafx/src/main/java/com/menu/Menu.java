package com.menu;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe principale du jeu qui gère l'interface du menu et la navigation.
 * Cette classe hérite de Application pour permettre le lancement de l'application JavaFX.
 */
public class Menu extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);
    
    /** Composant qui gère la grille de sélection de niveaux */
    private GridComponent gridComponent;
    
    /** Conteneur principal pour les éléments du menu */
    private HBox content;
    
    /** Boîte contenant les boutons du menu principal */
    private VBox menuBox;
    
    /** Fenêtre principale de l'application */
    private Stage stage;

    /**
     * Programme principal du jeu : lancement de la fenêtre du menu.
     * Cette méthode est appelée automatiquement au démarrage de l'application.
     * 
     * @param primaryStage La fenêtre principale fournie par JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        logger.info("Démarrage de l'application Slitherlink");
        stage = primaryStage;

        logger.debug("Chargement des niveaux et des étoiles");
        ButtonFactory.chargerNiveaux();
        BoxFactory.chargerEtoiles();

        // Vérifier si le dossier existe
        File projectRoot = new File(System.getProperty("user.dir"));
        File slitherLinkDir = new File(projectRoot, ".slitherlinkGroup2");
        logger.debug("Vérification de l'existence du dossier de sauvegarde: {}", slitherLinkDir.getAbsolutePath());

        // Afficher directement le menu
        showMenu();

        SoundPlayer.init();

        // Si le dossier n'existe pas, lancer la vidéo après le menu
        if (!slitherLinkDir.exists()) {
            logger.info("Première exécution détectée, lancement de la vidéo d'introduction");
            IntroVideoPlayer videoPlayer = new IntroVideoPlayer(stage, this::showMenu);
            videoPlayer.showIntroVideo();
        }
        else{
            SoundPlayer.lanceMusic(); 
        }
        
    }

    /**
     * Met en place le fond de la fenêtre.
     * 
     * @return StackPane contenant l'image de fond
     */
    private StackPane setupBackground() {
        logger.trace("Configuration du fond d'écran");
        Image backgroundImage = new Image(("/slither.png"));
        BackgroundImage background = new BackgroundImage(
                backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        StackPane root = new StackPane();
        root.setBackground(new Background(background));
        return root;
    }

    /**
     * Initialise les composants principaux du layout principal.
     * 
     * @param title Titre du jeu à afficher
     * @return BorderPane avec les différents composants organisés
     */
    private BorderPane setupMainLayout(Text title) {
        logger.trace("Configuration du layout principal");
        BorderPane mainLayout = new BorderPane();
        StackPane titleContainer = new StackPane(title);
        titleContainer.setPadding(new Insets(40, 0, 0, 0));
        BorderPane.setAlignment(titleContainer, Pos.TOP_CENTER);
        mainLayout.setTop(titleContainer);
        mainLayout.setCenter(content);
        return mainLayout;
    }

    /**
     * Point d'entrée principal du programme.
     * 
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        logger.info("Lancement de l'application Slitherlink");
        launch(args);
    }

    /**
     * Affiche le menu avec la grille des jeux classiques.
     * Cette méthode combine l'affichage du menu principal et de la grille classique.
     */
    public void showClassicMenu() {
        logger.debug("Affichage du menu classique");
        showMainMenu();
        showClassicGrid();
    }

    /**
     * Affiche la grille de niveaux Classique.
     * Délègue l'affichage au composant GridComponent.
     */
    public void showClassicGrid() {
        logger.debug("Affichage de la grille classique");
        gridComponent.showClassicGrid();
    }

    /**
     * Affiche la grille de niveaux Libre.
     * Délègue l'affichage au composant GridComponent.
     */
    public void showFreeGrid() {
        logger.debug("Affichage de la grille libre");
        gridComponent.showFreeGrid();
    }

    /**
     * Affiche les techniques pour résoudre le jeu.
     * Délègue l'affichage au composant GridComponent.
     */
    public void showTechniquesGrid() {
        logger.debug("Affichage des techniques");
        gridComponent.showTechniquesGrid();
    }

    /**
     * Affiche un niveau de jeu libre.
     * 
     * @param nv_libre Niveau de difficulté du mode libre (1=facile, 2=moyen, 3=difficile)
     */
    public void showFree(int nv_libre) {
        logger.info("Démarrage d'un niveau libre de difficulté {}", nv_libre);
        Parent gameInterface = new SceneJeu(stage, this, nv_libre, true);
        StackPane root = setupBackground();
        root.getChildren().add(gameInterface);
        Scene scene2 = new Scene(root, 1006, 595);
        stage.setScene(scene2);
    }

    /**
     * Affiche un niveau spécifique du jeu en mode classique.
     * 
     * @param niveau Numéro du niveau à afficher
     */
    public void showGame(int niveau) {
        logger.info("Démarrage du niveau classique {}", niveau);
        Parent gameInterface = new SceneJeu(stage, this, niveau, false);
        StackPane root = setupBackground();
        root.getChildren().add(gameInterface);
        Scene scene2 = new Scene(root, 1006, 595);
        stage.setScene(scene2);
    }

    /**
     * Affiche le menu principal.
     * Configure et initialise tous les composants nécessaires pour le menu.
     */
    public void showMenu() {
        logger.info("Affichage du menu principal");
        TitleComponent titleComponent = new TitleComponent();
        MenuBoxComponent menuBoxComponent = new MenuBoxComponent(this);
        gridComponent = new GridComponent(this);

        StackPane root = setupBackground();

        menuBox = BoxFactory.setupMenuBox(menuBoxComponent);
        
        content = new HBox(40, menuBox, gridComponent.getGridPane());
        content.setStyle("-fx-padding: 20;");
        content.setAlignment(Pos.CENTER);

        BorderPane mainLayout = setupMainLayout(titleComponent.getTitle());
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1006, 595);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Slither Link");
        stage.getIcons().add(new Image("/star_completed.png"));
        logger.debug("Configuration de la fenêtre principale terminée");
        stage.show();
    }

    /**
     * Affiche les paramètres du jeu.
     * Remplace le contenu actuel par le panneau des paramètres.
     */
    public void showSettings() {
        logger.debug("Affichage des paramètres");
        gridComponent.clear();
        content.getChildren().clear();
        content.getChildren().addAll(BoxFactory.createSettingsBox(this), gridComponent.getGridPane());
    }

    /**
     * Affiche les détails d'un niveau spécifique.
     * 
     * @param i Numéro du niveau à afficher
     */
    public void showLevel(int i) {
        logger.debug("Affichage des détails du niveau {}", i);
        gridComponent.clear();
        content.getChildren().clear();
        content.getChildren().addAll(BoxFactory.createLevelBox(this, "/star_completed.png", "/star_uncompleted.png", i), gridComponent.getGridPane());
    }

    /**
     * Revient au menu principal.
     * Restaure le contenu du menu principal.
     */
    public void showMainMenu() {
        logger.debug("Retour au menu principal");
        content.getChildren().clear();
        content.getChildren().addAll(menuBox, gridComponent.getGridPane());
    }
    
    
}