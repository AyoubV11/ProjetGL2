package com.menu;

import java.util.*;

public class TechniqueAutour0 extends Technique {

    public TechniqueAutour0(Grille g) {
        super(g);
    }

    public void afficherAide() {
        System.out.println("Technique ligne autour 0 applicable");
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 0) {
                if(!c.matchNbAretesVoisines()) {
                    return true;
                }
            }
        }
        return false;
    }
}
