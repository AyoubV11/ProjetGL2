package com.menu;

import java.util.*;

public class TechniqueAutour0 implements Technique {
    private Grille g;
    public TechniqueAutour0(Grille g) {
        this.g=g;
    }

    public String afficherAide() {
        return("Technique ligne autour 0 applicable : \n\n Aucun zéro ne doit avoir de trait autour ou en direction de lui !");
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 0) {
                if(!c.matchNbAretesVoisines()) {
                    return true;
                }
                else if(c.getLigne() == 1 && c.getColonne() != 1 && c.getColonne() != g.getNbColonnes()-2) {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getLigne() == g.getNbColonnes()-2 && c.getColonne() != 1 && c.getColonne() != g.getNbColonnes()-2) {
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getColonne() == 1 && c.getLigne() != 1 && c.getLigne() != g.getNbLignes()-2) {
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getColonne() == g.getNbLignes()-2 && c.getLigne() != 1 && c.getLigne() != g.getNbLignes()-2) {
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
