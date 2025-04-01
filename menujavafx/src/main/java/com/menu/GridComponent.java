package com.menu;

import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

/* import pour les labels et hyperlinks */
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;
import javafx.application.HostServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe qui gère l'affichage des différentes grilles dans l'interface.
 * Cette classe est responsable de créer et gérer les différentes vues
 * en grille pour les modes de jeu et les techniques.
 */
public class GridComponent {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(GridComponent.class);
    
    /** Composant de grille pour afficher les éléments */
    private GridPane gridPane;

    /** Menu principal qui gère la navigation */
    private Menu menu;

    /**
     * Constructeur qui initialise le composant de grille.
     * 
     * @param leMenu le menu qui contient les autres box et gère la navigation
     */
    public GridComponent(Menu leMenu) {
        logger.debug("Initialisation d'un nouveau GridComponent");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        menu = leMenu;
    }

    /**
     * Renvoie le gridPane avec la composition actuelle.
     * 
     * @return Le GridPane contenant les éléments actuellement affichés
     */
    public GridPane getGridPane() {
        logger.trace("Récupération du GridPane");
        return gridPane;  // Retourner la grille existante
    }

    /**
     * Mise à jour du gridPane pour afficher la grille de niveaux du mode Classic.
     * Affiche 12 boutons représentant les différents niveaux classiques avec
     * leur difficulté correspondante.
     */
    public void showClassicGrid() {
        logger.info("Affichage de la grille des niveaux classiques");
        // Réinitialiser la grille pour l'affichage classique
        clear();  // Vider la grille actuelle

        // Logique pour créer la grille classique (même logique qu'avant)
        for (int i = 1; i <= 12; i++) {
            int difficulty = (i <= 3) ? 1 : (i <= 6) ? 2 : 3;
            String imagePath = "/skull.png";
            String imagePath2 = "/locked.png";
            Button button;
            if (i >= 10) {
                button = ButtonFactory.createSkullButton("Grille " + i, i, difficulty, imagePath, imagePath2);
                button.setPrefWidth(200);
                gridPane.add(button, (i - 1) % 2, (i - 1) / 2);
            } else {
                button = ButtonFactory.createSkullButton("Grille    " + i, i, difficulty, imagePath, imagePath2);
                button.setPrefWidth(200);
                gridPane.add(button, (i - 1) % 2, (i - 1) / 2);
            }
            logger.debug("Ajout du bouton pour la grille {} avec difficulté {}", i, difficulty);
            
            int indice = i;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    logger.debug("Niveau classique {} sélectionné", indice);
                    menu.showLevel(indice);
                }
            });
        }
    }

    /**
     * Mise à jour du gridPane pour afficher la grille de niveaux du mode Libre.
     * Affiche 3 boutons pour les différents niveaux de difficulté du mode libre.
     */
    public void showFreeGrid() {
        logger.info("Affichage de la grille des niveaux du mode libre");
        // Réinitialiser la grille pour afficher une grille vide
        clear();  // Vider la grille actuelle
        
        String imagePath = "/skull.png";
        String imagePath2 = "/locked.png";
        Button button;
        
        // Ajouter des boutons vides pour simuler une grille vide
        for (int i = 1; i <= 3; i++) {
            switch(i) {
                case 1:
                    button = ButtonFactory.createSkullButton("Facile", 0, i, imagePath, imagePath2);
                    button.setPrefWidth(200);
                    button.setOnAction(e -> {
                        logger.debug("Mode libre facile sélectionné");
                        menu.showFree(1);
                    });
                    gridPane.add(button, 0, i);
                    logger.debug("Ajout du bouton pour le niveau facile en mode libre");
                    break;
                case 2:
                    button = ButtonFactory.createSkullButton("Moyen", 0, i, imagePath, imagePath2);
                    button.setPrefWidth(200);
                    button.setOnAction(e -> {
                        logger.debug("Mode libre moyen sélectionné");
                        menu.showFree(2);
                    });
                    gridPane.add(button, 0, i);
                    logger.debug("Ajout du bouton pour le niveau moyen en mode libre");
                    break;
                case 3:
                    button = ButtonFactory.createSkullButton("Difficile", 0, i, imagePath, imagePath2);
                    button.setPrefWidth(200);
                    button.setOnAction(e -> {
                        logger.debug("Mode libre difficile sélectionné");
                        menu.showFree(3);
                    });
                    gridPane.add(button, 0, i);
                    logger.debug("Ajout du bouton pour le niveau difficile en mode libre");
                    break;
            }
        }
    }

    /**
     * Mise à jour du gridPane pour afficher les techniques de jeu.
     * Affiche une série d'images explicatives des techniques avec un texte
     * de résumé et un lien vers des ressources externes supplémentaires.
     */
    public void showTechniquesGrid() {
        logger.info("Affichage de la grille des techniques");
        // Réinitialiser la grille pour afficher une grille technique
        clear();  // Vider la grille actuelle
    
        // Créer un VBox pour les images (alignées verticalement)
        VBox vbox = new VBox(10);  // 10 pixels d'espacement entre les images
    
        // Logique pour ajouter des images différentes
        for (int i = 1; i <= 7; i++) {
            // Créer le chemin de l'image avec un numéro incrémenté
            String imagePath = "/techniques/techniques" + i + ".png"; // Exemple: /techniques1.png
            logger.debug("Chargement de l'image technique {}: {}", i, imagePath);
    
            try {
                // Créer une ImageView pour chaque image
                Image image = new Image(imagePath);  // Charger l'image
                ImageView imageView = new ImageView(image);
    
                // Ajuster la taille de l'image si nécessaire
                imageView.setFitWidth(1264*0.545);  // Ajuster la largeur de l'image
                imageView.setPreserveRatio(true); // Maintenir le ratio de l'image
    
                // Ajouter l'ImageView au VBox
                vbox.getChildren().add(imageView);
            } catch (Exception e) {
                logger.error("Erreur lors du chargement de l'image {}: {}", imagePath, e.getMessage());
            }
        }
        
        // Ajouter un texte de résumé et un lien à la fin
        Label summaryLabel = new Label("Vous voila maintenant pret a resoudre vos premieres grilles de Slitherlink, si vous souhaitez connaitre des techniques plus avancees, cliquez sur le lien ci-dessous:");
        
        // Centrer le texte à l'intérieur du Label
        summaryLabel.setTextAlignment(TextAlignment.CENTER);
        summaryLabel.setWrapText(true); // Permet d'afficher le texte correctement s'il est long
        
        Hyperlink summaryLink = new Hyperlink("Voir toutes les techniques");
        summaryLink.setOnAction(e -> {
            try {
                logger.info("Ouverture du lien externe vers les techniques avancées");
                // Ouvrir le lien dans le navigateur par défaut
                HostServices hostServices = menu.getHostServices();
                hostServices.showDocument("https://www.conceptispuzzles.com/index.aspx?uri=puzzle/slitherlink/techniques");
            } catch (Exception ex) {
                // Gérer les exceptions
                logger.error("Erreur lors de l'ouverture du lien: {}", ex.getMessage());
                System.err.println("Erreur lors de l'ouverture du lien: " + ex.getMessage());
            }
        });
        
        VBox summaryBox = new VBox(5, summaryLabel, summaryLink);
        summaryBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(summaryBox);
        logger.debug("Ajout du texte de résumé et du lien vers les techniques avancées");
    
        // Créer un ScrollPane pour activer le défilement vertical
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);  // Assurer que le contenu s'adapte à la largeur du ScrollPane
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);  // Toujours afficher la barre de défilement verticale
        scrollPane.setStyle("-fx-focus-color: transparent;" + "-fx-faint-focus-color: transparent;");    
        
        // Ajouter le ScrollPane à la grille
        gridPane.add(scrollPane, 0, 0);  // Placer le ScrollPane dans la grille
        logger.debug("Création et ajout du ScrollPane pour les techniques");
    }

    /**
     * Vide le gridPane en supprimant tous ses enfants.
     * Cette méthode est appelée avant de charger un nouveau contenu.
     */
    public void clear() {
        logger.debug("Nettoyage du GridPane");
        gridPane.getChildren().clear();
    }
}