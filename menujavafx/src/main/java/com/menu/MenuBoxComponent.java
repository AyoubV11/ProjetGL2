package com.menu;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuBoxComponent {

    public VBox getMenuBox() {
        VBox menuBox = new VBox(10);
        menuBox.setAlignment(javafx.geometry.Pos.CENTER);
        String[] menuItems = {"CLASSIQUE", "LIBRE", "TECHNIQUES", "PARAMETRES"};

        for (String item : menuItems) {
            Button button = ButtonFactory.createAnimatedButton(item);
            button.setPrefWidth(200);
            menuBox.getChildren().add(button);
        }

        menuBox.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 20; -fx-border-radius: 15; -fx-background-radius: 15;");
        return menuBox;
    }
}
