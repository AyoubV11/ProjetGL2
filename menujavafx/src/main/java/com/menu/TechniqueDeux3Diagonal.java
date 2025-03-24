package com.menu;

import java.util.*;

public class TechniqueDeux3Diagonal extends Technique {

    public TechniqueDeux3Diagonal(Grille g) {
        super(g);
    }

    public void afficherAide() {
        System.out.println("Technique deux 3 diagonal applicable");
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 3) {
                if(c.getChiffreHautGauche().getChiffre() == 3) {
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-3)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getChiffreHautDroit().getChiffre() == 3) {
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-3, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getChiffreBasDroit().getChiffre() == 3) {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+3)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getChiffreBasGauche().getChiffre() == 3) {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+3, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
