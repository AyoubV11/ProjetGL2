package com.menu;

import java.util.Stack;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Grille {
    protected int nbLignes;   // Abscisse de la grille
    protected int nbColonnes;   // Ordonnée de la grille
    protected Case[][] cases;   // Cases de la grille
    protected Stack<Action> pileUndo;   // Pile des actions effectuées
    protected Stack<Action> pileRedo;   // Pile des actions annulées

    public Grille(String fichier) {
        try {
            // Charger le JSON
            ObjectMapper objectMapper = new ObjectMapper();
            GrilleJson grilleJson = objectMapper.readValue(new File(fichier), GrilleJson.class);

            // Affectation des dimensions spécifiées à la grille
            this.nbLignes = grilleJson.getLigne() * 2 + 1;
            this.nbColonnes = grilleJson.getColonne() * 2 + 1;

            // Création des cases de la grille
            this.cases = new Case[this.nbLignes][this.nbColonnes];

            for(int i = 0; i < this.nbLignes; i++){
                for(int j = 0; j < this.nbColonnes; j++){
                    if(i%2 == 0 && j%2 == 0) this.cases[i][j] = new Point(i, j, this);
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

    public int getNbLignes() {
        return this.nbLignes;
    }

    public int getNbColonnes() {
        return this.nbColonnes;
    }

    public Case getCase(int ligne, int colonne) throws ArrayIndexOutOfBoundsException {
        return this.cases[ligne][colonne];
    }

    public void setChiffre(int ligne, int colonne, int chiffre) {
        Chiffre c = (Chiffre) this.getCase(ligne*2+1, colonne*2+1);
        c.setChiffre(chiffre);
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
