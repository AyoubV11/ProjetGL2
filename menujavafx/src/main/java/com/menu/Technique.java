package com.menu;

/**
 * Interface représentant une technique d'aide ou une stratégie utilisable dans le jeu.
 * Les classes qui implémentent cette interface fournissent des méthodes 
 * spécifiques pour aider le joueur à résoudre la grille.
 */
public interface Technique {
    
    /**
     * Renvoie le message d'aide à afficher à l'utilisateur.
     * Ce message explique la technique ou donne un indice sur la prochaine action à effectuer.
     * 
     * @return une chaîne de caractères contenant le message d'aide
     */
    public String afficherAide();
    
    /**
     * Vérifie si la technique est applicable dans l'état actuel du jeu.
     * Cette méthode permet de déterminer si l'aide proposée par cette technique
     * est pertinente dans la situation actuelle.
     * 
     * @return true si la technique est applicable, false sinon
     */
    public boolean applicable();
}