package com.menu;

import javafx.geometry.Pos;
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


    

}
