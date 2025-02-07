package com.menu;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu extends Application {
    private GridComponent gridComponent;
    private HBox content;
    private VBox menuBox;
    private MenuBoxComponent menuBoxComponent;

    @Override
    public void start(Stage primaryStage) {
        TitleComponent titleComponent = new TitleComponent();
        menuBoxComponent = new MenuBoxComponent(this);
        gridComponent = new GridComponent(this);

        StackPane root = setupBackground();
        menuBox = setupMenuBox();
        content = new HBox(40, menuBox, gridComponent.getGridPane());
        content.setStyle("-fx-padding: 20;");
        content.setAlignment(Pos.CENTER);

        BorderPane mainLayout = setupMainLayout(titleComponent.getTitle());
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1006, 595);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slither Link");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/star_completed.png")));
        primaryStage.show();
    }

    private StackPane setupBackground() {
        Image backgroundImage = new Image(getClass().getResource("/slither.png").toExternalForm());
        BackgroundImage background = new BackgroundImage(
                backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        StackPane root = new StackPane();
        root.setBackground(new Background(background));
        return root;
    }

    private BorderPane setupMainLayout(Text title) {
        BorderPane mainLayout = new BorderPane();
        StackPane titleContainer = new StackPane(title);
        titleContainer.setPadding(new Insets(40, 0, 0, 0));
        BorderPane.setAlignment(titleContainer, Pos.TOP_CENTER);
        mainLayout.setTop(titleContainer);
        mainLayout.setCenter(content);
        return mainLayout;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showClassicMenu() {
        showMainMenu();
        showClassicGrid();
    }

    public void showClassicGrid() {
        gridComponent.showClassicGrid();
    }

    public void showFreeGrid() {
        gridComponent.showFreeGrid();
    }

    public void showTechniquesGrid() {
        gridComponent.showTechniquesGrid();
    }

    public void showSettings() {
        gridComponent.clear();
        content.getChildren().clear();
        content.getChildren().addAll(createSettingsBox(), gridComponent.getGridPane());
    }

    private VBox createSettingsBox() {
        VBox settingsBox = createStyledBox(220, 260);
        
        HBox autoCrossToggle = ButtonFactory.createToggleButton("Croix auto : ", "OFF", "ON");
        Label volumeLabel = new Label("Volume : 50%");
        volumeLabel.setFont(BalooFont.setBalooSized(18));
        Slider volumeSlider = ButtonFactory.createVolumeSlider(volumeLabel);
        
        Button retourButton = ButtonFactory.createAnimatedButton("RETOUR");
        retourButton.setPrefWidth(200);
        retourButton.setOnAction(e -> showMainMenu());
        
        settingsBox.getChildren().addAll(autoCrossToggle, volumeLabel, volumeSlider, retourButton);
        return settingsBox;
    }

    private VBox createStyledBox(int width, int height) {
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

    public void showLevel() {
        gridComponent.clear();
        content.getChildren().clear();
        content.getChildren().addAll(createLevelBox(), gridComponent.getGridPane());
    }

    private VBox createLevelBox() {
        VBox levelBox = createStyledBox(440, 270);
        HBox starsBox = new HBox(30, createStar("/star_completed.png"), createStar("/star_completed.png"), createStar("/star_uncompleted.png"));
        starsBox.setAlignment(Pos.CENTER);

        HBox descriptionBox = new HBox(30,createDescription("Finir le niveau"),createDescription("Moins de 2 aides"),createDescription("Moins de 3min"));
        descriptionBox.setAlignment(Pos.CENTER);
        
        Button retourButton = ButtonFactory.createAnimatedButton("RETOUR");
        retourButton.setOnAction(e -> showClassicMenu());
        retourButton.setPrefWidth(200);
        Button jouerButton = ButtonFactory.createAnimatedButton("JOUER");
        jouerButton.setOnAction(e -> showSettings());
        jouerButton.setPrefWidth(200);
        
        HBox buttonBox = new HBox(30, retourButton, jouerButton);
        buttonBox.setAlignment(Pos.BOTTOM_LEFT);
        buttonBox.setPadding(new Insets(10,10,10,10));
        
        levelBox.getChildren().addAll(starsBox,descriptionBox, buttonBox);
        return levelBox;
    }

    private Label createDescription(String description){
        Label starDescription = new Label(description);
        starDescription.setFont(BalooFont.setBalooSized(16));
        starDescription.setAlignment(Pos.CENTER);
        return starDescription;
    }

    private ImageView createStar(String resource) {
        ImageView star = new ImageView(new Image(getClass().getResource(resource).toExternalForm()));
        star.setFitWidth(100);
        star.setFitHeight(100);
        return star;
    }

    public void showMainMenu() {
        content.getChildren().clear();
        content.getChildren().addAll(menuBox, gridComponent.getGridPane());
    }
}
