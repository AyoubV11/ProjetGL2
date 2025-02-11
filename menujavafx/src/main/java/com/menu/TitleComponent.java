package com.menu;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TitleComponent {

    /**
     * Fonction qui crée le titre du jeu.
     * @return Text le titre.
     */
    public Text getTitle() {
        Font baloo = Font.loadFont(getClass().getResourceAsStream("/Baloo2-Medium.ttf"), 50);
        Text title = new Text("SLITHER LINK");
        title.setFont(baloo);
        title.setFill(Color.BLACK);
        return title;
    }
}
