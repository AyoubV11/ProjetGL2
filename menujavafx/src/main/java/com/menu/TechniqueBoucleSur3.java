package com.menu;

import java.util.*;

public class TechniqueBoucleSur3 implements Technique {
    private Grille g;
    
    public TechniqueBoucleSur3(Grille g) {
        this.g = g;
    }

    public String afficherAide() {
        return "Technique boucle 3 applicable: \n\n Dans la disposition actuelle de la grille, il y a un 3 dont deux des traits autour de lui doivent être obligatoirement placés comme sur l'image.";
    }

    public boolean applicable() {
        Iterator<Chiffre> it = g.iteratorChiffres();
        while (it.hasNext()) {
            Chiffre c = it.next();
            if (c.getChiffre() == 3 && checkBoucleSur3(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBoucleSur3(Chiffre c) {
        int[][] directions = {
            {-2, -1}, {-1, -2}, {1, 0}, {0, 1},
            {-1, 2}, {-2, 1}, {1, 0}, {0, -1},
            {2, 1}, {1, 2}, {-1, 0}, {0, -1},
            {1, -2}, {2, -1}, {-1, 0}, {0, 1},
            {-1, -2}, {-2, -1}, {1, 0}, {0, 1},
            {-2, 1}, {-1, 2}, {1, 0}, {0, -1},
            {1, 2}, {2, 1}, {-1, 0}, {0, -1},
            {2, -1}, {1, -2}, {-1, 0}, {0, 1}
        };

        for (int i = 0; i < directions.length; i += 4) {
            try {
                if (isTrait(c, directions[i]) &&
                    (isTrait(c, directions[i + 1]) || !isTrait(c, directions[i + 2]) || !isTrait(c, directions[i + 3]))) {
                    return true;
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
        return false;
    }

    private boolean isTrait(Chiffre c, int[] d) {
        return ((Arete) g.getCase(c.getLigne() + d[0], c.getColonne() + d[1])).getEtat() == EnumEtat.TRAIT;
    }
}

