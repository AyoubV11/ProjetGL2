package com.menu;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuBoxComponent {

    private Menu menu;

    /**
     * Constructeur.
     * @param menu le contenant principal du jeu, qui contient les autres Box.
     */
    public MenuBoxComponent(Menu menu) {
        this.menu = menu; // Référence à la classe Menu pour changer de contenu
    }

    /**
     * Crée une VBox qui contient les boutons "Classique", "Libre", "Techniques" et "Paramètres".
     * @return VBox la box créée.
     */
    public VBox getMenuBox() {
        VBox menuBox = new VBox(10);
        menuBox.setAlignment(javafx.geometry.Pos.CENTER);
        String[] menuItems = {"CLASSIQUE", "LIBRE", "TECHNIQUES", "PARAMETRES"};

        for (String item : menuItems) {
            Button button = ButtonFactory.createAnimatedButton(item);
            button.setPrefWidth(200);

            // Ajouter des actions aux boutons
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    switch (item) {
                        case "CLASSIQUE":
                            menu.showClassicGrid();
                            break;
                        case "LIBRE":
                            menu.showFreeGrid();
                            break;
                        case "TECHNIQUES":
                            menu.showTechniquesGrid();
                            break;
                        case "PARAMETRES":
                            menu.showSettings();
                            break;
                    }
                }
            });

            menuBox.getChildren().add(button);
        }

        menuBox.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 20; -fx-border-radius: 15; -fx-background-radius: 15;");
        return menuBox;
    }

}