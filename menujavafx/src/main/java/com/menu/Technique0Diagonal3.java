package com.menu;

import java.util.*;

public class Technique0Diagonal3 extends Technique {

    public Technique0Diagonal3(Grille g) {
        super(g);
    }

    public void afficherAide() {
        System.out.println("Technique 0 diagonal 3 applicable");
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 3) {
                if(c.getChiffreHautGauche().getChiffre() == 0) {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getChiffreHautDroit().getChiffre() == 0) {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getChiffreBasDroit().getChiffre() == 0) {
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                else if(c.getChiffreBasGauche().getChiffre() == 0) {
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
