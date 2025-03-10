package com.menu;

import java.util.List;

// Classe pour représenter les données du JSON
class GrilleJson {
    private int ligne;
    private int colonne;
    private List<AjoutChiffre> ajoutChiffres;
    private List<Coordonnee> aretesGrilleResolue;

    // Getters et Setters
    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public List<AjoutChiffre> getAjoutChiffres() {
        return ajoutChiffres;
    }

    public void setAjoutChiffre(List<AjoutChiffre> ajoutChiffres) {
        this.ajoutChiffres = ajoutChiffres;
    }
    
    public List<Coordonnee> getAretesGrilleResolue() {
        return aretesGrilleResolue;
    }

    public void setAretesGrilleResolue(List<Coordonnee> aretesGrilleResolue) {
        this.aretesGrilleResolue = aretesGrilleResolue;
    }
}
