package com.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorldApp extends Application {

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

    private int rows = 5;
    private int cols = 5;
    private double cellSize;

    public SlitherlinkGrid(double width, double height) {
        super(width, height);
        cellSize = width / cols;
        drawGrid();
        setOnMouseClicked(this::handleMouseClick);
    }

    private void drawGrid() {
        GraphicsContext gc = getGraphicsContext2D();

        // Draw points
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                gc.fillOval(j * cellSize - 2, i * cellSize - 2, 4, 4);
            }
        }

        // Draw numbers (example numbers, you can customize this)
        int[][] numbers = {
            {3, 1, 2, 3, 0},
            {2, 3, 1, 0, 2},
            {1, 0, 3, 2, 1},
            {0, 2, 1, 3, 2},
            {3, 1, 2, 0, 3}
        };

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gc.fillText(String.valueOf(numbers[i][j]), j * cellSize + cellSize / 2 - 5, i * cellSize + cellSize / 2 + 5);
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

        if (xOffset < 10 || xOffset > cellSize - 10) {
            // Vertical line clicked
            drawVerticalLine(col, row);
        } else if (yOffset < 10 || yOffset > cellSize - 10) {
            // Horizontal line clicked
            drawHorizontalLine(col, row);
        }
    }

    private void drawVerticalLine(int col, int row) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.strokeLine(col * cellSize, row * cellSize, col * cellSize, (row + 1) * cellSize);
    }

    private void drawHorizontalLine(int col, int row) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.strokeLine(col * cellSize, row * cellSize, (col + 1) * cellSize, row * cellSize);
    }
}
