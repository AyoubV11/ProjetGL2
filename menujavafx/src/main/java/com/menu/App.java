package com.menu;

public class App {

    public static void main(String[] args) {
        Grille g = new Grille(5, 5) ;

        g.setChiffre(0, 1, 3);
        g.setChiffre(0, 2, 3);
        g.setChiffre(4, 3, 2);
        g.setChiffre(2, 1, 1);

        System.out.println(g);
    }
}
