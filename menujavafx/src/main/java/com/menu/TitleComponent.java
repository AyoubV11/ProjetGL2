package com.menu;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TitleComponent {

    public Text getTitle() {
        Text title = new Text("SLITHER LINK");
        title.setFont(Font.font("Arial", 40));
        title.setFill(Color.BLACK);
        return title;
    }
}
