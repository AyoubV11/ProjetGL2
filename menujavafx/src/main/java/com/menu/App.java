package com.menu;

public class App {

    public static void main(String[] args) {
        Grille g = new Grille(5, 5) ;

        g.setChiffre(0, 1, 3);
        g.setChiffre(0, 2, 3);
        g.setChiffre(4, 3, 2);
        g.setChiffre(2, 1, 1);

        for(Case[] x : g.cases){
            for(Case y : x){
                if(y instanceof Arete){
                    System.out.print(" - ");
                }
                else if(y instanceof Point){
                    System.out.print(" · ");
                }
                else if(y instanceof Chiffre && ((Chiffre) y).getChiffre() == -1){
                    System.out.print("   ");
                }
                else{
                    System.out.print(" " + ((Chiffre) y).getChiffre() + " ");
                }
            }
            System.out.println();
        }
    }
}
