package com.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

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
            String imagePath = getClass().getResource("/skull.png").toExternalForm();
            String imagePath2 = getClass().getResource("/locked.png").toExternalForm();
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
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    menu.showLevel();
                }
            });
        }
    }

    public void showFreeGrid() {
        // Réinitialiser la grille pour afficher une grille vide
        gridPane.getChildren().clear();  // Vider la grille actuelle
        String imagePath = getClass().getResource("/skull.png").toExternalForm();
        String imagePath2 = getClass().getResource("/locked.png").toExternalForm();
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

        // Logique pour ajouter des boutons techniques (exemple simple)
        for (int i = 1; i <= 12; i++) {
            Button button = ButtonFactory.createAnimatedButton("Techniques " + i);
            button.setPrefWidth(200);
            gridPane.add(button, (i - 1) % 2, (i - 1) / 2);
        }
    }


    public void clear() {
        gridPane.getChildren().clear();
    }
}