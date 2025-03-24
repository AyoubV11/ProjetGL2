package com.menu;

import java.util.*;

public class TechniqueDeux3Adjacent implements Technique {
    private Grille g;
    public TechniqueDeux3Adjacent(Grille g) {
        this.g=g;
    }

    public String afficherAide() {
        return("Technique deux 3 adjacent applicable");
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 3) {
                if(c.getChiffreHaut().getChiffre() == 3) {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-3, c.getColonne())).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getChiffreDroit().getChiffre() == 3) {
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+3)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getChiffreBas().getChiffre() == 3) {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+3, c.getColonne())).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getChiffreGauche().getChiffre() == 3) {
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-3)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
