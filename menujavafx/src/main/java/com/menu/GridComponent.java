package com.menu;

import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

public class GridComponent {

    private GridPane gridPane;
    private Menu menu;

    public GridComponent(Menu leMenu) {
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        menu=leMenu;
    }

    public GridPane getGridPane() {
        return gridPane;  // Retourner la grille existante
    }

    public void showClassicGrid() {
        // Réinitialiser la grille pour l'affichage classique
        gridPane.getChildren().clear();  // Vider la grille actuelle

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
            int indice=i;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    menu.showLevel(indice);
                }
            });
        }
    }

    public void showFreeGrid() {
        // Réinitialiser la grille pour afficher une grille vide
        gridPane.getChildren().clear();  // Vider la grille actuelle
        String imagePath = "/skull.png";
        String imagePath2 = "/locked.png";
        Button button;
        // Ajouter des boutons vides pour simuler une grille vide
        for (int i = 1; i <= 3; i++) {
            switch(i){
                case 1:button = ButtonFactory.createSkullButton("Facile", 0, i, imagePath, imagePath2);
                    button.setPrefWidth(200);
                    gridPane.add(button, 0, i);
                    break;
                case 2:button = ButtonFactory.createSkullButton("Moyen", 0, i, imagePath, imagePath2);
                    button.setPrefWidth(200);
                    gridPane.add(button, 0, i);
                    break;
                case 3:button = ButtonFactory.createSkullButton("Difficile", 0, i, imagePath, imagePath2);
                    button.setPrefWidth(200);
                    gridPane.add(button, 0, i);
                    break;
            }
        }
    }

    public void showTechniquesGrid() {
        // Réinitialiser la grille pour afficher une grille technique
        gridPane.getChildren().clear();  // Vider la grille actuelle
    
        // Créer un VBox pour les images (alignées verticalement)
        VBox vbox = new VBox(10);  // 10 pixels d'espacement entre les images
    
        // Logique pour ajouter des images différentes
        for (int i = 1; i <= 8; i++) {
            // Créer le chemin de l'image avec un numéro incrémenté
            String imagePath = "/techniques/image" + i + ".png"; // Exemple: /image1.png
    
            // Vérifier si le chemin est valide
            // Créer une ImageView pour chaque image
            Image image = new Image(imagePath);  // Charger l'image
            ImageView imageView = new ImageView(image);

            // Ajuster la taille de l'image si nécessaire
            imageView.setFitWidth(500*1.2);  // Ajuster la largeur de l'image
            imageView.setFitHeight(300*1.2); // Ajuster la hauteur de l'image
            imageView.setPreserveRatio(true); // Maintenir le ratio de l'image

            // Ajouter l'ImageView au VBox
            vbox.getChildren().add(imageView);
        }
    
        // Créer un ScrollPane pour activer le défilement vertical
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);  // Assurer que le contenu s'adapte à la largeur du ScrollPane
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);  // Toujours afficher la barre de défilement verticale
        scrollPane.setStyle("-fx-focus-color: transparent;" + "-fx-faint-focus-color: transparent;");    
        // Ajouter le ScrollPane à la grille (ou à un autre conteneur)
        gridPane.add(scrollPane, 0, 0);  // Placer le ScrollPane dans la grille
    }


    public void clear() {
        gridPane.getChildren().clear();
    }
}
