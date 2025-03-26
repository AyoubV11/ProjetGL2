package com.menu;

import java.util.*;


public class TechniqueAvancee2 implements Technique {
    private Grille g;
    
    public TechniqueAvancee2(Grille g) {
        this.g = g;
    }
    
    public String afficherAide() {
        return "Technique avancée 2 applicable";
    }
    
    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        boolean d;
        while (it.hasNext()) {
            Chiffre c = it.next();
            if (c.getChiffre() == 3) {
                if(c.getNbAretesVoisines()==2){
                    /*voisins haut et droit */
                    if (((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT &&  ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT){

                        /**vérifier si ça ne déborde pas */
                        if(c.getChiffreBas().getChiffre()==1 && c.getChiffreGauche().getChiffreGauche().getChiffre()==3  ){
                            try{
                                if(!((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()-3)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()-5)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            return true;
                        }
                        return false;

                    }


                    /*voisins droit et bas */
                    else if (((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT &&  ((Arete)g.getCase(c.getLigne(), c.getColonne()+1)).getEtat() == EnumEtat.TRAIT){

                        /**vérifier si ça ne déborde pas */
                        if(c.getChiffreGauche().getChiffre()==1 && c.getChiffreHaut().getChiffreHaut().getChiffre()==3){
                            try{
                                if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()-3, c.getColonne()+2)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()-5, c.getColonne()+2)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            return true;
                        }
                        return false;
                        
                    }
                    /*voisins bas et gauche */
                    else if (((Arete)g.getCase(c.getLigne()+1, c.getColonne())).getEtat() == EnumEtat.TRAIT &&  ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT){

                        /**vérifier si ça ne déborde pas */
                        if(c.getChiffreHaut().getChiffre()==1 && c.getChiffreDroit().getChiffreDroit().getChiffre()==3){
                            try{
                                if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()+3)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()+5)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            return true;
                        }
                        return false;

                        
                    }
                    /*voisins gauche et haut */
                    else if (((Arete)g.getCase(c.getLigne()-1, c.getColonne())).getEtat() == EnumEtat.TRAIT &&  ((Arete)g.getCase(c.getLigne(), c.getColonne()-1)).getEtat() == EnumEtat.TRAIT){

                        /**vérifier si ça ne déborde pas */
                        if(c.getChiffreDroit().getChiffre()==1 && c.getChiffreBas().getChiffreBas().getChiffre()==3){
                            try{
                                if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()+3, c.getColonne()-2)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            try{
                                if(!((Arete)g.getCase(c.getLigne()+5, c.getColonne()-2)).check()){
                                    return false;
                                }
                            }catch(IndexOutOfBoundsException e){
                                d=true;
                            }
                            return true;
                        }
                        return false;

                        
                    }
                }
        
            }
        }
        return false;
    }
    

 
}