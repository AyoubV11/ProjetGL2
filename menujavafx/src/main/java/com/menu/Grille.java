package com.menu;

import java.util.Stack;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Grille {
    protected int dimX;   // Abscisse de la grille
    protected int dimY;   // Ordonnée de la grille
    protected Case[][] cases;   // Cases de la grille
    protected Stack<Action> pileUndo;   // Pile des actions effectuées
    protected Stack<Action> pileRedo;   // Pile des actions annulées

    public Grille(String fichier) {
        try {
            // Charger le JSON
            ObjectMapper objectMapper = new ObjectMapper();
            GrilleJson grilleJson = objectMapper.readValue(new File(fichier), GrilleJson.class);

            // Affectation des dimensions spécifiées à la grille
            this.dimX = grilleJson.getLigne() * 2 + 1;
            this.dimY = grilleJson.getColonne() * 2 + 1;

            // Création des cases de la grille
            this.cases = new Case[this.dimX][this.dimY];

            for(int i = 0; i < this.dimX; i++){
                for(int j = 0; j < this.dimY; j++){
                    if(i%2 == 0 && j%2 == 0) this.cases[i][j] = new Point(i,j,this);
                    else if(i%2 == 0 || (i%2 == 1 && j%2 == 0)) this.cases[i][j] = new Arete(i,j,this,EnumEtat.VIDE);
                    else this.cases[i][j] = new Chiffre(i,j,this,-1);
                }
            }

            // Appliquer les modifications des cases chiffres
            this.appliquerModifications(grilleJson.getModifications());

            this.pileUndo = new Stack<Action>();
            this.pileRedo = new Stack<Action>();
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
        }
    }

    public int getDimX() {
        return this.dimX;
    }

    public int getDimY() {
        return this.dimY;
    }

    public Case getCase(int coordX, int coordY) {
        return this.cases[coordX][coordY];
    }

    public void setChiffre(int ligne, int colonne, int chiffre){
        ((Chiffre) this.getCase(ligne*2+1, colonne*2+1)).setChiffre(chiffre);
    }

    public void appliquerModifications(List<Modification> modifications) {
        for (Modification modif : modifications) {
            int ligne = modif.getLigne();
            int colonne = modif.getColonne();
            int chiffre = modif.getChiffre();
            this.setChiffre(ligne, colonne, chiffre);
        }
    }

    /**
     * Cette methode permet de verifier la reponse de l'utilisateur et renvoie VRAI ou FAUX
     * @return boolean
     */
    public boolean check(){
        /*Verifier que la grille est bonne
            COMMENT ?

            SOLUTION
            - Verifier que chaque points qui est en contact avec une aretes possedent 2 voisins
            - Verfifier que le nombre d'arrete correspond bien au chiffre d'une case 
            - La boucle (chemin) doit etre connexe
            */
        return false;
    }


    public String toString(){
        String chaine = "";
        for(Case[] x : this.cases){
            for(Case y : x){
                chaine += y;
            }
            chaine += "\n";
        }
        return chaine;
    }
}
