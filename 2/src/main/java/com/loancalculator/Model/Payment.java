package com.loancalculator.Model;

public class Payment {
    private final String data;
    private final String mokejimas;
    private final String procentoImoka;
    private final String paskolosKunas;
    private final String paskolosLikutis;
    private final String sumoketa;

    public Payment(String date, String paymentAmount, String percent, String loanBody, String loanRemainder, String sumoketa) {
        this.data = date;
        this.mokejimas = paymentAmount;
        this.procentoImoka = percent;
        this.paskolosKunas = loanBody;
        this.paskolosLikutis = loanRemainder;
        this.sumoketa = sumoketa;
    }

    public String getData() {
        return data;
    }

    public String getMokejimas() {
        return mokejimas;
    }

    public String getProcentoImoka() {
        return procentoImoka;
    }

    public String getPaskolosKunas() {
        return paskolosKunas;
    }

    public String getPaskolosLikutis() {
        return paskolosLikutis;
    }

    public String getSumoketa() {
        return sumoketa;
    }
}
