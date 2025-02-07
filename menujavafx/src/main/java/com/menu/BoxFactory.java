package com.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BoxFactory {


    public static VBox setupMenuBox(MenuBoxComponent menuBoxComponent ) {
        VBox menu = menuBoxComponent.getMenuBox();
        menu.setPrefSize(220, 260);
        menu.setMinSize(220, 260);
        menu.setMaxSize(220, 260);
        menu.setAlignment(Pos.CENTER);
        return menu;
    }

    public static VBox createSettingsBox(Menu menu) {
        VBox settingsBox = createStyledBox(220, 260);
        
        HBox autoCrossToggle = ButtonFactory.createToggleButton("Croix auto : ", "OFF", "ON");
        Label volumeLabel = new Label("Volume : 50%");
        volumeLabel.setFont(BalooFont.setBalooSized(18));
        Slider volumeSlider = ButtonFactory.createVolumeSlider(volumeLabel);
        
        Button retourButton = ButtonFactory.createAnimatedButton("RETOUR");
        retourButton.setPrefWidth(200);
        retourButton.setOnAction(e -> menu.showMainMenu());
        
        settingsBox.getChildren().addAll(autoCrossToggle, volumeLabel, volumeSlider, retourButton);
        return settingsBox;
    }

    public static VBox createStyledBox(int width, int height) {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 20; -fx-border-radius: 15; -fx-background-radius: 15;");
        box.setPrefSize(width, height);
        box.setMinWidth(width);
        box.setMaxWidth(width);
        box.setMinHeight(height);
        box.setMaxHeight(height);
        return box;
    }

    public static VBox createLevelBox(Menu menu,String pathCompleted, String pathUncompleted) {
        VBox levelBox = createStyledBox(440, 270);
        HBox starsBox = new HBox(30, createStar(pathCompleted), createStar(pathCompleted), createStar(pathUncompleted));
        starsBox.setAlignment(Pos.CENTER);

        HBox descriptionBox = new HBox(30,createDescription("Finir le niveau"),createDescription("Moins de 2 aides"),createDescription("Moins de 3min"));
        descriptionBox.setAlignment(Pos.CENTER);
        
        Button retourButton = ButtonFactory.createAnimatedButton("RETOUR");
        retourButton.setOnAction(e -> menu.showClassicMenu());
        retourButton.setPrefWidth(200);
        Button jouerButton = ButtonFactory.createAnimatedButton("JOUER");
        jouerButton.setOnAction(e -> menu.showSettings());
        jouerButton.setPrefWidth(200);
        
        HBox buttonBox = new HBox(30, retourButton, jouerButton);
        buttonBox.setAlignment(Pos.BOTTOM_LEFT);
        buttonBox.setPadding(new Insets(10,10,10,10));
        
        levelBox.getChildren().addAll(starsBox,descriptionBox, buttonBox);
        return levelBox;
    }

     private static Label createDescription(String description){
        Label starDescription = new Label(description);
        starDescription.setFont(BalooFont.setBalooSized(16));
        starDescription.setAlignment(Pos.CENTER);
        return starDescription;
    }

    private static ImageView createStar(String imagePath) {
        ImageView star = new ImageView(new Image(imagePath));
        star.setFitWidth(100);
        star.setFitHeight(100);
        return star;
    }

}
