package com.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        SlitherlinkGrid grid = new SlitherlinkGrid(300, 300);
        StackPane root = new StackPane(grid);
        Scene scene = new Scene(root, 300, 300);

        primaryStage.setTitle("JavaFX Slitherlink");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class SlitherlinkGrid extends Canvas {

    private int ligne = 11;
    private int colonne = 11;
    private double cellSize;
    Grille g = new Grille("grilleTest.json");

    public SlitherlinkGrid(double width, double height) {
        super(width, height);
        cellSize = width / colonne;
        drawGrid();
        setOnMouseClicked(this::handleMouseClick);
    }

    private void drawGrid() {
        GraphicsContext gc = getGraphicsContext2D();

        gc.clearRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                gc.fillText(g.getCase(i, j).toString(), j * cellSize + cellSize / 2 - 5, i * cellSize + cellSize / 2 + 5);
            }
        }
    }

    private void handleMouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        // Determine which line was clicked
        int row = (int) (y / cellSize);
        int col = (int) (x / cellSize);

        double xOffset = x % cellSize;
        double yOffset = y % cellSize;

        if((col % 2 == 1 && row % 2 == 0) || (col % 2 == 0 && row % 2 == 1)) {
            if (xOffset < 10 || xOffset > cellSize - 10 || yOffset < 10 || yOffset > cellSize - 10) {
                if(event.getButton() == MouseButton.PRIMARY) {
                    if(((Arete)(this.g.getCase(row, col))).getEtat() == EnumEtat.TRAIT) {
                        ((Arete)(this.g.getCase(row, col))).setVide();
                    }
                    else {
                        ((Arete)(this.g.getCase(row, col))).setTrait();
                    }
                }
                else if(event.getButton() == MouseButton.SECONDARY) {
                    if(((Arete)(this.g.getCase(row, col))).getEtat() == EnumEtat.CROIX) {
                        ((Arete)(this.g.getCase(row, col))).setVide();
                    }
                    else {
                        ((Arete)(this.g.getCase(row, col))).setCroix();
                    }
                }
                drawGrid();
            }
        }
    }
}
