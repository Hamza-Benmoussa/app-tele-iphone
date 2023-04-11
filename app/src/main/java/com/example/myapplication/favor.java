package com.example.myapplication;

public class favor {
    private int idf;
    private String nomf;
    private String prenomf;

    public favor(int idf, String nomf, String prenomf) {
        this.idf = idf;
        this.nomf = nomf;
        this.prenomf = prenomf;
    }

    public int getIdf() {
        return idf;
    }

    public void setIdf(int idf) {
        this.idf = idf;
    }

    public String getNomf() {
        return nomf;
    }

    public void setNomf(String nomf) {
        this.nomf = nomf;
    }

    public String getPrenomf() {
        return prenomf;
    }

    public void setPrenomf(String prenomf) {
        this.prenomf = prenomf;
    }
}