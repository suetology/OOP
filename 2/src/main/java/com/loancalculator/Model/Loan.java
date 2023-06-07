package com.loancalculator.Model;

public class Loan {
    public enum GrazinimoGrafikas {
        Null,
        Anuiteto,
        Linijinis
    }

    private float paskolosSuma;
    private int terminasMen;
    private int atidejimasMen;
    private GrazinimoGrafikas grazinimoGrafikas;
    private float paskolosProcentas;
    private float atidejimoProcentas;

    public float getPaskolosSuma() {
        return paskolosSuma;
    }

    public void setPaskolosSuma(float paskolosSuma) {
        this.paskolosSuma = paskolosSuma;
    }

    public int getTerminasMen() {
        return terminasMen;
    }

    public void setTerminasMen(int terminasMen) {
        this.terminasMen = terminasMen;
    }

    public int getAtidejimasMen() {
        return atidejimasMen;
    }

    public void setAtidejimasMen(int atidejimasMen) {
        this.atidejimasMen = atidejimasMen;
    }

    public GrazinimoGrafikas getGrazinimoGrafikas() {
        return grazinimoGrafikas;
    }

    public void setGrazinimoGrafikas(GrazinimoGrafikas grazinimoGrafikas) {
        this.grazinimoGrafikas = grazinimoGrafikas;
    }

    public float getPaskolosProcentas() {
        return paskolosProcentas;
    }

    public void setPaskolosProcentas(float paskolosProcentas) {
        this.paskolosProcentas = paskolosProcentas;
    }

    public float getAtidejimoProcentas() {
        return atidejimoProcentas;
    }

    public void setAtidejimoProcentas(float atidejimoProcentas) {
        this.atidejimoProcentas = atidejimoProcentas;
    }
}
