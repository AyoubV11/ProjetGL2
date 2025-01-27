package com.menu;

import javafx.scene.layout.GridPane;

public class GridComponent {

    public GridPane getGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);

        for (int i = 1; i <= 12; i++) {
            int difficulty = (i <= 3) ? 1 : (i <= 6) ? 2 : 3;
            String imagePath = getClass().getResource("/skull.png").toExternalForm();
            gridPane.add(ButtonFactory.createSkullButton("Grille " + i, difficulty, imagePath), (i - 1) % 2, (i - 1) / 2);
        }

        return gridPane;
    }
}
