package com.menu;

import java.util.List;

// Classe pour représenter les données du JSON
class GrilleJson {
    private int ligne;
    private int colonne;
    private List<Modification> modifications;

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

    public List<Modification> getModifications() {
        return modifications;
    }

    public void setModifications(List<Modification> modifications) {
        this.modifications = modifications;
    }
}
