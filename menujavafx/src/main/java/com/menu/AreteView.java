package com.menu;

import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Représente la vue graphique d'une arête dans l'interface utilisateur du jeu Slitherlink.
 * Étend la classe Button pour permettre l'interaction avec l'arête.
 * Gère l'affichage visuel des arêtes (traits ou croix) ainsi que les interactions utilisateur.
 */
public class AreteView extends Button {
    private static final Logger logger = LoggerFactory.getLogger(AreteView.class);

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
     * Scène de jeu pour afficher la victoire.
     */
    private SceneJeu scene;

    /**
     * Constante d'opacité par défaut pour les images d'arêtes.
     * Représente l'opacité maximale (100%) utilisée pour les arêtes pleinement visibles.
     */
    private static final double DEFAULT_OPACITY = 1.0;

    /**
     * Constante d'opacité pour l'effet de survol du curseur.
     * Représente l'opacité réduite utilisée pour afficher une prévisualisation
     * lorsque le curseur survole une arête vide.
     */
    private static final double CURSOR_OPACITY = 0.4;

    /**
     * Effet de couleur par défaut, sans modification de teinte.
     * Utilisé pour l'affichage normal des arêtes.
     */
    private static final ColorAdjust TEINTE_DEFAULT = new ColorAdjust(0, 0, 0, 0);

    /**
     * Effet de couleur pour le mode tâtonnement.
     * Ajuste la teinte pour distinguer visuellement les arêtes en mode tâtonnement.
     */
    private static final ColorAdjust TEINTE_TATONNEMENT = new ColorAdjust(-0.5, 0, 0, 0);

    /**
     * Constructeur de AreteView.
     * Initialise la vue de l'arête avec ses images et ses interactions.
     * Configure les événements de souris et l'apparence visuelle.
     * 
     * @param arete L'arête du modèle à associer à cette vue
     * @param scene La scène de jeu à laquelle cette arête appartient
     */
    public AreteView(Arete arete, SceneJeu scene){
        super();
        this.arete = arete;
        this.scene = scene;
        
        logger.debug("Création d'une vue d'arête pour l'arête à la position ({}, {}), orientation: {}",
                arete.getLigne(), arete.getColonne(), arete.getOrientation());
        
        // Charge l'image du trait selon l'orientation de l'arête
        this.imTrait = new Image("trait" +  
        (arete.getOrientation() == EnumOrientation.VERTICAL ? "Vertical" : "Horizontal") +
        ".png");
        this.imCroix = new Image("croix" + 
        (arete.getOrientation() == EnumOrientation.VERTICAL ? "Vertical" : "Horizontal") +".png");
        this.iv = new ImageView();
        this.update();

        logger.trace("Images chargées pour l'arête ({}, {})", arete.getLigne(), arete.getColonne());

        // Ajuste la taille de l'image
        iv.fitWidthProperty().bind(this.widthProperty());
        iv.fitHeightProperty().bind(this.heightProperty());
        this.setGraphic(iv);

        // Configuration du style du bouton
        this.setMinSize(0,0);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setStyle("-fx-background-color: transparent;");
        this.setOnMousePressed(event -> {this.gererClic(event);});

        //
        this.setOnMouseEntered(e-> {
            logger.trace("Souris entrée sur l'arête ({}, {})", arete.getLigne(), arete.getColonne());
            if(this.arete.getEtat() == EnumEtat.VIDE){
                this.iv.setImage(imTrait);
                this.iv.setVisible(true);
            }
        });

        this.setOnMouseExited(e-> {
            logger.trace("Souris sortie de l'arête ({}, {})", arete.getLigne(), arete.getColonne());
            if(this.arete.getEtat() == EnumEtat.VIDE){
                this.iv.setVisible(false);
            }
        });
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
     * Si le placement est invalide et que le paramètre croix est vrai,
     * transforme l'arête en croix au lieu d'un trait.
     * 
     * @param croix Indique si une croix doit être placée en cas d'échec de placement du trait
     */
    public void setTrait(Boolean croix){
        logger.debug("Tentative de définir un trait sur l'arête vue ({}, {}) avec autocroix={}",
                arete.getLigne(), arete.getColonne(), croix);
        if(!croix){
            this.arete.setTrait(croix);
            this.updateToTrait();
        }
        else{
            if(this.arete.setTrait(croix)) {
                logger.debug("Trait placé avec succès sur l'arête ({}, {})", 
                          arete.getLigne(), arete.getColonne());
                this.updateToTrait();
            }
            else {
                logger.debug("Placement de trait impossible, placement d'une croix sur l'arête ({}, {})", 
                          arete.getLigne(), arete.getColonne());
                this.setCroix();
            }
        }
        SoundPlayer.bruitDeClique();
    }

    /**
     * Met à jour l'affichage pour montrer un trait.
     * Définit l'image appropriée avec l'opacité par défaut et la rend visible.
     */
    private void updateToTrait(){
        logger.trace("Mise à jour de l'affichage vers trait pour l'arête ({}, {})", 
                  arete.getLigne(), arete.getColonne());
        this.iv.setImage(imTrait);
        this.iv.setOpacity(DEFAULT_OPACITY);
        this.iv.setVisible(true);
    }

    /**
     * Définit l'arête comme une croix.
     * Met à jour à la fois le modèle et la vue.
     */
    public void setCroix(){
        logger.debug("Définition d'une croix sur l'arête vue ({}, {})", 
                  arete.getLigne(), arete.getColonne());
        this.arete.setCroix();
        this.updateToCroix();
    }

    /**
     * Met à jour l'affichage pour montrer une croix.
     * Définit l'image de croix avec l'opacité par défaut et la rend visible.
     */
    private void updateToCroix(){
        logger.trace("Mise à jour de l'affichage vers croix pour l'arête ({}, {})", 
                  arete.getLigne(), arete.getColonne());
        this.iv.setImage(imCroix);
        this.iv.setOpacity(DEFAULT_OPACITY);
        this.iv.setVisible(true);
    }

    /**
     * Définit l'arête comme vide (sans trait ni croix).
     * Met à jour à la fois le modèle et la vue.
     */
    public void setVide(){
        logger.debug("Définition de l'arête vue ({}, {}) comme vide", 
                  arete.getLigne(), arete.getColonne());
        this.arete.setVide();
        this.iv.setVisible(false);
    }

    /**
     * Définit l'arête comme semi-transparente.
     * Utilisé pour la prévisualisation lorsque le curseur survole une arête vide.
     */
    public void setTransparent(){
        logger.trace("Application de la transparence sur l'arête ({}, {})", 
                  arete.getLigne(), arete.getColonne());
        this.updateToTrait();
        this.iv.setOpacity(CURSOR_OPACITY);
    }

    /**
     * Applique l'apparence du mode tâtonnement à l'arête.
     * Utilise une opacité réduite et change la teinte de l'image pour indiquer
     * visuellement que l'arête est en mode tâtonnement.
     */
    public void setTatonnement(){
        logger.debug("Application du mode tâtonnement sur l'arête ({}, {})", 
                  arete.getLigne(), arete.getColonne());
        this.iv.setImage(imTrait);
        this.iv.setOpacity(CURSOR_OPACITY);
        //changer la teinture de l'image

        this.iv.setVisible(true);
    }

    /**
     * Gère l'événement de clic sur l'arête.
     * Vérifie si le curseur est proche de l'arête et exécute l'action
     * correspondante selon le bouton utilisé (gauche ou droit).
     * Vérifie également si la grille est résolue après le clic.
     * 
     * @param event L'événement de souris
     */
    public void gererClic(MouseEvent event){
        if (this.isCurseurProche(event)){
            logger.debug("Clic détecté sur l'arête ({}, {}) avec le bouton {}", 
                      arete.getLigne(), arete.getColonne(), event.getButton());
            if (event.getButton() == MouseButton.PRIMARY){
                this.clicGauche(GameSettings.getInstance().isAutoCroix());
            }
                
            else if (event.getButton() == MouseButton.SECONDARY){
                this.clicDroit();
            }
            
            // Validation de la grille
            boolean resultat = this.arete.getGrille().resolue();
            if (resultat) {
                logger.info("Grille résolue après interaction avec l'arête ({}, {})", 
                         arete.getLigne(), arete.getColonne());
                if(!this.arete.getLibre()){
                    /* Victoire */
                    logger.info("Victoire détectée en mode normal");
                    this.scene.victoryScreen();
                }
                else{
                    logger.info("Grille terminée en mode libre");
                    this.scene.finishScreen();
                }
            }
        }
        else{
            logger.trace("Clic détecté mais curseur non proche de l'arête ({}, {})", 
                      arete.getLigne(), arete.getColonne());
        }
    }

    /**
     * Gère l'événement de survol de la souris sur l'arête.
     * Met à jour l'affichage et montre une prévisualisation si le curseur
     * est proche d'une arête vide.
     * 
     * @param event L'événement de souris
     */
    public void gererHover(MouseEvent event){
        this.update();
        if (this.isCurseurProche(event) && this.arete.getEtat() == EnumEtat.VIDE){
            logger.trace("Curseur proche de l'arête vide ({}, {}), affichage transparent", 
                      arete.getLigne(), arete.getColonne());
            this.setTransparent();
        }
    }

    /**
     * Vérifie si le curseur est proche de l'arête.
     * Utilise des calculs géométriques pour déterminer si le curseur se trouve
     * dans la zone d'interaction de l'arête, en tenant compte de son orientation.
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

        boolean estProche = distance < 0;
        logger.trace("Vérification proximité curseur pour l'arête ({}, {}): {}", 
                  arete.getLigne(), arete.getColonne(), estProche);
        return estProche;
    }

    /**
     * Gère le clic gauche sur l'arête.
     * Alterne entre trait et vide, en respectant les règles du jeu.
     * Si le trait ne peut pas être placé et que l'option autoCroix est activée,
     * une croix sera placée à la place.
     * 
     * @param croix Indique si une croix doit être placée automatiquement en cas d'échec
     */
    public void clicGauche(Boolean croix) {
        logger.debug("Clic gauche sur l'arête ({}, {}) avec autocroix={}", 
                  arete.getLigne(), arete.getColonne(), croix);
        if (this.arete.getEtat() != EnumEtat.TRAIT) 
            this.setTrait(croix);
        else 
            this.setVide();
    }

    /**
     * Gère le clic droit sur l'arête.
     * Alterne entre croix et vide.
     */
    public void clicDroit() {
        logger.debug("Clic droit sur l'arête ({}, {})", arete.getLigne(), arete.getColonne());
        if (this.arete.getEtat() != EnumEtat.CROIX) 
            this.setCroix();
        else 
            this.setVide();
    }

    /**
     * Met à jour l'affichage de l'arête en fonction de son état actuel.
     * Applique les effets visuels appropriés selon que l'arête est un trait,
     * une croix, ou vide, et si la grille est en mode tâtonnement.
     */
    public void update(){
        logger.trace("Mise à jour de l'affichage de l'arête ({}, {}), état actuel: {}", 
                  arete.getLigne(), arete.getColonne(), arete.getEtat());

        if(this.arete.getEtat() == EnumEtat.TRAIT){
            this.updateToTrait();
        }
        else if(this.arete.getEtat() == EnumEtat.CROIX){
                this.updateToCroix();
        }
        else{
            if (this.arete.grille.enModeTatonnement()){      
                logger.trace("Application du style tâtonnement pour l'arête ({}, {})", 
                            arete.getLigne(), arete.getColonne());
                this.setEffect(TEINTE_TATONNEMENT);      
            }
            else{
                this.setEffect(TEINTE_DEFAULT);
                //supprimer l'effet
                this.initColor();
            }
            this.iv.setVisible(false);
        }
    }

    /**
     * Réinitialise les effets de couleur de l'arête.
     * Supprime tous les effets visuels appliqués à l'arête.
     */
    public void initColor(){
        logger.trace("Réinitialisation des effets de couleur pour l'arête ({}, {})", 
                  arete.getLigne(), arete.getColonne());
        this.setEffect(null);
    }
}