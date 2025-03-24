package com.menu;

import java.util.*;

public class Technique0Adjacent3 extends Technique {

    public Technique0Adjacent3(Grille g) {
        super(g);
    }

    public void afficherAide() {
        System.out.println("Technique 0 adjacent 3 applicable");
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 3) {
                if(c.getChiffreGauche().getChiffre() == 0) {
                    if(!c.matchNbAretesVoisines() || ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() != EnumEtat.TRAIT ) {
                        return true;
                    }
                }
                else if(c.getChiffreHaut().getChiffre() == 0) {
                    if(!c.matchNbAretesVoisines() || ((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT ) {
                        return true;
                    }
                }
                else if(c.getChiffreDroit().getChiffre() == 0) {
                    if(!c.matchNbAretesVoisines() || ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() != EnumEtat.TRAIT ) {
                        return true;
                    }
                }
                else if(c.getChiffreBas().getChiffre() == 0) {
                    if(!c.matchNbAretesVoisines() || ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

