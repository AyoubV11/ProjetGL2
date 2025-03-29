package com.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Composant visuel qui représente un Point dans l'interface graphique.
 * Hérite de Rectangle pour afficher le point dans la grille JavaFX.
 */
public class PointView extends Rectangle {
    
    private static final Logger logger = LoggerFactory.getLogger(PointView.class);
    private Point point;

    /**
     * Constructeur de la vue d'un point.
     * Crée un rectangle noir dont la taille est liée aux propriétés
     * de dimensionnement de la grille.
     *
     * @param point le modèle de point à représenter
     * @param grille le contrôleur de la grille qui contient ce point
     */
    public PointView(Point point, GrilleController grille) {
        super();
        this.point = point;
        this.setFill(Color.BLACK);
        // Permettre au rectangle de s'étendre
        
        this.widthProperty().bind(grille.widthProperty().multiply(grille.getLargeurInterstice() / 100));
        this.heightProperty().bind(grille.heightProperty().multiply(grille.getLargeurInterstice() / 100));
        
        logger.debug("PointView créé pour le point [{},{}], largeur et hauteur liées à {}% de la grille", 
                    point.getLigne(), point.getColonne(), grille.getLargeurInterstice());
    }

    /**
     * Retourne le modèle de point associé à cette vue.
     *
     * @return le Point représenté par cette vue
     */
    public Point getPoint() {
        logger.trace("Accès au point [{},{}] depuis sa vue", point.getLigne(), point.getColonne());
        return this.point;
    }
}