package com.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class GrilleController extends GridPane {
    private Grille grille;
    private List<AreteView> listAreteView;

    private int nbLignes;
    private int nbColonnes;
    private double largeurInterstice;
    private double largeurChiffre; 

    public GrilleController(Grille grille, int sizeH, int sizeV, double proportionInterstice){
        this.grille = grille;
        this.listAreteView = new ArrayList<AreteView>();
        this.setGridLinesVisible(false);
        
        this.nbLignes = grille.getNbLignes();
        this.nbColonnes = grille.getNbColonnes();

        this.largeurInterstice = 100 * proportionInterstice  / (this.nbColonnes / 2 + 1);
        this.largeurChiffre = 100 * (1.0 - proportionInterstice) / (this.nbColonnes / 2); 

        System.out.println("largeurInterstice : " + largeurInterstice);
        System.out.println("largeurChiffre : " + largeurChiffre);

        // ajouter sizeH et sizeV à la taille de la fenêtre et empecher de changer la taille de la fenêtre
        this.setPrefSize(sizeH, sizeV);
        this.setMaxSize(sizeH, sizeV);
        this.setMinSize(sizeH, sizeV);

        for(int i = 0; i < nbColonnes; i++){
            ColumnConstraints column = new ColumnConstraints();
            // column.setHgrow(Priority.ALWAYS);
            double w = (i % 2 == 0) ? largeurInterstice : largeurChiffre;
            column.setPercentWidth(w);
            this.getColumnConstraints().add(column);
        }

        for(int i = 0; i < nbLignes; i++){
            RowConstraints row = new RowConstraints();
            // row.setVgrow(Priority.ALWAYS);
            double h = (i % 2 == 0) ? largeurInterstice : largeurChiffre;
            row.setPercentHeight(h);
            this.getRowConstraints().add(row);
        }

        // ajouter les chiffres à la grille
        Iterator<Chiffre> chiffres = grille.iteratorChiffres();
        while(chiffres.hasNext()){
            Chiffre c = chiffres.next();
            ChiffreView cv = new ChiffreView(c, this);
            this.add(cv, c.getColonne(), c.getLigne());  
        }

        // ajouter les arêtes à la grille
        Iterator<Arete> aretes = grille.iteratorAretes();
        while(aretes.hasNext()){
            Arete a = aretes.next();
            AreteView av = new AreteView(a);
            this.add(av, a.getColonne(), a.getLigne());
            this.listAreteView.add(av);
        }

        // ajouter les points à la grille
        Iterator<Point> points = grille.iteratorPoints();
        while(points.hasNext()){
            Point p = points.next();
            PointView pv = new PointView(p, this);
            this.add(pv, p.getColonne(), p.getLigne());
        }

        this.activeTriggerAreteView();

        // récuperer les coordonées des points de l'areteview


        
        
    }


    private void activeTriggerAreteView(){
        this.setOnMouseMoved(event -> {
            for(AreteView av : this.listAreteView){
                // if(av.isCurseurProche(event)){
                //     av.setStyle("-fx-background-color: #ffffff;");
                // } else {
                //     av.setStyle("-fx-background-color: #000000;");
                // }
            }
        });

        this.setOnMouseClicked(event -> {
            for(AreteView av : this.listAreteView){
                if(av.isCurseurProche(event))
                    if (event.isPrimaryButtonDown())
                        av.clicGauche();
                    else if (event.isSecondaryButtonDown())
                       { av.clicDroit(); System.out.println("test");}
            }
        });
    }

    public double getLargeurInterstice(){
        return this.largeurInterstice;
    }

    public double getLargeurChiffre(){
        return this.largeurChiffre;
    }


}
