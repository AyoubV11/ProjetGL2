package com.menu;
import java.util.*;

public class TechniqueAvancee6 implements Technique{
    private Grille g;
    public TechniqueAvancee6(Grille g) {
        this.g=g;
    }

    public String afficherAide() {
        return("Technique avancée 6 disponible: \n\n Si un 1 a une contrainte (trait non-posable) dans un de ses coins, alors il est possible de poser un trait dans la case diagonalement opposée à ce coin si ce trait respecte les contraintes comme illustré sur l'image.");
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while(it.hasNext()) {
            Chiffre c = it.next();
            if(c.getChiffre() == 1) {

                /**haut droit */
                try{
                    if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()+1)).check()){
                        try{
                            if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).check()){
                                if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).check() && !((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).check()){
                                    return true;
                                }
                            }
                        }catch(IndexOutOfBoundsException e){
                            
                            if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).check() && !((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).check()){
                                return true;
                            }
                        }
                        
                    }
                }catch(IndexOutOfBoundsException e){
                    
                    try{
                        if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).check()){
                            if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).check() && !((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).check()){
                                return true;
                            }
                        }
                    }catch(IndexOutOfBoundsException f){
                        
                        if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()-3)).check() && !((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).check()){
                            return true;
                        }
                    }  
                }

                /**droit bas*/
                try{
                    if(!((Arete)g.getCase(c.getLigne()+1, c.getColonne()+2)).check()){
                        try{
                            if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).check()){
                                if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).check() && !((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).check()){
                                    return true;
                                }
                            }
                        }catch(IndexOutOfBoundsException e){
                            
                            if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).check() && !((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).check()){
                                return true;
                            }
                        }
                        
                    }
                }catch(IndexOutOfBoundsException e){
                    
                    try{
                        if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).check()){
                            if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).check() && !((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).check()){
                                return true;
                            }
                        }
                    }catch(IndexOutOfBoundsException f){
                        
                        if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).check() && !((Arete)g.getCase(c.getLigne()-3, c.getColonne()-2)).check()){
                            return true;
                        }
                    }  
                }



                /**bas gauche*/
                try{
                    if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()-1)).check()){
                        try{
                            if(!((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).check()){
                                if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).check() && !((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).check()){
                                    return true;
                                }
                            }
                        }catch(IndexOutOfBoundsException e){
                            
                            if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).check() && !((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).check()){
                                return true;
                            }
                        }
                        
                    }
                }catch(IndexOutOfBoundsException e){
                    
                    try{
                        if(!((Arete)g.getCase(c.getLigne()+1, c.getColonne()-2)).check()){
                            if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).check() && !((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).check()){
                                return true;
                            }
                        }
                    }catch(IndexOutOfBoundsException f){
                        
                        if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()+2)).check() && !((Arete)g.getCase(c.getLigne()-2, c.getColonne()+3)).check()){
                            return true;
                        }
                    }  
                }

                /**gauche haut */
                try{
                    if(!((Arete)g.getCase(c.getLigne()-1, c.getColonne()-2)).check()){
                        try{
                            if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).check()){
                                if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).check() && !((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).check()){
                                    return true;
                                }
                            }
                        }catch(IndexOutOfBoundsException e){
                            
                            if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).check() && !((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).check()){
                                return true;
                            }
                        }
                        
                    }
                }catch(IndexOutOfBoundsException e){
                    
                    try{
                        if(!((Arete)g.getCase(c.getLigne()-2, c.getColonne()-1)).check()){
                            if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).check() && !((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).check()){
                                return true;
                            }
                        }
                    }catch(IndexOutOfBoundsException f){
                        
                        if(!((Arete)g.getCase(c.getLigne()+2, c.getColonne()+1)).check() && !((Arete)g.getCase(c.getLigne()+3, c.getColonne()+2)).check()){
                            return true;
                        }
                    }  
                }
                
            }
        }

        return false;
    }
}
