package com.example.myapplication;

public class listcontact {
    public String nom;
    public String prenom;
    public String tele;
    public String email;
    public String adrss;



    public  String numc ;
    public byte[] image;
    public int id;

    public listcontact(String nom, String prenom, String tele, String email, String adrss, byte[] image, int id,String numx) {
        this.nom = nom;
        this.prenom = prenom;
        this.tele = tele;
        this.email = email;
        this.adrss = adrss;
        this.image = image;
        this.id = id;
        this.numc =numx ;
    }
}
