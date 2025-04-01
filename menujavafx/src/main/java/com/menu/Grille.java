package com.menu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe représentant une grille de jeu Slitherlink.
 * Gère la structure des cases, les actions de jeu, la sauvegarde et le chargement,
 * ainsi que les techniques d'aide.
 */
public class Grille {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(Grille.class);
    
    /** Dossier de sauvegarde des parties */
    public static final String SAVE_FOLDER = "sauvegarde";
    
    /** Nom de la grille */
    private String name;

    /** Nombre de lignes de la grille */
    protected int nbLignes;
    
    /** Nombre de colonnes de la grille */
    protected int nbColonnes;
    
    /** Tableau des cases de la grille */
    protected Case[][] cases;
    
    /** Pile des actions effectuées pour l'annulation (undo) */
    protected Stack<Action> pileUndo;
    
    /** Pile des actions annulées pour le rétablissement (redo) */
    protected Stack<Action> pileRedo;

    /** Pile des actions effectuées en mode tâtonnement */
    protected Stack<Action> pileUndoTatonnement;
    
    /** Pile des actions annulées en mode tâtonnement */
    protected Stack<Action> pileRedoTatonnement;

    /** Liste des coordonnées des arêtes de la grille résolue */
    protected List<Coordonnee> aretesGrilleResolue;

    /** Référence à la scène de jeu */
    protected SceneJeu sceneJeu;
    
    /** Chronomètre pour le suivi du temps de jeu */
    protected Timeline timer;
    
    /** Gestion des temps de jeu (courant et meilleur) */
    protected TempsSauvegarde tempsSauvegarde;

    /** Objet pour la sérialisation/désérialisation JSON */
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** Liste des techniques d'aide disponibles */
    protected ArrayList<Technique> techniques;

    /** Tableau des aides déjà utilisées */
    private boolean[] listeAides;
    
    /** Indique si le mode tâtonnement est actif */
    private boolean modeTatonnement;
    
    /** Nombre d'actions effectuées en mode tâtonnement */
    private int nbActionsTatonnement;

    /**
     * Constructeur de la grille.
     * Initialise la grille à partir d'un fichier JSON et configure les structures nécessaires.
     *
     * @param fichier Nom du fichier JSON contenant la définition de la grille
     * @param sceneJeu Référence à la scène de jeu
     * @param libre Indique si la grille est en mode libre ou classique
     */
    public Grille(String fichier, SceneJeu sceneJeu, boolean libre) {
        logger.info("Création d'une nouvelle grille à partir du fichier: {}, mode libre: {}", fichier, libre);
        this.initialiserAides();
        try {
            // Charger le JSON
            this.modeTatonnement = false;
            this.nbActionsTatonnement = 0;
            this.name = fichier.split("\\.")[0];
            this.sceneJeu = sceneJeu;

            GrilleJson grilleJson;

            if(!libre) {
                logger.debug("Chargement de la grille classique depuis: grilles/{}", fichier);
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("grilles/"+fichier);
                if (inputStream == null) {
                    logger.error("Ressource non trouvée: grilles/{}", fichier);
                    throw new IOException("Resource not found: grilles/" + fichier);
                }
                grilleJson = OBJECT_MAPPER.readValue(inputStream, GrilleJson.class);
            }
            else {
                logger.debug("Chargement de la grille libre depuis: grilles_libre/{}", fichier);
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("grilles_libre/"+fichier);
                if (inputStream == null) {
                    logger.error("Ressource non trouvée: grilles_libre/{}", fichier);
                    throw new IOException("Resource not found: grilles_libre/" + fichier);
                }
                grilleJson = OBJECT_MAPPER.readValue(inputStream, GrilleJson.class);
            }

            // Affectation des dimensions spécifiées à la grille
            this.nbLignes = grilleJson.getLigne() * 2 + 1;
            this.nbColonnes = grilleJson.getColonne() * 2 + 1;
            logger.debug("Dimensions de la grille: {}x{}", this.nbLignes, this.nbColonnes);

            // Création des cases de la grille
            this.cases = new Case[this.nbLignes][this.nbColonnes];

            for(int i = 0; i < this.nbLignes; i++){
                for(int j = 0; j < this.nbColonnes; j++){
                    if(i%2 == 0 && j%2 == 0) this.cases[i][j] = new Point(i, j, this);
                    else if(i%2 == 0 || (i%2 == 1 && j%2 == 0)) this.cases[i][j] = new Arete(i,j,this,EnumEtat.VIDE,libre);
                    else this.cases[i][j] = new Chiffre(i, j, this);
                }
            }
            logger.debug("Initialisation des cases de la grille terminée");

            // Appliquer les modifications des cases chiffres
            this.ajouterChiffres(grilleJson.getAjoutChiffres());
            this.ajouterAretesGrilleResolue(grilleJson.getAretesGrilleResolue());
            
            this.charger(libre);

            if(!libre) {
                logger.debug("Initialisation du timer pour la grille classique");
                this.timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                    this.tempsSauvegarde.incrementerTemps();
                    sceneJeu.setTemps(this.tempsSauvegarde.getTemps());
                    this.sauvegarderTemps();
                }));
                this.timer.setCycleCount(Timeline.INDEFINITE);
            }

            // Initialisation des techniques d'aide
            logger.debug("Initialisation des techniques d'aide");
            techniques = new ArrayList<>();
            Collections.addAll(techniques, 
                new TechniqueAutour0(this), 
                new Technique0Adjacent3(this), 
                new Technique0Diagonal3(this), 
                new TechniqueDeux3Adjacent(this), 
                new TechniqueDeux3Diagonal(this), 
                new TechniqueNombreCoin(this), 
                new TechniqueContraintes3(this), 
                new TechniqueBoucleSur3(this), 
                new TechniqueBoucleSur1(this), 
                new TechniqueAvancee6(this),
                new TechniqueAvancee2(this), 
                new TechniqueAvancee5(this)
            );  

        } catch (IOException e) {
            logger.error("Erreur lors de la lecture du fichier JSON: {}", e.getMessage(), e);
            System.out.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
        }
    }

    /**
     * Initialise le tableau des aides utilisées.
     * Chaque élément du tableau correspond à une technique d'aide.
     */
    public void initialiserAides() {
        logger.debug("Initialisation du tableau des aides");
        this.listeAides = new boolean[12];
        this.techniques = new ArrayList<Technique>();
        int i;
        for (i=0; i<12; i++) {
            listeAides[i] = false;
        }
    }

    /**
     * Compte le nombre d'aides déjà utilisées.
     * 
     * @return Le nombre d'aides utilisées
     */
    public int nbAides() {
        int nb = 0;
        int i;
        for (i=0; i<12; i++) {
            if(listeAides[i]) {
                nb++;
            }
        }
        logger.trace("Nombre d'aides utilisées: {}", nb);
        return nb;
    }

    /**
     * Récupère la pile des actions pour l'annulation.
     * 
     * @return La pile des actions pour l'annulation
     */
    public Stack<Action> getPileUndo() {
        return this.pileUndo;
    }

    /**
     * Récupère la pile des actions pour le rétablissement.
     * 
     * @return La pile des actions pour le rétablissement
     */
    public Stack<Action> getPileRedo() {
        return this.pileRedo;
    }

    /**
     * Récupère la pile des actions pour l'annulation en mode tâtonnement.
     * 
     * @return La pile des actions pour l'annulation en mode tâtonnement
     */
    public Stack<Action> getPileUndoTatonnement() {
        return this.pileUndoTatonnement;
    }

    /**
     * Récupère la pile des actions pour le rétablissement en mode tâtonnement.
     * 
     * @return La pile des actions pour le rétablissement en mode tâtonnement
     */
    public Stack<Action> getPileRedoTatonnement() {
        return this.pileRedoTatonnement;
    }

    /**
     * Récupère le nombre de lignes de la grille.
     * 
     * @return Le nombre de lignes
     */
    public int getNbLignes() {
        return this.nbLignes;
    }

    /**
     * Récupère le nombre de colonnes de la grille.
     * 
     * @return Le nombre de colonnes
     */
    public int getNbColonnes() {
        return this.nbColonnes;
    }

    /**
     * Vérifie si les coordonnées passées en paramètre correspondent à une case existante de la grille.
     * 
     * @param ligne Coordonnée de ligne
     * @param colonne Coordonnée de colonne
     * @return true si la case existe, false sinon
     */
    public boolean caseExiste(int ligne, int colonne) {
        boolean existe = (ligne >= 0 && ligne < this.nbLignes && colonne >= 0 && colonne < this.nbColonnes);
        logger.trace("Vérification si la case [{},{}] existe: {}", ligne, colonne, existe);
        return existe;
    }

    /**
     * Récupère une case de la grille à partir de ses coordonnées.
     * 
     * @param ligne Coordonnée de ligne
     * @param colonne Coordonnée de colonne
     * @return La case aux coordonnées spécifiées
     * @throws ArrayIndexOutOfBoundsException Si les coordonnées sont hors des limites de la grille
     */
    public Case getCase(int ligne, int colonne) throws ArrayIndexOutOfBoundsException {
        if (!caseExiste(ligne, colonne)) {
            logger.warn("Tentative d'accès à une case hors limites: [{},{}]", ligne, colonne);
        }
        return this.cases[ligne][colonne];
    }

    /**
     * Définit la valeur d'un chiffre dans la grille.
     * 
     * @param ligne Coordonnée de ligne (logique, non matricielle)
     * @param colonne Coordonnée de colonne (logique, non matricielle)
     * @param chiffre Valeur du chiffre à définir
     */
    public void setChiffre(int ligne, int colonne, int chiffre) {
        logger.debug("Définition du chiffre {} à la position [{},{}]", chiffre, ligne, colonne);
        Chiffre c = (Chiffre) this.getCase(ligne*2+1, colonne*2+1);
        c.setChiffre(chiffre);
    }

    /**
     * Ajoute des chiffres à la grille à partir d'une liste de modifications.
     * 
     * @param ajoutChiffres Liste des modifications de chiffres à appliquer
     */
    public void ajouterChiffres(List<AjoutChiffre> ajoutChiffres) {
        logger.debug("Ajout de {} chiffres à la grille", ajoutChiffres.size());
        for (AjoutChiffre modif : ajoutChiffres) {
            int ligne = modif.getLigne();
            int colonne = modif.getColonne();
            int chiffre = modif.getChiffre();
            this.setChiffre(ligne, colonne, chiffre);
        }
    }

    /**
     * Définit les arêtes qui font partie de la solution de la grille.
     * 
     * @param aretesGrilleResolue Liste des coordonnées des arêtes faisant partie de la solution
     */
    public void ajouterAretesGrilleResolue(List<Coordonnee> aretesGrilleResolue) {
        logger.debug("Ajout de {} arêtes à la solution de la grille", aretesGrilleResolue.size());
        Iterator<Arete> itAretes = this.iteratorAretes();
        while(itAretes.hasNext()) {
            Arete a = itAretes.next();
            for (Coordonnee coordonnee : aretesGrilleResolue) {
                if (coordonnee.getLigne() == a.getLigne() && coordonnee.getColonne() == a.getColonne()) {
                    a.devientUneAreteDeLaGrilleResolue();
                    break;
                }
            }
        }
    }

    /**
     * Vérifie si la grille est résolue, c'est-à-dire si toutes les arêtes qui doivent
     * être tracées le sont, et si toutes les arêtes qui ne doivent pas être tracées ne le sont pas.
     * 
     * @return true si la grille est résolue, false sinon
     */
    public boolean resolue() {
        logger.debug("Vérification si la grille est résolue");
        Iterator<Arete> itAretes = this.iteratorAretes();
        while(itAretes.hasNext()) {      
            Arete a = itAretes.next();
            if (a.estUneAreteDeLaGrilleResolue() && a.getEtat() != EnumEtat.TRAIT ||
                !a.estUneAreteDeLaGrilleResolue() && a.getEtat() == EnumEtat.TRAIT) {
                logger.debug("Grille non résolue: arête incorrecte trouvée");
                return false;
            }
        }
        logger.info("Grille résolue !");
        return true;
    }

    /**
     * Compte le nombre d'erreurs dans la grille actuelle.
     * Une erreur est une arête tracée qui ne fait pas partie de la solution.
     * 
     * @return Le nombre d'erreurs dans la grille
     */
    public int check() {
        int nbErreurs = 0;
        Iterator<Arete> itAretes = this.iteratorAretes();
        while(itAretes.hasNext()) {      
            Arete a = itAretes.next();
            if (!a.estUneAreteDeLaGrilleResolue() && a.getEtat() == EnumEtat.TRAIT) {
                nbErreurs++;
            }
        }
        logger.debug("Nombre d'erreurs dans la grille: {}", nbErreurs);
        return nbErreurs;
    }

    /**
     * Retourne une représentation textuelle de la grille.
     * 
     * @return Chaîne représentant la grille
     */
    @Override
    public String toString() {
        logger.trace("Génération de la représentation textuelle de la grille");
        String chaine = "";
        for(Case[] x : this.cases) {
            for(Case y : x) {
                chaine += y;
            }
            chaine += "\n";
        }
        return chaine;
    }

    /**
     * Crée un itérateur pour parcourir les chiffres de la grille.
     * 
     * @return Itérateur sur les chiffres de la grille
     */
    public Iterator<Chiffre> iteratorChiffres() { 
        return new Iterator<Chiffre>() {
            private int ligne = 1;
            private int colonne = 1;
            public boolean hasNext() {
                return ligne < nbLignes;
            }
            public Chiffre next() {
                Chiffre c = (Chiffre) Grille.this.cases[ligne][colonne];
                colonne += 2;
                if (colonne >= nbColonnes) {
                    colonne = 1;
                    ligne += 2;
                }
                return c;
            }
        };
    }
    
    /**
     * Crée un itérateur pour parcourir les arêtes de la grille.
     * 
     * @return Itérateur sur les arêtes de la grille
     */
    public Iterator<Arete> iteratorAretes() { 
        return new Iterator<Arete>() {
            private int ligne = 0;
            private int colonne = 1;
            public boolean hasNext() {
                return ligne < nbLignes;
            }
            public Arete next() {
                Arete a = (Arete) Grille.this.cases[ligne][colonne];
                colonne += 2;
                if (colonne >= nbColonnes) { // changement de ligne
                    if (a.getOrientation() == EnumOrientation.HORIZONTAL) {
                        colonne = 0;
                    } else {
                        colonne = 1;
                    }
                    ligne++;
                }
                
                return a;
            }
        };
    }
    
    /**
     * Crée un itérateur pour parcourir les points de la grille.
     * 
     * @return Itérateur sur les points de la grille
     */
    public Iterator<Point> iteratorPoints() { 
        return new Iterator<Point>() {
            private int ligne = 0;
            private int colonne = 0;
            public boolean hasNext() {
                return ligne < nbLignes;
            }
            public Point next() {
                Point p = (Point) Grille.this.cases[ligne][colonne];
                colonne += 2;
                if (colonne >= nbColonnes) {
                    colonne = 0;
                    ligne += 2;
                }
                return p;
            }
        };
    }

    /**
     * Annule la dernière action effectuée.
     * Utilise la pile appropriée selon que le mode tâtonnement est actif ou non.
     */
    public void undo() {
        Stack<Action> pileUndo = modeTatonnement ? this.pileUndoTatonnement : this.pileUndo;
        Stack<Action> pileRedo = modeTatonnement ? this.pileRedoTatonnement : this.pileRedo;
        
        if(!pileUndo.isEmpty()) {
            Action action = pileUndo.pop();
            Arete a = (Arete) this.getCase(action.getLigne(), action.getColonne());
            logger.debug("Annulation d'une action: arête [{},{}] passe de {} à {}", 
                action.getLigne(), action.getColonne(), action.getEtat(), action.getEtatPrecedent());
            a.setEtat(action.getEtatPrecedent());
            pileRedo.push(action);
        } else {
            logger.debug("Tentative d'annulation sans action disponible");
        }
    }

    /**
     * Rétablit la dernière action annulée.
     * Utilise la pile appropriée selon que le mode tâtonnement est actif ou non.
     */
    public void redo() {
        Stack<Action> pileUndo = modeTatonnement ? this.pileUndoTatonnement : this.pileUndo;
        Stack<Action> pileRedo = modeTatonnement ? this.pileRedoTatonnement : this.pileRedo;
        
        if(!pileRedo.isEmpty()) {
            Action action = pileRedo.pop();
            Arete a = (Arete) this.getCase(action.getLigne(), action.getColonne());
            logger.debug("Rétablissement d'une action: arête [{},{}] passe de {} à {}", 
                action.getLigne(), action.getColonne(), action.getEtatPrecedent(), action.getEtat());
            a.setEtat(action.getEtat());
            pileUndo.push(action);
        } else {
            logger.debug("Tentative de rétablissement sans action disponible");
        }
    }

    /**
     * Réinitialise la grille en effaçant toutes les arêtes.
     * 
     * @param libre Indique si la grille est en mode libre ou classique
     */
    public void clear(boolean libre) {
        logger.info("Réinitialisation de la grille");
        Iterator<Arete> itAretes = this.iteratorAretes();
        while(itAretes.hasNext()) {
            Arete a = itAretes.next();
            a.setEtat(EnumEtat.VIDE);
        }
        this.pileUndo.clear();
        this.pileRedo.clear();
        
        if(!libre) {
            logger.debug("Sauvegarde de la progression (mode classique)");
            this.sauvegarderProgression();
        } else {
            logger.debug("Sauvegarde de la progression (mode libre)");
            this.sauvegarderProgressionLibre();
        }
    }

    /**
     * Charge la progression enregistrée pour la grille actuelle.
     */
    private void chargerProgression() {
        try {
            File fichierProgression = new File(getSauvegardePath("progress"));
            this.pileUndo = new Stack<Action>();
            this.pileRedo = new Stack<Action>();
            this.pileUndoTatonnement = new Stack<Action>();
            this.pileRedoTatonnement = new Stack<Action>();
            
            if (!fichierProgression.exists()) {
                logger.info("Aucune progression sauvegardée trouvée");
                return;
            }
            
            logger.info("Chargement de la progression depuis {}", fichierProgression.getPath());
            this.pileUndo = OBJECT_MAPPER.readValue(fichierProgression, new TypeReference<Stack<Action>>(){});
            this.chargerPile(this.pileUndo);
        } catch (Exception e) {
            logger.error("Erreur lors de la lecture du fichier JSON de progression: {}", e.getMessage(), e);
            System.err.println("Erreur lors de la lecture du fichier JSON de progression : " + e.getMessage());
        }
    }

    /**
     * Applique les actions d'une pile à la grille.
     * 
     * @param pile Pile d'actions à appliquer
     */
    private void chargerPile(Stack<Action> pile) {
        logger.debug("Application d'une pile de {} actions", pile.size());
        Iterator<Arete> itAretes = iteratorAretes();
        while(itAretes.hasNext()) {
            Arete a = itAretes.next();
            a.setEtat(EnumEtat.VIDE);
        }
        
        for(Action action : pile) {
            Arete a = (Arete) this.getCase(action.getLigne(), action.getColonne());
            a.setEtat(action.getEtat());
        }
    }
    
    /**
     * Charge la progression enregistrée pour la grille en mode libre.
     */
    private void chargerProgressionLibre() {
        try {
            File fichierProgression = new File(getSauvegardePath("progress_libre"));
            this.pileUndo = new Stack<Action>();
            this.pileRedo = new Stack<Action>();
            
            if (!fichierProgression.exists()) {
                logger.info("Aucune progression libre sauvegardée trouvée");
                return;
            }
            
            logger.info("Chargement de la progression libre depuis {}", fichierProgression.getPath());
            this.pileUndo = OBJECT_MAPPER.readValue(fichierProgression, new TypeReference<Stack<Action>>(){});
            this.chargerPile(this.pileUndo);
        } catch (Exception e) {
            logger.error("Erreur lors de la lecture du fichier JSON de progression libre: {}", e.getMessage(), e);
            System.err.println("Erreur lors de la lecture du fichier JSON de progression : " + e.getMessage());
        }
    }

    /**
     * Charge les temps enregistrés pour la grille actuelle.
     */
    private void chargerTemps() {
        try {
            File fichierTemps = new File(getSauvegardePath("time"));
            if (!fichierTemps.exists()) {
                logger.info("Aucun temps sauvegardé trouvé, initialisation à 0");
                this.tempsSauvegarde = new TempsSauvegarde(0, -1);
                return;
            }
            
            logger.info("Chargement des temps depuis {}", fichierTemps.getPath());
            tempsSauvegarde = OBJECT_MAPPER.readValue(fichierTemps, TempsSauvegarde.class);
            sceneJeu.setTemps(tempsSauvegarde.getTemps());
            sceneJeu.setMeilleurTemps(tempsSauvegarde.getMeilleurTemps());
        } catch (Exception e) {
            logger.error("Erreur lors de la lecture du fichier JSON de temps: {}", e.getMessage(), e);
            System.err.println("Erreur lors de la lecture du fichier JSON de temps : " + e.getMessage());
        }
    }

    /**
     * Charge les données sauvegardées pour la grille.
     * 
     * @param libre Indique si la grille est en mode libre ou classique
     */
    public void charger(boolean libre) {
        logger.info("Chargement des données sauvegardées, mode libre: {}", libre);
        if(!libre) {
            this.chargerProgression();
            this.chargerTemps();
            this.chargerAides();
        } else {
            this.chargerProgressionLibre();
            this.chargerAidesLibre();
        }
    }

    /**
     * Sauvegarde la progression de la grille en mode classique.
     */
    public void sauvegarderProgression() {
        try {
            File fichierProgression = new File(getSauvegardePath("progress"));
            logger.info("Sauvegarde de la progression dans {}", fichierProgression.getPath());
            OBJECT_MAPPER.writeValue(fichierProgression, this.pileUndo);
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du fichier JSON de progression: {}", e.getMessage(), e);
            System.err.println("Erreur lors de la sauvegarde du fichier JSON : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde la progression de la grille en mode libre.
     */
    public void sauvegarderProgressionLibre() {
        try {
            File fichierProgression = new File(getSauvegardePath("progress_libre"));
            logger.info("Sauvegarde de la progression libre dans {}", fichierProgression.getPath());
            OBJECT_MAPPER.writeValue(fichierProgression, this.pileUndo);
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du fichier JSON de progression libre: {}", e.getMessage(), e);
            System.err.println("Erreur lors de la sauvegarde du fichier JSON : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde les temps de jeu.
     */
    public void sauvegarderTemps() {
        try {
            File fichierTemps = new File(getSauvegardePath("time"));
            logger.debug("Sauvegarde des temps dans {}", fichierTemps.getPath());
            OBJECT_MAPPER.writeValue(fichierTemps, this.tempsSauvegarde);
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde du fichier JSON de temps: {}", e.getMessage(), e);
            System.err.println("Erreur lors de la sauvegarde du fichier JSON : " + e.getMessage());
        }
    }

    /**
     * Met à jour le meilleur temps si nécessaire.
     */
    public void updateMeilleurTemps() {
        if(tempsSauvegarde.getTemps() < tempsSauvegarde.getMeilleurTemps() || tempsSauvegarde.getMeilleurTemps() == -1) {
            logger.info("Mise à jour du meilleur temps: {} -> {}", 
                tempsSauvegarde.getMeilleurTemps(), tempsSauvegarde.getTemps());
            tempsSauvegarde.setMeilleurTemps(tempsSauvegarde.getTemps());
        }
    }

    /**
     * Récupère le chemin d'une ressource dans le JAR.
     * 
     * @param fichier Nom du fichier à rechercher
     * @return URL de la ressource
     * @throws IOException Si la ressource n'est pas trouvée
     */
    public String getResourceFilePath(String fichier) throws IOException {
        var resourceUrl = this.getClass().getClassLoader().getResource("grilles/"+fichier);
        if (resourceUrl == null) {
            logger.error("Ressource non trouvée: grilles/{}", fichier);
            throw new IOException("Resource not found: grilles/" + fichier);
        }
        
        // Return the URL as a string, don't convert to path
        logger.debug("Ressource trouvée à l'emplacement: {}", resourceUrl.toString());
        return resourceUrl.toString();
    }

    /**
     * Récupère le chemin d'un fichier de sauvegarde.
     * 
     * @param type Type de sauvegarde (progress, time, aides, etc.)
     * @return Chemin complet du fichier de sauvegarde
     */
    public String getSauvegardePath(String type) {
        Path userDir = Paths.get(System.getProperty("user.dir"), ".slitherlinkGroup2");
        // vérifier que le dossier existe, sinon le créer
        if(!Files.exists(userDir)) {
            try {
                logger.info("Création du dossier de sauvegarde: {}", userDir);
                Files.createDirectory(userDir);
            } catch (IOException e) {
                logger.error("Erreur lors de la création du dossier de sauvegarde: {}", e.getMessage(), e);
                System.out.println("Erreur lors de la création du dossier de sauvegarde : " + e.getMessage());
            }
        }
        Path filePath = userDir.resolve(this.name + "_" + type + ".json");
        logger.trace("Chemin de sauvegarde pour {}: {}", type, filePath);
        return filePath.toString();
    }

    /**
     * Récupère le chemin du fichier de paramètres.
     * 
     * @return Chemin complet du fichier de paramètres
     */
    public static String getSettingsPath() {
        Path userDir = Paths.get(System.getProperty("user.dir"), ".slitherlinkGroup2");
        // vérifier que le dossier existe, sinon le créer
        if(!Files.exists(userDir)) {
            try {
                logger.info("Création du dossier de sauvegarde pour les paramètres: {}", userDir);
                Files.createDirectory(userDir);
            } catch (IOException e) {
                logger.error("Erreur lors de la création du dossier de sauvegarde: {}", e.getMessage(), e);
                System.out.println("Erreur lors de la création du dossier de sauvegarde : " + e.getMessage());
            }
        }
        Path filePath = userDir.resolve("settings.json");
        return filePath.toString();
    } 

    /**
     * Rétablit un état valide de la grille en annulant les actions qui ont causé des erreurs.
     */
    public void retablirEtatValide() {
        logger.info("Tentative de rétablir un état valide de la grille");
        int nbErreurs = this.check();
        if (nbErreurs > 0) {
            logger.debug("Annulation de {} erreurs", nbErreurs);
        }
        while(this.check() > 0) {
            this.undo();
        }
        logger.info("État valide rétabli");
    }

    /**
     * Démarre le chronomètre de la partie.
     */
    public void runTimer() {
        if (timer != null) {
            logger.debug("Démarrage du timer");
            timer.play();
        }
    }

    /**
     * Arrête le chronomètre de la partie.
     */
    public void stopTimer() {
        if (timer != null) {
            logger.debug("Arrêt du timer");
            timer.stop();
        }
    }

    /**
     * Réinitialise le chronomètre de la partie.
     */
    public void resetTimer() {
        logger.debug("Réinitialisation du timer");
        this.tempsSauvegarde.setTemps(0);
        if (sceneJeu != null) sceneJeu.setTemps(0);
    }

    /**
     * Définit la référence à la scène de jeu.
     * 
     * @param sceneJeu La scène de jeu à associer
     */
    public void setSceneJeu(SceneJeu sceneJeu) {
        logger.debug("Changement de la référence à la scène de jeu");
        this.sceneJeu = sceneJeu;
    }

    /**
     * Charge les aides déjà utilisées pour la grille en mode classique.
     */
    private void chargerAides() {
        try {
            File fichierAides = new File(getSauvegardePath("aides"));
            if (!fichierAides.exists()) {
                logger.info("Aucune aide sauvegardée trouvée, initialisation par défaut");
                this.initialiserAides();
                return;
            }

            logger.info("Chargement des aides depuis {}", fichierAides.getPath());
            this.listeAides = OBJECT_MAPPER.readValue(fichierAides, boolean[].class);

        } catch (Exception e) {
            logger.error("Erreur lors du chargement des aides: {}", e.getMessage(), e);
            System.out.println("Erreur lors du chargement des aides");
            this.initialiserAides();
        }
    }

    /**
     * Charge les aides déjà utilisées pour la grille en mode libre.
     */
    private void chargerAidesLibre() {
        try {
            File fichierAides = new File(getSauvegardePath("aides_libre"));
            if (!fichierAides.exists()) {
                logger.info("Aucune aide libre sauvegardée trouvée, initialisation par défaut");
                this.initialiserAides();
                return;
            }

            logger.info("Chargement des aides libres depuis {}", fichierAides.getPath());
            this.listeAides = OBJECT_MAPPER.readValue(fichierAides, boolean[].class);

        } catch (Exception e) {
            logger.error("Erreur lors du chargement des aides libres: {}", e.getMessage(), e);
            System.out.println("Erreur lors du chargement des aides");
            this.initialiserAides();
        }
    }

    /**
     * Sauvegarde les aides utilisées pour la grille en mode classique.
     */
    public void sauvegarderAides() {
        try {
            File fichierAides = new File(getSauvegardePath("aides"));
            logger.debug("Sauvegarde des aides dans {}", fichierAides.getPath());
            OBJECT_MAPPER.writeValue(fichierAides, this.listeAides);
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde des aides: {}", e.getMessage(), e);
            System.out.println("Erreur lors de la sauvegarde des aides");
        }
    }

    /**
     * Sauvegarde les aides utilisées pour la grille en mode libre.
     */
    public void sauvegarderAidesLibre() {
        try {
            File fichierAides = new File(getSauvegardePath("aides_libre"));
            logger.debug("Sauvegarde des aides libres dans {}", fichierAides.getPath());
            OBJECT_MAPPER.writeValue(fichierAides, this.listeAides);
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde des aides libres: {}", e.getMessage(), e);
            System.out.println("Erreur lors de la sauvegarde des aides");
        }
    }

    /**
     * Récupère le tableau des aides utilisées.
     * 
     * @return Tableau de booléens indiquant les aides utilisées
     */
    public boolean[] getListeAides() {
        return this.listeAides;
    }

    /**
     * Affiche une aide applicable à la situation actuelle.
     * 
     * @param libre Indique si la grille est en mode libre ou classique
     */
    public void aide(boolean libre) {
        logger.info("Recherche d'une technique d'aide applicable");
        int i = 1;
        for(Technique t : techniques) {
            if(t.applicable()) {
                logger.debug("Technique applicable trouvée: technique {}", i);
                BoxFactory.showTechnique(i, sceneJeu.getPrimaryStage(), t);
                if (!listeAides[i-1]) {
                    logger.info("Première utilisation de la technique {}", i);
                    listeAides[i-1] = true;
                    if(!libre) {
                        this.sauvegarderAides();
                    } else {
                        this.sauvegarderAidesLibre();
                    }
                    sceneJeu.setRightBox(BoxFactory.createHelpButtonBox(listeAides, sceneJeu.getPrimaryStage(), this));
                }
                break;
            }
            i++;
        }
        
        if (i > techniques.size()) {
            logger.info("Aucune technique d'aide applicable trouvée");
        }
    }

    /**
     * Active le mode tâtonnement qui permet d'essayer des actions
     * sans les enregistrer définitivement.
     */
    public void activerTatonnement() {
        logger.info("Activation du mode tâtonnement");
        modeTatonnement = true;
        //copier la pile undo dans la pile undo tatonnement
        this.pileUndoTatonnement = new Stack<Action>();
        this.pileRedoTatonnement = new Stack<Action>();
        for(Action action : pileUndo) {
            pileUndoTatonnement.push(action);
        }
    }

    /**
     * Vérifie si le mode tâtonnement est actif.
     * 
     * @return true si le mode tâtonnement est actif, false sinon
     */
    public boolean enModeTatonnement() {
        return modeTatonnement;
    }

    /**
     * Annule toutes les actions effectuées en mode tâtonnement.
     */
    public void annulerTatonnement() {
        logger.info("Annulation du mode tâtonnement");
        modeTatonnement = false; 
        this.chargerPile(this.pileUndo);
        this.pileRedo.clear();
    }

    /**
     * Valide les actions effectuées en mode tâtonnement et les
     * intègre à la progression normale.
     */
    public void validerTatonnement() {
        logger.info("Validation du mode tâtonnement");
        modeTatonnement = false;
        this.pileUndo = this.pileUndoTatonnement;
        this.pileRedo = this.pileRedoTatonnement;
        this.sauvegarderProgression();
    }
}