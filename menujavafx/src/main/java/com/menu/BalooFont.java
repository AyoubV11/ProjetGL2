package com.menu;

import javafx.scene.text.Font;

public class BalooFont {

    /**
     * Chargement de la police Baloo.
     * @param size taille de la police.
     * @return la police chargée.
    */
    public static Font setBalooSized(int size){
        return(Font.loadFont(BalooFont.class.getResourceAsStream("/Baloo2-Medium.ttf"), size));
    }
}
