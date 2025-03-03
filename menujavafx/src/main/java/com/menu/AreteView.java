package com.menu;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import java.util.List;

/**
 * Représente la vue graphique d'une arête dans l'interface utilisateur du jeu Slitherlink.
 * Étend la classe Button pour permettre l'interaction avec l'arête.
 */
public class AreteView extends Button {

    /**
     * L'arête du modèle associée à cette vue.
     */
    private Arete arete;
    
    /**
     * Image représentant un trait (horizontal ou vertical).
     */
    private Image imTrait;

    /**
     * Image représentant une croix.
     */
    private Image imCroix;

    /**
     * Vue de l'image pour afficher le trait ou la croix.
     */
    private ImageView iv;

    /**
     * Constructeur de AreteView.
     * Initialise la vue de l'arête avec ses images et ses interactions.
     * 
     * @param arete L'arête du modèle à associer à cette vue
     */
    public AreteView(Arete arete){
        super();
        this.arete = arete;
        
        // Charge l'image du trait selon l'orientation de l'arête
        this.imTrait = new Image("trait" +  
        (arete.getOrientation() == EnumOrientation.VERTICAL ? "Vertical" : "Horizontal") +
        ".png");
        this.imCroix = new Image("croix.png");
        this.iv = new ImageView();
        this.setVide();

        // Ajuste la taille de l'image
        iv.fitWidthProperty().bind(this.widthProperty());
        iv.fitHeightProperty().bind(this.heightProperty());
        this.setGraphic(iv);

        // Configuration du style du bouton
        this.setMinSize(0,0);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setStyle("-fx-background-color: transparent;");
        this.setOnMousePressed(event -> {this.gererClic(event);});
    }

    /**
     * Récupère l'arête du modèle associée à cette vue.
     * 
     * @return L'arête du modèle
     */
    public Arete getArete(){
        return this.arete;
    }

    /**
     * Tente de définir l'arête comme un trait.
     * Si le placement est invalide, transforme en croix.
     */
    public void setTrait(){
        if(this.arete.setTrait()) {
            this.iv.setVisible(true);
            this.iv.setImage(imTrait);
            
            // Place automatiquement des croix si l'option est activée
            if (GameSettings.getInstance().isAutoCroix()) {
                placeAutoCroix();
            }
        }
        else {
            this.setCroix();
        }
    }
    
    /**
     * Place automatiquement des croix sur les arêtes qui ne peuvent pas être utilisées
     * en raison des contraintes du jeu.
     */
    private void placeAutoCroix() {
        // Récupère les points voisins de cette arête
        List<Point> pointsVoisins = this.arete.getPointsVoisins();
        
        for (Point point : pointsVoisins) {
            // Si le point a déjà 2 arêtes (le maximum permis)
            if (point.getNbAretesVoisines() == 2) {
                // Trouve et marque les arêtes voisines vides d'une croix
                for (Arete areteVoisine : point.getAretesVoisines()) {
                    if (areteVoisine.getEtat() == EnumEtat.VIDE) {
                        AreteView areteView = findAreteView(areteVoisine);
                        if (areteView != null) {
                            areteView.setCroix();
                        }
                    }
                }
            }
        }
        
        // Récupère les chiffres voisins de cette arête
        List<Chiffre> chiffresVoisins = this.arete.getChiffresVoisins();
        
        for (Chiffre chiffre : chiffresVoisins) {
            // Si le chiffre n'est pas vide et a déjà le nombre exact d'arêtes
            if (!chiffre.estVide() && chiffre.matchNbAretesVoisines()) {
                // Trouve et marque les arêtes voisines vides d'une croix
                List<Arete> aretesAutourChiffre = chiffre.getAretesVoisines();
                for (Arete areteVoisine : aretesAutourChiffre) {
                    if (areteVoisine.getEtat() == EnumEtat.VIDE) {
                        AreteView areteView = findAreteView(areteVoisine);
                        if (areteView != null) {
                            areteView.setCroix();
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Trouve la vue d'arête correspondant à une arête du modèle.
     * 
     * @param arete L'arête du modèle à rechercher
     * @return La vue de l'arête correspondante, ou null si non trouvée
     */
    private AreteView findAreteView(Arete arete) {
        // Recherche du contrôleur de grille dans la hiérarchie des parents
        Parent parent = this.getParent();
        while (parent != null && !(parent instanceof GrilleController)) {
            parent = parent.getParent();
        }
        
        if (parent instanceof GrilleController) {
            return ((GrilleController) parent).getAreteView(arete);
        }
        
        return null;
    }

    /**
     * Définit l'arête comme une croix.
     */
    public void setCroix(){
        this.arete.setCroix();
        this.iv.setVisible(true);
        this.iv.setImage(imCroix);
    }

    /**
     * Définit l'arête comme vide (sans trait ni croix).
     */
    public void setVide(){
        this.arete.setVide();
        this.iv.setVisible(false);
    }

    /**
     * Gère l'événement de clic sur l'arête.
     * 
     * @param event L'événement de souris
     */
    public void gererClic(MouseEvent event){
        if (this.isCurseurProche(event)){
            // Gère les clics gauche et droit
            if (event.getButton() == MouseButton.PRIMARY)
                this.clicGauche();
            else if (event.getButton() == MouseButton.SECONDARY)
                this.clicDroit();
            
            // Placement automatique des croix si activé
            if (GameSettings.getInstance().isAutoCroix()) {
                GrilleController grilleController = findGrilleController();
                if (grilleController != null) {
                    grilleController.applyAutoCroixToAllAretes();
                }
            }
            
            // Validation de la grille
            System.out.println("Validation de la grille");
            boolean resultat = this.arete.getGrille().check();
            if (resultat) {
                System.out.println("Grille correcte");
            } else {
                System.out.println("Grille incorrecte");
            }
        }
    }
    
    /**
     * Trouve le contrôleur de grille dans la hiérarchie des parents.
     * 
     * @return Le contrôleur de grille, ou null s'il n'est pas trouvé
     */
    private GrilleController findGrilleController() {
        Parent parent = this.getParent();
        while (parent != null && !(parent instanceof GrilleController)) {
            parent = parent.getParent();
        }
        return (GrilleController) parent;
    }

    /**
     * Vérifie si le curseur est proche de l'arête.
     * 
     * @param event L'événement de souris
     * @return true si le curseur est proche, false sinon
     */
    public boolean isCurseurProche(MouseEvent event){
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();
        
        double sommetHautGaucheX = this.localToScene(this.getBoundsInLocal()).getMinX();
        double sommetBasDroiteX = this.localToScene(this.getBoundsInLocal()).getMaxX();
        double sommetHautGaucheY = this.localToScene(this.getBoundsInLocal()).getMinY();
        double sommetBasDroiteY = this.localToScene(this.getBoundsInLocal()).getMaxY();

        //calcul des autres sommets
        double sommetHautDroitX = sommetBasDroiteX;
        double sommetHautDroitY = sommetHautGaucheY;
        double sommetBasGaucheX = sommetHautGaucheX;
        double sommetBasGaucheY = sommetBasDroiteY;

        double distance1, distance2, distance = 0;
        if(this.getArete().getOrientation() == EnumOrientation.VERTICAL){
            // coté gauche
            distance1 = (sommetHautGaucheX + sommetHautGaucheY) - (mouseX + mouseY) ;
            distance2 = (sommetBasGaucheX - sommetBasGaucheY) - (mouseX - mouseY) ;
            double distanceGauche = Math.max(distance1, distance2);

            // coté droit
            distance1 = (mouseX - mouseY) - (sommetHautDroitX - sommetHautDroitY) ;
            distance2 = (mouseX + mouseY) - (sommetBasDroiteX + sommetBasDroiteY);
            double distanceDroit = Math.max(distance1, distance2);

            distance = Math.max(distanceGauche, distanceDroit);
            
        }
        else{
            // coté haut 
            distance1 = (sommetHautGaucheX + sommetHautGaucheY) - (mouseX + mouseY) ;
            distance2 = (mouseX - mouseY) - (sommetHautDroitX - sommetHautDroitY) ;
            double distanceHaut = Math.max(distance1, distance2);

            // coté bas
            distance1 = (sommetBasGaucheX - sommetBasGaucheY) - (mouseX - mouseY);
            distance2 = (mouseX + mouseY) - (sommetBasDroiteX + sommetBasDroiteY);
            double distanceBas = Math.max(distance1, distance2);

            distance = Math.max(distanceHaut, distanceBas);
        }

        
        return distance < 0;
    }

    /**
     * Gère le clic gauche sur l'arête.
     * Alterne entre trait et vide.
     */
    public void clicGauche() {
        if (this.arete.getEtat() != EnumEtat.TRAIT) 
            this.setTrait();
        else 
            this.setVide();
    }

    /**
     * Gère le clic droit sur l'arête.
     * Alterne entre croix et vide.
     */
    public void clicDroit() {
        if (this.arete.getEtat() != EnumEtat.CROIX) 
            this.setCroix();
        else 
            this.setVide();
    }
}