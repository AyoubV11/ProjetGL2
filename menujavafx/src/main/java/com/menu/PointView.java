package com.menu;

import javafx.scene.shape.Rectangle;

public class PointView extends Rectangle{
    private Point point;

    public PointView(Point point){
        super();
        this.point = point;
        this.setStyle("-fx-fill: #000000;");
        this.setWidth(10);
        this.setHeight(10);
        
    }

    public Point getPoint(){
        return this.point;
    }
}
