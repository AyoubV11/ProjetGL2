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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Grille {
    public static final String SAVE_FOLDER = "sauvegarde";
    private String name;

    protected int nbLignes;   // Abscisse de la grille
    protected int nbColonnes;   // Ordonnée de la grille
    protected Case[][] cases;   // Cases de la grille
    protected Stack<Action> pileUndo;   // Pile des actions effectuées
    protected Stack<Action> pileRedo;   // Pile des actions annulées
    protected List<Coordonnee> aretesGrilleResolue;   // Liste des coordonnées des arêtes de la grille résolue

    protected SceneJeu sceneJeu;
    protected Timeline timer;   // Chronomètre
    protected TempsSauvegarde tempsSauvegarde;// Temps de la partie en cours et meilleur temps

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected ArrayList<Technique> techniques;

    private boolean[] listeAides;
    
    private boolean modeTatonnement;
    private int nbActionsTatonnement;

    public Grille(String fichier, SceneJeu sceneJeu) {
        this.initialiserAides();
        try {
            // Charger le JSON
            this.modeTatonnement = false;
            this.nbActionsTatonnement = 0;
            this.name = fichier.split("\\.")[0];
            this.sceneJeu = sceneJeu;

            File fichierJSON = new File(getResourceFilePath(fichier));

            GrilleJson grilleJson = OBJECT_MAPPER.readValue(fichierJSON, GrilleJson.class);

            // Affectation des dimensions spécifiées à la grille
            this.nbLignes = grilleJson.getLigne() * 2 + 1;
            this.nbColonnes = grilleJson.getColonne() * 2 + 1;

            // Création des cases de la grille
            this.cases = new Case[this.nbLignes][this.nbColonnes];

            for(int i = 0; i < this.nbLignes; i++){
                for(int j = 0; j < this.nbColonnes; j++){
                    if(i%2 == 0 && j%2 == 0) this.cases[i][j] = new Point(i, j, this);
                    else if(i%2 == 0 || (i%2 == 1 && j%2 == 0)) this.cases[i][j] = new Arete(i,j,this,EnumEtat.VIDE);
                    else this.cases[i][j] = new Chiffre(i, j, this);
                }
            }

            // Appliquer les modifications des cases chiffres
            this.ajouterChiffres(grilleJson.getAjoutChiffres());
            this.ajouterAretesGrilleResolue(grilleJson.getAretesGrilleResolue());
            
            this.charger();

            this.timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                this.tempsSauvegarde.incrementerTemps();
                sceneJeu.setTemps(this.tempsSauvegarde.getTemps());
                this.sauvegarderTemps();
            }));
            this.timer.setCycleCount(Timeline.INDEFINITE);

            Collections.addAll(techniques, new TechniqueAutour0(this), new Technique0Adjacent3(this), new Technique0Diagonal3(this), new TechniqueDeux3Adjacent(this), new TechniqueDeux3Diagonal(this), new TechniqueNombreCoin(this), new TechniqueContraintes3(this), new TechniqueBoucleSur3(this), new TechniqueBoucleSur1(this), new TechniqueAvancee6(this),new TechniqueAvancee2(this), new TechniqueAvancee5(this));  

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
        }
    }

    public void initialiserAides(){
        this.listeAides = new boolean[12];
        this.techniques = new ArrayList<Technique>();
        int i;
        for (i=0;i<12;i++){
            listeAides[i]=false;
        }
    }

    public int nbAides(){
        int nb=0;
        int i;
        for (i=0;i<12;i++){
            if(listeAides[i]){
                nb++;
            }
        }
        return nb;
    }

    public Stack<Action> getPileUndo(){
        return this.pileUndo;
    }

    public Stack<Action> getPileRedo(){
        return this.pileRedo;
    }

    public int getNbLignes() {
        return this.nbLignes;
    }

    public int getNbColonnes() {
        return this.nbColonnes;
    }

    /**
     * Cette methode vérifie si les coordonnées passés en paramètre sont celle d'une case de la grille
     * @return boolean
     */
    public boolean caseExiste(int ligne, int colonne) {
        // System.out.println("nb ligne" + this.nbLignes);
        // System.out.println("nb Colonne" + this.nbColonnes);
        // System.out.println("ligne" + ligne);
        // System.out.println("colonne" + colonne);
    
        return (ligne >= 0 && ligne < this.nbLignes && colonne >= 0 && colonne < this.nbColonnes);
    }

    

    public Case getCase(int ligne, int colonne) throws ArrayIndexOutOfBoundsException {
        return this.cases[ligne][colonne];
    }

    public void setChiffre(int ligne, int colonne, int chiffre) {
        Chiffre c = (Chiffre) this.getCase(ligne*2+1, colonne*2+1);
        c.setChiffre(chiffre);
    }

    public void ajouterChiffres(List<AjoutChiffre> ajoutChiffres) {
        for (AjoutChiffre modif : ajoutChiffres) {
            int ligne = modif.getLigne();
            int colonne = modif.getColonne();
            int chiffre = modif.getChiffre();
            this.setChiffre(ligne, colonne, chiffre);
        }
    }

    public void ajouterAretesGrilleResolue (List<Coordonnee> aretesGrilleResolue) {
        Iterator<Arete> itAretes = this.iteratorAretes();
        while(itAretes.hasNext()){
            Arete a = itAretes.next();
            for (Coordonnee coordonnee : aretesGrilleResolue) {
                if (coordonnee.getLigne() == a.getLigne() && coordonnee.getColonne() == a.getColonne()) {
                    a.devientUneAreteDeLaGrilleResolue();
                    break;
                }
            }
        }
    }

    // Cette méthode vérifie si la grille est résolue
    public boolean resolue(){
        Iterator<Arete> itAretes = this.iteratorAretes();
        while(itAretes.hasNext()){      
            Arete a = itAretes.next();
            if (a.estUneAreteDeLaGrilleResolue() && a.getEtat() != EnumEtat.TRAIT ||
                !a.estUneAreteDeLaGrilleResolue() && a.getEtat() == EnumEtat.TRAIT) {
                return false;
            }
        }
        return true;
    }

    // Cette méthode compte le nombre d'erreurs dans la grille
    public int check(){
        int nbErreurs = 0;
        Iterator<Arete> itAretes = this.iteratorAretes();
        while(itAretes.hasNext()){      
            Arete a = itAretes.next();
            if (!a.estUneAreteDeLaGrilleResolue() && a.getEtat() == EnumEtat.TRAIT) {
                nbErreurs++;
            }
        }
        return nbErreurs;
    }

    public String toString(){
        String chaine = "";
        for(Case[] x : this.cases){
            for(Case y : x){
                chaine += y;
            }
            chaine += "\n";
        }
        return chaine;
    }

    // voici le code d'un iterateur pour parcourir les chiffres de la grille :
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

    public void undo(){
        if(!this.pileUndo.isEmpty()){
            System.out.println("undo");
            Action action = this.pileUndo.pop();
            Arete a = (Arete) this.getCase(action.getLigne(), action.getColonne());
            a.setEtat(action.getEtatPrecedent());
            this.pileRedo.push(action);
        }
    }

    public void redo(){
        if(!this.pileRedo.isEmpty()){
            System.out.println("redo");
            Action action = this.pileRedo.pop();
            Arete a = (Arete) this.getCase(action.getLigne(), action.getColonne());
            a.setEtat(action.getEtat());
            this.pileUndo.push(action);
        }
    }

    public void clear(){
        Iterator<Arete> itAretes = this.iteratorAretes();
        while(itAretes.hasNext()){
            Arete a = itAretes.next();
            a.setEtat(EnumEtat.VIDE);
        }
        this.pileUndo.clear();
        this.pileRedo.clear();
        this.sauvegarderProgression();
    }

    private void chargerProgression(){
        try {
            File fichierProgression = new File(getSauvegardePath("progress"));
            this.pileUndo = new Stack<Action>();
            this.pileRedo = new Stack<Action>();
            if (!fichierProgression.exists()) {
                return;
            }
            this.pileUndo = OBJECT_MAPPER.readValue(fichierProgression, new TypeReference<Stack<Action>>(){});
            for(Action action : this.pileUndo){
                Arete a = (Arete) this.getCase(action.getLigne(), action.getColonne());
                a.setEtat(action.getEtat());
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du fichier JSON de progression : " + e.getMessage());
        }
    }

    private void chargerTemps(){
        try{
            File fichierTemps = new File(getSauvegardePath("time"));
            if (!fichierTemps.exists()) {
                this.tempsSauvegarde = new TempsSauvegarde(0,0);
                return;
            }
            tempsSauvegarde = OBJECT_MAPPER.readValue(fichierTemps, TempsSauvegarde.class);
            sceneJeu.setTemps(tempsSauvegarde.getTemps());
        } catch (Exception e){
            System.out.println("Erreur lors de la lecture du fichier JSON de temps : " + e.getMessage());
        }
    }

    public void charger(){
        this.chargerProgression();
        this.chargerTemps();
        this.chargerAides();
    }

    public void sauvegarderProgression(){
        try{
            File fichierProgression = new File(getSauvegardePath("progress"));
            OBJECT_MAPPER.writeValue(fichierProgression, this.pileUndo);
        }
        catch (Exception e){
            System.out.println("Erreur lors de la sauvegarde du fichier JSON : " + e.getMessage());
        }
    }

    public void sauvegarderTemps(){
        try{
            File fichierTemps = new File(getSauvegardePath("time"));
            OBJECT_MAPPER.writeValue(fichierTemps, this.tempsSauvegarde);
        }
        catch (Exception e){
            System.out.println("Erreur lors de la sauvegarde du fichier JSON : " + e.getMessage());
        }
    }

    public void updateMeilleurTemps(){
        if(tempsSauvegarde.getTemps() < tempsSauvegarde.getMeilleurTemps() || tempsSauvegarde.getMeilleurTemps() == 0){
            tempsSauvegarde.setMeilleurTemps(tempsSauvegarde.getTemps());
        }
    }

    public String getResourceFilePath(String fichier){
        return this.getClass().getClassLoader().getResource("grilles/"+fichier).getPath();
    }

    public String getSauvegardePath(String type){
        Path userDir = Paths.get(System.getProperty("user.home"), ".slitherlinkGroup2");
        // verifier que le dossier existe, sinon le créer
        if(!Files.exists(userDir)){
            try {
                Files.createDirectory(userDir);
            } catch (IOException e) {
                System.out.println("Erreur lors de la création du dossier de sauvegarde : " + e.getMessage());
            }
        }
        Path filePath = userDir.resolve(this.name + "_" + type + ".json");
        return filePath.toString();
    }

    public static String getSettingsPath(){
        Path userDir = Paths.get(System.getProperty("user.home"), ".slitherlinkGroup2");
        // verifier que le dossier existe, sinon le créer
        if(!Files.exists(userDir)){
            try {
                Files.createDirectory(userDir);
            } catch (IOException e) {
                System.out.println("Erreur lors de la création du dossier de sauvegarde : " + e.getMessage());
            }
        }
        Path filePath = userDir.resolve("settings.json");
        return filePath.toString();
    } 

    public void retablirEtatValide(){
        while(this.check() > 0){
            this.undo();
        }
    }


   

    public void runTimer() {
        timer.play();
    }


    public void stopTimer() {
        timer.stop();
    }


    public void resetTimer() {
        this.tempsSauvegarde.setTemps(0);
        if (sceneJeu != null) sceneJeu.setTemps(0);
    }


    public void setSceneJeu(SceneJeu sceneJeu) {
        this.sceneJeu = sceneJeu;
    }

    private void chargerAides(){
        try{
            File fichierAides = new File(getSauvegardePath("aides"));
            if (!fichierAides.exists()) {
                this.initialiserAides();
                return;
            }

            this.listeAides = OBJECT_MAPPER.readValue(fichierAides, boolean[].class);

        } catch (Exception e ) {
            System.out.println("Erreur lors du chargement des aides");
            this.initialiserAides();
        }
    }

    public void sauvegarderAides(){
        try{
            File fichierAides = new File(getSauvegardePath("aides"));
            OBJECT_MAPPER.writeValue(fichierAides, this.listeAides);
        } catch (Exception e){
            System.out.println("Erreur lors de la sauvegarde des aides");
        }
    }

    public boolean[] getListeAides(){
        return this.listeAides;
    }


    public void aide() {
        int i=1;
        for(Technique t : techniques) {
            if(t.applicable()) {
                BoxFactory.showTechnique(i,  sceneJeu.getPrimaryStage(),t);
                if (!listeAides[i-1]){
                    listeAides[i-1]=true;
                    this.sauvegarderAides();
                    sceneJeu.setRightBox(BoxFactory.createHelpButtonBox(listeAides, sceneJeu.getPrimaryStage(), this));
                }
                break;
            }
            i++;
        }
    }


    public void activerTatonnement(){
        modeTatonnement = true;
    }

    public boolean enModeTatonnement(){
        return modeTatonnement;
    }

    public void annulerTatonnement(){
        modeTatonnement = false;
    }

    public void validerTatonnement(){
        modeTatonnement = false;
    }
}



