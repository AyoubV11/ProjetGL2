package com.menu;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ButtonFactory {

    public static Button createAnimatedButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 18));
        button.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 15; -fx-background-radius: 15;");

        // Animation au survol
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #444444; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #000000; -fx-text-fill: white;"));

        // Animation au clic
        button.setOnMousePressed(e -> animateButton(button, 1.1));
        button.setOnMouseReleased(e -> animateButton(button, 1.0));

        return button;
    }

    public static Button createSkullButton(String text, int difficulty, String imagePath) {
        Image skullImage = new Image(imagePath);

        // Contenu du bouton
        HBox content = new HBox(5);
        content.setAlignment(javafx.geometry.Pos.CENTER);
        Text buttonText = new Text(text);
        buttonText.setFont(Font.font("Arial", 14));

        for (int i = 0; i < 3; i++) {
            ImageView skullIcon = new ImageView(skullImage);
            skullIcon.setFitWidth(20);
            skullIcon.setFitHeight(20);
            if (i >= difficulty) skullIcon.setOpacity(0.5);
            content.getChildren().add(skullIcon);
        }
        content.getChildren().add(0, buttonText);

        // Créer le bouton
        Button button = new Button();
        button.setGraphic(content);
        button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black;");

        // Animation au clic
        button.setOnMousePressed(e -> animateButton(button, 1.1));
        button.setOnMouseReleased(e -> animateButton(button, 1.0));

        return button;
    }

    private static void animateButton(Button button, double scale) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        scaleTransition.setToX(scale);
        scaleTransition.setToY(scale);
        scaleTransition.play();
    }
}
