package com.menu;

public class TempsSauvegarde {
    private int temps;
    private int meilleurTemps;

    public TempsSauvegarde(int temps, int meilleurTemps){
        this.temps = temps;
        this.meilleurTemps = meilleurTemps;
    }

    public TempsSauvegarde(){}

    public int getTemps() {
        return temps;
    }

    public int getMeilleurTemps() {
        return meilleurTemps;
    }

    public void incrementerTemps(){
        temps++;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public void setMeilleurTemps(int meilleurTemps) {
        this.meilleurTemps = meilleurTemps;
    }
}
