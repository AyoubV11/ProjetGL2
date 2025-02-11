package com.menu;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ButtonFactory {

    private static int unlockedLevel = 1;  // Le niveau actuellement débloqué, peut être mis à jour dynamiquement

    
    
    /** 
     * Crée un bouton contenant un texte blanc sur fond noir, qui grossit quand on clique dessus et qui s'éclaircit lorsque la souris passe dessus.
     * @param text le texte affiché dans le bouton.
     * @return Button le bouton créé.
     */
    public static Button createAnimatedButton(String text) {
        Button button = new Button(text);
        button.setFont(BalooFont.setBalooSized(18));
        button.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;");

        // Animation au survol
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));

        // Animation au clic
        button.setOnMousePressed(e -> animateButton(button, 1.1));
        button.setOnMouseReleased(e -> animateButton(button, 1.0));

        return button;
    }

    
    
    /** 
     * Crée les boutons contenant le numéro d'une grille ainsi que 3 images de tête de mort.
     * Plus ou moins de têtes de mort sont grisées en fonction de la difficulté.
     * Si le niveau n'est pas débloqué, il apparait grisé avec une image de cadenas dessus.
     * @param text le nom de la grille.
     * @param level le numéro de la grille.
     * @param difficulty la difficulté de la grille, définissant le nombre de têtes de mort qui seront grisées.
     * @param imagePath le chemin vers l'image de tête de mort.
     * @param lockedImagePath le chemin vers l'image du cadenas, qui se superpose sur le bouton d'un niveau non-débloqué.
     * @return Button le bouton créé.
     */
    public static Button createSkullButton(String text, int level, int difficulty, String imagePath, String lockedImagePath) {
        Image skullImage = new Image(imagePath);
        Image lockedImage = new Image(lockedImagePath);
        
        // Contenu du bouton
        HBox content = new HBox(5);
        content.setAlignment(javafx.geometry.Pos.CENTER);
        Text buttonText = new Text(text);
        buttonText.setFont(BalooFont.setBalooSized(17));
        
        // Mise en place des crânes dans chaque bouton
        for (int i = 0; i < 3; i++) {
            ImageView skullIcon = new ImageView(skullImage);
            skullIcon.setFitWidth(20);
            skullIcon.setFitHeight(20);
            if (i >= difficulty) skullIcon.setOpacity(0.5);
            content.getChildren().add(skullIcon);
        }
        content.getChildren().add(0, buttonText);
        
        // Créer le bouton avec une StackPane pour superposer les éléments
        Button button = new Button();
        StackPane buttonStack = new StackPane();
        buttonStack.getChildren().add(content);
        
        // Si le niveau est bloqué, ajouter une image de verrouillage
        if (level > unlockedLevel) {
            button.setDisable(true);
            button.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
            // Ajouter l'image de verrouillage en superposition
            ImageView lockedIcon = new ImageView(lockedImage);
            lockedIcon.setPreserveRatio(false);
            lockedIcon.setFitWidth(125); // Ajuster selon la taille du bouton
            lockedIcon.setFitHeight(20);
            lockedIcon.setOpacity(1);
            buttonStack.getChildren().add(lockedIcon);
        } else {
            // Style pour les boutons débloqués
            button.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-border-color: black; -fx-border-radius: 15; -fx-background-radius: 15;");
        }

        button.setGraphic(buttonStack);

        // Animation au clic
        button.setOnMousePressed(e -> animateButton(button, 1.1));
        button.setOnMouseReleased(e -> animateButton(button, 1.0));

        return button;
    }

    
    
    /** 
     * Fonction animant un bouton de classe Button passé en paramètre. 
     * @param button le bouton à animer
     * @param scale la taille de l'agrandissement
     */
    private static void animateButton(Button button, double scale) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        scaleTransition.setToX(scale);
        scaleTransition.setToY(scale);
        scaleTransition.play();
    }

    
    /** 
     * Fonction animant un bouton de classe ToggleButton passé en paramètre. 
     * @param button le bouton à animer
     * @param scale la taille de l'agrandissement
     */
    private static void animateButton(ToggleButton button, double scale) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        scaleTransition.setToX(scale);
        scaleTransition.setToY(scale);
        scaleTransition.play();
    }



    /**
     * Fonction débloquant un niveau passé en paramètre.
     * @param level le niveau à débloquer.
     */
    public static void unlockLevel(int level) {
        if (level > unlockedLevel) {
            unlockedLevel = level;
        }
    }

    /**
     * Crée un bouton qui active ou désactive un paramètre quand on clique dessus.
     * @param labelText le texte à afficher à coté du bouton.
     * @param offText le texte lorsque le paramètre est désactivé.
     * @param onText le texte lorsque le paramètre est activé.
     * @return la HBox contenant le texte et le bouton créé.
     */
    public static HBox createToggleButton(String labelText, String offText, String onText) {
        Label label = new Label(labelText);
        label.setFont(BalooFont.setBalooSized(18));
        ToggleButton toggle = new ToggleButton(offText);
        toggle.setFont(BalooFont.setBalooSized(18));
        toggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;");

        toggle.setOnMouseEntered(e -> toggle.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));
        toggle.setOnMouseExited(e -> toggle.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-border-radius: 20; -fx-background-radius: 20;"));

        toggle.setOnMousePressed(e -> animateButton(toggle, 1.1));
        toggle.setOnMouseReleased(e -> animateButton(toggle, 1.0));
        toggle.setOnAction(event -> toggle.setText(toggle.isSelected() ? onText : offText));
        HBox box = new HBox(10, label, toggle);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /**
     * Crée un slider pour régler un paramètre.
     * Ce slider va de 0 à 100.
     * @param volumeLabel le texte à afficher au dessus du bouton.
     * @return le Slider créé.
     */
    public static Slider createVolumeSlider(Label volumeLabel) {
        Slider slider = new Slider(0, 100, 50);
        slider.setStyle("-fx-control-inner-background: #000000; -fx-background-color: #000000; -fx-border-radius: 20; -fx-background-radius: 20;" +"-fx-focus-color: transparent;" + "-fx-faint-focus-color: transparent;");
        slider.valueProperty().addListener((obs, oldVal, newVal) ->
                volumeLabel.setText("Volume : " + newVal.intValue() + "%"));
        return slider;
    }
}
