package com.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contrôleur qui gère l'affichage et l'interaction avec la grille de jeu.
 * Fait le lien entre le modèle (Grille) et les vues (AreteView, ChiffreView, PointView).
 */
public class GrilleController extends GridPane {
    private static final Logger logger = LoggerFactory.getLogger(GrilleController.class);
    
    /** Référence au modèle de grille */
    private Grille grille;
    
    /** Liste des vues d'arêtes pour faciliter leur mise à jour */
    private List<AreteView> listAreteView;

    /** Nombre de lignes dans la grille */
    private int nbLignes;
    
    /** Nombre de colonnes dans la grille */
    private int nbColonnes;
    
    /** Largeur en pourcentage des interstices entre les cases */
    private double largeurInterstice;
    
    /** Largeur en pourcentage des cases contenant des chiffres */
    private double largeurChiffre; 
    
    /** Référence à la scène de jeu */
    private SceneJeu scene;

    /** Proportion par défaut des interstices par rapport à la taille totale */
    private static final double DEFAULT_PROPORTION_INTERSTICE = 0.2;

    /**
     * Constructeur principal qui initialise le contrôleur de grille avec les dimensions spécifiées.
     * 
     * @param grille La grille modèle à afficher
     * @param sizeH La largeur en pixels du contrôleur
     * @param sizeV La hauteur en pixels du contrôleur
     * @param proportionInterstice La proportion des interstices par rapport à la taille totale
     * @param scene La scène de jeu
     */
    public GrilleController(Grille grille, int sizeH, int sizeV, double proportionInterstice, SceneJeu scene) {
        logger.info("Création d'un nouveau GrilleController de taille {}x{} avec une proportion d'interstice de {}", 
                    sizeH, sizeV, proportionInterstice);
        
        this.grille = grille;
        this.listAreteView = new ArrayList<AreteView>();
        this.setGridLinesVisible(false);
        this.setStyle("-fx-background-color: transparent;");
        this.scene = scene;
        
        this.nbLignes = grille.getNbLignes();
        this.nbColonnes = grille.getNbColonnes();

        this.largeurInterstice = 100 * proportionInterstice / (this.nbColonnes / 2 + 1);
        this.largeurChiffre = 100 * (1.0 - proportionInterstice) / (this.nbColonnes / 2); 

        logger.debug("Dimensions de la grille: {}x{}, largeur interstice: {}%, largeur chiffre: {}%", 
                    nbLignes, nbColonnes, largeurInterstice, largeurChiffre);

        // Définir la taille de la fenêtre et empêcher de changer la taille
        this.setPrefSize(sizeH, sizeV);
        this.setMaxSize(sizeH, sizeV);
        this.setMinSize(sizeH, sizeV);

        // Configuration des contraintes de colonnes
        for(int i = 0; i < nbColonnes; i++) {
            ColumnConstraints column = new ColumnConstraints();
            double w = (i % 2 == 0) ? largeurInterstice : largeurChiffre;
            column.setPercentWidth(w);
            this.getColumnConstraints().add(column);
        }

        // Configuration des contraintes de lignes
        for(int i = 0; i < nbLignes; i++) {
            RowConstraints row = new RowConstraints();
            double h = (i % 2 == 0) ? largeurInterstice : largeurChiffre;
            row.setPercentHeight(h);
            this.getRowConstraints().add(row);
        }

        logger.debug("Configuration des contraintes de colonnes et de lignes terminée");

        // Ajout des chiffres à la grille
        logger.debug("Ajout des chiffres à la grille");
        Iterator<Chiffre> chiffres = grille.iteratorChiffres();
        int nbChiffres = 0;
        while(chiffres.hasNext()) {
            Chiffre c = chiffres.next();
            ChiffreView cv = new ChiffreView(c, this);
            this.add(cv, c.getColonne(), c.getLigne());
            nbChiffres++;
        }
        logger.debug("{} chiffres ajoutés à la grille", nbChiffres);

        // Ajout des arêtes à la grille
        logger.debug("Ajout des arêtes à la grille");
        Iterator<Arete> aretes = grille.iteratorAretes();
        int nbAretes = 0;
        while(aretes.hasNext()) {
            Arete a = aretes.next();
            AreteView av = new AreteView(a, this.scene);
            this.add(av, a.getColonne(), a.getLigne());
            this.listAreteView.add(av);
            nbAretes++;
        }
        logger.debug("{} arêtes ajoutées à la grille", nbAretes);

        // Ajout des points à la grille
        logger.debug("Ajout des points à la grille");
        Iterator<Point> points = grille.iteratorPoints();
        int nbPoints = 0;
        while(points.hasNext()) {
            Point p = points.next();
            PointView pv = new PointView(p, this);
            this.add(pv, p.getColonne(), p.getLigne());
            nbPoints++;
        }
        logger.debug("{} points ajoutés à la grille", nbPoints);

        this.ameliorerHitboxAreteView();
        logger.info("Initialisation du GrilleController terminée");
    }

    /**
     * Constructeur qui utilise une proportion d'interstice par défaut.
     * 
     * @param grille La grille modèle à afficher
     * @param sizeH La largeur en pixels du contrôleur
     * @param sizeV La hauteur en pixels du contrôleur
     * @param scene La scène de jeu
     */
    public GrilleController(Grille grille, int sizeH, int sizeV, SceneJeu scene) {
        this(grille, sizeH, sizeV, DEFAULT_PROPORTION_INTERSTICE, scene);
        logger.debug("Utilisation de la proportion d'interstice par défaut: {}", DEFAULT_PROPORTION_INTERSTICE);
    }

    /**
     * Constructeur qui utilise une taille carrée.
     * 
     * @param grille La grille modèle à afficher
     * @param size La taille en pixels du contrôleur (largeur = hauteur)
     * @param proportionInterstice La proportion des interstices par rapport à la taille totale
     * @param scene La scène de jeu
     */
    public GrilleController(Grille grille, int size, double proportionInterstice, SceneJeu scene) {
        this(grille, size, size, proportionInterstice, scene);
        logger.debug("Utilisation d'une taille carrée: {}", size);
    }

    /**
     * Constructeur qui utilise une taille carrée et une proportion d'interstice par défaut.
     * 
     * @param grille La grille modèle à afficher
     * @param size La taille en pixels du contrôleur (largeur = hauteur)
     * @param scene La scène de jeu
     */
    public GrilleController(Grille grille, int size, SceneJeu scene) {
        this(grille, size, size, DEFAULT_PROPORTION_INTERSTICE, scene);
        logger.debug("Utilisation d'une taille carrée: {} et de la proportion d'interstice par défaut: {}", 
                    size, DEFAULT_PROPORTION_INTERSTICE);
    }

    /**
     * Améliore la zone cliquable des arêtes en gérant les clics et survols au niveau du GridPane.
     * Cette méthode permet de détecter les clics à proximité des arêtes même s'ils ne sont pas directement sur elles.
     */
    private void ameliorerHitboxAreteView() {
        logger.debug("Configuration des gestionnaires d'événements pour améliorer les hitbox des arêtes");
        this.setOnMousePressed(event -> {
            for(AreteView av : this.listAreteView) {
                av.gererClic(event);
            }
        });
        this.setOnMouseMoved(event -> {
            for(AreteView av : this.listAreteView) {
                av.gererHover(event);
            }
        });
    }

    /**
     * Retourne la vue d'arête correspondant à l'arête du modèle donnée.
     * 
     * @param arete L'arête du modèle à rechercher
     * @return La vue correspondante ou null si non trouvée
     */
    public AreteView getAreteView(Arete arete) {
        logger.trace("Recherche de l'AreteView pour l'arête [{},{}]", arete.getLigne(), arete.getColonne());
        for (AreteView areteView : listAreteView) {
            if (areteView.getArete() == arete) {
                return areteView;
            }
        }
        logger.warn("AreteView non trouvée pour l'arête [{},{}]", arete.getLigne(), arete.getColonne());
        return null;
    }

    /**
     * Retourne la largeur en pourcentage des interstices.
     * 
     * @return La largeur des interstices
     */
    public double getLargeurInterstice() {
        return this.largeurInterstice;
    }

    /**
     * Retourne la largeur en pourcentage des cases contenant des chiffres.
     * 
     * @return La largeur des cases de chiffres
     */
    public double getLargeurChiffre() {
        return this.largeurChiffre;
    }

    /**
     * Retourne la référence au modèle de grille.
     * 
     * @return Le modèle de grille
     */
    public Grille getGrille() {
        return this.grille;
    }

    /**
     * Met à jour l'état visuel de toutes les arêtes de la grille.
     * Cette méthode est appelée après des changements dans le modèle.
     */
    public void update() {
        logger.debug("Mise à jour de l'affichage de toutes les arêtes");
        for(AreteView av : this.listAreteView) {
            av.update();
        }
    }

    /**
     * Réinitialise la couleur de toutes les arêtes de la grille.
     * Utilisé pour revenir à l'affichage normal après des mises en évidence.
     */
    public void initColorArete() {
        logger.debug("Réinitialisation de la couleur de toutes les arêtes");
        for(AreteView av : this.listAreteView) {
            av.initColor();
        }
    }
}