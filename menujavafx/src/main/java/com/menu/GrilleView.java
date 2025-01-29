package com.menu;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GrilleView extends GridPane{
    private Grille grille;

    private int nbLignes;
    private int nbColonnes;

    public GrilleView(Grille grille, int sizeH){
        this.grille = grille;
        this.setGridLinesVisible(false);
        
        this.nbLignes = grille.getNbLignes();
        this.nbColonnes = grille.getNbColonnes();

        // je veux que les colonnes et les lignes s'adaptent à la taille de la fenêtre

        int percentInterstice = 5; // pourcentage de l'espace entre les chiffres (points et aretes)
        int largeurInterstice = sizeH * percentInterstice / 100;
        int largeurChiffre=  (sizeH - (nbColonnes + 1) * largeurInterstice) / nbColonnes;
        // explication : on a nbColonnes interstices de largeur largeurInterstice, donc on a nbColonnes + 1 espaces
        // on a donc sizeH - (nbColonnes + 1) * largeurInterstice pixels à répartir sur les nbColonnes cases
        // donc chaque case fait (sizeH - (nbColonnes + 1) * largeurInterstice) / nbColonnes pixels de large

        int sizeV = largeurChiffre * nbLignes;

        // ajouter sizeH et sizeV à la taille de la fenêtre et empecher de changer la taille de la fenêtre
        this.setPrefSize(sizeH, sizeV);
        this.setMaxSize(sizeH, sizeV);
        this.setMinSize(sizeH, sizeV);

        for(int i = 0; i < nbColonnes; i++){
            ColumnConstraints column = new ColumnConstraints();
            int w = (i % 2 == 0) ? largeurInterstice : largeurChiffre;
            column.setPrefWidth(w);
            this.getColumnConstraints().add(column);
        }

        for(int i = 0; i < nbLignes; i++){
            RowConstraints row = new RowConstraints();
            int h = (i % 2 == 0) ? largeurInterstice : largeurChiffre;
            row.setPrefHeight(h);
            this.getRowConstraints().add(row);
        }
        
    }
}
