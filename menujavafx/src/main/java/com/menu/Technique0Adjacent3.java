package com.menu;

import java.util.*;

public class Technique0Adjacent3 implements Technique {
    private Grille g;

    public Technique0Adjacent3(Grille g) {
        this.g = g;
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
                    if(!c.matchNbAretesVoisines()) {
                        return true;
                    }
                }
                else if(c.getChiffreHaut().getChiffre() == 0) {
                    if(!c.matchNbAretesVoisines()) {
                        return true;
                    }
                }
                else if(c.getChiffreDroit().getChiffre() == 0) {
                    if(!c.matchNbAretesVoisines()) {
                        return true;
                    }
                }
                else if(c.getChiffreBas().getChiffre() == 0) {
                    if(!c.matchNbAretesVoisines()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

