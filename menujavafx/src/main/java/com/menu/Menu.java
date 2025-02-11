package com.menu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application {
    private GridComponent gridComponent;
    private HBox content;
    private VBox menuBox;

    @Override
    public void start(Stage primaryStage) {




        BoxFactory.initialiserListe();




        TitleComponent titleComponent = new TitleComponent();
        MenuBoxComponent menuBoxComponent = new MenuBoxComponent(this);
        gridComponent = new GridComponent(this);

        StackPane root = setupBackground();
        menuBox = BoxFactory.setupMenuBox(menuBoxComponent);
        content = new HBox(40, menuBox, gridComponent.getGridPane());
        content.setStyle("-fx-padding: 20;");
        content.setAlignment(Pos.CENTER);

        BorderPane mainLayout = setupMainLayout(titleComponent.getTitle());
        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1006, 595);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slither Link");
        primaryStage.getIcons().add(new Image("/star_completed.png"));
        primaryStage.show();
    }

    private StackPane setupBackground() {
        Image backgroundImage = new Image(("/slither.png"));
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
        content.getChildren().addAll(BoxFactory.createSettingsBox(this), gridComponent.getGridPane());
    }


    public void showLevel(int i) {
        gridComponent.clear();
        content.getChildren().clear();
        content.getChildren().addAll(BoxFactory.createLevelBox(this,"/star_completed.png","/star_uncompleted.png",i), gridComponent.getGridPane());
    }

    public void showMainMenu() {
        content.getChildren().clear();
        content.getChildren().addAll(menuBox, gridComponent.getGridPane());
    }
}
