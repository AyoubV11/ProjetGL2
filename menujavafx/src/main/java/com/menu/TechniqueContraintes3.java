package com.menu;

import java.util.*;

public class TechniqueContraintes3 implements Technique {
    private Grille g;
    public TechniqueContraintes3(Grille g) {
        this.g=g;
    }

    public String afficherAide() {
        return("Technique contraintes 3 applicable: \n\n Un 3 a dans un de ses angles deux directions dans lesquelles il ne peut pas aller, alors il doit avoir des traits dans cet angle.");
    }

    public boolean applicable() {
        boolean c1, c2 , c3, c4, c5, c6, c7, c8, c9, c10;
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 3) {
                if(c.getColonne() == 1) {
                    try {
                        c1 = ((Arete)g.getCase(c.getLigne()+3, c.getColonne())).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+4, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c1 = false;
                    }

                    try {
                        c2 = ((Arete)g.getCase(c.getLigne()+6, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+5, c.getColonne())).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+4, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c2 = false;
                    }

                    try {
                        c3 = ((Chiffre)g.getCase(c.getLigne()+4, c.getColonne())).getChiffre() == 1 && ((Arete)g.getCase(c.getLigne()+5, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c3 = false;
                    }

                    try {
                        c4 = ((Chiffre)g.getCase(c.getLigne()+4, c.getColonne())).getChiffre() == 0;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c4 = false;
                    }

                    try {
                        c5 = ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c5 = false;
                    }          

                    try {
                        c6 = ((Arete)g.getCase(c.getLigne()-3, c.getColonne())).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-4, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c6 = false;
                    }

                    try {
                        c7 = ((Arete)g.getCase(c.getLigne()-6, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-5, c.getColonne())).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-4, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-3, c.getColonne()+2)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c7 = false;
                    }

                    try {
                        c8 = ((Chiffre)g.getCase(c.getLigne()-4, c.getColonne())).getChiffre() == 1 && ((Arete)g.getCase(c.getLigne()-5, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c8 = false;
                    }

                    try {
                        c9 = ((Chiffre)g.getCase(c.getLigne()-4, c.getColonne())).getChiffre() == 0;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c9 = false;
                    }

                    try {
                        c10 = ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c10 = false;
                    }

                    if(((c1 || c2 || c3 || c4) && c5) || ((c6 || c7 || c8 || c9) && c10)) {
                        return true;
                    }
                }
                else if(c.getColonne() == g.getNbColonnes()-2) {
                    try {
                        c1 = ((Arete)g.getCase(c.getLigne()+3, c.getColonne())).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+4, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c1 = false;
                    }

                    try {
                        c2 = ((Arete)g.getCase(c.getLigne()+6, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+5, c.getColonne())).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+4, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+3, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c2 = false;
                    }

                    try {
                        c3 = ((Chiffre)g.getCase(c.getLigne()+4, c.getColonne())).getChiffre() == 1 && ((Arete)g.getCase(c.getLigne()+5, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c3 = false;
                    }

                    try {
                        c4 = ((Chiffre)g.getCase(c.getLigne()+4, c.getColonne())).getChiffre() == 0;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c4 = false;
                    }

                    try {
                        c5 = ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c5 = false;
                    }

                    try {
                        c6 = ((Arete)g.getCase(c.getLigne()-3, c.getColonne())).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-4, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c6 = false;
                    }

                    try {
                        c7 = ((Arete)g.getCase(c.getLigne()-6, c.getColonne()+1)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-5, c.getColonne())).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-4, c.getColonne()-1)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c7 = false;
                    }

                    try {
                        c8 = ((Chiffre)g.getCase(c.getLigne()-4, c.getColonne())).getChiffre() == 1 && ((Arete)g.getCase(c.getLigne()-5, c.getColonne())).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c8 = false;
                    }

                    try {
                        c9 = ((Chiffre)g.getCase(c.getLigne()-4, c.getColonne())).getChiffre() == 0;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c9 = false;
                    }

                    try {
                        c10 = ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c10 = false;
                    }

                    if(((c1 || c2 || c3 || c4) && c5) || ((c6 || c7 || c8 || c9) && c10)) {
                        return true;
                    }
                }
                else if(c.getLigne() == 1) {
                    try {
                        c1 = ((Arete)g.getCase(c.getLigne(), c.getColonne()+3)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+4)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c1 = false;
                    }

                    try {
                        c2 = ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+6)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne(), c.getColonne()+5)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+4)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+3)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c2 = false;
                    }

                    try {
                        c3 = ((Chiffre)g.getCase(c.getLigne(), c.getColonne()+4)).getChiffre() == 1 && ((Arete)g.getCase(c.getLigne(), c.getColonne()+5)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c3 = false;
                    }

                    try {
                        c4 = ((Chiffre)g.getCase(c.getLigne(), c.getColonne()+4)).getChiffre() == 0;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c4 = false;
                    }

                    try {
                        c5 = ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c5 = false;
                    }

                    try {
                        c6 = ((Arete)g.getCase(c.getLigne(), c.getColonne()-3)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-1, c.getColonne()-4)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c6 = false;
                    }

                    try {
                        c7 = ((Arete)g.getCase(c.getLigne()-1, c.getColonne()-6)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne(), c.getColonne()-5)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-4)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c7 = false;
                    }

                    try {
                        c8 = ((Chiffre)g.getCase(c.getLigne(), c.getColonne()-4)).getChiffre() == 1 && ((Arete)g.getCase(c.getLigne(), c.getColonne()-5)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c8 = false;
                    }

                    try {
                        c9 = ((Chiffre)g.getCase(c.getLigne(), c.getColonne()-4)).getChiffre() == 0;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c9 = false;
                    }

                    try {
                        c10 = ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c10 = false;
                    }

                    if(((c1 || c2 || c3 || c4) && c5) || ((c6 || c7 || c8 || c9) && c10)) {
                        return true;
                    }
                }
                else if(c.getLigne() == g.getNbLignes()-2) {
                    try {
                        c1 = ((Arete)g.getCase(c.getLigne(), c.getColonne()+3)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+4)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c1 = false;
                    }

                    try {
                        c2 = ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+6)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne(), c.getColonne()+5)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+4)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c2 = false;
                    }

                    try {
                        c3 = ((Chiffre)g.getCase(c.getLigne(), c.getColonne()+4)).getChiffre() == 1 && ((Arete)g.getCase(c.getLigne(), c.getColonne()+5)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c3 = false;
                    }

                    try {
                        c4 = ((Chiffre)g.getCase(c.getLigne(), c.getColonne()+4)).getChiffre() == 0;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c4 = false;
                    }

                    try {
                        c5 = ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c5 = false;
                    }

                    try {
                        c6 = ((Arete)g.getCase(c.getLigne(), c.getColonne()-3)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-4)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c6 = false;
                    }

                    try {
                        c7 = ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-6)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne(), c.getColonne()-5)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-1, c.getColonne()-4)).getEtat() == EnumEtat.TRAIT && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-3)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c7 = false;
                    }

                    try {
                        c8 = ((Chiffre)g.getCase(c.getLigne(), c.getColonne()-4)).getChiffre() == 1 && ((Arete)g.getCase(c.getLigne(), c.getColonne()-5)).getEtat() == EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c8 = false;
                    }

                    try {
                        c9 = ((Chiffre)g.getCase(c.getLigne(), c.getColonne()-4)).getChiffre() == 0;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c9 = false;
                    }

                    try {
                        c10 = ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT;
                    }
                    catch(IndexOutOfBoundsException e) {
                        c10 = false;
                    }

                    if(((c1 || c2 || c3 || c4) && c5) || ((c6 || c7 || c8 || c9) && c10)) {
                        return true;
                    }
                }
                else {
                    try {
                        if(!((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).check() && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT && !((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).check() && ((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).getEtat() != EnumEtat.TRAIT && (((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT)) {
                            return true;
                        }
                    }catch(IndexOutOfBoundsException e) {}

                    try {
                        if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).check() && ((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).getEtat() != EnumEtat.TRAIT && !((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).check() && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).getEtat() != EnumEtat.TRAIT && (((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() != EnumEtat.TRAIT)) {
                            return true;
                        }
                    }catch(IndexOutOfBoundsException e) {}

                    try {
                        if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).check() && ((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT && !((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).check() && ((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).getEtat() != EnumEtat.TRAIT && (((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT)) {
                            return true;
                        }
                    }catch(IndexOutOfBoundsException e) {}

                    try {
                        if(!((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).check() && ((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).getEtat() != EnumEtat.TRAIT && !((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).check() && ((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).getEtat() != EnumEtat.TRAIT && (((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() != EnumEtat.TRAIT || ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() != EnumEtat.TRAIT)) {
                            return true;
                        }
                    }catch(IndexOutOfBoundsException e) {}
                }
            }
        }
        return false;
    }
}

