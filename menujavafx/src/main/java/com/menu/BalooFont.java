package com.menu;

import javafx.scene.text.Font;

/**
 * Utilitaire pour charger et gérer la police de caractères Baloo.
 * Permet de charger la police Baloo avec une taille personnalisée.
 */
public class BalooFont {

    /**
     * Charge la police Baloo avec une taille spécifiée.
     * 
     * @param size La taille de la police à charger
     * @return La police Baloo chargée avec la taille spécifiée
     */
    public static Font setBalooSized(int size){
        return(Font.loadFont(BalooFont.class.getResourceAsStream("/Baloo2-Medium.ttf"), size));
    }
}