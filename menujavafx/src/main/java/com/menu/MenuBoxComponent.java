package com.menu;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Composant qui gère la création et le comportement du menu principal.
 * Cette classe est responsable de la création des boutons du menu et
 * de la gestion des événements associés.
 */
public class MenuBoxComponent {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(MenuBoxComponent.class);

    /** Référence au menu principal pour naviguer entre les écrans */
    private Menu menu;

    /**
     * Constructeur du composant de menu.
     * 
     * @param menu Le menu principal qui contient les autres éléments d'interface
     */
    public MenuBoxComponent(Menu menu) {
        logger.debug("Initialisation du MenuBoxComponent");
        this.menu = menu; // Référence à la classe Menu pour changer de contenu
    }

    /**
     * Crée une VBox qui contient les boutons "Classique", "Libre", "Techniques" et "Paramètres".
     * Chaque bouton est configuré avec des animations et des gestionnaires d'événements.
     * 
     * @return VBox contenant les boutons du menu principal
     */
    public VBox getMenuBox() {
        logger.debug("Création de la boîte de menu principal");
        VBox menuBox = new VBox(10);
        menuBox.setAlignment(javafx.geometry.Pos.CENTER);
        String[] menuItems = {"CLASSIQUE", "LIBRE", "TECHNIQUES", "PARAMETRES"};

        for (String item : menuItems) {
            logger.trace("Création du bouton de menu: {}", item);
            Button button = ButtonFactory.createAnimatedButton(item);
            button.setPrefWidth(200);

            // Ajouter des actions aux boutons
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    logger.debug("Bouton {} cliqué", item);
                    switch (item) {
                        case "CLASSIQUE":
                            logger.info("Navigation vers la grille classique");
                            menu.showClassicGrid();
                            break;
                        case "LIBRE":
                            logger.info("Navigation vers la grille libre");
                            menu.showFreeGrid();
                            break;
                        case "TECHNIQUES":
                            logger.info("Navigation vers les techniques");
                            menu.showTechniquesGrid();
                            break;
                        case "PARAMETRES":
                            logger.info("Navigation vers les paramètres");
                            menu.showSettings();
                            break;
                    }
                }
            });

            menuBox.getChildren().add(button);
        }

        menuBox.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 20; -fx-border-radius: 15; -fx-background-radius: 15;");
        logger.debug("Boîte de menu principal créée avec {} boutons", menuItems.length);
        return menuBox;
    }
}