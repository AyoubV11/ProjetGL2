package com.menu;

import java.util.Scanner;

public class AppTest {

    public static void main(String[] args) {
        Grille g = new Grille("menujavafx/src/test/resources/grilleTest.json") ;

        Scanner scanner = new Scanner(System.in);

        int ligne;
        int colonne;
        String symbole = "";
        
        while(!symbole.equals("9")){
            System.out.println(g);

            ligne = Integer.parseInt(scanner.next());
            colonne = Integer.parseInt(scanner.next());
            symbole = scanner.next();

            if(symbole.equals("x")) ((Arete)g.getCase(ligne, colonne)).setCroix();
            if(symbole.equals("/")) ((Arete)g.getCase(ligne, colonne)).setTrait();
            if(symbole.equals("-")) ((Arete)g.getCase(ligne, colonne)).setVide();
        }

        scanner.close();
    }
}