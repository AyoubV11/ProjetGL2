package com.menu;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class PointView extends Rectangle{
    private Point point;

    public PointView(Point point, GrilleController grille){
        super();
        this.point = point;
        this.setStyle("-fx-fill: #0000FF;");
        this.widthProperty().bind(grille.widthProperty().multiply(grille.getLargeurInterstice() / 100));
        this.heightProperty().bind(grille.heightProperty().multiply(grille.getLargeurInterstice() / 100));
        // System.out.println("dimension : " + gridPane.getColumnConstraints().get(point.getColonne()).prefWidthProperty().getValue());
        
    }

    public Point getPoint(){
        return this.point;
    }
}
