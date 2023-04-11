package com.example.myapplication;

public class appel {
    private  int v ;
    private int id;
    private String datex;


    private String nom;
    private String prenom;
    private String number;


    public appel(int id, String datex, String nom, String number,String prenom,int v){
        this.nom = nom;
        this.prenom = prenom;
        this.number = number;
         this.datex = datex;
         this.id =id ;
         this.v =v;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatex() {
        return datex;
    }

    public void setDatex(String datex) {
        this.datex = datex;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


};
