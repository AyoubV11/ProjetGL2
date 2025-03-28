package com.menu;
import java.util.*;

public class TechniqueAvancee5 implements Technique{
    private Grille g;
    public TechniqueAvancee5(Grille g) {
        this.g=g;
    }

    public String afficherAide() {
        return("Technique avancée 5 disponible: \n\n Si un 1 a un 3 dans une de ses diagonales, avec des traits dans le coin opposé au 1 (voir image), alors ce 1 ne peut pas avoir de traits dans le coin opposé au 3 (voir image).");
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        boolean d=false;
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 1) {

                /**haut gauche */
                try{
                    if((c.getChiffreHautGauche().getChiffre()==3 && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-3)).getEtat()==EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).getEtat()==EnumEtat.TRAIT) && (((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat()==EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat()==EnumEtat.TRAIT)){
                        return true;
                    }
                }catch(IndexOutOfBoundsException e){
                    
                   d=false;
                }


                /**haut droit */
                try{
                    if((c.getChiffreHautDroit().getChiffre()==3 && ((Arete)g.getCase(c.getLigne()-3, c.getColonne()+2)).getEtat()==EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).getEtat()==EnumEtat.TRAIT) && (((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat()==EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat()==EnumEtat.TRAIT)){
                        return true;
                    }
                }catch(IndexOutOfBoundsException e){
                    
                   d=false;
                }


                /**bas droit */
                try{
                    if((c.getChiffreBasDroit().getChiffre()==3 && ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+3)).getEtat()==EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).getEtat()==EnumEtat.TRAIT) && (((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat()==EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat()==EnumEtat.TRAIT)){
                        return true;
                    }
                }catch(IndexOutOfBoundsException e){
                    
                   d=false;
                }


                /**bas gauche */
                try{
                    if((c.getChiffreBasGauche().getChiffre()==3 && ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).getEtat()==EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+3, c.getColonne()-2)).getEtat()==EnumEtat.TRAIT) && (((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat()==EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat()==EnumEtat.TRAIT)){
                        return true;
                    }
                }catch(IndexOutOfBoundsException e){
                    
                   d=false;
                }

                
            }
        }

        return d;
    }
}

