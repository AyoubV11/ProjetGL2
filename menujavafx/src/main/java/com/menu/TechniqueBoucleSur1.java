package com.menu;

import java.util.*;

public class TechniqueBoucleSur1 implements Technique {
    private Grille g;
    public TechniqueBoucleSur1(Grille g) {
        this.g=g;
    }

    public String afficherAide() {
        return("Technique boucle 1 applicable: \n\n Non respect des règles correspondant au chiffre 1, avec la disposition de votre grille, si vous continuez votre chemin il y aura un chiffre 1 avec plus qu'un trait autour de lui.");
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 1) {
                try {
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    
                }
                try {
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    
                }
                try {
                    if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    
                }
                try{          
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    
                }
                try{
                    if(((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    
                }
                try{
                    if(((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    
                }
                try{
                    if(((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    
                }
                try {
                    if(((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT) {
                        if(((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT) {
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    
                }
            }
        }
        return false;
    }
}