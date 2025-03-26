package com.menu;

import java.util.*;

public class TechniqueAvancee2 implements Technique{
    private Grille g;
    public TechniqueAvancee2(Grille g){
        this.g=g;
    }

    public String afficherAide() {
        return "Technique avancee 2 applicable";
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
        }
        return false;
    }
}
