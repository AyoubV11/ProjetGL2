package com.menu;

import java.util.Iterator;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class GrilleController extends GridPane {
    private Grille grille;

    private int nbLignes;
    private int nbColonnes;

    public GrilleController(Grille grille, int sizeH){
        this.grille = grille;
        this.setGridLinesVisible(false);
        
        this.nbLignes = grille.getNbLignes();
        this.nbColonnes = grille.getNbColonnes();

        // je veux que les colonnes et les lignes s'adaptent à la taille de la fenêtre

        int percentInterstice = 2; // pourcentage de l'espace entre les chiffres (points et aretes)
        int largeurInterstice = sizeH * percentInterstice / 100;
        int largeurChiffre=  (sizeH - largeurInterstice) / nbColonnes - largeurInterstice;
        int sizeV = largeurChiffre * nbLignes;

        System.out.println(percentInterstice + "% interstice, largeur interstice : " + largeurInterstice + ", largeur chiffre : " + largeurChiffre + ", taille fenetre : " + sizeH + "x" + sizeV);

        // ajouter sizeH et sizeV à la taille de la fenêtre et empecher de changer la taille de la fenêtre
        this.setPrefSize(sizeH, sizeV);
        this.setMaxSize(sizeH, sizeV);
        this.setMinSize(sizeH, sizeV);

        for(int i = 0; i < nbColonnes; i++){
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.ALWAYS);
            int w = (i % 2 == 0) ? largeurInterstice : largeurChiffre;
            column.setPrefWidth(w);
            this.getColumnConstraints().add(column);
        }

        for(int i = 0; i < nbLignes; i++){
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            int h = (i % 2 == 0) ? largeurInterstice : largeurChiffre;
            row.setPrefHeight(h);
            this.getRowConstraints().add(row);
        }

        // ajouter les chiffres à la grille
        Iterator<Chiffre> chiffres = grille.iteratorChiffres();
        while(chiffres.hasNext()){
            Chiffre c = chiffres.next();
            ChiffreView cv = new ChiffreView(c);
            this.add(cv, c.getColonne(), c.getLigne());    
        }

        // ajouter les arêtes à la grille
        Iterator<Arete> aretes = grille.iteratorAretes();
        while(aretes.hasNext()){
            Arete a = aretes.next();
            AreteView av = new AreteView(a);
            this.add(av, a.getColonne(), a.getLigne());
        }

        // ajouter les points à la grille
        Iterator<Point> points = grille.iteratorPoints();
        while(points.hasNext()){
            Point p = points.next();
            PointView pv = new PointView(p);
            this.add(pv, p.getColonne(), p.getLigne());
        }
        
    }
}
