package com.menu;

import javafx.scene.text.Font;

public class BalooFont {
    public static Font setBalooSized(int size){
        return(Font.loadFont(BalooFont.class.getResourceAsStream("/Baloo2-Medium.ttf"), size));
    }
}
