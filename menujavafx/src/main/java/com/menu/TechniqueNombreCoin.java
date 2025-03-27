package com.menu;

import java.util.Iterator;

public class TechniqueNombreCoin implements Technique {
    private Grille g;
    public TechniqueNombreCoin(Grille g){
        this.g=g;
    }

    public String afficherAide() {
        return("Technique nombre dans les coins applicable");
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();

            if(c.getLigne() == 1 && c.getColonne() == 1){
                
                if(c.getChiffre() == 0) {
                    if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT) {
                        return true;
                    }
                }
                if(c.getChiffre() == 1){
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT) {
                        
                        return true;

                    }
                }
                if(c.getChiffre() == 2){
                    if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT ) {
                        
                        return true;
                    }
                }
                if(c.getChiffre() == 3){
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
            }

            if(c.getLigne() == 1 && c.getColonne() == g.getNbColonnes()-2){
                
                if(c.getChiffre() == 0) {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                if(c.getChiffre() == 1){
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT) {
                        return true;
                    }
                }
                if(c.getChiffre() == 2){
                    if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT ) {
                        return true;
                    }
                }
                if(c.getChiffre() == 3){
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
            }

            if(c.getLigne() == g.getNbLignes()-2 && c.getColonne() == 1){
                
                if(c.getChiffre() == 0) {
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                if(c.getChiffre() == 1){
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT) {
                        return true;
                    }
                }
                if(c.getChiffre() == 2){
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT ) {
                        return true;
                    }
                }
                if(c.getChiffre() == 3){
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
            }

            if(c.getLigne() == g.getNbLignes()-2 && c.getColonne() == g.getNbColonnes()-2){
                
                if(c.getChiffre() == 0) {
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
                if(c.getChiffre() == 1){
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT) {
                        return true;
                    }
                }
                if(c.getChiffre() == 2){
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT ) {
                        return true;
                    }
                }
                if(c.getChiffre() == 3){
                    if(((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT) {
                        return true;
                    }
                }
            }

            
        }

        return false;
    }
}
